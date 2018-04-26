package com.vension.mvp.ui.activitys.headline

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import butterknife.OnClick
import cn.jzvd.JZMediaManager
import cn.jzvd.JZVideoPlayerManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.vension.frame.utils.VEmptyUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseActivity
import com.vension.mvp.beans.headline.CommentData
import com.vension.mvp.beans.headline.CommentResponse
import com.vension.mvp.beans.headline.DetailCloseEvent
import com.vension.mvp.mvp.headline.contract.NewsDetailContract
import com.vension.mvp.mvp.headline.presenter.NewsDetailPresenter
import com.vension.mvp.ui.adapters.headline.CommentAdapter
import com.vension.mvp.ui.fragments.headline.NewsDetailHeaderView
import com.vension.mvp.utils.Constants
import com.vension.mvp.widget.powerfulrecyclerview.PowerfulRecyclerView
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * @author ：Created by Administrator on 2018/4/23 15:19.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
abstract class NewsDetailBaseActivity : BaseActivity() , NewsDetailContract.View, BaseQuickAdapter.RequestLoadMoreListener{

    val CHANNEL_CODE = "channelCode"
    val PROGRESS = "progress"
    val POSITION = "position"
    val DETAIL_URL = "detailUrl"
    val GROUP_ID = "groupId"
    val ITEM_ID = "itemId"

    protected var mRvComment: PowerfulRecyclerView?= null
    protected var mTvCommentCount: TextView?= null

    private val mCommentList = ArrayList<CommentData>()
    private var mCommentAdapter: CommentAdapter? = null
    protected var mHeaderView: NewsDetailHeaderView? = null
    private var mDetalUrl: String? = null
    private var mGroupId: String? = null
    private var mItemId: String? = null
    protected var mCommentResponse: CommentResponse? = null

    protected var mChannelCode: String = ""
    protected var mPosition: Int = 0

    private val mPresenter by lazy { NewsDetailPresenter() }

    override fun attachLayoutRes(): Int {
        return getViewContentViewId()
    }

    protected abstract fun getViewContentViewId(): Int


    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        val intent = intent
        mPresenter.attachView(this)

        mChannelCode = intent.getStringExtra(CHANNEL_CODE)
        mPosition = intent.getIntExtra(POSITION, 0)

        mDetalUrl = intent.getStringExtra(DETAIL_URL)
        mGroupId = intent.getStringExtra(GROUP_ID)
        mItemId = intent.getStringExtra(ITEM_ID)
        mItemId = mItemId?.replace("i", "")

        mPresenter.getNewsDetail(mDetalUrl!!)

        mCommentAdapter = CommentAdapter(this, R.layout.item_comment, mCommentList)
        mRvComment?.adapter = mCommentAdapter

        mHeaderView = NewsDetailHeaderView(this)
        mCommentAdapter?.addHeaderView(mHeaderView)

        mCommentAdapter?.setEnableLoadMore(true)
        mCommentAdapter?.setOnLoadMoreListener(this, mRvComment)

        mCommentAdapter?.setEmptyView(R.layout.pager_no_comment,mRvComment)
        mCommentAdapter?.setHeaderAndEmpty(true)

    }


    override fun requestLoadData() {
        loadCommentData()
    }

    private fun loadCommentData() {
        mLayoutStatusView?.showLoading()
        mPresenter.getComment(mGroupId!!, mItemId!!, 1)
    }


    override fun onLoadMoreRequested() {
        mPresenter.getComment(mGroupId!!, mItemId!!, mCommentList.size / Constants.COMMENT_PAGE_SIZE + 1)
    }

    override fun onGetCommentSuccess(response: CommentResponse) {
        mCommentResponse = response

        if (VEmptyUtil.isEmpty(response.data)) {
            //没有评论了
            mCommentAdapter?.loadMoreEnd()
            return
        }

        if (response.total_number > 0) {
            //如果评论数大于0，显示红点
            mTvCommentCount?.visibility = View.VISIBLE
            mTvCommentCount?.text = response.total_number.toString()
        }

        mCommentList.addAll(response.data)
        mCommentAdapter?.notifyDataSetChanged()

        if (!response.has_more) {
            mCommentAdapter?.loadMoreEnd()
        } else {
            mCommentAdapter?.loadMoreComplete()
        }
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        mLayoutStatusView?.showError()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }


    @OnClick(R.id.fl_comment_icon)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.fl_comment_icon -> {
                //底部评论的图标
                val layoutManager = mRvComment?.getLayoutManager()
                if (layoutManager is LinearLayoutManager) {
                    val firstPosition = layoutManager.findFirstVisibleItemPosition()
                    val last = layoutManager.findLastVisibleItemPosition()
                    if (firstPosition == 0 && last == 0) {
                        //处于头部，滚动到第一个条目
                        mRvComment?.scrollToPosition(1)
                    } else {
                        //不是头部，滚动到头部
                        mRvComment?.scrollToPosition(0)
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter.detachView()
        }
    }
    /**
     * 发送事件，用于更新上个页面的播放进度以及评论数
     */
    protected fun postVideoEvent(isVideoDetail: Boolean) {
        val event = DetailCloseEvent()
        event.channelCode = mChannelCode
        event.position = mPosition

        if (mCommentResponse != null) {
            event.commentCount = mCommentResponse!!.total_number
        }

        if (isVideoDetail && JZMediaManager.instance().jzMediaInterface != null && JZVideoPlayerManager.getCurrentJzvd() != null) {
            //如果是视频详情
            val progress = JZMediaManager.instance().positionInList
            event.progress = progress
        }
        EventBus.getDefault().postSticky(event)
        finish()
    }

}
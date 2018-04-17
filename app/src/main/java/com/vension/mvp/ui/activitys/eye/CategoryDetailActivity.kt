package com.vension.mvp.ui.activitys.eye

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kevin.vension.demo.utils.statubar.StatusBarUtil
import com.sunfusheng.glideimageview.progress.GlideApp
import com.vension.frame.utils.VObsoleteUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseActivity
import com.vension.mvp.beans.eyes.CategoryBean
import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.mvp.eye.contract.CategoryDetailContract
import com.vension.mvp.mvp.eye.presenter.CategoryDetailPresenter
import com.vension.mvp.showToast
import com.vension.mvp.ui.adapters.eye.RecyCategoryDetailAdapter
import com.vension.mvp.utils.Constants
import kotlinx.android.synthetic.main.activity_eye_category_detail.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/13 10:32
 * 描  述：
 * ========================================================
 */

class CategoryDetailActivity : BaseActivity(), CategoryDetailContract.View,
SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private val mPresenter by lazy { CategoryDetailPresenter() }

    private val mAdapter by lazy { RecyCategoryDetailAdapter() }

    private var categoryData: CategoryBean? = null

    init {
        mPresenter.attachView(this)
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int = R.layout.activity_eye_category_detail

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        categoryData = intent?.getSerializableExtra(Constants.BUNDLE_CATEGORY_DATA) as CategoryBean?
        mLayoutStatusView = multipleStatusView
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        //初始化刷新控件
        SwipeRefreshLayout_category_detail.setColorSchemeColors(VObsoleteUtil.getColor(R.color.app_main_thme_color))
        //设置刷新控件监听
        SwipeRefreshLayout_category_detail.setOnRefreshListener(this)

        // 加载headerImage
        GlideApp.with(this)
                .load(categoryData?.headerImage)
                .placeholder(R.color.placeholder_color)
                .into(imageView)

        tv_category_desc.text ="#${categoryData?.description}#"

        collapsing_toolbar_layout.title = categoryData?.name
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE) //设置还没收缩时状态下字体颜色
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK) //设置收缩后Toolbar上字体的颜色

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter?.let {
            it.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
            it.setOnLoadMoreListener(this, mRecyclerView)
            mRecyclerView.adapter = it
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            //跳转到视频详情页面播放
            mAdapter?.setOnItemClickListener { adapter, view, position ->
                val item : HomeBean.Issue.Item = mAdapter?.getItem(position) as HomeBean.Issue.Item
                goToVideoPlayer(view,item)
            }
        }

        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
    }

    override fun requestLoadData() {
        SwipeRefreshLayout_category_detail.isRefreshing = true
        SwipeRefreshLayout_category_detail.postDelayed( {
            onRefresh()
        },300)
    }


    override fun onRefresh() {
        //获取当前分类列表
        if (mAdapter != null) {
            mAdapter?.setEnableLoadMore(false)//这里的作用是防止下拉刷新的时候还可以上拉加载
        }
        mPresenter.getCategoryDetailList(categoryData?.id!!)
    }

    override fun onLoadMoreRequested() {
        SwipeRefreshLayout_category_detail?.isEnabled = false //加载更多时不能同时下拉刷新
        mPresenter.loadMoreData()
    }

    override fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>) {
        mAdapter?.setEnableLoadMore(true)
        //添加内容
        if (itemList.isNotEmpty()) {
            mAdapter?.setNewData(itemList)
        } else {
            mLayoutStatusView?.showEmpty()
        }
        if (SwipeRefreshLayout_category_detail.isRefreshing) {
            SwipeRefreshLayout_category_detail.isRefreshing = false
        }
    }

    override fun setMoreCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>) {
        SwipeRefreshLayout_category_detail.isEnabled = true //加载更多时不能同时下拉刷新
        if (itemList.isNotEmpty()) {
            mAdapter?.addData(itemList)
            mAdapter?.loadMoreComplete()//加载更多完成
        }else{
            showToast("没有更多了")
            mAdapter?.loadMoreEnd()//没有更多了
        }
        if (SwipeRefreshLayout_category_detail.isRefreshing) {
            SwipeRefreshLayout_category_detail.isRefreshing = false
        }
    }

    override fun showError(errorMsg: String) {
        mLayoutStatusView?.showError()
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(this, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.Companion.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair<View, String>(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair)
            ActivityCompat.startActivity(this, intent, activityOptions.toBundle())
        } else {
            startActivity(intent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }

}
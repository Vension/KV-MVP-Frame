package com.vension.mvp.ui.fragments.eye

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.kevin.vension.demo.utils.statubar.StatusBarUtil
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import com.vension.frame.utils.VObsoleteUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.http.exception.ErrorStatus
import com.vension.mvp.mvp.eye.contract.HomeContract
import com.vension.mvp.mvp.eye.presenter.HomePresenter
import com.vension.mvp.showToast
import com.vension.mvp.ui.activitys.eye.SearchActivity
import com.vension.mvp.ui.adapters.eye.RecyHomeAdapter
import kotlinx.android.synthetic.main.fragment_eye_home.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 17:45
 * 描  述：开眼·精选
 * ========================================================
 */

class HomeFragment : BaseFragment(),HomeContract.View{

    private val mPresenter by lazy { HomePresenter() }

    private var mTitle: String? = null

    private var num: Int = 1

    private var mHomeAdapter: RecyHomeAdapter? = null

    private var loadingMore = false

    private var isRefresh = false
    private var mMaterialHeader: MaterialHeader?=null

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }


    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_eye_home
    }


    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        mPresenter.attachView(this)
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestHomeData(num)
        }
        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader?
        //打开下拉刷新区域块背景:
        mMaterialHeader?.setShowBezierWave(true)
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.white, R.color.app_main_backgroup_color)


        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = mRecyclerView.childCount
                    val itemCount = mRecyclerView.layoutManager.itemCount
                    val firstVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        if (!loadingMore) {
                            loadingMore = true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }

            //RecyclerView滚动的时候调用
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    toolbar.setBackgroundColor(VObsoleteUtil.getColor(R.color.transparent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (mHomeAdapter?.mData!!.size > 1) {
                        toolbar.setBackgroundColor(VObsoleteUtil.getColor(R.color.app_main_thme_color))
                        iv_search.setImageResource(R.mipmap.ic_action_search_white)
                        val itemList = mHomeAdapter!!.mData
                        val item = itemList[currentVisibleItemPosition + mHomeAdapter!!.bannerItemSize - 1]
                        if (item.type == "textHeader") {
                            tv_header_title.text = item.data?.text
                        } else {
                            tv_header_title.text = simpleDateFormat.format(item.data?.date)
                        }
                    }
                }


            }
        })

        iv_search.setOnClickListener { openSearchActivity() }

        mLayoutStatusView = multipleStatusView

        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity!!)
        StatusBarUtil.setPaddingSmart(activity!!, toolbar)
    }


    private fun openSearchActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, iv_search, iv_search.transitionName)
            startActivity(Intent(activity, SearchActivity::class.java), options.toBundle())
        } else {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }


    override fun lazyLoadData() {
        mPresenter.requestHomeData(num)
    }

    /**
     * 显示 Loading （下拉刷新的时候不需要显示 Loading）
     */
    override fun showLoading() {
        if (!isRefresh) {
            isRefresh = false
            mLayoutStatusView?.showLoading()
        }
    }

    /**
     * 隐藏 Loading
     */
    override fun dismissLoading() {
        mRefreshLayout.finishRefresh()
        mLayoutStatusView?.showContent()
    }


    /**
     * 设置首页数据
     */
    override fun setHomeData(homeBean: HomeBean) {
        Logger.d(homeBean)

        // Adapter
        mHomeAdapter = RecyHomeAdapter(activity!!, homeBean.issueList[0].itemList)
        //设置 banner 大小
        mHomeAdapter?.setBannerSize(homeBean.issueList[0].count)

        mRecyclerView.adapter = mHomeAdapter
        mRecyclerView.layoutManager = linearLayoutManager

    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mHomeAdapter?.addItemData(itemList)
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


}
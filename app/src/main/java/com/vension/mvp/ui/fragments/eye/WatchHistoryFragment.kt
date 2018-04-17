package com.vension.mvp.ui.fragments.eye

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.frame.VFrame
import com.vension.mvp.R
import com.vension.mvp.base.BaseRefreshFragment
import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.ui.activitys.eye.VideoDetailActivity
import com.vension.mvp.ui.adapters.eye.RecyWatchHistoryAdapter
import com.vension.mvp.utils.Constants
import com.vension.mvp.utils.WatchHistoryUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import java.util.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 10:25
 * 描  述：观看记录
 * ========================================================
 */

class WatchHistoryFragment : BaseRefreshFragment<HomeBean.Issue.Item>() {

    companion object {
        private val HISTORY_MAX = 20
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        mCommonTitleBar.centerTextView.text = "观看记录"
    }

    override fun hasLoadMore(): Boolean {
        return false
    }

    override fun initRefreshFragment() {
    }

    override fun createCommonRecyAdapter(): BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {
    return RecyWatchHistoryAdapter()
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>) {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as HomeBean.Issue.Item
            goToVideoPlayer(this!!.activity!!,view,item)
        }
    }

    override fun onTargerRequestApi(isRefreshing: Boolean, page: Int, limit: Int) {
        addItemData(true,queryWatchHistory())
    }


    /**
     * 查询观看的历史记录
     */
    private fun queryWatchHistory():ArrayList<HomeBean.Issue.Item> {
        val watchList = ArrayList<HomeBean.Issue.Item>()
        val hisAll = WatchHistoryUtils.getAll(Constants.FILE_WATCH_HISTORY_NAME,VFrame.getContext()) as Map<*, *>
        //将key排序升序
        val keys = hisAll.keys.toTypedArray()
        Arrays.sort(keys)
        val keyLength = keys.size
        //这里计算 如果历史记录条数是大于 可以显示的最大条数，则用最大条数做循环条件，防止历史记录条数-最大条数为负值，数组越界
        val hisLength = if (keyLength > HISTORY_MAX) HISTORY_MAX else keyLength
        // 反序列化和遍历 添加观看的历史记录
        (1..hisLength).mapTo(watchList) {
            WatchHistoryUtils.getObject(Constants.FILE_WATCH_HISTORY_NAME,VFrame.getContext(),
                    keys[keyLength - it] as String) as HomeBean.Issue.Item
        }

        return watchList
    }



    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.Companion.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair<View, String>(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }

}
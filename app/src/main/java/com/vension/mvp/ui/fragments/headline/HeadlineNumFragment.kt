package com.vension.mvp.ui.fragments.headline

import android.content.Context
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.frame.base.V_BasePresenter
import com.vension.frame.base.V_IBaseView
import com.vension.frame.dialogs.VAlertDialog
import com.vension.frame.utils.VObsoleteUtil
import com.vension.frame.utils.VToastUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseRefreshMVPFragment
import com.vension.mvp.beans.headline.MediaChannelBean
import com.vension.mvp.db.sqlite.dao.MediaChannelDao
import com.vension.mvp.ui.adapters.headline.RecyMediaAdapter
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers



/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/3 13:25
 * 描  述：头条号
 * ========================================================
 */

class HeadlineNumFragment : BaseRefreshMVPFragment<MediaChannelBean, V_BasePresenter<V_IBaseView>>(), V_IBaseView {

    private val TAG = "MediaChannelView"
    private val isFirstTime = "isFirstTime"
    private val dao by lazy{ MediaChannelDao() }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setBackgroundColor(VObsoleteUtil.getColor(R.color.color_new_main))
        mCommonTitleBar.leftImageButton.visibility = View.GONE
        mCommonTitleBar.centerTextView.text = "头条号"
        mCommonTitleBar.centerTextView.setTextColor(VObsoleteUtil.getColor(R.color.white))
    }


    override fun initRefreshFragment() {
    }

    override fun createPresenter(): V_BasePresenter<V_IBaseView> {
        return V_BasePresenter()
    }

    override fun createCommonRecyAdapter(): BaseQuickAdapter<MediaChannelBean, BaseViewHolder> {
        return RecyMediaAdapter()
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<MediaChannelBean, BaseViewHolder>) {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item : MediaChannelBean = mAdapter?.getItem(position) as MediaChannelBean
            mBundle?.putString("mediaId",item.id)
//            startAgentActivity()
            VToastUtil.showSuccess("进入头条号")
        }

        mAdapter.onItemLongClickListener = BaseQuickAdapter.OnItemLongClickListener { adapter, view, position ->
            val item2 : MediaChannelBean = mAdapter?.getItem(position) as MediaChannelBean
            VAlertDialog(activity).builder()
                    .setTitle("取消订阅")
                    .setContent("确定取消订阅\" " + item2.name + " \"?")
                    .setPositiveButton("确定", VObsoleteUtil.getColor(R.color.Color_ff9734), {
                        Thread {
                            dao.delete(item2.id)
                            setData()
                        }.start()
                    })
                    .setNegativeButton("取消", {})
                    .show()
            true
        }
    }

    override fun onTargerRequestApi(isRefreshing: Boolean, page: Int, limit: Int) {
        val editor = activity?.getSharedPreferences(TAG, Context.MODE_PRIVATE)
        val result = editor?.getBoolean(isFirstTime, true)
        if (result!!) {
            dao.initData()
            editor.edit().putBoolean(isFirstTime, false).apply()
        }
        addItemData(true, dao.queryAll())
    }


    private fun setData() {
        Flowable.create(FlowableOnSubscribe<List<MediaChannelBean>> { emitter ->
            //执行一些其他操作
            val list: List<MediaChannelBean> = dao.queryAll()
            //执行完毕，触发回调，通知观察者
            emitter.onNext(list)
            emitter.onComplete()
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    addItemData(true, list)
                })
    }


    override fun hasLoadMore(): Boolean {
        return false
    }

}
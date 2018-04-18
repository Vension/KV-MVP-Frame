package com.vension.mvp.base

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import butterknife.ButterKnife
import com.vension.frame.base.V_BaseFragment
import com.vension.mvp.utils.AppCompat
import com.wuhenzhizao.titlebar.widget.CommonTitleBar

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 17:05
 * 描  述：
 * ========================================================
 */

abstract class BaseFragment : V_BaseFragment(), View.OnClickListener{

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setListener {_: View?, action: Int, extra: String? ->
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON || action == CommonTitleBar.ACTION_LEFT_TEXT) {
                activity?.onBackPressed()
            }
        }
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        //绑定view
        rootView?.let { ButterKnife.bind(this,it) }
    }

    override fun startAgentActivity(_Class: Class<*>) {
        startActivity(AppCompat.createAgentIntent(activity, _Class))
    }

    override fun startAgentActivity(_Class: Class<*>, bundle: Bundle) {
        startActivity(AppCompat.createAgentIntent(activity, _Class, bundle))
    }

    override fun startAgentActivity(_Class: Class<*>, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivity(activity!!, AppCompat.createAgentIntent(activity, _Class), _ActivityOptionsCompat.toBundle())
    }

    override fun startAgentActivity(_Class: Class<*>, bundle: Bundle, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivity(activity!!, AppCompat.createAgentIntent(activity, _Class, bundle), _ActivityOptionsCompat.toBundle())
    }

    override fun startAgentActivityForResult(_Class: Class<*>, requestCode: Int) {
        startActivityForResult(AppCompat.createAgentIntent(activity, _Class), requestCode)
    }


    override fun startAgentActivityForResult(_Class: Class<*>, bundle: Bundle, requestCode: Int) {
        startActivityForResult(AppCompat.createAgentIntent(activity, _Class, bundle), requestCode)
    }

    override fun startAgentActivityForResult(_Class: Class<*>, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivityForResult(activity!!, AppCompat.createAgentIntent(activity, _Class), requestCode, _ActivityOptionsCompat.toBundle())
    }

    override fun startAgentActivityForResult(_Class: Class<*>, bundle: Bundle, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat) {
        ActivityCompat.startActivityForResult(activity!!, AppCompat.createAgentIntent(activity, _Class, bundle), requestCode, _ActivityOptionsCompat.toBundle())
    }

    override fun onClick(view: View?) {
        //TODO 点击事件监听
    }
}
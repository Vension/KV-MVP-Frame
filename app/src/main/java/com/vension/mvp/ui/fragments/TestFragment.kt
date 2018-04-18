package com.vension.mvp.ui.fragments

import android.os.Bundle
import com.vension.frame.utils.VLogUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_test.*

/**
 * @author ：Created by Administrator on 2018/4/4 10:54.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class TestFragment : BaseFragment() {

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): TestFragment {
            val fragment = TestFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }


    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        mCommonTitleBar.centerTextView.text = mTitle
    }


    override fun attachLayoutRes(): Int {
        return R.layout.fragment_test
    }


    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        VLogUtil.e("TestFragment == $mTitle")
        tv_content.text = mTitle
    }

    override fun lazyLoadData() {
    }

}
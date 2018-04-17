package com.vension.mvp.ui.adapters.eye

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayoutManager
import com.kevin.vension.demo.adapters.recy.CommonRecyAdapter
import com.kevin.vension.demo.adapters.recy.ViewHolder
import com.vension.mvp.R

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/17 12:03
 * 描  述：Tag 标签布局的 Adapter
 * ========================================================
 */

class HotKeywordsAdapter(mContext: Context,mList: ArrayList<String>, layoutId: Int) :
        CommonRecyAdapter<String>(mContext, mList, layoutId){

    /**
     * Kotlin的函数可以作为参数，写callback的时候，可以不用interface了
     */

    private var mOnTagItemClick: ((tag:String) -> Unit)? = null

    fun setOnTagItemClickListener(onTagItemClickListener:(tag:String) -> Unit) {
        this.mOnTagItemClick = onTagItemClickListener
    }

    override fun bindData(holder: ViewHolder, data: String, position: Int) {

        holder.setText(R.id.tv_title,data)

        val params = holder.getView<TextView>(R.id.tv_title).layoutParams
        if(params is FlexboxLayoutManager.LayoutParams){
            params.flexGrow = 1.0f
        }

        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                mOnTagItemClick?.invoke(data)
            }

        }

        )

    }


}
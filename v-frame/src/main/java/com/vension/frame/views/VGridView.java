package com.vension.frame.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/18 18:03
 * 描  述：Gridview嵌套在SrcollView中会出现显示不全的情况
 *        这个类通过设置不滚动来避免
 * ========================================================
 */

public class VGridView extends GridView {

	public VGridView(Context context) {
		super(context);
	}

	public VGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}

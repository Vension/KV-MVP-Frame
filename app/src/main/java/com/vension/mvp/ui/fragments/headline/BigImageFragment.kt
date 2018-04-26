package com.vension.mvp.ui.fragments.headline

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.github.chrisbanes.photoview.OnPhotoTapListener
import com.sunfusheng.glideimageview.GlideImageLoader
import com.sunfusheng.glideimageview.util.DisplayUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.vension.mvp.utils.UIUtils
import kotlinx.android.synthetic.main.fragment_big_image.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/23 17:46
 * 描  述：展示大图的fragment
 * ========================================================
 */

class BigImageFragment : BaseFragment(){

     val IMG_URL = "imgUrl"

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_big_image
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)

        val imgUrl = arguments?.getString(IMG_URL)

        val imageLoader = GlideImageLoader.create(pv_pic)
        imageLoader.setOnGlideImageViewListener(imgUrl) { percent, isDone, exception ->
            if (exception != null && !TextUtils.isEmpty(exception.message)) {
                UIUtils.showToast(getString(R.string.net_error))
            }
            progressView.progress = percent
            progressView.visibility = if (isDone) View.GONE else View.VISIBLE
        }

        val options = imageLoader.requestOptions(R.color.placeholder_color)
                .centerCrop()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)

        val requestBuilder = imageLoader.requestBuilder(imgUrl, options)
        requestBuilder.transition(DrawableTransitionOptions.withCrossFade())
                .into(object : SimpleTarget<Drawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>) {
                        if (resource.intrinsicHeight > DisplayUtil.getScreenHeight(activity)) {
                            pv_pic.scaleType = ImageView.ScaleType.CENTER_CROP
                        }
                        requestBuilder.into(pv_pic)
                    }
                })

        pv_pic.setOnPhotoTapListener(OnPhotoTapListener { view, x, y ->
            activity?.finish()
        })

    }

    override fun lazyLoadData() {

    }

}
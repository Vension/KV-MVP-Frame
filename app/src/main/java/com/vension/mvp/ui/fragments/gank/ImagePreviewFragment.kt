package com.vension.mvp.ui.fragments.gank

import android.os.Bundle
import android.view.View
import com.sunfusheng.glideimageview.GlideImageLoader
import com.vension.frame.utils.VToastUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.vension.mvp.db.realm.RealmHelper
import com.vension.mvp.db.realm.bean.RealmLikeBean
import com.vension.mvp.utils.Constants
import com.vension.mvp.utils.ImageHelper
import com.vension.mvp.utils.ViewUtil
import kotlinx.android.synthetic.main.fragment_gank_image_preview.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/9 9:45
 * 描  述：图片大图预览
 * ========================================================
 */

class ImagePreviewFragment : BaseFragment(){

    var url : String = ""
    var image_id : String = ""
    private var imageHelper: ImageHelper? = null
    private var mRealmHelper: RealmHelper? = null
    private var isLiked: Boolean = false

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_gank_image_preview
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        url = mBundle?.getString("image_url").toString()
        image_id = mBundle?.getString("image_id").toString()
        imageHelper = ImageHelper(activity)
        mRealmHelper = RealmHelper()

        setLikeState(mRealmHelper?.queryLikeId(image_id)!!)
        val imageLoader = GlideImageLoader.create(photoView)
        imageLoader.loadImage(url, R.color.transparent)
        imageLoader.setOnGlideImageViewListener(url) { percent, isDone, exception ->
            progressView.progress = percent
            progressView.visibility = if (isDone) View.GONE else View.VISIBLE
        }

        photoView.setOnPhotoTapListener({ view1, x, y -> activity?.finish() })

        //保存图片到本地
        ViewUtil.singleClick(view_save, { o ->
            imageHelper?.saveImage(url)
        })
        //收藏图片
        ViewUtil.singleClick(view_like, { o ->
            if (isLiked) {
                mRealmHelper?.deleteLikeBean(image_id)
                VToastUtil.showSuccess("取消收藏成功")
            } else {
                val bean = RealmLikeBean()
                bean.id = image_id
                bean.image = url
                bean.type = Constants.TYPE_GIRL
                bean.time = System.currentTimeMillis()
                mRealmHelper?.insertLikeBean(bean)
                VToastUtil.showSuccess("添加收藏成功")
            }
            isLiked = !isLiked
            setLikeState(isLiked)
        })
    }


    /**
     * 设置收藏按钮的状态
     */
    private fun setLikeState(state: Boolean) {
        isLiked = if (state) {
            view_like.setImageResource(R.drawable.ic_favorite_main_24dp)
            true
        } else {
            view_like.setImageResource(R.drawable.ic_favorite_white_24dp)
            false
        }
    }

    override fun lazyLoadData() {

    }


    override fun onDestroy() {
        if (imageHelper != null) {
            imageHelper?.unInit()
        }
        super.onDestroy()
    }

//    class PhotoViewAdapter(private val mActivity: ImagePreviewFragment, private val mList: List<String>) : PagerAdapter() {
//
//        override fun getCount(): Int {
//            return mList.size
//        }
//
//        override fun instantiateItem(container: ViewGroup, position: Int): View {
//            val inflater = container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            val view = inflater.inflate(R.layout.item_image_preview, null)
//
//            val photoView = view.findViewById<View>(R.id.photoView) as PhotoView
//            photoView.scaleType = ImageView.ScaleType.FIT_CENTER
//            val progressView = view.findViewById<View>(R.id.progressView) as CircleProgressView
//
//            photoView.setOnPhotoTapListener({ view1, x, y -> mActivity.activity?.finish() })
//
//            val imageLoader = GlideImageLoader.create(photoView)
//            imageLoader.loadImage(mList[position], R.color.transparent)
//            imageLoader.setOnGlideImageViewListener(mList[position]) { percent, isDone, exception ->
//                progressView.progress = percent
//                progressView.visibility = if (isDone) View.GONE else View.VISIBLE
//            }
//            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//            return view
//        }
//
//        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//            container.removeView(`object` as View)
//        }
//
//        override fun isViewFromObject(view: View, `object`: Any): Boolean {
//            return view === `object`
//        }
//    }


}
package com.vension.mvp.ui.activitys.headline

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.vension.frame.utils.VLogUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseActivity
import com.vension.mvp.showToast
import com.vension.mvp.ui.fragments.headline.BigImageFragment
import com.vension.mvp.utils.UIUtils
import com.vension.mvp.utils.vFileUtils
import kotlinx.android.synthetic.main.activity_image_view_pager.*
import java.io.File
import java.util.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/23 17:43
 * 描  述：详情页查看图片的activity
 * ========================================================
 */

class ImageViewPagerActivity : BaseActivity(), ViewPager.OnPageChangeListener  {

    private val TAG = ImageViewPagerActivity::class.java.simpleName
    val IMG_URLS = "mImageUrls"
    val POSITION = "position"

    private var mImageUrls: List<String> = ArrayList()
    private val mFragments = ArrayList<BigImageFragment>()
    private var mCurrentPosition: Int = 0
    private val mDownloadingFlagMap = HashMap<Int, Boolean>()//用于保存对应位置图片是否在下载的标识

    override fun attachLayoutRes(): Int {
        return R.layout.activity_image_view_pager
    }


    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        val intent = intent
        mImageUrls = intent.getStringArrayListExtra(IMG_URLS)
        val position = intent.getIntExtra(POSITION, 0)
        mCurrentPosition = position

        for (i in mImageUrls.indices) {
            val url = mImageUrls[i]
            val imageFragment = BigImageFragment()

            val bundle = Bundle()
            bundle.putString("imgUrl", url)
            imageFragment.arguments = bundle

            mFragments.add(imageFragment)//添加到fragment集合中
            mDownloadingFlagMap[i] = false//初始化map，一开始全部的值都为false
        }

        vp_pics.adapter = MyPagerAdapter(supportFragmentManager)
        vp_pics.addOnPageChangeListener(this)
        vp_pics.currentItem = mCurrentPosition// 设置当前所在的位置
        setIndicator(mCurrentPosition)//设置当前位置指示
    }


    override fun requestLoadData() {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        mCurrentPosition = position
        setIndicator(mCurrentPosition)
    }

    private fun setIndicator(position: Int) {
        tv_indicator.text = (position + 1).toString() + "/" + mImageUrls.size//设置当前的指示
    }


    @OnClick(R.id.tv_save)
    fun onViewClicked() {
//        val imgUrl = mImageUrls[mCurrentPosition]
//        ImageHelper(this).saveImage(imgUrl)
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) {
                        // All requested permissions are granted
                        downloadImg() //保存图片
                    } else {
                        // At least one permission is denied
                        showToast("用户关闭了权限")
                    }
                }
    }

    private fun downloadImg() {
        val imgUrl = mImageUrls[mCurrentPosition]
        val isDownlading = mDownloadingFlagMap[mCurrentPosition]
        if ((!isDownlading!!)!!) {
            //如果不是正在下载，则开始下载
            mDownloadingFlagMap[mCurrentPosition] = true//更改标识为下载中
            DownloadImgTask(mCurrentPosition).execute(imgUrl)
        }
    }


     inner class DownloadImgTask(private val mPosition: Int) : AsyncTask<String, Int, String>() {

        override fun doInBackground(vararg params: String): String {
            val imgUrl = params[0]
            var file: File? = null
            try {
                val future = Glide
                        .with(this@ImageViewPagerActivity)
                        .load(imgUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                file = future.get()

                val filePath = file!!.absolutePath

                val destFileName = System.currentTimeMillis().toString() + vFileUtils.getImageFileExt(filePath)
                val destFile = File(vFileUtils.getDir(""), destFileName)

                vFileUtils.copy(file, destFile)// 保存图片

                // 最后通知图库更新
                sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(destFile.path))))
            } catch (e: Exception) {
                VLogUtil.e( e.message)
            }

            return imgUrl
        }

        override fun onPostExecute(aVoid: String) {
            mDownloadingFlagMap[mPosition] = false//下载完成后更改对应的flag
            UIUtils.showToast("保存成功，图片所在文件夹:SD卡根路径/TouTiao")
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            VLogUtil.i("progress: " + values[0])
        }
    }

     inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }
    }

}
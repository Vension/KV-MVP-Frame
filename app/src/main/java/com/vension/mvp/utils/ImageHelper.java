package com.vension.mvp.utils;

import android.Manifest;
import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vension.frame.dialogs.VLoadingDialog;
import com.vension.frame.utils.VToastUtil;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/9 11:29
 * 描  述：
 * ========================================================
 */

public class ImageHelper {

    private Activity mActivity;
    private Disposable mDisposable;
    private String imagePath;

    public ImageHelper(Activity activity) {
        this.mActivity = activity;
        imagePath = getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getPath();
    }

    public void unInit() {
        VLoadingDialog.with(mActivity).dismiss();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    // 保存图片
    public void saveImage(String imageUrl) {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (!granted) {
                        VToastUtil.showInfo("您已禁止了写数据权限");
                    } else {
                        Observable.just(imageUrl)
                                .filter(it -> !TextUtils.isEmpty(it))
                                .map(this::getImageName)
                                .filter(it -> !TextUtils.isEmpty(it))
                                .filter(it -> !isImageExist(it))
                                .subscribe(it -> downloadImage(imageUrl, it),
                                        Throwable::printStackTrace);
                    }
                });
    }

    // 保存图片
    public void saveImage2(String imageUrl) {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        Observable<Boolean> requestPermissionObservable = rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        Observable<String> getImageNameObservable = Observable.just(imageUrl)
                .filter(it -> !TextUtils.isEmpty(it))
                .map(this::getImageName)
                .filter(it -> !TextUtils.isEmpty(it))
                .filter(it -> !isImageExist(it))
                .doOnError(Throwable::printStackTrace);

        Observable.zip(requestPermissionObservable, getImageNameObservable,
                (permission, imageName) -> {
                    if (!permission) {
                        VToastUtil.showInfo("您已禁止了写数据权限");
                        return null;
                    }
                    return imageName;
                })
                .filter(it -> !TextUtils.isEmpty(it))
                .subscribe(it -> downloadImage(imageUrl, it), Throwable::printStackTrace);
    }

    // 下载图片
    public void downloadImage(String imageUrl, String imageName) {
        RxDownload.getInstance()
                .download(imageUrl, imageName, imagePath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DownloadStatus>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                        VLoadingDialog.with(mActivity)
								.setCanceled(false)
								.setOrientation(VLoadingDialog.VERTICAL)
								.setBackgroundColor(Color.parseColor("#aa000000"))
								.setMessageColor(Color.WHITE)
								.setMessage("下载图片中...")
								.show();
                    }

                    @Override
                    public void onNext(DownloadStatus value) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        VLoadingDialog.with(mActivity).dismiss();
						VToastUtil.showSuccess("保存失败");
                    }

                    @Override
                    public void onComplete() {
                        VLoadingDialog.with(mActivity).dismiss();
                        VToastUtil.showSuccess("保存成功");
                    }
                });
    }

    // 获取图片名称
    public String getImageName(String url) {
        String[] split = url.split("/");
        return split[split.length - 1];
    }

    // 判断图片是否存在
    private boolean isImageExist(String fileName) {
        File file = new File(imagePath, fileName);
        boolean isExist = file.exists();
        if (isExist) {
            VToastUtil.showWarning("图片已存在");
        }
        return isExist;
    }
}

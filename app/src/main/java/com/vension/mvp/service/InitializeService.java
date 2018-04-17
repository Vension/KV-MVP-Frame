package com.vension.mvp.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/9 9:21
 * 描  述：开启子线程中完成其他初始化
 *        在Application中InitializeService.start(this);
 * ========================================================
 */

public class InitializeService extends IntentService {

    private static final String ACTION_INIT = "initApplication";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {
                initApplication();
            }
        }
    }

    private void initApplication() {
        //初始化日志
//        Logger.init(getPackageName()).hideThreadInfo();

        //初始化错误收集
//        CrashHandler.init(new CrashHandler(getApplicationContext()));
//        initBugly();

        //初始化内存泄漏检测
//        LeakCanary.install(App.getInstance());

        //初始化过度绘制检测
//        BlockCanary.install(getApplicationContext(), new AppBlockCanaryContext()).start();

        //初始化tbs x5 webview
//        QbSdk.allowThirdPartyAppDownload(true);
//        QbSdk.initX5Environment(getApplicationContext(), QbSdk.WebviewInitType.FIRSTUSE_AND_PRELOAD, new QbSdk.PreInitCallback() {
//            @Override
//            public void onCoreInitFinished() {
//            }
//
//            @Override
//            public void onViewInitFinished(boolean b) {
//            }
//        });
    }

//    private void initBugly() {
//        Context context = getApplicationContext();
//        String packageName = context.getPackageName();
//        String processName = SystemUtil.getProcessName(android.os.Process.myPid());
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        CrashReport.initCrashReport(context, Constants.BUGLY_ID, isDebug, strategy);
//    }
}

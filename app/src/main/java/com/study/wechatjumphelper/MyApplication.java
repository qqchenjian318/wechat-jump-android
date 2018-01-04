package com.study.wechatjumphelper;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;

/**
 * Created by cj on 2018/1/4.
 * desc
 */

public class MyApplication  extends Application {
    private static Context mContext;
    int result;
    Intent intent;
    MediaProjectionManager mediaProjectionManager;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
    public static Context getContext() {
        return mContext;
    }

    public Intent getIntent() {
        return intent;
    }

    public int getResult() {
        return result;
    }

    public MediaProjectionManager getMediaProjectionManager() {
        return mediaProjectionManager;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public void setMediaProjectionManager(MediaProjectionManager mediaProjectionManager) {
        this.mediaProjectionManager = mediaProjectionManager;
    }
}

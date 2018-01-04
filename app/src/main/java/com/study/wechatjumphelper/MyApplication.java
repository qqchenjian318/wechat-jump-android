package com.study.wechatjumphelper;

import android.app.Application;
import android.content.Context;

/**
 * Created by cj on 2018/1/4.
 * desc
 */

public class MyApplication  extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
    public static Context getContext() {
        return mContext;
    }
}

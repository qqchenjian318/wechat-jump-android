package com.study.wechatjumphelper;

import android.os.Environment;

import java.io.File;

/**
 * Created by cj on 2017/6/26 .
 *
 */

public class Constants {

    public static String getBaseFolder() {
        String baseFolder = Environment.getExternalStorageDirectory() +"/";
        File f = new File(baseFolder);
        if (!f.exists()) {
            boolean b = f.mkdirs();
            if (!b) {
                baseFolder = MyApplication.getContext().getExternalFilesDir(null).getAbsolutePath() + "/";
            }
        }
        return baseFolder;
    }
    //获取VideoPath
    public static String getPath(String path, String fileName) {
        String p = getBaseFolder() + path;
        File f = new File(p);
        if (!f.exists() && !f.mkdirs()) {
            return getBaseFolder() + fileName;
        }
        return p + fileName;
    }
}

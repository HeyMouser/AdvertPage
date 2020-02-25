package com.tkda.advert.tools;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;

public class FileUtils {
    private String cachePath;

    public FileUtils(Context context) {
        cachePath = context.getApplicationContext().getFilesDir().getAbsolutePath() + "/advert/images/";
    }

    public String getCachePath() {
        return cachePath;
    }

    public File getFile(String url) {
        if (TextUtils.isEmpty(url) || !url.contains("/")) {
            return null;
        }
        String suffix;
        if (url.contains(".")) {
            suffix = url.substring(url.lastIndexOf("."));
        } else {
            suffix = ".jpg";
        }
        String fileName = MD5Utils.encode(url) + suffix;
        return new File(cachePath + fileName);
    }
}

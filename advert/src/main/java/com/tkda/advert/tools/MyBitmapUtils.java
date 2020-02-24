package com.tkda.advert.tools;

import android.graphics.Bitmap;

import com.tkda.advert.tools.cache.LocalCacheUtils;
import com.tkda.advert.tools.cache.MemoryCacheUtils;

public class MyBitmapUtils {
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public MyBitmapUtils() {
        mMemoryCacheUtils = new MemoryCacheUtils();
        mLocalCacheUtils = new LocalCacheUtils();
    }

    public Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        bitmap = mMemoryCacheUtils.getBitmapFromMemory(url);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            mMemoryCacheUtils.setBitmapToMemory(url, bitmap);
            return bitmap;
        }
        return bitmap;
    }
}

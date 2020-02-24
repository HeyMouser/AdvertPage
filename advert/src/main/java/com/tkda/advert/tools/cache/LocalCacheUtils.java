package com.tkda.advert.tools.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.tkda.advert.tools.MD5Utils;

import java.io.File;
import java.io.FileOutputStream;

public class LocalCacheUtils {
    private static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/advert/images/";

    /**
     * 从本地读取图片
     *
     * @param url
     */
    public Bitmap getBitmapFromLocal(String url) {
        String fileName = null;//把图片的url当做文件名,并进行MD5加密
        try {
            fileName = MD5Utils.encode(url);    //这里加不加密无所谓
            String path = CACHE_PATH + fileName + ".jpg";
            Log.i("TAG", "-=-=本地获取图片path：" + path);
            //图片压缩
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;// 缩放比例，数值越大，图片越小，
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从网络获取图片后,保存至本地缓存
     *
     * @param url
     * @param bitmap
     */
    public void setBitmapToLocal(String url, Bitmap bitmap) {
        try {

            String fileName = MD5Utils.encode(url);//把图片的url当做文件名,并进行MD5加密
            File dir = new File(CACHE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(CACHE_PATH, fileName + ".jpg");
            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            Log.i("TAG", "-=-=本地存储图片成功path：" + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "-=-=本地存储图片异常：" + e.getMessage());
        }
    }
}

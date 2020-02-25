//package com.tkda.advert.http;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//
//import com.tkda.advert.interf.HttpCallBack;
//import com.tkda.advert.tools.FileUtils;
//import com.tkda.advert.tools.cache.MemoryCacheUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import pl.droidsonroids.gif.GifDrawable;
//
//public class DownLoadImage extends AsyncTask<String, Void, File> {
//
//    private String url;
//    private HttpCallBack httpCallBack;
//    private FileUtils mLocalCacheUtils;
//    private MemoryCacheUtils mMemoryCacheUtils;
//
//    public DownLoadImage(Context context) {
//        mLocalCacheUtils = new FileUtils();
//        mMemoryCacheUtils = new MemoryCacheUtils();
//    }
//
//    public void setHttpCallBack(HttpCallBack httpCallBack) {
//        this.httpCallBack = httpCallBack;
//    }
//
//    /**
//     * 表示任务执行之前的操作
//     */
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        if (httpCallBack != null) {
//            httpCallBack.onStart();
//        }
//    }
//
//    /**
//     * 主要是完成耗时的操作
//     */
//    @Override
//    protected File doInBackground(String... arg0) {
//        // 使用网络连接类HttpClient类王城对网络数据的提取
//        url = arg0[0];
//        Bitmap bitmap = getNetWorkBitmap(url);
//        return bitmap;
//    }
//
//    /**
//     * 主要是更新UI的操作
//     */
//    @Override
//    protected void onPostExecute(File result) {
//        super.onPostExecute(result);
//        if (result != null) {
//            //从网络获取图片后,保存至本地缓存
//            mLocalCacheUtils.setBitmapToLocal(url, result);
//            //保存至内存中
//            mMemoryCacheUtils.setBitmapToMemory(url, result);
//        }
//        if (httpCallBack != null) {
//            httpCallBack.onFinish(result);
//        }
//    }
//
//    public Bitmap getNetWorkBitmap(String urlString) {
//        URL imgUrl;
//        HttpURLConnection urlConn = null;
//        Bitmap bitmap = null;
//        try {
//            imgUrl = new URL(urlString);
//            // 使用HttpURLConnection打开连接
//            urlConn = (HttpURLConnection) imgUrl.openConnection();
//            urlConn.setConnectTimeout(5000);
//            urlConn.setReadTimeout(5000);
//            urlConn.setRequestMethod("GET");
//            urlConn.setDoInput(true);
//            urlConn.connect();
//            int responseCode = urlConn.getResponseCode();
//            if (responseCode == 200) {
//                // 将得到的数据转化成InputStream
//                InputStream is = urlConn.getInputStream();
//                // 将InputStream转换成Bitmap
//                bitmap = BitmapFactory.decodeStream(is);
//                is.close();
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (urlConn != null) {
//                urlConn.disconnect();
//            }
//        }
//        return bitmap;
//    }
//}

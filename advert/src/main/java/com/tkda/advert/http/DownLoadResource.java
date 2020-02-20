package com.tkda.advert.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.Menu;

import com.tkda.advert.R;
import com.tkda.advert.interf.HttpCallBack;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownLoadResource extends AsyncTask<String, Void, Bitmap> {

    private HttpCallBack httpCallBack;

    public void setHttpCallBack(HttpCallBack httpCallBack) {
        this.httpCallBack = httpCallBack;
    }

    /**
     * 表示任务执行之前的操作
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        dialog.show();
    }

    /**
     * 主要是完成耗时的操作
     */
    @Override
    protected Bitmap doInBackground(String... arg0) {
        // 使用网络连接类HttpClient类王城对网络数据的提取
        return getNetWorkBitmap(arg0[0]);
    }

    /**
     * 主要是更新UI的操作
     */
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (httpCallBack != null) {
            httpCallBack.onSuccess(result);
        }
    }

    public static Bitmap getNetWorkBitmap(String urlString) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(urlString);
            // 使用HttpURLConnection打开连接
            HttpURLConnection urlConn = (HttpURLConnection) imgUrl.openConnection();
            urlConn.setDoInput(true);
            urlConn.connect();
            // 将得到的数据转化成InputStream
            InputStream is = urlConn.getInputStream();
            // 将InputStream转换成Bitmap
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            System.out.println("[getNetWorkBitmap->]MalformedURLException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("[getNetWorkBitmap->]IOException");
            e.printStackTrace();
        }
        return bitmap;
    }
}

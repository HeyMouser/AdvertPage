package com.tkda.advert.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tkda.advert.interf.HttpCallBack;
import com.tkda.advert.tools.MD5Utils;
import com.tkda.advert.tools.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownLoadFile extends AsyncTask<String, Void, File> {

    private Context mContext;
    private String url;
    private HttpCallBack httpCallBack;

    public DownLoadFile(Context mContext) {
        this.mContext = mContext;
    }

    public void setHttpCallBack(HttpCallBack httpCallBack) {
        this.httpCallBack = httpCallBack;
    }

    /**
     * 表示任务执行之前的操作
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (httpCallBack != null) {
            httpCallBack.onStart();
        }
    }

    /**
     * 主要是完成耗时的操作
     */
    @Override
    protected File doInBackground(String... arg0) {
        url = arg0[0];
        return getNetWorkBitmap(url);
    }


    /**
     * 主要是更新UI的操作
     */
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (httpCallBack != null) {
            httpCallBack.onResult(file);
        }
    }

    public File getNetWorkBitmap(String urlString) {
        URL imgUrl;
        HttpURLConnection urlConn = null;

        InputStream is = null;
        FileOutputStream os = null;

        String path = new FileUtils(mContext).getCachePath();
        File fileDir = new File(path);
        if (!fileDir.exists()) {  //如果目录 不存在 ，就创建
            fileDir.mkdirs();
        }
        //获取要下载的文件名称，在这里可以更改下载的文件名
        String suffix = url.substring(url.lastIndexOf("."));
        String fileName = MD5Utils.encode(url) + suffix;
        //创建 这个文件名 命名的 file 对象
        File file = new File(path, fileName);
        if (!file.exists()) {  //倘若没有这个文件
            try {
                file.createNewFile();  //创建这个文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            imgUrl = new URL(urlString);
            // 使用HttpURLConnection打开连接
            urlConn = (HttpURLConnection) imgUrl.openConnection();
            urlConn.setConnectTimeout(5000);
            urlConn.setReadTimeout(5000);
            urlConn.setRequestMethod("GET");
            urlConn.setDoInput(true);
            urlConn.connect();
            int responseCode = urlConn.getResponseCode();
            if (responseCode == 200) {
                // 将得到的数据转化成InputStream
                is = urlConn.getInputStream();
                // 将InputStream转换成Bitmap
                os = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int tem = 0;
                while ((tem = is.read(b)) != -1) {
                    os.write(b, 0, tem);
                }

                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "-=-=下载异常:" + e.getMessage());
            return null;
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}

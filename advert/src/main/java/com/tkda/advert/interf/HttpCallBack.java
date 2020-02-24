package com.tkda.advert.interf;

import android.graphics.Bitmap;

public interface HttpCallBack {
    void onStart();
    void onLoading();
    void onFinish(Bitmap bitmap);
}

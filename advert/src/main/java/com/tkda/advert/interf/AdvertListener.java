package com.tkda.advert.interf;

import java.util.List;

public interface AdvertListener {
    /**
     * 广告点击
     */
    void onADClick();

    /**
     * 广告消失的时候回调该方法：
     * 1、跳过广告
     * 2、点击广告进入广告落地页且从落地页back回去
     */
    void onADDismiss();

    void onPermission(List<String> permissions);
}

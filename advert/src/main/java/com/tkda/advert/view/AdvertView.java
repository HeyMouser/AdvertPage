package com.tkda.advert.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.tkda.advert.R;
import com.tkda.advert.http.DownLoadResource;
import com.tkda.advert.interf.AdvertListener;
import com.tkda.advert.interf.HttpCallBack;
import com.tkda.advert.interf.TimeListener;
import com.tkda.advert.tools.DensityUtils;
import com.tkda.advert.tools.cache.LocalCacheUtils;
import com.tkda.advert.tools.cache.MemoryCacheUtils;

import java.util.ArrayList;
import java.util.List;

public class AdvertView extends FrameLayout {
    private Context mContext;
    private ImageView imageView;
    private CountDownView countDownView;
    private LoadingView loading;

    private final int defCircleBg = 0x583d3d3d;
    private final int defTextColor = 0xffefefef;
    private final int defTextSize = 12;//sp
    private final int defProgressColor = 0xffff0000;
    private final int defProgressWidth = 3;// dp
    private final int defTime = 3000; //单位 ms
    private final int defRadius = 25; //单位 dp

    private final int defLoadingColor = 0xffff0000;
    private final int defLoadingRadius = 25;
    private final int defLoadingWidth = 2;


    private boolean time_view_show;
    private boolean time_show;
    private int time_bg;
    private int time_text_color;
    private int time_text_size;
    private int time_pro_color;
    private int time_pro_width;
    private int time_radius;
    private int time_num;

    private int loading_width;
    private int loading_color;
    private int loading_radius;

    private AdvertListener advertListener;

    public void setAdvertListener(AdvertListener advertListener) {
        this.advertListener = advertListener;
    }

    public AdvertView(Context context) {
        this(context, null);
    }

    public AdvertView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdvertView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AdvertView, defStyleAttr, 0);
        time_bg = typedArray.getColor(R.styleable.AdvertView_ad_time_bg, defCircleBg);

        time_pro_color = typedArray.getColor(R.styleable.AdvertView_ad_time_pro_color, defProgressColor);
        time_pro_width = typedArray.getDimensionPixelSize(R.styleable.AdvertView_ad_time_pro_width, DensityUtils.dp2px(context, defProgressWidth));
        time_text_color = typedArray.getColor(R.styleable.AdvertView_ad_time_text_color, defTextColor);
        time_text_size = typedArray.getDimensionPixelSize(R.styleable.AdvertView_ad_time_text_size, DensityUtils.sp2px(context, defTextSize));
        time_num = typedArray.getInteger(R.styleable.AdvertView_ad_time_num, defTime);
        time_radius = typedArray.getDimensionPixelSize(R.styleable.AdvertView_ad_time_radius, DensityUtils.dp2px(context, defRadius));

        loading_color = typedArray.getColor(R.styleable.AdvertView_ad_loading_color, defLoadingColor);
        loading_radius = typedArray.getDimensionPixelSize(R.styleable.AdvertView_ad_loading_radius, DensityUtils.dp2px(context, defLoadingRadius));
        loading_width = typedArray.getDimensionPixelSize(R.styleable.AdvertView_ad_loading_width, DensityUtils.dp2px(context, defLoadingWidth));

        time_show = typedArray.getBoolean(R.styleable.AdvertView_ad_time_show, false);
        time_view_show = typedArray.getBoolean(R.styleable.AdvertView_ad_time_view_show, true);

        typedArray.recycle();

        View view = View.inflate(context, R.layout.advert_layout, this);
        loading = view.findViewById(R.id.iv_loading);
        imageView = view.findViewById(R.id.image);
        countDownView = view.findViewById(R.id.cd_view);

        loading.setColor(loading_color);
        loading.setRadius(loading_radius);
        loading.setWidth(loading_width);

        countDownView.setCircleBg(time_bg);
        countDownView.setProgressColor(time_pro_color);
        countDownView.setProgressWidth(time_pro_width);
        countDownView.setTextColor(time_text_color);
        countDownView.setTextSize(time_text_size);
        countDownView.setTime(time_num);
        countDownView.setRadius(time_radius);

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (advertListener != null) {
                    advertListener.onADClick();
                    countDownView.stop();
                }
            }
        });

        countDownView.setTimeListener(new TimeListener() {
            @Override
            public void onDismiss() {
                if (advertListener != null) {
                    advertListener.onADDismiss();
                }
            }
        });
    }

    public void setTime_view_show(boolean time_view_show) {
        this.time_view_show = time_view_show;
        if (time_show) {
            countDownView.setVisibility(View.VISIBLE);
        } else {
            countDownView.setVisibility(View.GONE);
        }
    }

    public void setTime_show(boolean time_show) {
        this.time_show = time_show;
        if (time_show) {
            countDownView.setShowTime(time_show);
            countDownView.setVisibility(View.VISIBLE);
        } else {
            countDownView.setVisibility(View.GONE);
        }
    }

    public void setTime_bg(int time_bg) {
        this.time_bg = time_bg;
        countDownView.setCircleBg(time_bg);
    }

    public void setTime_text_color(int time_text_color) {
        this.time_text_color = time_text_color;
        countDownView.setTextColor(time_text_color);
    }

    public void setTime_text_size(int time_text_size) {
        this.time_text_size = DensityUtils.sp2px(mContext, time_text_size);
        countDownView.setTextSize(this.time_text_size);
    }

    public void setTime_pro_color(int time_pro_color) {
        this.time_pro_color = time_pro_color;
        countDownView.setProgressColor(time_pro_color);
    }

    public void setTime_pro_width(int time_pro_width) {
        this.time_pro_width = DensityUtils.dp2px(mContext, time_pro_width);
        countDownView.setProgressWidth(this.time_pro_width);
    }

    public void setTime_radius(int time_radius) {
        this.time_radius = DensityUtils.dp2px(mContext, time_radius);
        countDownView.setRadius(this.time_radius);
    }

    public void setTime_num(int time_num) {
        this.time_num = time_num;
        countDownView.setTime(time_num);
    }

    public void setLoading_width(int loading_width) {
        this.loading_width = DensityUtils.dp2px(mContext, loading_width);
        loading.setWidth(this.loading_width);
    }

    public void setLoading_color(int loading_color) {
        this.loading_color = loading_color;
        loading.setColor(this.loading_color);
    }

    public void setLoading_radius(int loading_radius) {
        this.loading_radius = DensityUtils.dp2px(mContext, loading_radius);
        loading.setRadius(this.loading_radius);
    }

    private void startTime() {
        if (time_view_show) {
            countDownView.setVisibility(View.VISIBLE);
            countDownView.showStartTime();
        } else {
            countDownView.setVisibility(View.GONE);
            countDownView.unShowStart();
        }
    }

    public void setImage(int resourceId) {
        if (imageView != null) {
            imageView.setImageResource(resourceId);
        }
        startTime();
    }

    public void setImage(Bitmap bitmap) {
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
        startTime();
    }

    public void setImage(String url) {
        //判断读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            int checkReadResutl = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
            boolean hasReadPermission = checkReadResutl == PackageManager.PERMISSION_GRANTED;
            if (!hasReadPermission) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            int checkWriteResult = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            boolean hasWritePermission = checkWriteResult == PackageManager.PERMISSION_GRANTED;
            if (!hasWritePermission) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissions.size() > 0 && advertListener != null) {
                advertListener.onPermission(permissions);
                return;
            }
        }
        if (url != null) {
            Bitmap bitmap = null;
            //第一步去内存查找
            bitmap = new MemoryCacheUtils().getBitmapFromMemory(url);
            if (bitmap != null) {
                Log.i("TAG", "-=-=内存中有图");
                setImage(bitmap);
                return;
            }
            //第二步去本地查找
            bitmap = new LocalCacheUtils().getBitmapFromLocal(url);
            if (bitmap != null) {
                Log.i("TAG", "-=-=本地中有图");
                setImage(bitmap);
                return;
            }
            //第三步去网络下载
            if (url.contains("http")) {
                DownLoadResource downLoadResource = new DownLoadResource(mContext);
                downLoadResource.setHttpCallBack(new HttpCallBack() {
                    @Override
                    public void onStart() {
                        loading.setVisibility(VISIBLE);
                        loading.start();
                    }

                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onFinish(Bitmap bitmap) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loading.setVisibility(GONE);
                            }
                        }, 3000);
                        Log.i("TAG", "-=-=网络图");
                        setImage(bitmap);
                    }
                });
                downLoadResource.execute(url);
            }
        }
    }
}

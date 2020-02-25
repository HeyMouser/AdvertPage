package com.taikang.tkda.advertpage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.tkda.advert.interf.AdvertListener;
import com.tkda.advert.interf.PermissionListener;
import com.tkda.advert.view.AdvertView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PermissionListener {
    private final int REQUESTPERMISSION_CODE = 1001;
    private AdvertView advert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        advert = findViewById(R.id.advert);

        advert.setLoading_color(Color.parseColor("#0000ff"));// 设置loading的颜色
        advert.setLoading_width(2); // 设置loading的粗细
        advert.setLoading_radius(30);// 设置加载loading大小

//        advert.setTime_bg(Color.parseColor("#ff0000")); // 设置倒计时按钮的背景颜色
//        advert.setTime_num(5000);//设置倒计时时间
//        advert.setTime_pro_color(Color.parseColor("#0000ff"));// 设置倒计时进度条的颜色
//        advert.setTime_pro_width(4);// 设置倒计时进度条的宽度
//        advert.setTime_radius(50);// 设置倒计时按钮的半径大小
//        advert.setTime_text_color(Color.parseColor("#000000")); // 设置倒计时view文字的颜色
//        advert.setTime_text_size(24);// 设置倒计时view文字的大小
//        advert.setTime_view_show(true);// 设置倒计时是否显示，默认true
//        advert.setTime_show(true); // 设置倒计时秒数是否显示，false

//        advert.setImage(R.drawable.ic_launcher_background);
//        advert.setGif(R.drawable.anim);
//        advert.setImage(new ResourceHelp().getImage(), this);
        advert.setGif(new ResourceHelp().getGif(), this);

        advert.setAdvertListener(new AdvertListener() {
            @Override
            public void onADClick() {
                Toast.makeText(MainActivity.this, "广告点击", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                finish();
            }

            @Override
            public void onADDismiss() {
                Toast.makeText(MainActivity.this, "结束", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTPERMISSION_CODE) {
            List<String> list = new ArrayList<>();
            if (permissions.length > 0) {
                for (int i = 0; i < permissions.length; i++) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                        list.add(permissions[i]);
                    }
                }
            }
            if (list.size() > 0) {
                Toast.makeText(MainActivity.this, "请允许权限", Toast.LENGTH_SHORT).show();
            } else {
//                advert.setImage(url, this);
//                advert.setGif(url_gif, this);
            }
        }
    }

    @Override
    public void onPermission(List<String> permissions) {
        if (permissions != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int size = permissions.size();
                String[] permission = new String[size];
                for (int i = 0; i < size; i++) {
                    permission[i] = permissions.get(i);
                }
                requestPermissions(permission, REQUESTPERMISSION_CODE);
            }
        }
    }
}

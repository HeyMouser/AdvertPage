package com.taikang.tkda.advertpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.tkda.advert.interf.AdvertListener;
import com.tkda.advert.view.AdvertView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final int REQUESTPERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdvertView advert = findViewById(R.id.advert);
        // 这个方法必须先执行
        advert.setAdvertListener(new AdvertListener() {
            @Override
            public void onADClick() {
                Toast.makeText(MainActivity.this, "广告点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onADDismiss() {
                Toast.makeText(MainActivity.this, "结束", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                finish();
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
        });
        advert.setImage("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1582273722&di=b5ecf3fcc90f2213ca38393a3543de8d&src=http://file02.16sucai.com/d/file/2014/0822/b44cd1310d09026f6dd1f0633a1cc2a5.jpg");
//        advert.setImage(R.drawable.ic_launcher_background);

        advert.setLoading_color(Color.parseColor("#0000ff"));// 设置loading的颜色
        advert.setLoading_width(1); // 设置loading的粗细
        advert.setLoading_radius(30);// 设置加载loading大小

//        advert.setTime_bg(Color.parseColor("#ff0000")); // 设置倒计时按钮的背景颜色
//        advert.setTime_num(5000);//设置倒计时时间
//        advert.setTime_pro_color(Color.parseColor("#0000ff"));// 设置倒计时进度条的颜色
//        advert.setTime_pro_width(4);// 设置倒计时进度条的宽度
//        advert.setTime_radius(50);// 设置倒计时按钮的半径大小
//        advert.setTime_text_color(Color.parseColor("#000000")); // 设置倒计时view文字的颜色
//        advert.setTime_text_size(24);// 设置倒计时view文字的大小

//        advert.setTime_view_show(true);// 设置倒计时是否显示，默认true
        advert.setTime_show(true); // 设置倒计时秒数是否显示，false
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTPERMISSION_CODE) {

        }
    }
}

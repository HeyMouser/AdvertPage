package com.taikang.tkda.advertpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tkda.advert.view.AdvertView;
import com.tkda.advert.interf.FinishInterfece;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdvertView advert = findViewById(R.id.advert);
        advert.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582184513188&di=9756049deedd826da11c0664a10d4c3e&imgtype=0&src=http%3A%2F%2Fwww.17qq.com%2Fimg_qqtouxiang%2F16932598.jpeg");
//        advert.setImage(R.drawable.ic_launcher_background);
        advert.setFinishInterfece(new FinishInterfece() {
            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "结束", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                finish();
            }
        });
    }
}

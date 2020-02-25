# Android 闪屏广告页面
封装了ImageView+倒计时功能
# 功能特点

 - 倒计时时间设定
 - 倒计时进度条颜色设定
 - 倒计时进度条宽度设定
 - 倒计时“跳过”文字颜色设定
 - 倒计时“跳过”文字大小设定
 - 倒计时view背景颜色设定
 - 倒计时view半径大小设定
 - 倒计时按钮是否显示
 - 广告Image支持本地资源（drawable，bitmap，gif）、网络资源

# 简单用例
在需要使用的Activity里面使用
```java
        AdvertView advert = findViewById(R.id.advert);
        advert.setImage(R.drawable.ic_launcher_background);
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
```
对应的Activity的xml 布局文件里面引入，根据自己需要设置参数
```java
    <com.tkda.advert.view.AdvertView
            android:id="@+id/advert"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:ad_time_bg="#993d3d3d"
            app:ad_time_num="3000"
            app:ad_time_pro_color="#ff0000"
            app:ad_time_pro_width="3dp"
            app:ad_time_radius="30dp"
            app:ad_time_show="true"
            app:ad_time_text_color="#fefefe"
            app:ad_time_text_size="14sp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
```
# Attributes
| name | format  | description |
|--|--|--|
| ad_time_show | boolean | 是否显示倒计时 |
| ad_time_bg|color|倒计时背景颜色|
| ad_time_num | integer | 倒计时秒数 |
| ad_time_pro_color | color | 倒计时进度条颜色 |
| ad_time_pro_width | dimension | 倒计时进度条宽度 |
| ad_time_text_color | color | 倒计时按钮中间“跳过”文字颜色 |
| ad_time_text_size | dimension | 倒计时按钮中间“跳过”文字大小 |
|  |  |  |
|  |  |  |
# Method
| name | format  | description |
|--|--|--|
| setTime_view_show | boolean | 设置倒计时view是否显示 |
| setTime_show | boolean | 设置倒计时读秒数字是否显示 |
| setTime_show | boolean | 设置倒计时读秒数字是否显示 |
| setTime_bg | int | 设置倒计时view的背景颜色 |
| setTime_num | int | 设置倒计时时间，单位毫秒 |
| setLoading_width | int | 设置loading圈的宽度 |
| setLoading_color | int | 设置loading圈的颜色 |
| setLoading_radius | int | 设置loading圈的半径大小 |
| setImage | int | 资源图片的id |
| setImage | Bitmap | 资源图片 |
| setImage | String | 资源图片的url |
| setAdvertListener|AdvertListener|结束、点击回调|
| setGif|String||网络资源URL|
| setGif | int | 本地资源文件id |


# 使用
①
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```java
allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

Step 2. Add the dependency

```java
dependencies {
            implementation 'com.github.HeyMouser:AdvertPage:0.1'
    }
```
②
直接下载advert，并依赖
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200220162928446.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RhcmxpbmdfUg==,size_16,color_FFFFFF,t_70)
```java
implementation project(':advert')
```










package com.zzg.zcib.myandroid_order.utils;

import android.app.Application;
import android.content.IntentFilter;

import cn.jpush.android.api.JPushInterface;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }


}
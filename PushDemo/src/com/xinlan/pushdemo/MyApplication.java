package com.xinlan.pushdemo;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;

public class MyApplication extends Application{
	@Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        System.out.println("init!!!");
    }
}//end class

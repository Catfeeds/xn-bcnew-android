package com.cdkj.link_community;

import android.app.Application;
import android.content.Context;

import com.cdkj.baselibrary.CdApplication;

/**
 * Created by cdkj on 2018/1/31.
 */

public class BaseApplication extends Application {

    public static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CdApplication.initialize(this,BuildConfig.LOG_DEBUG);
    }

    public static Context getInstance() {
        return instance;
    }
}

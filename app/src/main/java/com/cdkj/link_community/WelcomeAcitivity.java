package com.cdkj.link_community;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.cdkj.baselibrary.base.BaseActivity;
import com.cdkj.link_community.manager.MyRouteHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 启动页
 * Created by cdkj on 2017/6/8.
 */

public class WelcomeAcitivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 用于第一次安装APP，进入到除这个启动activity的其他activity，点击home键，再点击桌面启动图标时，
        // 系统会重启此activty，而不是直接打开之前已经打开过的activity，因此需要关闭此activity

        try {
            if (getIntent() != null && (getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        } catch (Exception e) {
        }
        setContentView(R.layout.activity_welcom);
        ImageView img = (ImageView) findViewById(R.id.img_start);

        mSubscription.add(Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    MyRouteHelper.openMain();
                    finish();
                }, Throwable::printStackTrace));
    }


}

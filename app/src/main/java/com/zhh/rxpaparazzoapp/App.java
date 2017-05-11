package com.zhh.rxpaparazzoapp;

import android.app.Application;

import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;

/**
 * Created by zhh on 2017/5/9.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxPaparazzo.register(this);
    }
}

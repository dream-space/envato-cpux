package com.app.cpux.data;

import android.app.Application;

import com.app.cpux.R;
import com.google.android.gms.ads.MobileAds;


public class ThisApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }

}

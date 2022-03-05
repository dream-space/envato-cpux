package com.app.cpux.data;

import android.app.Application;

import com.app.cpux.R;
import com.app.cpux.advertise.AdNetworkHelper;
import com.google.android.gms.ads.MobileAds;


public class ThisApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AdNetworkHelper.init(this);
    }

}

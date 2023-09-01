package com.app.cpux.advertise;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.app.cpux.AppConfig;
import com.app.cpux.BuildConfig;
import com.app.cpux.R;

import dreamspace.ads.sdk.AdConfig;
import dreamspace.ads.sdk.AdNetwork;
import dreamspace.ads.sdk.gdpr.GDPR;
import dreamspace.ads.sdk.gdpr.LegacyGDPR;
import dreamspace.ads.sdk.listener.AdOpenListener;

public class AdNetworkHelper {
    private Activity activity;
    private AdNetwork adNetwork;
    private LegacyGDPR legacyGDPR;
    private GDPR gdpr;

    public AdNetworkHelper(Activity activity) {
        this.activity = activity;
        adNetwork = new AdNetwork(activity);
        legacyGDPR = new LegacyGDPR(activity);
        gdpr = new GDPR(activity);
    }

    public void updateConsentStatus() {
        if (!AppConfig.ads.ad_enable || !AppConfig.ads.ad_enable_gdpr) return;
        gdpr.updateGDPRConsentStatus();
    }

    public static void initConfig() {
        AdConfig.ad_enable = AppConfig.ads.ad_enable;
        AdConfig.ad_enable_open_app = AppConfig.ads.ad_global_open_app;
        AdConfig.limit_time_open_app_loading = AppConfig.ads.limit_time_open_app_loading;
        AdConfig.debug_mode = BuildConfig.DEBUG;
        AdConfig.enable_gdpr = AppConfig.ads.ad_enable_gdpr;
        AdConfig.ad_networks = AppConfig.ads.ad_networks;
        AdConfig.ad_inters_interval = AppConfig.ads.ad_inters_interval;

        AdConfig.ad_admob_publisher_id = AppConfig.ads.ad_admob_publisher_id;
        AdConfig.ad_admob_banner_unit_id = AppConfig.ads.ad_admob_banner_unit_id;
        AdConfig.ad_admob_interstitial_unit_id = AppConfig.ads.ad_admob_interstitial_unit_id;
        AdConfig.ad_admob_open_app_unit_id = AppConfig.ads.ad_admob_open_app_unit_id;

        AdConfig.ad_fan_banner_unit_id = AppConfig.ads.ad_fan_banner_unit_id;
        AdConfig.ad_fan_interstitial_unit_id = AppConfig.ads.ad_fan_interstitial_unit_id;
    }

    public void init() {
        AdNetworkHelper.initConfig();
        adNetwork.init();
    }

    public void loadBannerAd(boolean enable) {
        adNetwork.loadBannerAd(enable, activity.findViewById(R.id.ad_container));
    }

    public void loadInterstitialAd(boolean enable) {
        adNetwork.loadInterstitialAd(enable);
    }

    public boolean showInterstitialAd(boolean enable) {
        return adNetwork.showInterstitialAd(enable);
    }

    public static void loadAndShowOpenAppAd(Context context, boolean enable, AdOpenListener listener) {
        AdNetwork.loadAndShowOpenAppAd(context, enable, listener);
    }

    public static void loadOpenAppAd(Context context, boolean enable) {
        AdNetwork.loadOpenAppAd(context, enable);
    }

    public static void showOpenAppAd(Context context, boolean enable, AdOpenListener listener) {
        AdNetwork.showOpenAppAd(context, enable, listener);
    }

    public static void initActivityListener(Application application) {
        initConfig();
        AdNetwork.init(application);
        AdNetwork.initActivityListener(application);
    }
}
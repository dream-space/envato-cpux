package com.app.cpux.advertise;

import android.app.Activity;

import com.app.cpux.AppConfig;
import com.app.cpux.BuildConfig;
import com.app.cpux.R;

import dreamspace.ads.AdConfig;
import dreamspace.ads.AdNetwork;
import dreamspace.ads.gdpr.GDPR;
import dreamspace.ads.gdpr.LegacyGDPR;

public class AdNetworkHelper {

    private Activity activity;
    private AdNetwork adNetwork;
    private LegacyGDPR legacyGDPR;
    private GDPR gdpr;

    public AdNetworkHelper(Activity activity) {
        this.activity = activity;
        init(activity);
        adNetwork = new AdNetwork(activity);
        legacyGDPR = new LegacyGDPR(activity);
        gdpr = new GDPR(activity);
    }

    public void updateConsentStatus() {
        if (!AppConfig.ads.ad_enable || !AppConfig.ads.ad_enable_gdpr) return;
        gdpr.updateGDPRConsentStatus();
    }

    public static void init(Activity context) {
        AdConfig.ad_enable = AppConfig.ads.ad_enable;
        AdConfig.debug_mode = BuildConfig.DEBUG;
        AdConfig.enable_gdpr = true;
        AdConfig.retry_ad_networks = AppConfig.ads.retry_ad_networks;
        AdConfig.ad_networks = AppConfig.ads.ad_networks;
        AdConfig.ad_inters_interval = AppConfig.ads.ad_inters_interval;

        AdConfig.ad_admob_publisher_id = AppConfig.ads.ad_admob_publisher_id;
        AdConfig.ad_admob_banner_unit_id = AppConfig.ads.ad_admob_banner_unit_id;
        AdConfig.ad_admob_interstitial_unit_id = AppConfig.ads.ad_admob_interstitial_unit_id;

        AdConfig.ad_ironsource_app_key = AppConfig.ads.ad_ironsource_app_key;
        AdConfig.ad_ironsource_banner_unit_id = AppConfig.ads.ad_ironsource_banner_unit_id;
        AdConfig.ad_ironsource_interstitial_unit_id = AppConfig.ads.ad_ironsource_interstitial_unit_id;

        AdConfig.ad_unity_game_id = AppConfig.ads.ad_unity_game_id;
        AdConfig.ad_unity_banner_unit_id = AppConfig.ads.ad_unity_banner_unit_id;
        AdConfig.ad_unity_interstitial_unit_id = AppConfig.ads.ad_unity_interstitial_unit_id;

        AdNetwork.init(context);
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

}

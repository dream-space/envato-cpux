package com.app.cpux.advertise;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import com.app.cpux.BuildConfig;
import com.app.cpux.R;
import com.app.cpux.data.AppConfig;
import com.app.cpux.data.GDPR;
import com.app.cpux.data.SharedPref;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class AdNetworkHelper {

    private static final String TAG = AdNetworkHelper.class.getSimpleName();

    private final Activity activity;
    private final SharedPref sharedPref;
    private final static String unity_game_id = "4643549";
    private final static String unity_banner_id = "Banner_Android";
    private final static String unity_interstitial_id = "Interstitial_Android";

    public AdNetworkHelper(Activity activity) {
        this.activity = activity;
        sharedPref = new SharedPref(activity);
    }

    @SuppressLint("MissingPermission")
    public static void init(Context context) {
        UnityAds.initialize(context, unity_game_id, BuildConfig.DEBUG, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                Log.d(TAG, "Unity Ads Initialization Complete");
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                Log.d(TAG, "Unity Ads Initialization Failed: [" + error + "] " + message);
            }
        });
    }

    public void showGDPR() {
        GDPR.updateConsentStatus(activity);
    }

    @SuppressLint("MissingPermission")
    public void loadBannerAd(boolean enable) {
        if (!enable) return;
        LinearLayout ad_container = activity.findViewById(R.id.ad_container);
        ad_container.removeAllViews();
        ad_container.setVisibility(View.GONE);
        BannerView bottomBanner = new BannerView(activity, unity_banner_id, getUnityBannerSize());
        bottomBanner.setListener(new BannerView.IListener() {
            @Override
            public void onBannerLoaded(BannerView bannerView) {
                ad_container.setVisibility(View.VISIBLE);
                Log.d(TAG, "ready");
            }

            @Override
            public void onBannerClick(BannerView bannerView) {

            }

            @Override
            public void onBannerFailedToLoad(BannerView bannerView, BannerErrorInfo bannerErrorInfo) {
                Log.d(TAG, "Banner Error" + bannerErrorInfo);
                ad_container.setVisibility(View.GONE);
            }

            @Override
            public void onBannerLeftApplication(BannerView bannerView) {

            }
        });
        ad_container.addView(bottomBanner);
        bottomBanner.load();
    }

    public void loadInterstitialAd(boolean enable) {

    }

    public boolean showInterstitialAd(boolean enable) {
        if (!enable) return false;
        int counter = new SharedPref(activity).getIntersCounter();
        if (counter > AppConfig.ADS_INTERSTITIAL_INTERVAL) {
            UnityAds.show(activity, unity_interstitial_id, new IUnityAdsShowListener() {
                @Override
                public void onUnityAdsShowFailure(String s, UnityAds.UnityAdsShowError unityAdsShowError, String s1) {

                }

                @Override
                public void onUnityAdsShowStart(String s) {
                    sharedPref.setIntersCounter(0);
                    loadInterstitialAd(enable);
                }

                @Override
                public void onUnityAdsShowClick(String s) {

                }

                @Override
                public void onUnityAdsShowComplete(String s, UnityAds.UnityAdsShowCompletionState unityAdsShowCompletionState) {

                }
            });
            return true;
        } else {
            sharedPref.setIntersCounter(sharedPref.getIntersCounter() + 1);
        }
        return false;
    }

    private UnityBannerSize getUnityBannerSize() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return new UnityBannerSize(adWidth, 50);
    }

}

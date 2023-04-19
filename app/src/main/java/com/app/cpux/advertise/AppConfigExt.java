package com.app.cpux.advertise;

import com.app.cpux.AppConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import dreamspace.ads.data.AdNetworkType;

public class AppConfigExt {

    /* --------------- DONE EDIT CODE BELOW ------------------------------------------------------ */

    // define static variable for all config class
    public static AppConfig.Ads ads = new AppConfig.Ads();

    // Set data from remote config
    public static void setFromRemoteConfig(FirebaseRemoteConfig remote) {

        // fetch Ads Config with data from remote config
        if (!remote.getString("ad_enable").isEmpty()) {
            AppConfig.ads.ad_enable = Boolean.parseBoolean(remote.getString("ad_enable"));
        }

        if (!remote.getString("ad_networks").isEmpty()) {

            String[] arr = remote.getString("ad_networks").split(",");
            AdNetworkType[] adNetworkTypes = new AdNetworkType[arr.length];
            for (int i = 0; i < arr.length; i++) {
                try {
                    adNetworkTypes[i] = AdNetworkType.valueOf(arr[i].trim());
                } catch (Exception e) {
                    adNetworkTypes[i] = AdNetworkType.ADMOB;
                }
            }
            AppConfig.ads.ad_networks = adNetworkTypes;
        }

        if (!remote.getString("ad_enable_gdpr").isEmpty()) {
            AppConfig.ads.ad_enable_gdpr = Boolean.parseBoolean(remote.getString("ad_enable_gdpr"));
        }

        if (!remote.getString("retry_ad_networks").isEmpty()) {
            try {
                AppConfig.ads.ad_inters_interval = Integer.parseInt(remote.getString("retry_ad_networks"));
            } catch (Exception e) {
            }
        }

        if (!remote.getString("ad_inters_interval").isEmpty()) {
            try {
                AppConfig.ads.ad_inters_interval = Integer.parseInt(remote.getString("ad_inters_interval"));
            } catch (Exception e) {
            }
        }

        if (!remote.getString("ad_admob_publisher_id").isEmpty()) {
            AppConfig.ads.ad_admob_publisher_id = remote.getString("ad_admob_publisher_id");
        }
        if (!remote.getString("ad_admob_banner_unit_id").isEmpty()) {
            AppConfig.ads.ad_admob_banner_unit_id = remote.getString("ad_admob_banner_unit_id");
        }
        if (!remote.getString("ad_admob_interstitial_unit_id").isEmpty()) {
            AppConfig.ads.ad_admob_interstitial_unit_id = remote.getString("ad_admob_interstitial_unit_id");
        }

        if (!remote.getString("ad_ironsource_app_key").isEmpty()) {
            AppConfig.ads.ad_ironsource_app_key = remote.getString("ad_ironsource_app_key");
        }
        if (!remote.getString("ad_ironsource_banner_unit_id").isEmpty()) {
            AppConfig.ads.ad_ironsource_banner_unit_id = remote.getString("ad_ironsource_banner_unit_id");
        }
        if (!remote.getString("ad_ironsource_interstitial_unit_id").isEmpty()) {
            AppConfig.ads.ad_ironsource_interstitial_unit_id = remote.getString("ad_ironsource_interstitial_unit_id");
        }

        if (!remote.getString("ad_unity_game_id").isEmpty()) {
            AppConfig.ads.ad_unity_game_id = remote.getString("ad_unity_game_id");
        }
        if (!remote.getString("ad_unity_banner_unit_id").isEmpty()) {
            AppConfig.ads.ad_unity_banner_unit_id = remote.getString("ad_unity_banner_unit_id");
        }
        if (!remote.getString("ad_unity_interstitial_unit_id").isEmpty()) {
            AppConfig.ads.ad_unity_interstitial_unit_id = remote.getString("ad_unity_interstitial_unit_id");
        }
    }
}

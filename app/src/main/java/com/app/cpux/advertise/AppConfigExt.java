package com.app.cpux.advertise;

import com.app.cpux.AppConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import dreamspace.ads.sdk.data.AdNetworkType;

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

                }
            }
            AppConfig.ads.ad_networks = adNetworkTypes;
        }

        if (!remote.getString("retry_from_start_max").isEmpty()) {
            try {
                AppConfig.ads.retry_from_start_max = Integer.parseInt(remote.getString("retry_from_start_max"));
            } catch (Exception e) {
            }
        }

        if (!remote.getString("ad_enable_gdpr").isEmpty()) {
            AppConfig.ads.ad_enable_gdpr = Boolean.parseBoolean(remote.getString("ad_enable_gdpr"));
        }

        if (!remote.getString("ad_main_banner").isEmpty()) {
            AppConfig.ads.ad_main_banner = Boolean.parseBoolean(remote.getString("ad_main_banner"));
        }
        if (!remote.getString("ad_main_interstitial").isEmpty()) {
            AppConfig.ads.ad_main_interstitial = Boolean.parseBoolean(remote.getString("ad_main_interstitial"));
        }
        if (!remote.getString("ad_global_open_app").isEmpty()) {
            AppConfig.ads.ad_global_open_app = Boolean.parseBoolean(remote.getString("ad_global_open_app"));
        }
        if (!remote.getString("ad_splash_open_app").isEmpty()) {
            AppConfig.ads.ad_splash_open_app = Boolean.parseBoolean(remote.getString("ad_splash_open_app"));
        }

        if (!remote.getString("ad_inters_interval").isEmpty()) {
            try {
                AppConfig.ads.ad_inters_interval = Integer.parseInt(remote.getString("ad_inters_interval"));
            } catch (Exception e) {
            }
        }

        if (!remote.getString("ad_replace_unsupported_open_app_with_interstitial_on_splash").isEmpty()) {
            AppConfig.ads.ad_replace_unsupported_open_app_with_interstitial_on_splash = Boolean.parseBoolean(remote.getString("ad_replace_unsupported_open_app_with_interstitial_on_splash"));
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
        if (!remote.getString("ad_admob_rewarded_unit_id").isEmpty()) {
            AppConfig.ads.ad_admob_rewarded_unit_id = remote.getString("ad_admob_rewarded_unit_id");
        }
        if (!remote.getString("ad_admob_open_app_unit_id").isEmpty()) {
            AppConfig.ads.ad_admob_open_app_unit_id = remote.getString("ad_admob_open_app_unit_id");
        }


        if (!remote.getString("ad_manager_banner_unit_id").isEmpty()) {
            AppConfig.ads.ad_manager_banner_unit_id = remote.getString("ad_manager_banner_unit_id");
        }
        if (!remote.getString("ad_manager_interstitial_unit_id").isEmpty()) {
            AppConfig.ads.ad_manager_interstitial_unit_id = remote.getString("ad_manager_interstitial_unit_id");
        }
        if (!remote.getString("ad_manager_rewarded_unit_id").isEmpty()) {
            AppConfig.ads.ad_manager_rewarded_unit_id = remote.getString("ad_manager_rewarded_unit_id");
        }
        if (!remote.getString("ad_manager_open_app_unit_id").isEmpty()) {
            AppConfig.ads.ad_manager_open_app_unit_id = remote.getString("ad_manager_open_app_unit_id");
        }

        if (!remote.getString("ad_fan_banner_unit_id").isEmpty()) {
            AppConfig.ads.ad_fan_banner_unit_id = remote.getString("ad_fan_banner_unit_id");
        }
        if (!remote.getString("ad_fan_interstitial_unit_id").isEmpty()) {
            AppConfig.ads.ad_fan_banner_unit_id = remote.getString("ad_fan_banner_unit_id");
        }
        if (!remote.getString("ad_fan_rewarded_unit_id").isEmpty()) {
            AppConfig.ads.ad_fan_rewarded_unit_id = remote.getString("ad_fan_rewarded_unit_id");
        }

        if (!remote.getString("ad_ironsource_app_key").isEmpty()) {
            AppConfig.ads.ad_ironsource_app_key = remote.getString("ad_ironsource_app_key");
        }
        if (!remote.getString("ad_ironsource_banner_unit_id").isEmpty()) {
            AppConfig.ads.ad_ironsource_banner_unit_id = remote.getString("ad_ironsource_banner_unit_id");
        }
        if (!remote.getString("ad_ironsource_rewarded_unit_id").isEmpty()) {
            AppConfig.ads.ad_ironsource_rewarded_unit_id = remote.getString("ad_ironsource_rewarded_unit_id");
        }
        if (!remote.getString("ad_ironsource_interstitial_unit_id").isEmpty()) {
            AppConfig.ads.ad_ironsource_interstitial_unit_id = remote.getString("ad_ironsource_interstitial_unit_id");
        }

    }
}

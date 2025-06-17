package com.app.cpux;

import com.app.cpux.advertise.AppConfigExt;

import java.io.Serializable;

import dreamspace.ads.sdk.data.AdNetworkType;

public class AppConfig extends AppConfigExt implements Serializable {

    /* -------------------------------------- INSTRUCTION : ----------------------------------------
     * This is config file used for this app, you can configure Ads, Notification, and General data from this file
     * some values are not explained and can be understood easily according to the variable name
     * value can change remotely (optional), please read documentation to follow instruction
     *
     * variable with UPPERCASE name will NOT fetch / replace with remote config
     * variable with LOWERCASE name will fetch / replace with remote config
     * See video Remote Config tutorial https://www.youtube.com/watch?v=tOKXwOTqOzA
     ----------------------------------------------------------------------------------------------*/

    /* set true for fetch config with firebase remote config, */
    public static final boolean USE_REMOTE_CONFIG = true;

    /* config for Ad Network */
    public static class Ads {

        /* enable disable ads */
        public boolean ad_enable = true;

        /* MULTI Ad network selection,
         * Fill this array to enable ad backup flow, left this empty to use single ad_network above
         * app will try show sequentially from this array
         * example flow ADMOB > FAN > IRONSOURCE
         *
         * OPTION :
         * ADMOB,MANAGER, FAN,IRONSOURCE, FAN_BIDDING_ADMOB, FAN_BIDDING_AD_MANAGER, FAN_BIDDING_IRONSOURCE
         * */
        public AdNetworkType[] ad_networks = {
                AdNetworkType.ADMOB,
                AdNetworkType.IRONSOURCE,
                AdNetworkType.FAN,
        };

        /* ad backup flow retry attempt cycle */
        public Integer retry_from_start_max = 2;

        public boolean ad_enable_gdpr = true;

        /* disable enable ads each page */
        public boolean ad_main_banner = true;
        public boolean ad_main_interstitial = true;
        public boolean ad_splash_open_app = false;
        public boolean ad_global_open_app = false;

        /* when ad networks not supported open app format, it will replace with interstitial format
         * for placement after plash screen only */
        public boolean ad_replace_unsupported_open_app_with_interstitial_on_splash = true;

        /* maximum load time in second for open app ads */
        public Integer limit_time_open_app_loading = 10;

        /* show interstitial after several action, this value for action counter */
        public Integer ad_inters_interval = 5;

        /* ad unit for ADMOB */
        public String ad_admob_publisher_id = "pub-4553889194429284";
        public String ad_admob_banner_unit_id = "ca-app-pub-4553889194429284/9940125078";
        public String ad_admob_interstitial_unit_id = "ca-app-pub-4553889194429284/4503169408";
        public String ad_admob_rewarded_unit_id = "ca-app-pub-4553889194429284/6364104460";
        public String ad_admob_open_app_unit_id = "ca-app-pub-4553889194429284/1752882987";

        /* ad unit for Google Ad Manager */
        public String ad_manager_banner_unit_id = "/6499/example/banner";
        public String ad_manager_interstitial_unit_id = "/6499/example/interstitial";
        public String ad_manager_rewarded_unit_id = "/6499/example/rewarded";
        public String ad_manager_open_app_unit_id = "/6499/example/app-open";

        /* ad unit for FAN */
        public String ad_fan_banner_unit_id = "YOUR_PLACEMENT_ID";
        public String ad_fan_interstitial_unit_id = "YOUR_PLACEMENT_ID";
        public String ad_fan_rewarded_unit_id = "VID_HD_9_16_39S_APP_INSTALL";

        /* ad unit for IRON SOURCE */
        public String ad_ironsource_app_key = "170112cfd";
        public String ad_ironsource_banner_unit_id = "DefaultBanner";
        public String ad_ironsource_rewarded_unit_id = "DefaultRewardedVideo";
        public String ad_ironsource_interstitial_unit_id = "DefaultInterstitial";


    }

}

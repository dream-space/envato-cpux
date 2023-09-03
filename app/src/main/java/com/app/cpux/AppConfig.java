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
         * example flow ADMOB > FAN */
        public AdNetworkType[] ad_networks = {
                AdNetworkType.ADMOB,
                AdNetworkType.FAN
        };

        /* disable enable ads each page */
        public boolean ad_main_banner = true;
        public boolean ad_main_interstitial = true;
        public boolean ad_splash_open_app = false;
        public boolean ad_global_open_app = false;

        public boolean ad_enable_gdpr = true;

        /* show interstitial after several action, this value for action counter */
        public int ad_inters_interval = 2;

        /* maximum load time in second for open app ads */
        public Integer limit_time_open_app_loading = 4;

        /* ad unit for ADMOB */
        public String ad_admob_publisher_id = "pub-4553889194429284";
        public String ad_admob_banner_unit_id = "ca-app-pub-4553889194429284/6832866968";
        public String ad_admob_interstitial_unit_id = "ca-app-pub-4553889194429284/4206703621";
        public String ad_admob_open_app_unit_id = "ca-app-pub-4553889194429284/4952416182";

        /* ad unit for FAN */
        public String ad_fan_banner_unit_id = "838618500856743_838618544190072";
        public String ad_fan_interstitial_unit_id = "838618500856743_838618550856738";
    }

}

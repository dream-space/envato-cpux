package com.app.cpux;

import com.app.cpux.advertise.AppConfigExt;

import java.io.Serializable;

import dreamspace.ads.data.AdNetworkType;

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

        /* trial count for backup ads */
        public int retry_ad_networks = 1;

        /* Ad networks selection and backup,
         * Available ad networks : ADMOB, UNITY, IRONSOURCE */
        public AdNetworkType[] ad_networks = {
                AdNetworkType.IRONSOURCE, AdNetworkType.ADMOB, AdNetworkType.UNITY
        };

        public boolean ad_enable_gdpr = true;

        /* show interstitial after several action, this value for action counter */
        public int ad_inters_interval = 2;

        /* ad unit for ADMOB */
        public String ad_admob_publisher_id = "pub-4553889194429284";
        public String ad_admob_banner_unit_id = "ca-app-pub-4553889194429284/6832866968";
        public String ad_admob_interstitial_unit_id = "ca-app-pub-4553889194429284/4206703621";

        /* ad unit for IRON SOURCE */
        public String ad_ironsource_app_key = "19a2e37a5";
        public String ad_ironsource_banner_unit_id = "DefaultBanner";
        public String ad_ironsource_interstitial_unit_id = "DefaultInterstitial";

        /* ad unit for UNITY */
        public String ad_unity_game_id = "4640907";
        public String ad_unity_banner_unit_id = "Banner_Android";
        public String ad_unity_interstitial_unit_id = "Interstitial_Android";
    }

}

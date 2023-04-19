package com.app.cpux.data;

import android.app.Application;

import com.app.cpux.BuildConfig;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;


public class ThisApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        iniFirebase();
    }

    private void iniFirebase() {
        // initialize firebase
        FirebaseApp.initializeApp(this);
        FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(BuildConfig.DEBUG ? 0 : 60)
                .setFetchTimeoutInSeconds(4)
                .build();
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
    }

}

package com.app.cpux.tools;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.app.cpux.BuildConfig;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

public class Utils {

    private static AppUpdateManager appUpdateManager;
    private static InstallStateUpdatedListener installStateUpdatedListener;

    public static boolean cekConnection(Context context) {
        return Utils.isConnectingToInternet(context);
    }

    private static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public static void rateAction(Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            activity.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    public static void checkGooglePlayUpdateStopListener() {
        if (appUpdateManager != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }

    public static void checkGooglePlayUpdate(Activity activity) {
        if (BuildConfig.DEBUG) return;
        installStateUpdatedListener = state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "New app update is ready!", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Install", view -> {
                    if (appUpdateManager != null) appUpdateManager.completeUpdate();
                });
                snackbar.show();
            } else if (state.installStatus() == InstallStatus.INSTALLED) {
                if (appUpdateManager != null && installStateUpdatedListener != null) {
                    appUpdateManager.unregisterListener(installStateUpdatedListener);
                }
            } else {

            }
        };

        appUpdateManager = AppUpdateManagerFactory.create(activity);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateManager.registerListener(installStateUpdatedListener);
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, activity, 200);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

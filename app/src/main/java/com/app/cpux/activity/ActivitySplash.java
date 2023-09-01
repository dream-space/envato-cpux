package com.app.cpux.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.app.cpux.AppConfig;
import com.app.cpux.R;
import com.app.cpux.advertise.AdNetworkHelper;
import com.app.cpux.advertise.AppConfigExt;
import com.app.cpux.tools.LoaderData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import dreamspace.ads.sdk.listener.ActivityListener;

public class ActivitySplash extends Activity {

    private LoaderData cpu = null;
    private LinearLayout lyt_progress;
    private TextView tv_message;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable workRunnable;
    private AlertDialog alertDialog;
    private boolean remoteConfigLoaded = true;
    private boolean cpuDataLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_message = (TextView) findViewById(R.id.tv_message);
        lyt_progress = (LinearLayout) findViewById(R.id.lyt_progress);
        cpu = new LoaderData(this);

        new LoaderInfo().execute("");

        if (AppConfig.USE_REMOTE_CONFIG) {
            requestRemoteConfig();
        }
    }

    public class LoaderInfo extends AsyncTask<String, String, String> {

        public LoaderInfo() {
            tv_message.setText("Starting...");
        }

        @Override
        protected String doInBackground(String... params) {
            cpuDataLoaded = false;
            try {
                publishProgress("load cpu info");
                Thread.sleep(300);
                cpu.loadCpuInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                publishProgress("load battery info");
                Thread.sleep(300);
                cpu.loadBateryInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                publishProgress("load device info");
                Thread.sleep(300);
                cpu.loadDeviceInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                publishProgress("load system info");
                Thread.sleep(300);
                cpu.loadSystemInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                publishProgress("load sensor info");
                Thread.sleep(300);
                cpu.loadSupportInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                publishProgress("please wait..");
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cpuDataLoaded = true;
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            tv_message.setText(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            cpuDataLoaded = true;
            //lyt_progress.setVisibility(View.GONE);
            startActivityMain();
            super.onPostExecute(result);
        }

    }

    private void requestRemoteConfig() {
        remoteConfigLoaded = false;
        Log.d("REMOTE_CONFIG", "requestRemoteConfig");
        FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this, (OnCompleteListener<Boolean>) task -> {
            if (task.isSuccessful()) {
                remoteConfigLoaded = true;
                Log.d("REMOTE_CONFIG", "SUCCESS");
                boolean updated = task.getResult();
                AppConfigExt.setFromRemoteConfig(firebaseRemoteConfig);

                // init ads
                new AdNetworkHelper(this).init();
                ActivityListener.currentActivity = this;
                AdNetworkHelper.initActivityListener(getApplication());

                startActivityMain();
            } else {
                Log.d("REMOTE_CONFIG", "FAILED");
                dialogFailedRemoteConfig("Failed when load data");
            }
        });

        // add timer to prevent too long waiting about 10 sec
        workRunnable = () -> dialogFailedRemoteConfig("Failed when load data");
        handler.postDelayed(workRunnable, 6 * 1000);
    }

    private void startActivityMain() {
        if (workRunnable != null) handler.removeCallbacks(workRunnable);
        if (!remoteConfigLoaded || !cpuDataLoaded) return;
        // init open ads for admob
        AdNetworkHelper.loadAndShowOpenAppAd(this, AppConfig.ads.ad_splash_open_app, () -> {
            new Handler(getMainLooper()).postDelayed(() -> {
                Intent i = new Intent(ActivitySplash.this, ActivityMain.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(i);
                finish();
            }, 50);
        });
    }

    public void dialogFailedRemoteConfig(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Failed");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("RETRY", (dialog, which) -> {
            dialog.dismiss();
            requestRemoteConfig();
        });
        alertDialog = builder.show();
    }

}

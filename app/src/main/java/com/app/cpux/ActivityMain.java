package com.app.cpux;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.cpux.advertise.AdNetworkHelper;
import com.app.cpux.data.AppConfig;
import com.app.cpux.fragment.FragmentAbout;
import com.app.cpux.fragment.FragmentInfo;
import com.app.cpux.tools.LoaderData;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private LinearLayout lyt_progress;
    private TextView tv_message;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        iniComponent();
        prepareAds();
    }

    private void iniComponent() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mSectionsPagerAdapter.addFragment(new FragmentInfo(), getString(R.string.tab_title_cpu));
        mSectionsPagerAdapter.addFragment(new FragmentInfo(), getString(R.string.tab_title_device));
        mSectionsPagerAdapter.addFragment(new FragmentInfo(), getString(R.string.tab_title_system));
        mSectionsPagerAdapter.addFragment(new FragmentInfo(), getString(R.string.tab_title_battery));
        mSectionsPagerAdapter.addFragment(new FragmentInfo(), getString(R.string.tab_title_sensor));
        mSectionsPagerAdapter.addFragment(new FragmentAbout(), getString(R.string.tab_title_about));

        tv_message = (TextView) findViewById(R.id.tv_message);
        lyt_progress = (LinearLayout) findViewById(R.id.lyt_progress);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(6);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                showInterstitialAd();
            }
        });
        lyt_progress.setVisibility(View.GONE);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private AdNetworkHelper adNetworkHelper;

    private void prepareAds() {
        adNetworkHelper = new AdNetworkHelper(this);
        adNetworkHelper.showGDPR();
        adNetworkHelper.loadBannerAd(AppConfig.ADS_MAIN_BANNER);
        adNetworkHelper.loadInterstitialAd(AppConfig.ADS_MAIN_INTERSTITIAL);
    }

    public void showInterstitialAd() {
        adNetworkHelper.showInterstitialAd(AppConfig.ADS_MAIN_INTERSTITIAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    private Menu menu;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.refresh) {
            new LoaderInfo(this).execute("");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class LoaderInfo extends AsyncTask<String, String, String> {
        LoaderData cpu = null;
        String status = "failed";
        Context context;

        public LoaderInfo(Activity act) {
            context = act;
            cpu = new LoaderData(act);
            mViewPager.setVisibility(View.INVISIBLE);
            lyt_progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                publishProgress("load cpu info");
                Thread.sleep(200);
                cpu.loadCpuInfo();

                publishProgress("load battery info");
                Thread.sleep(200);
                cpu.loadBateryInfo();

                publishProgress("load device info");
                Thread.sleep(200);
                cpu.loadDeviceInfo();

                publishProgress("load system info");
                Thread.sleep(200);
                cpu.loadSystemInfo();

                publishProgress("load sensor info");
                Thread.sleep(200);
                cpu.loadSupportInfo();

                status = "succced";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            tv_message.setText(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            mViewPager.setVisibility(View.VISIBLE);
            lyt_progress.setVisibility(View.GONE);
            if (!status.equals("failed")) {
                Toast.makeText(context, "Info updated", Toast.LENGTH_SHORT).show();
                //refresh view
                mSectionsPagerAdapter.notifyDataSetChanged();
            }
            super.onPostExecute(result);
        }

    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = mFragmentList.get(position);
            Bundle args = new Bundle();
            args.putString(FragmentInfo.ARG_SECTION_TITLE, mFragmentTitleList.get(position));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

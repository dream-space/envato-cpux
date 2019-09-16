package com.app.cpux;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.app.cpux.data.AppConfig;
import com.app.cpux.data.GDPR;
import com.app.cpux.fragment.FragmentAbout;
import com.app.cpux.fragment.FragmentInfo;
import com.app.cpux.tools.LoaderData;
import com.app.cpux.tools.Utils;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    //for ads
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        iniComponent();
    }

    private void iniComponent() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mSectionsPagerAdapter.addFragment(new FragmentInfo(), getString(R.string.tab_title_cpu));
        mSectionsPagerAdapter.addFragment(new FragmentInfo(), getString(R.string.tab_title_device));
        mSectionsPagerAdapter.addFragment(new FragmentInfo(), getString(R.string.tab_title_system));
        mSectionsPagerAdapter.addFragment(new FragmentInfo(), getString(R.string.tab_title_battery));
        mSectionsPagerAdapter.addFragment(new FragmentInfo(), getString(R.string.tab_title_sensor));
        mSectionsPagerAdapter.addFragment(new FragmentAbout(), getString(R.string.tab_title_about));

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(6);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                showInterstitial();
            }
        });
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

    private void initAds() {
        if (AppConfig.ENABLE_ADSENSE) GDPR.updateConsentStatus(this);

        AdRequest adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, GDPR.getBundleAd(this)).build();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(adRequest);

        mAdView = (AdView) findViewById(R.id.ad_view);
        if (AppConfig.ENABLE_ADSENSE && Utils.cekConnection(getApplicationContext())) {
            mAdView.setVisibility(View.VISIBLE);
            mAdView.loadAd(adRequest);
        } else {
            mAdView.setVisibility(View.GONE);
        }

    }

    public void showInterstitial() {
        if (!AppConfig.ENABLE_ADSENSE || !Utils.cekConnection(getApplicationContext())) {
            return;
        }
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    protected void onResume() {
        initAds();
        super.onResume();
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
        switch (item.getItemId()) {
            case R.id.refresh:
                new LoaderInfo(this).execute("");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public class LoaderInfo extends AsyncTask<String, String, String> {
        LoaderData cpu = null;
        String status = "failed";
        Context context;

        public LoaderInfo(Activity act) {
            context = act;
            cpu = new LoaderData(act);
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                cpu.loadCpuInfo();
                cpu.loadBateryInfo();
                cpu.loadDeviceInfo();
                cpu.loadSystemInfo();
                cpu.loadSupportInfo();
                status = "succced";
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            setProgressBarIndeterminateVisibility(false);
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

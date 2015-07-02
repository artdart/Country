package com.dataart.country.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dataart.country.Country;
import com.dataart.country.R;
import com.dataart.country.network.CountryIntentService;
import com.dataart.country.network.RandomCountryCommand;
import com.dataart.country.network.Receiver;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager pager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getFragmentManager()));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.front));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.back));
        if (savedInstanceState == null) {
            CountryIntentService.start(this, countryReceiver);
        }
        pager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.setRotationY(180 * position);
            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            CountryIntentService.start(this, countryReceiver);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private final Receiver countryReceiver = new Receiver(new Handler()) {
        @Override
        protected void handleResult(Bundle bundle) {
            Country country = RandomCountryCommand.getCountry(bundle);
            ((FaceFragment) ((PagerAdapter) pager.getAdapter()).getItem(0)).fillCountry(country);
        }

        @Override
        protected void handleError(String error) {
            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
        }
    };

    private class PagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>(2);
            fragments.add(new FaceFragment());
            fragments.add(new BackFragment());

        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}

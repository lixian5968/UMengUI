package com.zongbutech.umenguilxcopy;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.home_tablayout)
    TabLayout home_tablayout;
    @Bind(R.id.home_viewpager)
    ViewPager home_viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umeng_comm_community_frag_layout);
        ButterKnife.bind(this);


        List<String> newsList = new ArrayList<>();
        newsList.add("热门");
        newsList.add("推荐");
        newsList.add("关注");
        newsList.add("话题");
        MainHomeFragmentAdaper adapter = new MainHomeFragmentAdaper(getSupportFragmentManager(), newsList);
        home_viewpager.setAdapter(adapter);
        home_viewpager.setOffscreenPageLimit(1);
        home_tablayout.setupWithViewPager(home_viewpager);
        if (newsList.size() > 3) {
            home_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }


    }


    public class MainHomeFragmentAdaper extends FragmentPagerAdapter {
        List<String> newsList;

        public MainHomeFragmentAdaper(FragmentManager childFragmentManager, List<String> newsList) {
            super(childFragmentManager);
            this.newsList = newsList;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                FragmentHotDemo demo = new FragmentHotDemo();
                return demo;
            } else {
                FragmentDemo demo = new FragmentDemo();
                Bundle bd = new Bundle();
                bd.putSerializable("lx", newsList.get(position));
                demo.setArguments(bd);
                return demo;
            }

        }

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return newsList.get(position);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.d3if3804160.shareeat;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.d3if3804160.shareeat.Fragment.DetailRestaurant_Detail;
import com.d3if3804160.shareeat.Fragment.DetailRestaurant_Menu;
import com.d3if3804160.shareeat.Fragment.DetailRestaurant_Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fajar on 11/10/2016.
 */


public class DetailRestaurant extends AppCompatActivity {
    String id;
    private Toolbar toolbar;
    private ViewPager pager;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant);

        //set up toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar_restaurant);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //inisialisasi tab dan pager
        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        setupViewPager(pager);
        tabs.setupWithViewPager(pager);
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("ID")!= null)
        {
            id=bundle.getString("ID");
            System.out.println(id);
        }



    }
    public String getId() {
        return id;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DetailRestaurant_Review(), "REVIEW");
        adapter.addFragment(new DetailRestaurant_Detail(), "DETAIL");
        adapter.addFragment(new DetailRestaurant_Menu(), "MENU");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
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
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}

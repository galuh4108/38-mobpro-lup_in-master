package com.d3if3804160.shareeat.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.d3if3804160.shareeat.Fragment.DetailRestaurant_Detail;
import com.d3if3804160.shareeat.Fragment.DetailRestaurant_Menu;
import com.d3if3804160.shareeat.Fragment.DetailRestaurant_Review;

/**
 * Created by Fajar on 11/10/2016.
 */

public class DetailRestaurant_Adapter extends FragmentPagerAdapter {
    //nama tab nya
    String[] title = new String[]{
            "Review", "Detail", "Menu"
    };

    public DetailRestaurant_Adapter(FragmentManager fm) {
        super(fm);
    }

    //method ini yang akan memanipulasi penampilan Fragment dilayar
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new DetailRestaurant_Review();
                break;
            case 1:
                fragment = new DetailRestaurant_Detail();
                break;
            case 2 :
                fragment = new DetailRestaurant_Menu();
                break;
            default:
                fragment = null;
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return title.length;
    }
}


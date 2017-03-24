package com.d3if3804160.shareeat;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.d3if3804160.shareeat.Fragment.TambahTempatMakanFragment;

import static com.d3if3804160.shareeat.MainActivity.CURRENT_TAG;

public class TambahTempatMakanActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_makanan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadHomeFragment();
    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, new TambahTempatMakanFragment(), CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }
}

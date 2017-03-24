package com.d3if3804160.shareeat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.d3if3804160.shareeat.Fragment.KategoriFragment;
import com.d3if3804160.shareeat.Fragment.LoginFragment;
import com.d3if3804160.shareeat.Fragment.TerdekatFragment;
import com.d3if3804160.shareeat.Util.Constant;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Deni Cahya Wintaka on 10/18/2016.
 */

public class MainActivity  extends AppCompatActivity   {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private Toolbar toolbar;
    Location mLastLocation=null;
    private FirebaseAuth auth;



    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_TERDEKAT = "terdekat";
    private static final String TAG_KATEGORI = "kategori";
    private static final String TAG_TEMPATMAKAN = "tempat_makan";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_ABOUT = "about";
    public static String CURRENT_TAG = TAG_TERDEKAT;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();
        auth = FirebaseAuth.getInstance();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        //  searchView = (SearchView) navHeader.findViewById(R.id.search_bar);
        // imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);


        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        if (isPermissionAllowed()) {


            // load nav menu header data

            // initializing navigation menu
            setUpNavigationView();

            if (savedInstanceState == null) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_TERDEKAT;
                loadHomeFragment();
            }
        }
        else{
            requestPermission();
        }
    }


    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            //toggleFab();
            return;
        }


        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                TerdekatFragment terdekatFragment = new TerdekatFragment();
                return terdekatFragment;
            case 1:
                // photos
                KategoriFragment rekomendasiFragment = new KategoriFragment();
                return rekomendasiFragment;
            case 2:
                TambahTempatdanMenu tambahTempatMakanFragment = new TambahTempatdanMenu();
                return tambahTempatMakanFragment;
            case 4:
                // settings fragment
                LoginFragment sign_upFragment= new LoginFragment();
                return sign_upFragment;

//            case 5:
//                //about
//                About aboutFragment = new About();
//                return aboutFragment;

            default:
                return new TerdekatFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_terdekat:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_TERDEKAT;
                        break;
                    case R.id.nav_cari:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_KATEGORI;
                        break;

                    case R.id.nav_tambah:
                        if (auth.getCurrentUser()!= null){
                            navItemIndex = 2;
                            CURRENT_TAG = TAG_TEMPATMAKAN;
                        }else{
                            dialogLogin();

                        }

                        break;

                    case R.id.nav_data :
                        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                        break;
                    case R.id.nav_login:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_LOGIN;
                        break;
//                    case R.id.nav_about_us:
//                        navItemIndex = 5;
//                        CURRENT_TAG = TAG_ABOUT;
//                        break;
                  /*   case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        return true;*/
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_TERDEKAT;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected


        // when fragment is notifications, load the menu created for notifications
       /* if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            startActivity(new Intent(getApplicationContext(),AccountActivity.class));
            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'

        // user is in notifications fragment
        // and selected 'Clear All'
       /* if (id == R.id.next) {
                navItemIndex = 4;
                CURRENT_TAG = TAG_MAPS;
                loadHomeFragment();
        }
*/
        return super.onOptionsItemSelected(item);
    }




    //We are calling this method to check the permission status
    private boolean isPermissionAllowed() {

        //Getting the permission status
        int access_fine_location = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int access_coarse_location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        //If permission is granted returning true
        if (access_fine_location == PackageManager.PERMISSION_GRANTED && access_coarse_location == PackageManager.PERMISSION_GRANTED){
            return true;
        }


        //If permission is not granted returning false
        return false;
    }

    private void dialogLogin(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login")
                .setMessage("Harus Login Terlebih Dahulu")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        navItemIndex=4;
                        CURRENT_TAG = TAG_LOGIN;

                        loadHomeFragment();
                    }

                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadHomeFragment();
                    }
                })
                .show();
    }

    //Requesting permission
    private void requestPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
            //jelasin fungsi izin
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},Constant.ACCESS_FINE_LOCATION);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},Constant.ACCESS_COARSE_LOCATION);

    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Constant.ACCESS_FINE_LOCATION :
                //If permission is granted
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    //Displaying a toast
                    Toast.makeText(this, R.string.allow_permission,Toast.LENGTH_LONG).show();
                }else{
                    //Displaying another toast if permission is not granted
                    Toast.makeText(this, R.string.denied_permission,Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}

package com.liscanner;

import com.facebook.react.ReactActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.reactlibrary.JSBundleManager;
import com.reactlibrary.JSBundleManagerActivity;
import com.reactlibrary.model.Connection;

import static com.reactlibrary.view.ListConnectionActivity.versionList;

public class MainActivity extends JSBundleManagerActivity implements  JSBundleManager.Interface  , DefaultHardwareBackBtnHandler, NavigationView.OnNavigationItemSelectedListener {


    private HelloFragment mViewFragment;
    private ReactInstanceManager mReactInstanceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mReactInstanceManager =
                ((MainApplication) getApplication()).getReactNativeHost().getReactInstanceManager();



        mViewFragment = new HelloFragment();

        if (mViewFragment != null) {
            mViewFragment.setMainApplication((ReactApplication) getApplication());
            mViewFragment.setmReactInstanceManager(mReactInstanceManager);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.container, mViewFragment).commit();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();

//        isHomeScreen();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        isHomeScreen();
    }

    @Override
    protected void refreshFragment() {

    }

    @Override
    protected void onStart() {
        super.onStart();
            super.onStart();
//            if(updater == null) {
//                updater = getBundleManager(getApplicationContext());
//
//            }
//            updater.setParentActivity(this);

    }

    /*
      * Any activity that uses the ReactFragment or ReactActivty
      * Needs to call onHostPause() on the ReactInstanceManager
      */
    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause();
        }
    }

    /*
     * Same as onPause - need to call onHostResume
     * on our ReactInstanceManager
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this,this);
            }
        }

    public boolean isHomeScreen() {

        WritableMap params = Arguments.createMap();
        mReactInstanceManager.getCurrentReactContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("onBackPressed",params);

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            Bundle dataBundle =  data.getExtras();
            Connection newConnection  = dataBundle.getParcelable("URL");
            versionList.add(newConnection);
        }
        else {
            if (resultCode == Activity.RESULT_OK) {
                Bundle dataBundle = data.getExtras();
                Connection newConnection = dataBundle.getParcelable("URL");

                if (newConnection != null) {
                    versionList.add(newConnection);
                    JSBundleManagerActivity.getBundleManager(getApplicationContext()).setHostnameForRelativeDownloadURLs(newConnection.getConnectionUrl());
                    JSBundleManagerActivity.getBundleManager(getBaseContext()).setUpdateMetadataUrl(newConnection.getConnectionUrl()+"/android/LIS.helloworld/update.json");
                    JSBundleManagerActivity.getBundleManager(getApplicationContext()).checkForUpdates();
                }
            }
        }
    }
}

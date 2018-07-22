package com.mweslacey.city.foodfail.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import com.mweslacey.city.foodfail.R;
import com.mweslacey.city.foodfail.fragment.LandingFragment;
import com.mweslacey.city.foodfail.fragment.LocalMapFragment;
import com.mweslacey.city.foodfail.fragment.SearchFragment;
import com.mweslacey.city.foodfail.model.db.InspectionDatabase;
import com.mweslacey.city.foodfail.model.pojo.FacilityAndLastInspection;
import java.util.List;

/**
 * Entry point for Food Fail Application. Main controller for fragments.
 */
public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private static final String LOCAL_TAG = "Local Facilities";
  private static final String SEARCH_TAG = "Search Facilities";

  private Fragment fragment;
  private FragmentManager fManager;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    fManager = getSupportFragmentManager();
    new SearchAsync().execute("");
    fragment = LandingFragment.newInstance();
    setContentView(R.layout.activity_main);
    fManager.beginTransaction()
        .addToBackStack(null)
        .add(R.id.fragment_container, fragment).commit();
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  /**
   * Hides navigation drawer on upward navigation.
   */
  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    loadFragment(item.getItemId());
    drawer.closeDrawer(Gravity.START);
    return true;
  }

  // Identify appropriate fragment and attach to activity with fragment manager.
  private void loadFragment(int itemId) {
    switch (itemId) {
      case R.id.nav_local:
        if (fManager.findFragmentByTag(LOCAL_TAG) == null) {
          // Remove last fragment to allow for continuous peer navigation
          fManager.popBackStackImmediate();
          fragment = LocalMapFragment.newInstance();
          fManager.beginTransaction()
              .addToBackStack(null)
              .replace(R.id.fragment_container, fragment, LOCAL_TAG).commit();
          break;
        }
        return;
      case R.id.nav_search:
        if (fManager.findFragmentByTag(SEARCH_TAG) == null) {
          // Remove last fragment to allow for continuous peer navigation
          fManager.popBackStackImmediate();
          fragment = SearchFragment.newInstance();
          fManager.beginTransaction()
              .addToBackStack(null)
              .replace(R.id.fragment_container, fragment, SEARCH_TAG).commit();
        } else {
          return;
        }
    }
  }


  /*
  Custom AsyncTask used to query database.
  This is an attempt to pre-load the database prior to user navigating to the search view.
   */
  private class SearchAsync extends AsyncTask<String, Void, List<FacilityAndLastInspection>> {

    @Override
    protected List<FacilityAndLastInspection> doInBackground(String... searches) {
      return InspectionDatabase.getInstance(MainActivity.this)
          .getInspectionDAO().getMatches(searches[0]);
    }
  }

}

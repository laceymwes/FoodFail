package com.mweslacey.city.foodfail.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.mweslacey.city.foodfail.R;
import com.mweslacey.city.foodfail.fragment.FacilityDetailFragment;
import com.mweslacey.city.foodfail.fragment.LandingFragment;
import com.mweslacey.city.foodfail.fragment.LocalMapFragment;
import com.mweslacey.city.foodfail.fragment.SearchFragment;
import com.mweslacey.city.foodfail.model.db.InspectionDatabase;
import com.mweslacey.city.foodfail.model.pojo.FacilityAndLastInspection;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private static final String LOCAL_TAG = "Local Facilities";
  private static final String SEARCH_TAG = "Search Facilities";

  private LandingFragment landingFragment;
  private Fragment fragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new SearchAsync().execute("");
    landingFragment = LandingFragment.newInstance();
    setContentView(R.layout.activity_main);
    getSupportFragmentManager().beginTransaction()
        .addToBackStack(null)
        .add(R.id.fragment_container, landingFragment).commit();
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

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

  private void loadFragment(int itemId) {
    switch(itemId) {
      case R.id.nav_local:
        fragment = LocalMapFragment.newInstance();
        break;
      case R.id.nav_search:
        fragment = SearchFragment.newInstance();
    }
    getSupportFragmentManager().beginTransaction()
        .addToBackStack(null)
        .replace(R.id.fragment_container, fragment).commit();
  }


  private class SearchAsync extends AsyncTask<String, Void, List<FacilityAndLastInspection>> {

    @Override
    protected List<FacilityAndLastInspection> doInBackground(String... searches) {
      return InspectionDatabase.getInstance(MainActivity.this)
          .getInspectionDAO().getMatches(searches[0]);
    }
  }

}

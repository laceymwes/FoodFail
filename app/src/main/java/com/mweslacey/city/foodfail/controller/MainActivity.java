package com.mweslacey.city.foodfail.controller;

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
import com.mweslacey.city.foodfail.R;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,
    LandingFragment.OnFragmentInteractionListener,
    LocalMapFragment.OnFragmentInteractionListener,
    SearchFragment.OnFragmentInteractionListener{

  public static final String SEARCH_FRAGMENT_TITLE = "Search";
  public static final String LOCAL_FRAGMENT_TITLE = "Local Facilities";
  private LandingFragment landingFragment;
  private Fragment fragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }



  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    //noinspection SimplifiableIfStatement
    return true;
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

  public void loadFragment(int itemId) {
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

  @Override
  public void searchTitle() {
    getSupportActionBar().setTitle(SEARCH_FRAGMENT_TITLE);
  }

  @Override
  public void localTitle() {
    getSupportActionBar().setTitle(LOCAL_FRAGMENT_TITLE);
  }
}

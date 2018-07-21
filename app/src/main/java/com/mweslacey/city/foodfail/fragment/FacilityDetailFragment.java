package com.mweslacey.city.foodfail.fragment;

import static android.support.constraint.Constraints.TAG;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mweslacey.city.foodfail.BuildConfig;
import com.mweslacey.city.foodfail.R;
import com.mweslacey.city.foodfail.service.Result;
import com.mweslacey.city.foodfail.model.db.InspectionDatabase;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;
import com.mweslacey.city.foodfail.model.pojo.FacilityAndAllInspections;
import com.mweslacey.city.foodfail.service.GeoCodeService;
import java.io.IOException;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacilityDetailFragment extends Fragment implements OnMapReadyCallback {

  private static final String ARG_FACILITY_KEY =
      "com.mweslacey.city.foodfail.model.facilitydetailfragment.FacilityKey";
  public static final String MAP_BUNDLE_KEY = "MapBundleKey";

  private int facilityKey;
  private Facility facility;
  private List<Inspection> inspections;
  private ViewPager viewPager;
  private GoogleMap gMap;
  private GeoCodeService geoService;
  private Result result;
  private MapView mapView;

  private String addressParameter;

  public FacilityDetailFragment() {
    // Required empty public constructor
  }

  public static FacilityDetailFragment newInstance(int facilityKey) {
    FacilityDetailFragment fragment = new FacilityDetailFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_FACILITY_KEY, facilityKey);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    if (googleMap != null) {
      gMap = googleMap;
      setMapLocation();
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      facilityKey = getArguments().getInt(ARG_FACILITY_KEY);
    }
    View dec = getActivity().getWindow().getDecorView();
    // Hide navigation drawer
    dec.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_facility_detail, container, false);
    mapView = view.findViewById(R.id.detail_map);
    mapView.onCreate(savedInstanceState);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupService();
    viewPager = view.findViewById(R.id.detail_view_pager);
    new AsyncQuery().execute(facilityKey);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }

  @Override
  public void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  public void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
    mapView.onStart();
  }

  @Override
  public void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  private void setPagerAdapter() {
    viewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int position) {
        Inspection inspection = inspections.get(position);
        return InspectionFragment.newInstance(inspection.getInspectionDate(),
            inspection.getResultDesc(), inspection.getViolationCode(),
            inspection.getViolationDesc(), inspection.getInspectionMemo());
      }

      @Override
      public int getCount() {
        return inspections.size();
      }
    });
  }

  private void setupService() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(Level.BODY);
    OkHttpClient.Builder httpClient = new Builder();
    httpClient.addInterceptor(logging);
    Gson gson = new GsonBuilder().
        excludeFieldsWithoutExposeAnnotation()
        .create();
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient.build())
        .build();
    geoService = retrofit.create(GeoCodeService.class);
  }

  private void setMapLocation() {
    double lat = result.getGeometryCoordinates()[0];
    double lng = result.getGeometryCoordinates()[1];
    gMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
    CameraUpdate update = CameraUpdateFactory.newLatLng(new LatLng(lat, lng));
    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
  }

  private class AsyncQuery extends AsyncTask<Integer, Void, FacilityAndAllInspections> {

    @Override
    protected FacilityAndAllInspections doInBackground(Integer... keys) {
      return InspectionDatabase.getInstance(FacilityDetailFragment.this.getContext())
          .getInspectionDAO()
          .getInspections(keys[0]);
    }

    @Override
    protected void onPostExecute(FacilityAndAllInspections facilityInspections) {
      InspectionDatabase.forgetInstance(getActivity());
      FacilityDetailFragment.this.facility = facilityInspections.getFacility();
      FacilityDetailFragment.this.inspections = facilityInspections.getInspections();
      FacilityDetailFragment.this.setPagerAdapter();
      FacilityDetailFragment.this.addressParameter = facility.getStreetNumber() + "+"
          + facility.getStreetName() + ",+" + "Albuquerque" + ",+" + facility.getState()
          + "+" + facility.getZip();
      Log.d(TAG, "addressParameter : " + addressParameter);
      new AsyncRequest().execute();
    }
  }

  private class AsyncRequest extends AsyncTask<Void, Void, Result> {

    private Result result;

    @Override
    protected void onProgressUpdate(Void... values) {
      super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Result result) {
      Context context = getActivity();
      Toast.makeText(context,
          context.getString(R.string.geo_request_failure), Toast.LENGTH_SHORT).show();
      Log.e(TAG, "Response Body : " + result.toString() );
    }

    @Override
    protected Result doInBackground(Void... voids) {
      try {
        Response<Result> response = geoService.get(addressParameter, BuildConfig.API_KEY).execute();
//        Log.e(TAG, "Response Code: " + response.code());
//        Log.e(TAG, "doInBackground: " + response.raw().body().toString());

        if (response.isSuccessful() && response.body() != null) {
          result = response.body();
        } else {
          cancel(true);
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
      }
      return result;
    }

    @Override
    protected void onPostExecute(Result result) {
      FacilityDetailFragment.this.result = result;
      FacilityDetailFragment.this.mapView.getMapAsync(FacilityDetailFragment.this);
      getActivity().setTitle(facility.getFacilityName());
    }
  }
}

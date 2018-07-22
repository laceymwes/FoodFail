package com.mweslacey.city.foodfail.fragment;

import static android.support.constraint.Constraints.TAG;

import android.Manifest;
import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mweslacey.city.foodfail.BuildConfig;
import com.mweslacey.city.foodfail.R;
import com.mweslacey.city.foodfail.model.db.InspectionDatabase;
import com.mweslacey.city.foodfail.model.entity.Inspection;
import com.mweslacey.city.foodfail.model.pojo.FacilityAndLastInspection;
import com.mweslacey.city.foodfail.service.GeoCodeService;
import com.mweslacey.city.foodfail.service.ReverseResult;
import java.io.IOException;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LocalMapFragment extends Fragment implements OnMapReadyCallback {

  private GoogleMap gMap;
  private ViewPager viewPager;
  private FusedLocationProviderClient locationClient;
  private List<FacilityAndLastInspection> facilities;
  private double lat;
  private double lng;
  private GeoCodeService geoService;
  private ReverseResult rResult;

  public LocalMapFragment() {
    // Required empty public constructor
  }

  public static LocalMapFragment newInstance() {
    return new LocalMapFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    locationClient = LocationServices.getFusedLocationProviderClient(getActivity());
  }

  @Override
  public void onResume() {
    super.onResume();
    getActivity().setTitle(R.string.title_local);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_local_map, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    getActivity().setTitle(R.string.title_local);
    viewPager = view.findViewById(R.id.local_view_pager);
    getLocationPermissions();
    super.onViewCreated(view, savedInstanceState);
  }

  private void getLocationPermissions() {
    if (ContextCompat.checkSelfPermission(getActivity(),
        permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
          Manifest.permission.ACCESS_COARSE_LOCATION)) {
      } else {
        requestPermissions(new String[] {permission.ACCESS_COARSE_LOCATION}, 0);
      }
    } else {
      getDeviceLocation();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
      String permissions[], int[] grantResults) {
    if (grantResults.length > 0
        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      getDeviceLocation();
    } else {
      Toast.makeText(getActivity(), getString(R.string.location_exception),
          Toast.LENGTH_SHORT).show();
    }
  }

  private void getDeviceLocation() {
    try {
      locationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
        @Override
        public void onSuccess(Location location) {
          lat = location.getLatitude();
          lng = location.getLongitude();
          setUpService();
        }
      });
    } catch (SecurityException e) {
      Log.e(TAG, "getLocation has failed: \n");
      e.printStackTrace();
      Toast.makeText(getActivity(), getString(R.string.geo_request_failure), Toast.LENGTH_SHORT)
          .show();
    } finally {
      if (lat != 0.00) {
        setUpService();
      }
    }
  }

  private void setUpService() {
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
    new AsyncRequest().execute();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    gMap = googleMap;
  }

  private void setAdapter() {
    viewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int position) {
        FacilityAndLastInspection facility = facilities.get(position);
        Inspection inspection = facility.getInspection();
        return LocalFacilitiyFragment.newInstance(facility.getName(),
            facility.getAddress(), facility.getLastInspection(),
            inspection.getResultDesc());
      }

      @Override
      public int getCount() {
        return facilities.size();
      }
    });
  }

  private class AsyncRequest extends AsyncTask<Void, Void, ReverseResult> {

    ReverseResult rResult;

    @Override
    protected ReverseResult doInBackground(Void... voids) {
      try {
        Response<ReverseResult> response = geoService
            .getPostal(new String("" + lat + "," + lng)).execute();
        if (response.isSuccessful() && response.body() != null) {
          rResult = response.body();
        }
      } catch (IOException e) {
        Log.e(TAG, "Reverse Geocode Request Failed: ");
        Toast.makeText(getActivity(), getString(R.string.reverse_request_failure),
            Toast.LENGTH_SHORT).show();
        e.printStackTrace();
      } finally {
      }
      return rResult;
    }

    @Override
    protected void onPostExecute(ReverseResult reverseResult) {
      LocalMapFragment.this.rResult = reverseResult;
      new SearchAsync().execute(rResult.getPostalCode());
    }
  }

  private class SearchAsync extends AsyncTask<Integer, Void, List<FacilityAndLastInspection>> {

    @Override
    protected List<FacilityAndLastInspection> doInBackground(Integer... zips) {
      return InspectionDatabase.getInstance(getActivity()).
          getInspectionDAO().getLocalFacilities(zips[0]);
    }

    @Override
    protected void onPostExecute(List<FacilityAndLastInspection> facilityAndLastInspections) {
      LocalMapFragment.this.facilities = facilityAndLastInspections;
      setAdapter();
    }
  }
}

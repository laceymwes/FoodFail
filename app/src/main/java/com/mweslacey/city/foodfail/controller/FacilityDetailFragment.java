package com.mweslacey.city.foodfail.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mweslacey.city.foodfail.BuildConfig;
import com.mweslacey.city.foodfail.R;
import com.mweslacey.city.foodfail.model.Geometry;
import com.mweslacey.city.foodfail.model.db.InspectionDatabase;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;
import com.mweslacey.city.foodfail.model.pojo.FacilityAndAllInspections;
import com.mweslacey.city.foodfail.service.GeoCodeService;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FacilityDetailFragment extends Fragment implements OnMapReadyCallback{

  private static final String ARG_FACILITY_KEY =
      "com.mweslacey.city.foodfail.model.facilitydetailfragment.FacilityKey";

  private int facilityKey;
  private Facility facility;
  private List<Inspection> inspections;
  private ViewPager viewPager;
  private GoogleMap gMap;
  private GeoCodeService geoService;
  private Geometry location;

  private String addressParameter;


  private OnFragmentInteractionListener mListener;

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
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      facilityKey = getArguments().getInt(ARG_FACILITY_KEY);
    }
    setupService();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_facility_detail, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewPager = view.findViewById(R.id.detail_view_pager);
    SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.detail_map);
    mapFragment.getMapAsync(this);
    new AsyncQuery().execute(facilityKey);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
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

  private void setupService(){
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        .create();
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();
    geoService = retrofit.create(GeoCodeService.class);
  }

  private void setMapLocation() {
    
  }

  public interface OnFragmentInteractionListener {

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
      addressParameter = facility.getStreetNumber() + "+" + facility.getStreetName() + ",+"
         + "Albuquerque" + ",+" + facility.getState() + "+" + facility.getZip();
      new AsyncRequest().execute();
    }
  }

  private class AsyncRequest extends AsyncTask<Void, Void, Geometry> {

    private Geometry location;

    @Override
    protected void onPostExecute(Geometry geometry) {
      FacilityDetailFragment.this.location = location;
      setMapLocation();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
      super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Geometry geometry) {
      Context context = getActivity();
      Toast.makeText(context,
          context.getString(R.string.geo_request_failure), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Geometry doInBackground(Void... voids) {
      try {
        Response<Geometry> response = geoService.get(addressParameter, BuildConfig.API_KEY).execute();
        if (response.isSuccessful()) {
          location = response.body();
        }
      } catch (IOException e) {
        //FIXME: Determine best action for failure
      } finally {
        if (location == null){
          cancel(true);
        }
      }
      return location;
    }
  }
}

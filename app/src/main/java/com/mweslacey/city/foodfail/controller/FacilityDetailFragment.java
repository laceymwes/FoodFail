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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.mweslacey.city.foodfail.R;
import com.mweslacey.city.foodfail.model.db.InspectionDatabase;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;
import com.mweslacey.city.foodfail.model.pojo.FacilityAndAllInspections;
import java.util.List;

public class FacilityDetailFragment extends Fragment implements OnMapReadyCallback{

  private static final String ARG_FACILITY_KEY =
      "com.mweslacey.city.foodfail.model.facilitydetailfragment.FacilityKey";

  private int facilityKey;
  private Facility facility;
  private List<Inspection> inspections;
  private ViewPager viewPager;
  GoogleMap gMap;


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
    new AsyncQuery().execute(facilityKey);
    SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.detail_map);
    mapFragment.getMapAsync(this);
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
    }


  }
}

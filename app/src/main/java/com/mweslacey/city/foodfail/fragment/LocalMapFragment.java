package com.mweslacey.city.foodfail.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.mweslacey.city.foodfail.R;


public class LocalMapFragment extends Fragment implements OnMapReadyCallback {

  SupportMapFragment supportMapFragment = new SupportMapFragment();

  GoogleMap gMap;

  public LocalMapFragment() {
    // Required empty public constructor
  }

  public static LocalMapFragment newInstance() {
    return new LocalMapFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    gMap = googleMap;
  }

}

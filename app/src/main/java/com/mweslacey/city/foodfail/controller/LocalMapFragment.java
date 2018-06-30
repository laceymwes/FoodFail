package com.mweslacey.city.foodfail.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

  private OnFragmentInteractionListener mListener;

  public LocalMapFragment() {
    // Required empty public constructor
  }

  public static LocalMapFragment newInstance() {
    return new LocalMapFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    mListener.localTitle();
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_local_map, container, false);
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

  @Override
  public void onMapReady(GoogleMap googleMap) {
    gMap = googleMap;
  }

  public interface OnFragmentInteractionListener {
    public void localTitle();
  }
}

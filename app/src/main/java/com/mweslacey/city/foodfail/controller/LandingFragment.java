package com.mweslacey.city.foodfail.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mweslacey.city.foodfail.R;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link LandingFragment.OnFragmentInteractionListener} interface to handle interaction events. Use
 * the {@link LandingFragment#newInstance} factory method to create an instance of this fragment.
 */
public class LandingFragment extends Fragment {

  private OnFragmentInteractionListener mListener;

  public LandingFragment() {
    // Required empty public constructor
  }

  public static LandingFragment newInstance() {
    return new LandingFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_landing, container, false);
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

  public interface OnFragmentInteractionListener {

  }
}

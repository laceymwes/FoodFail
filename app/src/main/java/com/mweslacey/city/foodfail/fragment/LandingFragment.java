package com.mweslacey.city.foodfail.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mweslacey.city.foodfail.R;

public class LandingFragment extends Fragment {

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
  public void onResume() {
    super.onResume();
    getActivity().setTitle(R.string.title_landing);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getActivity().setTitle(R.string.title_landing);
  }


}

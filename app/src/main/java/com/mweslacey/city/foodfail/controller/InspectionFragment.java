package com.mweslacey.city.foodfail.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mweslacey.city.foodfail.R;
import org.w3c.dom.Text;

public class InspectionFragment extends Fragment {

  public static final String INSPECTION_ID_KEY = "com.mweslacey.city.foodfail.inspectionid";

  private int inspectionID;
  private TextView inspectionDate;
  private TextView resultDesc;
  private TextView violationCode;
  private TextView violationDesc;
  private TextView inspectionMemo;

  public InspectionFragment() {

  }

  public static InspectionFragment newInstance(int inspectionID) {
    InspectionFragment fragment = new InspectionFragment();
    Bundle args = new Bundle();
    args.putInt(INSPECTION_ID_KEY, inspectionID);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inspectionID = getArguments().getInt(INSPECTION_ID_KEY);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.inspection_item, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    inspectionDate = view.findViewById(R.id.inspection_date);
    resultDesc = view.findViewById(R.id.result_desc);
    violationCode = view.findViewById(R.id.violation_code);
    violationDesc = view.findViewById(R.id.violation_desc);
    inspectionMemo = view.findViewById(R.id.inspection_memo);
  }
}

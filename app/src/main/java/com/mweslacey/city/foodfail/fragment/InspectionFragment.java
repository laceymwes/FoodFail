package com.mweslacey.city.foodfail.fragment;

import static android.support.constraint.Constraints.TAG;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.mweslacey.city.foodfail.R;

public class InspectionFragment extends Fragment {

  private static final String INSPECTION_ID_KEY = "com.mweslacey.city.foodfail.inspectionid";
  private static final String DATE_KEY = "com.mweslacey.city.foodfail.date";
  private static final String RESULT_KEY = "com.mweslacey.city.foodfail.result";
  private static final String VIOLATION_CODE_KEY = "com.mweslacey.city.foodfail.vcode";
  private static final String VIOLATION_DESC_KEY = "com.mweslacey.city.foodfail.vdesc";
  private static final String INSPECTION_MEMO = "com.mweslacey.city.foodfail.memo";

  private TextView inspectionDate;
  private TextView resultDesc;
  private TextView violationCode;
  private TextView violationDesc;
  private TextView inspectionMemo;
  private ImageView complianceFlag;

  public InspectionFragment() {

  }

  public static InspectionFragment newInstance(String date, String result, String vCode,
      String vDesc, String memo) {
    InspectionFragment fragment = new InspectionFragment();
    Bundle args = new Bundle();
    args.putString(DATE_KEY, date);
    args.putString(RESULT_KEY, result);
    args.putString(VIOLATION_CODE_KEY, vCode);
    args.putString(VIOLATION_DESC_KEY, vDesc);
    args.putString(INSPECTION_MEMO, memo);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
    complianceFlag = view.findViewById(R.id.detail_compliance_flag);
    Log.e(TAG, "resultDesc Text onViewCreated(): " + resultDesc.getText().toString() );
    updateUI();
  }

  private void updateUI() {
    Bundle args = getArguments();
    String result = args.getString(RESULT_KEY);
    if (result.toLowerCase().equals("in compliance") || result.toLowerCase().equals("upgrade")) {
      complianceFlag.setImageResource(R.drawable.ic_check_green_24dp);
    }
    else {
      complianceFlag.setImageResource(R.drawable.ic_clear_red_24dp);
    }
    inspectionDate.setText(args.getString(DATE_KEY));
    resultDesc.setText(result);
    Log.e(TAG, "updateUI: " + args.getString(RESULT_KEY));
    violationCode.setText(args.getString(VIOLATION_CODE_KEY));
    Log.e(TAG, "resultDesc Text : " + resultDesc.getText().toString() );
    violationDesc.setText(args.getString(VIOLATION_DESC_KEY));
    inspectionMemo.setText(args.getString(INSPECTION_MEMO));

  }

}

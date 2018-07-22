package com.mweslacey.city.foodfail.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mweslacey.city.foodfail.R;

/**
 * Utilized by the ViewPager in {@link LocalMapFragment} to display facility local to the
 * device's current location.
 */
public class LocalFacilitiyFragment extends Fragment {

  private static final String FACILITY_NAME_KEY = "facility name";
  private static final String FACILITY_ADDRESS_KEY = "facility address";
  private static final String INSPECTION_DATE_KEY = "inspection date";
  private static final String INSPECTION_RESULT_KEY = "result desc";

  private TextView facilityName;
  private TextView facilityAddress;
  private TextView inspectionDate;
  private TextView resultDesc;

  /**
   * Empty constructor utizlied by the {@link #newInstance(String, String, String, String)} method.
   */
  public LocalFacilitiyFragment() {
    // Required empty public constructor
  }

  /**
   * Builds a new {@link #LocalFacilitiyFragment()} and sets arguments to be utilized in a selection
   * query and subsequent user interface updates.
   * @param facilityName Name of the local facility.
   * @param address Address of the local facility.
   * @param inspectionDate Date of most recent inspection.
   * @param resultDesc Inspection result description.
   * @return {@link #LocalFacilitiyFragment()}
   */
  public static LocalFacilitiyFragment newInstance(String facilityName, String address,
      String inspectionDate, String resultDesc) {
    LocalFacilitiyFragment fragment = new LocalFacilitiyFragment();
    Bundle args = new Bundle();
    args.putString(FACILITY_NAME_KEY, facilityName);
    args.putString(FACILITY_ADDRESS_KEY, address);
    args.putString(INSPECTION_DATE_KEY, inspectionDate);
    args.putString(INSPECTION_RESULT_KEY, resultDesc);
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.local_facility_item, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    Bundle args = getArguments();
    facilityName = view.findViewById(R.id.local_facility_name);
    facilityName.setText(args.getString(FACILITY_NAME_KEY));
    facilityAddress = view.findViewById(R.id.local_address);
    facilityAddress.setText(args.getString(FACILITY_ADDRESS_KEY));
    inspectionDate = view.findViewById(R.id.local_inspection_date);
    inspectionDate.setText(args.getString(INSPECTION_DATE_KEY));
    resultDesc = view.findViewById(R.id.local_result_desc);
    resultDesc.setText(args.getString(INSPECTION_RESULT_KEY));
    super.onViewCreated(view, savedInstanceState);
  }
}

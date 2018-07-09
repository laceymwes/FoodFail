package com.mweslacey.city.foodfail.controller;

import static android.support.constraint.Constraints.TAG;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import com.mweslacey.city.foodfail.R;
import com.mweslacey.city.foodfail.model.db.InspectionDatabase;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;
import com.mweslacey.city.foodfail.model.pojo.FacilityAndAllInspections;
import java.util.List;
import org.w3c.dom.Text;

public class FacilityDetailFragment extends Fragment {

  private static final String ARG_FACILITY_KEY =
      "com.mweslacey.city.foodfail.model.facilitydetailfragment.FacilityKey";

  private int facilityKey;
  private RecyclerView recyclerView;
  private Facility facility;

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


  public interface OnFragmentInteractionListener {

  }

  private class AsyncQuery extends AsyncTask<Integer, Void, FacilityAndAllInspections> {

    @Override
    protected FacilityAndAllInspections doInBackground(Integer... keys) {
      return InspectionDatabase.getInstance(FacilityDetailFragment.this.getContext()).getInspectionDAO()
          .getInspections(keys[0]);
    }

    @Override
    protected void onPostExecute(FacilityAndAllInspections facilityInspections) {
      InspectionDatabase.forgetInstance(getActivity());
      facility = facilityInspections.getFacility();
      Log.e(TAG, "Inspection Facility_ID : " + facilityInspections.getInspections().get(1).getFacilityID());
      recyclerView = getActivity().findViewById(R.id.inspection_recycler_view);
      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      recyclerView.setAdapter(new InspectionsAdapter(facilityInspections.getInspections()));
    }
  }
/*
  TODO: Replace with ViewPager. ViewPager shows only one item on the screen at a time.
  Efficiency comparable to RecyclerView

 */
  private class InspectionHolder extends RecyclerView.ViewHolder {

    private TextView inspectionDate;
    private TextView resultDesc;
    private TextView violationCode;
    private TextView violationDesc;
    private TextView inspectionMemo;

    public InspectionHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.inspection_item, parent, false));
      inspectionDate = itemView.findViewById(R.id.inspection_date);
      resultDesc = itemView.findViewById(R.id.result_desc);
      violationCode = itemView.findViewById(R.id.violation_code);
      this.violationDesc = itemView.findViewById(R.id.violation_desc);
      inspectionMemo = itemView.findViewById(R.id.inspection_memo);
    }

    public void setItemProperties(String date, String resultDesc, String violationCode,
        String violationDesc, String inspectionMemo) {
      inspectionDate.setText(date);
      this.resultDesc.setText(resultDesc);
      this.violationCode.setText(violationCode);
      this.violationDesc.setText(violationDesc);
      this.inspectionMemo.setText(inspectionMemo);
    }
  }

  private class InspectionsAdapter extends RecyclerView.Adapter {

    private List<Inspection> inspections;

    InspectionsAdapter(List<Inspection> inspections) {
      this.inspections = inspections;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(getActivity());
      return new InspectionHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      Inspection inspection = inspections.get(position);
      InspectionHolder iHolder = (InspectionHolder) holder;
      iHolder.setItemProperties(inspection.getInspectionDate(), inspection.getResultDesc(),
          inspection.getViolationCode(), inspection.getViolationDesc(),
          inspection.getInspectionMemo());

    }

    @Override
    public int getItemCount() {
      return inspections.size();
    }
  }
}

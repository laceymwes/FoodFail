package com.mweslacey.city.foodfail.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.mweslacey.city.foodfail.R;
import com.mweslacey.city.foodfail.model.db.InspectionDatabase;
import com.mweslacey.city.foodfail.model.pojo.FacilityAndLastInspection;
import java.util.List;

public class SearchFragment extends Fragment {

  private RecyclerView recyclerView;
  private EditText facilitySearch;
  private ImageView searchIcon;

  public SearchFragment() {
    // Required empty public constructor
  }

  public static SearchFragment newInstance() {
    return new SearchFragment();
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_search, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    facilitySearch = view.findViewById(R.id.facility_search);
    searchIcon = view.findViewById(R.id.search_icon);
    searchIcon.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        new SearchAsync().execute(facilitySearch.getText().toString());
      }
    });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }


  private class SearchAsync extends AsyncTask<String, Void, List<FacilityAndLastInspection>> {

    @Override
    protected List<FacilityAndLastInspection> doInBackground(String... searches) {
      return InspectionDatabase.getInstance(getActivity())
      .getInspectionDAO().getMatches(searches[0]);
    }

    @Override
    protected void onPostExecute(List<FacilityAndLastInspection> facilities) {
      recyclerView = getActivity().findViewById(R.id.search_recycler_view);
      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      recyclerView.setAdapter(new FacilityAdapter(facilities));
    }
  }

  private class FacilityHolder extends RecyclerView.ViewHolder {

    private TextView facilityName;
    private TextView facilityAddress;
    private TextView lastInspection;
    private ImageView complianceFlag;
    private int facilityKey;

    private final static String FAILURE = "NOT IN COMPLIANCE";

    public FacilityHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.facility_search_item, parent, false));
      facilityName = itemView.findViewById(R.id.search_facility_name);
      facilityAddress = itemView.findViewById(R.id.search_facility_address);
      lastInspection = itemView.findViewById(R.id.search_facility_inspection);
      complianceFlag = itemView.findViewById(R.id.search_compliance_flag);
    }

    public void setItemViewProperties(String name, String address, String inspection,
        final int facilityKey) {
      facilityName.setText(name);
      facilityAddress.setText(address);
      lastInspection.setText(inspection);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          callDetailView(facilityKey);
        }
      });
      if (inspection.contains(FAILURE)) {
        complianceFlag.setImageDrawable(getActivity().getDrawable(R.drawable.ic_clear_red_24dp));
      }
    }

    public void callDetailView(int facilityKey) {
      FacilityDetailFragment fragment = FacilityDetailFragment.newInstance(facilityKey);
      getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
          .replace(R.id.fragment_container, fragment).commit();
    }
  }

  private class FacilityAdapter extends RecyclerView.Adapter {

    private List<FacilityAndLastInspection> facilities;


    public FacilityAdapter(List<FacilityAndLastInspection> facilities){
      this.facilities = facilities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
      return new FacilityHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      FacilityHolder fHolder = (FacilityHolder) holder; // super class has no knowledge of subclass methods
      FacilityAndLastInspection facility = facilities.get(position);
      fHolder.setItemViewProperties(facility.getName(),
          facility.getAddress(), facility.getLastInspection(),
          facility.getFacility().getFacilityKey());
    }

    @Override
    public int getItemCount() {
      return facilities.size();
    }
  }
}

package com.mweslacey.city.foodfail.controller;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.mweslacey.city.foodfail.R;
import com.mweslacey.city.foodfail.model.db.InspectionDatabase;
import com.mweslacey.city.foodfail.model.entity.Facility;
import java.util.List;

public class SearchFragment extends Fragment {

  private OnFragmentInteractionListener mListener;
  private List<Facility> facilities;
  private InspectionDatabase inspectDB;
  private RecyclerView recyclerView;
  private FacilityAdapter facilityAdapter;
  private EditText facilitySearch;

  public SearchFragment() {
    // Required empty public constructor
  }

  public static SearchFragment newInstance() {
    return new SearchFragment();
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    mListener.searchTitle();
    super.onCreate(savedInstanceState);
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    inspectDB = InspectionDatabase.getInstance(getContext());
    return inflater.inflate(R.layout.fragment_search, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    facilitySearch = view.findViewById(R.id.facility_search);
    facilitySearch.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        new SearchAsync().execute(facilitySearch.getText().toString());
      }
    });

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
    public void searchTitle();
  }

  private class SearchAsync extends AsyncTask<String, Void, List<Facility>> {

    @Override
    protected List<Facility> doInBackground(String... searches) {
      return InspectionDatabase.getInstance(SearchFragment.this.getContext())
      .getInspectionDAO().getMatches(searches[0]);
    }

    @Override
    protected void onPostExecute(List<Facility> facilities) {
      recyclerView = getActivity().findViewById(R.id.search_recycler_view);
      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      recyclerView.setAdapter(new FacilityAdapter(facilities));
    }
  }

  private class FacilityHolder extends RecyclerView.ViewHolder {

    private TextView facilityName;

    public FacilityHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.facility_search_item, parent, false));
      facilityName = itemView.findViewById(R.id.search_card_text);
    }

    public void setFacilityName(String name) {
      facilityName.setText(name);
    }
  }

  private class FacilityAdapter extends RecyclerView.Adapter {

    private List<Facility> facilities;


    public FacilityAdapter(List<Facility> facilities){
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
      fHolder.setFacilityName(facilities.get(position).getFacilityName());
    }

    @Override
    public int getItemCount() {
      return facilities.size();
    }
  }
}

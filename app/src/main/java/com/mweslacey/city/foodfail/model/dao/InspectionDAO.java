package com.mweslacey.city.foodfail.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;
import com.mweslacey.city.foodfail.model.pojo.FacilityAndAllInspections;
import com.mweslacey.city.foodfail.model.pojo.FacilityAndLastInspection;
import java.util.List;

@Dao
public interface InspectionDAO {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  public void insertFacilities(Facility... facilities);

  @Insert(onConflict = OnConflictStrategy.FAIL)
  public void insertInspections(Inspection... inspections);

  // Called when user searches for facilities from SearchFragment
  @Query(Queries.LATEST_INSPECTION)
  public List<FacilityAndLastInspection> getMatches(String search);

  // Called when user selects facility from search results
  @Query(Queries.ALL_INSPECTIONS)
  public FacilityAndAllInspections getInspections(int facility_key);

  @Query(Queries.SINGLE_INSPECTION)
  public Inspection getInspection(int inspectionID);

  @Query(Queries.LOCAL_FACILITIES)
  public FacilityAndLastInspection getLocalFacilities(int zip);

}

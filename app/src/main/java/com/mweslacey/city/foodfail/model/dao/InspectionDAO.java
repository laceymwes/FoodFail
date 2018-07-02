package com.mweslacey.city.foodfail.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;
import com.mweslacey.city.foodfail.pojo.FacilityAndLastInspection;
import java.util.List;

@Dao
public interface InspectionDAO {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  public void insertFacilities(Facility... facilities);

  @Insert(onConflict = OnConflictStrategy.FAIL)
  public void insertInspections(Inspection... inspections);

  // Called when user searches for facilities from SearchFragment
  @Query("SELECT * FROM facilities WHERE facility_name like '%' || :search || '%'")
  public List<Facility> getMatches(String search);

  // Called when user selects facility from search results
  @Query("SELECT * FROM inspections WHERE facility_id = :facility_key")
  public List<Inspection> getInspections(int facility_key);

//  // TODO: Create  to pull only on, most recent, inspection record with facility
//  @Query(Queries.RECENT_INSPECTION)
//  public List<FacilityAndLastInspection> getFacilityListItem(String search);

}

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

/**
 * Interface implemented by Google Room.
 * Defines data manipulation language (DML) required to provide application view components with
 * appropriate data for user interface updates.
 */
@Dao
public interface InspectionDAO {

  /**
   * Inserts {@link Facility} record(s).
   * Utilized by {@link com.mweslacey.city.foodfail.model.db.InspectionDatabase}
   * database instantiation callback method for population.
   * @param facilities {@link Facility} instantiated by
   * {@link com.mweslacey.city.foodfail.model.db.InspectionDatabase}.
   */
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  public void insertFacilities(Facility... facilities);

  /**
   * Inserts {@link Inspection} record(s).
   * Utilized by {@link com.mweslacey.city.foodfail.model.db.InspectionDatabase}
   * database instantiation callback method for population.
   * @param inspections {@link Inspection} instantiated by
   * {@link com.mweslacey.city.foodfail.model.db.InspectionDatabase}.
   */
  @Insert(onConflict = OnConflictStrategy.FAIL)
  public void insertInspections(Inspection... inspections);

  // Called when user searches for facilities from SearchFragment
  /**
   * Retrieves {@link Facility} record(s) and most recent {@link Inspection} record for the
   * {@link com.mweslacey.city.foodfail.fragment.SearchFragment} class.
   * Utilizes custom {@link Queries#LATEST_INSPECTION} query.
   * @param search keyword provided by user in
   * {@link com.mweslacey.city.foodfail.fragment.SearchFragment}.
   * @return {@link FacilityAndLastInspection}
   */
  @Query(Queries.LATEST_INSPECTION)
  public List<FacilityAndLastInspection> getMatches(String search);

  // Called when user selects facility from search results
  /**
   * Retrieves a {@link Facility} record and the child {@link Inspection} record(s) for the
   * {@link com.mweslacey.city.foodfail.fragment.FacilityDetailFragment} class.
   * Utilizes custom {@link Queries#ALL_INSPECTIONS} query.
   * @param facility_key extracted from RecyclerView list item selected in
   * {@link com.mweslacey.city.foodfail.fragment.SearchFragment}.
   * @return {@link FacilityAndAllInspections}
   */
  @Query(Queries.ALL_INSPECTIONS)
  public FacilityAndAllInspections getInspections(int facility_key);

  /**
   * Retrieves singular {@link Inspection} record for the ViewPager in the
   * {@link com.mweslacey.city.foodfail.fragment.FacilityDetailFragment} class.
   * Utilizes {@link Queries#SINGLE_INSPECTION} query.
   * @param inspectionID provided by the
   * {@link com.mweslacey.city.foodfail.fragment.FacilityDetailFragment} class'
   * ViewPager FragmentStatePagerAdapter implementation.
   * @return {@link Inspection}
   */
  @Query(Queries.SINGLE_INSPECTION)
  public Inspection getInspection(int inspectionID);

  /**
   * Retrieves the local {@link Facility} record(s) requested by the
   * {@link com.mweslacey.city.foodfail.fragment.LocalMapFragment} class.
   * Utilizes the custom {@link Queries#LOCAL_FACILITIES} query.
   * @param zip provided by the {@link com.mweslacey.city.foodfail.fragment.LocalMapFragment} class.
   * @return {@link FacilityAndLastInspection}
   */
  @Query(Queries.LOCAL_FACILITIES)
  public List<FacilityAndLastInspection> getLocalFacilities(int zip);

}

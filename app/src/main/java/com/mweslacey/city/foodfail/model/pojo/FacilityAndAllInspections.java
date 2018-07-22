package com.mweslacey.city.foodfail.model.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;
import java.util.List;

/**
 * Java object (POJO) defining required return type for the
 * {@link com.mweslacey.city.foodfail.model.dao.InspectionDAO}
 * {@link com.mweslacey.city.foodfail.model.dao.InspectionDAO#getInspections(int)} query.
 */
public class FacilityAndAllInspections {

  /**
   * Empty constructor utilized by Room.
   */
  public FacilityAndAllInspections() {

  }

  @Embedded
  private Facility facility;

  @Relation(parentColumn = "facility_key", entityColumn = "facility_id")
  private List<Inspection> inspections;

  /**
   * Get {@link Facility} object instantiated by Room.
   * @return {@link Facility}
   */
  public Facility getFacility() {
    return facility;
  }

  /**
   * Utilized by Room to initialize the {@link Facility} object.
   * @param facility
   */
  public void setFacility(Facility facility) {
    this.facility = facility;
  }

  /**
   * Get List of {@link Inspection} objects instantiated by Room.
   * @return List {@link Inspection}
   */
  public List<Inspection> getInspections() {
    return inspections;
  }

  /**
   * Utilized by Room to initialize the List of {@link Inspection} objects.
   * @param inspections
   */
  public void setInspections(
      List<Inspection> inspections) {
    this.inspections = inspections;
  }
}

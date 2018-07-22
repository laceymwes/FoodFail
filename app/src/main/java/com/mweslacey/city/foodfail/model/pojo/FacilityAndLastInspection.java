package com.mweslacey.city.foodfail.model.pojo;

import android.arch.persistence.room.Embedded;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;

/**
 * Java object (POJO) defining required return type of the
 * {@link com.mweslacey.city.foodfail.model.dao.InspectionDAO}
 * {@link com.mweslacey.city.foodfail.model.dao.InspectionDAO#getLocalFacilities(int)} and
 * {@link com.mweslacey.city.foodfail.model.dao.InspectionDAO#getMatches(String)} queries.
 */
public class FacilityAndLastInspection {

  /**
   * Empty constructor utilized by Room.
   */
  public FacilityAndLastInspection() {

  }

  @Embedded
  private Facility facility;

  @Embedded
  private Inspection inspection;

  /**
   * Get {@link Inspection} object instantiated by Room.
   * @return {@link Inspection}
   */
  public Inspection getInspection() {
    return inspection;
  }

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
   * Utilized by Room to initialize the {@link Inspection} object.
   * @param inspection
   */
  public void setInspection(Inspection inspection) {
    this.inspection = inspection;
  }

  /**
   * Get name of the {@link Facility} object.
   * @return String facility name
   */
  public String getName() {
    return facility.getFacilityName();
  }

  /**
   * Get address of the {@link Facility} object.
   * @return String facility address
   */
  public String getAddress() {
    return facility.getStreetNumber() + " " + facility.getStreetName();
  }

  /**
   * Get date of {@link Inspection} object.
   * @return String inspection date
   */
  public String getLastInspection() {
    return inspection.getInspectionDate().replace("0:00", "") + " " + inspection.getResultDesc();
  }
}

package com.mweslacey.city.foodfail.model.dao;

public class Queries {


  public static final String LATEST_INSPECTION = "SELECT f.*, ir.* FROM facilities AS f "
      + "LEFT JOIN inspections AS ir "
      + "ON ir.facility_id = f.facility_key "
      + "WHERE f.facility_name LIKE '%' || :search || '%' "
      + "GROUP BY f.facility_key "
      + "ORDER BY facility_name ASC, inspection_date ASC";

  public static final String ALL_INSPECTIONS = "SELECT f.*, ir.* FROM facilities AS f "
      + "LEFT JOIN inspections AS ir "
      + "ON ir.facility_id = f.facility_key "
      + "WHERE f.facility_key = :facility_key "
      + "ORDER BY ir.inspection_date ASC ";

  public static final String SINGLE_INSPECTION = "SELECT * FROM inspections "
      + "WHERE inspection_id = :inspectionID";

  public static final String LOCAL_FACILITIES = "SELECT f.*, ir* FROM facilities AS f "
      + "LEFT JOIN inspections AS ir"
      + "ON ir.facility_id = f.facility_key "
      + "WHERE f.zip = :zip "
      + "GROUP BY f.facility_key"
      + "ORDER BY facility_name ASC, inspection_date ASC";
}

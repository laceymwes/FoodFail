package com.mweslacey.city.foodfail.model.dao;

public class Queries {


  public static final String RECENT_INSPECTION = "SELECT f.*, ir.* "
      + "FROM facilities AS f "
      + "WHERE f.facility_name like '%' || :search || '%' "
      + "JOIN (SELECT * FROM inspections WHERE facility_id = facility_key LIMIT 1) AS ir"
      + "ON facility_id = facility_key";

}

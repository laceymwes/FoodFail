package com.mweslacey.city.foodfail.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Facility.class, Inspection.class}, version = 1, exportSchema = false)
public abstract class InspectionDatabase extends RoomDatabase {

  public abstract InspectionDAO inspectionDAO();

  private static InspectionDatabase instance;
  
  public InspectionDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context, InspectionDatabase.class, "inspection-database")
          .build();
    }
    return instance;
  }
}

package com.mweslacey.city.foodfail.model.db;

import static android.content.ContentValues.TAG;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import com.mweslacey.city.foodfail.R;
import com.mweslacey.city.foodfail.controller.MainActivity;
import com.mweslacey.city.foodfail.model.dao.InspectionDAO;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;
import com.mweslacey.city.foodfail.pojo.Headers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Database(entities = {Facility.class, Inspection.class}, version = 1, exportSchema = false)
public abstract class InspectionDatabase extends RoomDatabase {

  public static final String DATABASE_NAME = "inspection_db";

  public abstract InspectionDAO getInspectionDAO();

  private static InspectionDatabase instance;

  public static InspectionDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context, InspectionDatabase.class, DATABASE_NAME)
          .addCallback(new InspectionCallback(context))
          .build();
    }
    return instance;
  }

  public static void forgetInstance(Context context) {
    instance = null;
  }

  private static class InspectionCallback extends RoomDatabase.Callback {

    private Context context;

    private InspectionCallback(Context context) {
      this.context = context;
    }

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      new PrepopulateTask().execute(context);
    }
  }

  private static class PrepopulateTask extends AsyncTask<Context, Void, Void> {


    @Override
    protected Void doInBackground(Context... contexts) {
      try {
        InputStream raw =
          contexts[0].getResources().openRawResource(R.raw.inspection_records);
          CSVParser parser= CSVParser.parse(raw, StandardCharsets.UTF_8, CSVFormat.EXCEL);
          long counter = 0;
          String prevKey = "";
        for (CSVRecord record : parser) {
          if (counter++ < 1 || prevKey.equals(record.get(1))) {
            continue;
          }
          prevKey = record.get(1);
          Facility facility = new Facility();
          facility.setFacilityName(record.get(0));
          facility.setFacilityKey(Integer.parseInt(record.get(1)));
          facility.setState(record.get(4));
          facility.setZip(Integer.valueOf(record.get(5).substring(0,5)));
          facility.setStreetNumber(record.get(9));
          facility.setStreetName(record.get(10));
        InspectionDatabase db = InspectionDatabase.getInstance(contexts[0]);
        db.getInspectionDAO().insertFacilities(facility);
        }
      } catch (IOException e) {
        Log.e(TAG, "Database pre-pop failure ");
        e.printStackTrace();
      } finally {
        InspectionDatabase.forgetInstance(contexts[0]);

      }
      return null;
    }

  }

}


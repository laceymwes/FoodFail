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
import com.mweslacey.city.foodfail.model.dao.InspectionDAO;
import com.mweslacey.city.foodfail.model.entity.Facility;
import com.mweslacey.city.foodfail.model.entity.Inspection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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

    // COLUMN INDICES
    public static final int FACILITY_KEY = 1;
    public static final int INSPECTION_DATE = 16;
    public static final int RESULT_CODE = 22;
    public static final int RESULT_DESC = 23;
    public static final int VIOLATION_CODE = 24;
    public static final int VIOLATION_DESC = 25;
    public static final int INSPECTION_MEMO = 26;
    public static final int FACILITY_NAME = 0;
    public static final int STATE = 4;
    public static final int ZIP = 5;
    public static final int STREET_NUM = 9;
    public static final int STREET_NAME = 10;

    @Override
    protected Void doInBackground(Context... contexts) {
      try {
        InputStreamReader raw = new InputStreamReader(
            contexts[0].getResources().openRawResource(R.raw.inspection_records));
        CSVParser parser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(raw);
        Facility facility = new Facility();
        Inspection inspection = new Inspection();
        for (CSVRecord record : parser) {
          inspection.setFacilityID(Integer.parseInt(record.get(FACILITY_KEY)));
          inspection.setInspectionDate(record.get(INSPECTION_DATE));
          inspection.setResultCode(Integer.parseInt(record.get(RESULT_CODE)));
          inspection.setResultDesc(record.get(RESULT_DESC));
          inspection.setViolationCode(record.get(VIOLATION_CODE));
          inspection.setViolationDesc(record.get(VIOLATION_DESC));
          inspection.setInspectionMemo(record.get(INSPECTION_MEMO));
          facility.setFacilityName(record.get(FACILITY_NAME));
          facility.setFacilityKey(Integer.parseInt(record.get(FACILITY_KEY)));
          facility.setState(record.get(STATE));
          facility.setZip(Integer.valueOf(record.get(ZIP).substring(0, 5)));
          facility.setStreetNumber(record.get(STREET_NUM));
          facility.setStreetName(record.get(STREET_NAME));
          InspectionDatabase db = InspectionDatabase.getInstance(contexts[0]);
          db.getInspectionDAO().insertFacilities(facility);
          db.getInspectionDAO().insertInspections(inspection);
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


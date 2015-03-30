package edu.lewisu.cs.howardcy.testmasterdetail;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cindy on 3/7/15.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "data.db";
    private static int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TaskTable.onCreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        TaskTable.onUpgrade(db, oldVersion, newVersion);

    }
}

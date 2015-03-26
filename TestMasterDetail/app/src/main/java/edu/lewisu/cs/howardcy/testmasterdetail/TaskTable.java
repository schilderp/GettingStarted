package edu.lewisu.cs.howardcy.testmasterdetail;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by cindy on 3/7/15.
 */

public class TaskTable {
    public static final String TABLE_TASKS = "tasks";
    public static final String COL_ID = "_id";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_PRIORITY = "priority";
    public static final String COL_DONE = "done";

    //database creation statement
    private static final String DATABASE_CREATE =
            "create table " + TABLE_TASKS +" (" +
                    COL_ID +  " integer primary key autoincrement, " +
                    COL_DESCRIPTION +  " text not null, " +
                    COL_PRIORITY + " text, " +
                    COL_DONE + " integer default 0"+ ");";

    public static void onCreate(SQLiteDatabase database){
        Log.d(TaskTable.class.getName(), DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE);
        database.execSQL("INSERT INTO tasks values(null, 'Grade Homework', 'low', 0)");
        database.execSQL("INSERT INTO tasks values(null, 'Investigate master/detail', 'high', 0)");

    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(TaskTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion  +
                "which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(database);
    }
}

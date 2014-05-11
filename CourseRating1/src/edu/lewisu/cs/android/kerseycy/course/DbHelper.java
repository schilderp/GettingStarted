package edu.lewisu.cs.android.kerseycy.course;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "reviews.db";
	private static final int DATABASE_VERSION = 1;

	public DbHelper(Context context){
		  super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		ReviewTable.onCreate(database);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		ReviewTable.onUpgrade(database, oldVersion, newVersion);

		
	}

}

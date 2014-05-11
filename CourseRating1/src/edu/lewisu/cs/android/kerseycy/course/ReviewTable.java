package edu.lewisu.cs.android.kerseycy.course;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ReviewTable {
	public static final String TABLE_REVIEWS= "reviews";
	public static final String COL_ID = "_id";
	public static final String COL_COURSE = "course";
	public static final String COL_COMMENTS = "comments";
	public static final String COL_COURSE_TYPE = "courseType";
	public static final String COL_RATING = "rating";
	public static final String COL_INSTRUCTOR = "instructor";

	//database creation statement
	private static final String DATABASE_CREATE =
			  "create table " + TABLE_REVIEWS +" (" +
			   COL_ID +  " integer primary key autoincrement, " + 
			   COL_COURSE +  " text not null, " +	
			   COL_INSTRUCTOR + " text not null, " + 
			   COL_COMMENTS + " text, " +
			   COL_COURSE_TYPE + " text, " +
			   COL_RATING + " real default 0"+ ");"; 

	
	//creates the table
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	//upgrades the table
	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
				int newVersion) {
	   Log.w(ReviewTable.class.getName(), "Upgrading database from version "
		+ oldVersion + " to " + newVersion
		+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
		onCreate(database);
	}

}

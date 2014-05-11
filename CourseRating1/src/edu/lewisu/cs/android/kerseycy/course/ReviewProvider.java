package edu.lewisu.cs.android.kerseycy.course;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ReviewProvider extends ContentProvider {
	DbHelper dbHelper;
	private static final int ALL_REVIEWS = 10;
	private static final int SINGLE_REVIEW = 20;
	private static final String AUTHORITY = "edu.lewisu.cs.android.kerseycy.course.ReviewProvider";
	public static final Uri CONTENT_URI =  Uri.parse("content://" + AUTHORITY +"/reviews");

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/reviews";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/review";

	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "reviews", ALL_REVIEWS);
		uriMatcher.addURI(AUTHORITY, "reviews/#", SINGLE_REVIEW); 
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return false;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
	    int uriType = uriMatcher.match(uri);
	    SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
	    long id = 0;
	    switch (uriType) {
		case ALL_REVIEWS:
		  id = sqlDB.insert(ReviewTable.TABLE_REVIEWS, null, values);
		  break;
			    
		 default:
	           throw new IllegalArgumentException("Unknown URI: " + uri);
			    }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return Uri.parse("tasks/" + id);
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {

	    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	    queryBuilder.setTables(ReviewTable.TABLE_REVIEWS);

	    int uriType = uriMatcher.match(uri);
	    switch (uriType) {
	    case ALL_REVIEWS:
	      break;
	    case SINGLE_REVIEW:
	      queryBuilder.appendWhere(ReviewTable.COL_ID + "="
	          + uri.getLastPathSegment());
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }

	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    Cursor cursor = queryBuilder.query(db, projection, selection,
	        selectionArgs, null, null, sortOrder);
	    
	    cursor.setNotificationUri(getContext().getContentResolver(), uri);

	    return cursor;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
		int rowsDeleted = 0;
		
		int uriType = uriMatcher.match(uri);
		
		switch (uriType) {
		  case ALL_REVIEWS:
		    rowsDeleted = sqlDB.delete(ReviewTable.TABLE_REVIEWS, selection, selectionArgs);
		     break;
		  
		  case SINGLE_REVIEW:
			    String id = uri.getLastPathSegment();
			    if (TextUtils.isEmpty(selection)) {
			        rowsDeleted = sqlDB.delete(ReviewTable.TABLE_REVIEWS, ReviewTable.COL_ID + "=" + id, null);
			     } else {
				  rowsDeleted = sqlDB.delete(ReviewTable.TABLE_REVIEWS,  ReviewTable.COL_ID + "="
			        + id  + " and " + selection, selectionArgs);
			     }
			     break;
			default:
			     throw new IllegalArgumentException("Unknown URI: " + uri);
			}

		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}



	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
	    SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
	    int rowsUpdated = 0;
	    int uriType = uriMatcher.match(uri);
	    switch (uriType) {
	    case SINGLE_REVIEW:
	        String id = uri.getLastPathSegment();
	        rowsUpdated = sqlDB.update(ReviewTable.TABLE_REVIEWS, values, 
	        		ReviewTable.COL_ID + "=" + id, null);
	        break;
	    default:
	        throw new IllegalArgumentException(
	                                 "Unknown URI: " + uri);
	    }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return rowsUpdated;
	}



}

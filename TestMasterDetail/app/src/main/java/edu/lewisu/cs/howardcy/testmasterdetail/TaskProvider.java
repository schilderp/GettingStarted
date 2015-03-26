package edu.lewisu.cs.howardcy.testmasterdetail;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class TaskProvider extends ContentProvider {
    private DBHelper dbHelper;

    private static final String AUTHORITY = "edu.lewisu.cs.howardcy.TaskProvider";


    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + TaskTable.TABLE_TASKS);

    private static final int ALL_TASKS = 1;
    private static final int TASK_ID = 2;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, TaskTable.TABLE_TASKS, ALL_TASKS);
        URI_MATCHER.addURI(AUTHORITY, TaskTable.TABLE_TASKS + "/#",TASK_ID);
    }


    public TaskProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;

        int uriType = URI_MATCHER.match(uri);

        switch (uriType){
            case ALL_TASKS:
                rowsDeleted = db.delete(TaskTable.TABLE_TASKS, selection, selectionArgs);
            case TASK_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    rowsDeleted = db.delete(TaskTable.TABLE_TASKS,
                            TaskTable.COL_ID + " = " + id, null);
                }else{
                    rowsDeleted = db.delete(TaskTable.TABLE_TASKS,
                            TaskTable.COL_ID + " = " + id +
                                    " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = URI_MATCHER.match(uri);

        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case ALL_TASKS:
                id = sqlDB.insert(TaskTable.TABLE_TASKS,null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(TaskTable.TABLE_TASKS + "/" + id);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TaskTable.TABLE_TASKS);

        int uriType = URI_MATCHER.match(uri);

        switch (uriType) {
            case ALL_TASKS:
                break;
            case TASK_ID:
                queryBuilder.appendWhere(TaskTable.COL_ID + "="
                        + uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;


    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated = 0;

        int uriType = URI_MATCHER.match(uri);

        switch (uriType){
            case TASK_ID:
                String id = uri.getLastPathSegment();
                rowsUpdated = db.update(TaskTable.TABLE_TASKS, values,
                        TaskTable.COL_ID + " = " + id, null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}

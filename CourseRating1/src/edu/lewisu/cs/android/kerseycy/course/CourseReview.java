package edu.lewisu.cs.android.kerseycy.course;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

public class CourseReview extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_rating);
	}
	
	public void submit(View v){
		EditText nameView = (EditText)findViewById(R.id.courseName);
		EditText instructorView = (EditText)findViewById(R.id.instructor);
		EditText commentView = (EditText)findViewById(R.id.comments);
		Spinner spinner = (Spinner)findViewById(R.id.courseType);
		RatingBar bar = (RatingBar)findViewById(R.id.rating);
		String courseName = nameView.getText().toString();
		String instructor = instructorView.getText().toString();
		String comments = commentView.getText().toString();
		String courseType = (String)spinner.getSelectedItem();
		int rating = (int)bar.getRating();
		
		ContentValues values = new ContentValues();
		values.put(ReviewTable.COL_COURSE, courseName);
		values.put(ReviewTable.COL_COURSE_TYPE, courseType);
		values.put(ReviewTable.COL_RATING, rating);
		values.put(ReviewTable.COL_COMMENTS, comments);
		values.put(ReviewTable.COL_INSTRUCTOR, instructor);
		
	    Uri result = getContentResolver().insert(ReviewProvider.CONTENT_URI, values);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(result.toString());
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        		finish();
	           }
	       });
		AlertDialog dialog = builder.create();
		dialog.show();
		
	
	}

}

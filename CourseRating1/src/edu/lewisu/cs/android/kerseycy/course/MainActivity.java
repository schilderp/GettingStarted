package edu.lewisu.cs.android.kerseycy.course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;

public class MainActivity extends Activity  {
	SimpleCursorAdapter adapter;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
	}

	
	public void buttonClick(View v){
		Intent launchReview = new Intent(this, CourseReview.class);
		startActivity(launchReview);
	}

	

}

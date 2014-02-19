package edu.lewisu.cs.android.kerseycy.filetest;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FileStats extends Activity {
	TextView directoryView;
	TextView filesView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_stats);
		
		directoryView = (TextView)findViewById(R.id.dirTextView);
		filesView = (TextView)findViewById(R.id.filesTextView);
		
		File f = getFilesDir();
		String dir = getString(R.string.directory) + f.getName();
		directoryView.setText(dir);

		String[] files = fileList();
		String fileList = getString(R.string.files);
		for(String name : files){
			fileList += "\n";
			fileList += name;
		}
		filesView.setText(fileList);
	}
		
		public void done(View v){
			finish();
		}
}

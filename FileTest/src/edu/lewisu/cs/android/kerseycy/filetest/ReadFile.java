package edu.lewisu.cs.android.kerseycy.filetest;

import java.io.FileInputStream;
import java.io.IOException;

import edu.lewisu.cs.android.kerseycy.filetest.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ReadFile extends Activity {
	TextView readOutput;
	Button doneButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		String filename = i.getStringExtra("filename");
		setContentView(R.layout.read_file);
		readOutput = (TextView) findViewById(R.id.read_output);
		doneButton = (Button) findViewById(R.id.done_button);
		
		FileInputStream fis = null;
		try{
			fis  = openFileInput(filename);
			byte[] reader = new byte[fis.available()];
			while(fis.read(reader) != -1){
				readOutput.setText(new String(reader));
			}
		}catch(IOException ex){
			Log.e("ReadFile", ex.getMessage(), ex);
		}finally{
			if(fis != null){
				try{
					fis.close();
				}catch (IOException ex){
					
				}
			}
		}
		
		doneButton.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				finish();
				
			}});
	}

}

package edu.lewisu.cs.android.kerseycy.filetest;

import java.io.IOException;
import java.io.InputStream;

import edu.lewisu.cs.android.kerseycy.filetest.R;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ReadStaticFile extends Activity {
	TextView readOutput;
	Button doneButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.read_file);
		readOutput = (TextView) findViewById(R.id.read_output);
		doneButton = (Button) findViewById(R.id.done_button);
		
		InputStream is = null;
		try{
			is  = getResources().openRawResource(R.raw.rawfile);			
			byte[] reader = new byte[is.available()];
			while(is.read(reader) != -1){
				readOutput.setText(new String(reader));
			}
		}catch(IOException ex){
			Log.e("ReadFile", ex.getMessage(), ex);
		}finally{
			if(is != null){
				try{
					is.close();
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

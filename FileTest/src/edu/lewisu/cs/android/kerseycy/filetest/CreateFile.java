package edu.lewisu.cs.android.kerseycy.filetest;

import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class CreateFile extends Activity {
	private EditText createInput;
	private String filename;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		filename = i.getStringExtra("filename");
		
		setContentView(R.layout.create_file);
		createInput = (EditText)findViewById(R.id.create_input);

	}
	
	public void save(View v){
		FileOutputStream fos = null;
		try{
			//fos=openFileOutput(filename, MODE_PRIVATE);
			fos=openFileOutput(filename, MODE_APPEND);			
			fos.write(createInput.getText().toString().getBytes());
		}catch(IOException ex){
			Log.e("Create File", ex.getLocalizedMessage());
		}finally{
			if(fos !=null){
				try{
					fos.flush();
					fos.close();
				}catch(IOException ex){
					
				}
				
			}
			
		}
		
		finish();
	}

}

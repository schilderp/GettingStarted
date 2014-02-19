package edu.lewisu.cs.android.kerseycy.filetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FileTest extends Activity {

	private final String FILENAME = "settings.txt";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
   }
    
    public void createFile(View v){
		Intent create = new Intent(getApplicationContext(), CreateFile.class);
		create.putExtra("filename", FILENAME);
		startActivity(create);
    }
    
    public void readFile(View v){
		Intent read = new Intent(getApplicationContext(), ReadFile.class);
		read.putExtra("filename", FILENAME);
		startActivity(read);
    }
    
    public void readStatic(View v){
			Intent read = new Intent(getApplicationContext(), ReadStaticFile.class);
			startActivity(read);	
    }
    
    public void fileStats(View v){
		Intent stats = new Intent(getApplicationContext(), FileStats.class);
			startActivity(stats);
    }
    
    public void delete(View v){
 		deleteFile(FILENAME);
  }
}

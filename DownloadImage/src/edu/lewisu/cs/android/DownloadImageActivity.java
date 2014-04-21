package edu.lewisu.cs.android;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class DownloadImageActivity extends Activity {
	
	ImageView imageView;
	EditText editText;  
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        imageView = (ImageView)findViewById(R.id.imageView);
        editText = (EditText)findViewById(R.id.address);
    }
    
    public void buttonClick(View v){
    	new DownloadImageTask().execute(editText.getText().toString());
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
 
    	
        protected Bitmap doInBackground(String... urls) {
        	try {
        		URL url = new URL(urls[0]);
                URLConnection conn = url.openConnection();
                conn.connect();

                BufferedInputStream is = 
                		new BufferedInputStream(conn.getInputStream());
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();
                return bitmap;

        	} catch (IOException e) {
                Log.d("DEBUGTAG", "Unable to download file...");
        	}
        	return null;
        }
        
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
        
       
        
    }
}
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
        /** The system calls this to perform work in a worker thread and
          * delivers it the parameters given to AsyncTask.execute() */
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }
        
        /** The system calls this to perform work in the UI thread and delivers
          * the result from doInBackground() */
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
        
        private Bitmap loadImageFromNetwork(String imageURL){
          	try {
        		URL url = new URL(imageURL);
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
        
    }
}
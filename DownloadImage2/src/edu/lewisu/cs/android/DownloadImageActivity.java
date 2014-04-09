package edu.lewisu.cs.android;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.app.ProgressDialog;
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
    /** Called when the activity is first created. */
	
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

    private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
    	private ProgressDialog dialog;
    	
    	
    	 protected void onPreExecute() {
    		 
    		 dialog = new ProgressDialog(DownloadImageActivity.this);
    		 dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
             dialog.setMessage("Downloading Image. Please wait...");
             dialog.setCancelable(false);
             dialog.setMax(100);
             dialog.show();
         }
    	
    	
    	
    	/** The system calls this to perform work in a worker thread and
          * delivers it the parameters given to AsyncTask.execute() */
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }
        
        /** The system calls this to perform work in the UI thread and delivers
          * the result from doInBackground() */
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
        }
            
     }

        @Override
        protected void onProgressUpdate(Integer... values) {
		dialog.setProgress(values[0]);
        }
        
        
        private Bitmap loadImageFromNetwork(String imageURL){
        	int fileSize=0;
        	double bytesRead=0;
          	try {
        		URL url = new URL(imageURL);
                URLConnection conn = url.openConnection();
                conn.connect();
                fileSize = conn.getContentLength();
                BufferedInputStream bis = 
                		new BufferedInputStream(conn.getInputStream());
                ByteArrayBuffer baf = new ByteArrayBuffer(50);
                int current = 0;
                
                while ((current = bis.read()) != -1) {
                        baf.append((byte) current);
                        bytesRead = baf.length();
                        if(bytesRead % 1000==0){
                        	publishProgress((int)(bytesRead/fileSize * 100));
                        }
                }
                
                Bitmap bitmap=BitmapFactory.decodeByteArray(baf.toByteArray(), 0,baf.length());
                bis.close();
                return bitmap;

        	} catch (IOException e) {
                Log.d("DEBUGTAG", "Unable to download file...");
        	}
        	return null;
        	}
        }
}
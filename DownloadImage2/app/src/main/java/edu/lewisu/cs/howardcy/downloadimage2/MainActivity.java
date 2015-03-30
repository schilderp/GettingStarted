package edu.lewisu.cs.howardcy.downloadimage2;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends ActionBarActivity {

    ImageView imageView;
    EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageView);
        editText = (EditText)findViewById(R.id.address);
    }

    public void buttonClick(View v){
        new DownloadImageTask().execute(editText.getText().toString());
    }

    private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
        private ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage("Downloading Image. Please wait...");
            dialog.setCancelable(false);
            dialog.setMax(100);
            dialog.show();
        }

        protected Bitmap doInBackground(String... urls) {
            int fileSize = 0;
            double bytesRead = 0;
            try {
                URL url = new URL(urls[0]);
                URLConnection conn = url.openConnection();
                conn.connect();

                // first get the file size
                fileSize = conn.getContentLength();

                BufferedInputStream bis = new BufferedInputStream(
                        conn.getInputStream());
                ByteArrayBuffer baf = new ByteArrayBuffer(50);

                // read bytes into buffer and update progress every 1,000 bytes
                int current = bis.read();
                while (current  != -1) {
                    baf.append((byte) current);
                    bytesRead = baf.length();
                    if (bytesRead % 1000 == 0) {
                        publishProgress((int) (bytesRead / fileSize * 100));
                    }
                    current = bis.read();
                }

                Bitmap bitmap = BitmapFactory.decodeByteArray(
                        baf.toByteArray(), 0, baf.length());
                bis.close();
                return bitmap;

            } catch (IOException e) {
                Log.d("DEBUGTAG", "Unable to download file...");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            dialog.setProgress(values[0]);
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }

    }
}

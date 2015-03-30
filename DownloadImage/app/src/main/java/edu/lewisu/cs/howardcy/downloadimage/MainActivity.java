package edu.lewisu.cs.howardcy.downloadimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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

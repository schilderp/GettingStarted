package edu.lewisu.cs.howardcy.networkdb;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DownloadBooks().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DownloadBooks extends
            AsyncTask<Void, Void,ArrayList< HashMap<String,String> >> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(MainActivity.this, "",
                    "Downloading Books. Please wait...", true, false);
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, result, R.layout.book_row,
                    new String[] { "isbn",   "title"},
                    new int[] { R.id.isbn, R.id.title });
            MainActivity.this.setListAdapter(adapter);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(
                Void... params) {
            StringBuilder builder = new StringBuilder();

            HttpClient client = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.cs.lewisu.edu/~howardcy/php/books1.php");

            try {


                HttpResponse response = client.execute(httppost);
                StatusLine statusLine = response.getStatusLine();


                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {

                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(content));
                    String line  = reader.readLine();
                    while (line  != null) {
                        builder.append(line);
                        line  = reader.readLine();
                    }
                }else {
                    Log.e("booklist", "Failed to download file");
                }
            } catch (Exception e) {
                Log.e("books", e.getMessage());
            }

            ArrayList<HashMap<String, String>> results =
                    new ArrayList<HashMap<String, String>>();
            HashMap<String, String> book =
                    new HashMap<String, String>();
            String title;
            String isbn;

            try{
                JSONArray jArray = new JSONArray(builder.toString());
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    title=jsonObject.getString("title");
                    isbn = jsonObject.getString("isbn");
                    book = new HashMap<String, String>();
                    book.put("isbn", isbn);
                    book.put("title", title);
                    results.add(book);
                }
                return results;
            } catch (Exception e) {
                Log.e("books", e.getMessage());
            }
            return null;
        }

    }
}

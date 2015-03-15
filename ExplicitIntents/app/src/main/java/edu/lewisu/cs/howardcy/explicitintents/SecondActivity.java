package edu.lewisu.cs.howardcy.explicitintents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;


public class SecondActivity extends ActionBarActivity {
    TextView textView;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //get the value from the Intent
        Intent sender = getIntent();
        String name = sender.getStringExtra("name");

        //get a reference to the textView and set the text
        textView = (TextView)findViewById(R.id.hello_tv);
        textView.setText(name);

        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
    }


    public void submit(View v){

        //retrieve the rating from the rating bar
        float rating = ratingBar.getRating();

        //create a message, set extras and set result
        Intent returnIntent = new Intent();
        returnIntent.putExtra("rating", rating);
        setResult(RESULT_OK, returnIntent);

        //finish activity and return message to caller
        finish();
    }
}

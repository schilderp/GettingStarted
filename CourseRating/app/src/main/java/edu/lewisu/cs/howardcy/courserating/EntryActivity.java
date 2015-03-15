package edu.lewisu.cs.howardcy.courserating;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class EntryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
    }


    public void enterButtonClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

package edu.lewisu.cs.howardcy.explicitintents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    final int SECOND_ID = 12345;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.editText);
    }


    public void buttonClick(View v){
        String name = editText.getText().toString();
        Intent launchSecond = new Intent(this, SecondActivity.class);
        launchSecond.putExtra("name", name);
        startActivityForResult(launchSecond, SECOND_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //verify return result is the one expected
        if(resultCode == RESULT_OK  && requestCode == SECOND_ID){

            //get the rating from the Intent extras or set to zero
            float rating = data.getFloatExtra("rating", 0);

            //make a toast
            Toast.makeText(getApplicationContext(), "returned: " + rating, Toast.LENGTH_SHORT).show();
        }
    }

}

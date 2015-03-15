package edu.lewisu.cs.howardcy.courserating;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;


public class MainActivity extends ActionBarActivity {
    Button button;
    RatingBar ratingBar;
    EditText courseName_et;
    EditText instructorName_et;
    EditText comments_et;
    RadioButton genEd_button;
    RadioButton elective_button;
    RadioButton major_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        courseName_et = (EditText)findViewById(R.id.course_name);
        instructorName_et = (EditText)findViewById(R.id.instructor_text);
        comments_et = (EditText)findViewById(R.id.comments_text);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        genEd_button = (RadioButton)findViewById(R.id.gened_button);
        elective_button = (RadioButton)findViewById(R.id.elective_button);
        major_button = (RadioButton)findViewById(R.id.major_button);
        button = (Button)findViewById(R.id.button);

    }

    public void buttonHandler(View v){
        String courseName = courseName_et.getText().toString();
        String instructorName = instructorName_et.getText().toString();
        String comments = comments_et.getText().toString();
        float rating = ratingBar.getRating();
        String reason;
        if(elective_button.isChecked()){
            reason = elective_button.getText().toString();
        }else if(genEd_button.isChecked()) {
            reason = genEd_button.getText().toString();
        }else{
            reason = major_button.getText().toString();
        }
        String toastText = "Course: " + courseName + "\n";
        toastText += "Instructor: " + instructorName + "\n";
        toastText += "Comments: " + comments + "\n";
        toastText += reason + "\n";
        toastText += "Rating: " + rating + " stars";

        /*Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT);
        toast.show();*/

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(toastText);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                courseName_et.setText("");
                instructorName_et.setText("");
                comments_et.setText("");
                elective_button.setChecked(false);
                genEd_button.setChecked(false);
                major_button.setChecked(false);
                ratingBar.setRating(0);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


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
}

package edu.lewisu.cs.howardcy.shapedrawable;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class DrawShape extends ActionBarActivity {

    CustomShape customShape;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customShape = new CustomShape(this);

        setContentView(customShape);
    }
}

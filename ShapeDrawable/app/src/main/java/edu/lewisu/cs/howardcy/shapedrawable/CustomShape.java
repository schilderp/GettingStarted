package edu.lewisu.cs.howardcy.shapedrawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

/**
 * Created by cindy on 4/2/15.
 */
public class CustomShape extends View {
    private ShapeDrawable shape;

    public CustomShape(Context context) {
        super(context);

        int x = 10;
        int y = 10;
        int width = 300;
        int height = 250;

        shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setColor(0xffff00ff);
        shape.setBounds(x, y, x + width, y + height);
    }

    protected void onDraw(Canvas canvas) {

        shape.draw(canvas);
    }
}

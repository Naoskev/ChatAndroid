package com.mines_nantes.utilisateur.chat.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Utilisateur on 20/01/2015.
 */
public class DrawView extends SurfaceView {

    private static int[] COLORS = {Color.BLACK, Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.GRAY};
    private float radius;
    private List<Circle> circles = new ArrayList<Circle>();
    private float coordX;
    private float coordY;
    private Paint paint = new Paint();

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for(Circle c : circles) {
            paint.setColor(c.color);
            canvas.drawCircle(c.x, c.y, c.r,paint);
            // Dessiner ici !
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        // Gérer les actions ici !
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                coordY = y;
                coordX = x;
                break;

            case MotionEvent.ACTION_UP:
                // relâchement du doigt
                circles.add(new Circle(x,y, radius, COLORS[new Random().nextInt(6)]));
                break;

            case MotionEvent.ACTION_MOVE:
                radius = (float) Math.sqrt((coordX -x) *(coordX -x) + (coordY-y) *(coordY -y));
                break;
        }

        invalidate();
        return true;
    }

    private class Circle {
        float x, y, r;
        int color;

        public Circle(float px, float py, float pr, int pc){
            x = px;
            y = py;
            r = pr;
            color = pc;
        }
    }

}

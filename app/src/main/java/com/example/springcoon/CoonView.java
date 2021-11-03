package com.example.springcoon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class CoonView extends View {
    private Paint p = new Paint();
    private Flower[] flowers= new Flower[5];
    public CoonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        for(Flower i: flowers)
            i = new Flower(0, 0.5);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Flower i: flowers)
            i.drawFlower(canvas);
        p.setColor(Color.WHITE);
        //draw raccoon here
        invalidate();
    }
    public class Flower{
        int x;
        int y=0;
        double speed;
        int height = getHeight();
        Paint pink;
        public Flower(int randx, double s){
            x=randx;
            speed = s;
            pink = new Paint(Color.MAGENTA);
        }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void drawFlower(Canvas c){
            y+=speed%height;
            //flower top left corner...xy
            c.drawPath(makePath(x,y), pink);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private Path makePath(int x, int y) {
            Path path = new Path();
            //https://shapeshifter.design/
            //M 4 8 L 12 15 L 12 6 C 12 6 6 -3 4 8
            path.moveTo(4, 8);
            path.lineTo(12, 15);
            path.lineTo(12, 6);
            path.addArc(12, 6, 6, -3, 4, 8);//TODO this is probably messed up
            // M 16 5 L 11 16 L 23 10 C 23 10 25 -3 16 5
            path.moveTo(16, 5);
            path.lineTo(11, 6);
            path.lineTo(23, 10);
            path.addArc(23, 10, 25, -3, 16, 5);
            // M 13 14 L 4 17 C 4 17 -3 28 8 19 L 13 14
            // M 12 14 L 10 19 C 10 19 12 28 15 18 L 12 14
            // M 12 15 L 19 14 C 19 14 28 18 19 20 L 12 15
            return path;
        }

    }
}

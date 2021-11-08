package com.example.springcoon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;


public class CoonView extends View {
    private Paint p = new Paint();
    private Flower[] flowers= new Flower[5];
    private Flower[] bgflowers= new Flower[15];
    private Drawable myVectorDrawable, theArm;
    int width, height;
    public CoonView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        for(int i=0; i<flowers.length; i++) {
            int randx = (int)(Math.random()*width/2);
            double speed = Math.random()*4+1;
            flowers[i] = new Flower(randx, speed);
        }
        for(int i=0; i<bgflowers.length; i++) {
            int randx = (int)(Math.random()*width/2);
            int randy = (int)(Math.random()*(height-50));
            int color = getResources().getColor(R.color.purple1);
            if(i%2==1) color = getResources().getColor(R.color.purple2);
            bgflowers[i] = new Flower(randx, randy, color);
        }
        myVectorDrawable = VectorDrawableCompat.create(getContext().getResources(), R.drawable.vd_vector, null);
        myVectorDrawable.setBounds(new Rect(width/3,0,width, height));
        theArm = VectorDrawableCompat.create(getContext().getResources(), R.drawable.arm, null);
        theArm.setBounds(new Rect(width/4,0,width, height));
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //add background flowers
        for(Flower i: bgflowers)
            i.drawfixedFlower(canvas);
        //add moving flowers
        for(Flower i: flowers)
            i.drawFlower(canvas);
        //draw raccoon
        myVectorDrawable.draw(canvas);
        theArm.draw(canvas);
        invalidate();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void armUp(int h){
        //adjust arm
        theArm.setBounds(new Rect(width/4,0-h,width, height-h));
    }



    public class Flower{
        int x;
        int y=0;
        double speed;
        Paint pink;
        public Flower(int randx, double s){
            x=randx;
            speed = s;
            //TODO fix color
            pink = new Paint();
            pink.setColor(Color.MAGENTA);
        }
        public Flower(int randx, int randy, int c){
            x=randx;
            y = randy;
            pink = new Paint();
            pink.setColor(c);
        }
        public void drawfixedFlower(Canvas c){
            c.drawPath(makePath(x,y), pink);
        }
        public void drawFlower(Canvas c){
            y+=speed;
            y%=height;
            //flower top left corner...xy
            c.drawPath(makePath(x,y), pink);
        }

        private Path makePath(int x, int y) {
            Path path = new Path();
            //https://shapeshifter.design/
            //M 4 8 L 12 15 L 12 6 C 12 6 6 -3 4 8
            path.moveTo(16+x, 32+y);
            path.lineTo(48+x, 60+y);
            path.lineTo(48+x, 24+y);
            path.cubicTo(48+x, 24+y, 24+x, -12+y, 16+x, 32+y);
            // M 16 5 L 11 16 L 23 10 C 23 10 25 -3 16 5
            path.moveTo(64+x, 20+y);
            path.lineTo(44+x, 64+y);
            path.lineTo(92+x, 40+y);
            path.cubicTo(92+x, 40+y, 100+x, -12+y, 64+x, 20+y);
            // M 13 14 L 4 17 C 4 17 -3 28 8 19 L 13 14
            path.moveTo(52+x, 56+y);
            path.lineTo(16+x,68+y);
            path.cubicTo(16+x, 68+y, -12+x, 112+y, 32+x, 76+y);
            path.lineTo(52+x, 56+y);
            // M 12 14 L 10 19 C 10 19 12 28 15 18 L 12 14
            path.moveTo(48+x, 56+y);
            path.lineTo(40+x, 76+y);
            path.cubicTo(40+x, 76+y, 48+x, 112+y, 60+x, 76+y);
            path.lineTo(48+x, 56+y);
            // M 12 15 L 19 14 C 19 14 28 18 19 20 L 12 15
            path.moveTo(48+x, 60+y);
            path.lineTo(76+x, 56+y);
            path.cubicTo(76+x, 56+y, 112+x, 72+y,76+x, 80+y);
            path.lineTo(48+x, 60+y);
            return path;
        }

    }

}

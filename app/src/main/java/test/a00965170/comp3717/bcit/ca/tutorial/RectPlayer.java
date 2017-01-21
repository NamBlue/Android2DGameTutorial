package test.a00965170.comp3717.bcit.ca.tutorial;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by NamBlue on 1/19/2017.
 */

public class RectPlayer implements GameObject
{
    private Rect rectangle;
    private int color;
    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    private AnimationManager animationManager;

    public Rect getRectangle()
    {
        return rectangle;
    }

    public RectPlayer(Rect rectangle, int color)
    {
        this.rectangle = rectangle;
        this.color = color;

        //For Decoding, Producing, Modifying Bitmaps etc.
        BitmapFactory bitmapFactory = new BitmapFactory();
        //Make sure image names are all lowercase or will cause errors!
        Bitmap idleImg = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue);
        Bitmap walk1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk1);
        Bitmap walk2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk2);

        idle = new Animation(new Bitmap[]{idleImg}, 5);
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        //walkleft requires to reflect the image on the vertical axis.
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), matrix, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), matrix, false);
        walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        animationManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});
    }

    @Override
    public void draw(Canvas canvas)
    {
        //Paint paint = new Paint();
        //paint.setColor(color);
        //canvas.drawRect(rectangle, paint);
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update()
    {
        animationManager.update();
    }

    public void update(Point point)
    {
        float oldleft = rectangle.left;

        rectangle.set(point.x - rectangle.width()/2,
                        point.y - rectangle.height()/2,
                        point.x + rectangle.width()/2,
                        point.y + rectangle.height()/2);
        //0 for idle, 1 walking right, 2 walking left animations;
        int state = 0;
        //5 for bigger movements before animation changes
        if (rectangle.left - oldleft > 5)
        {
            state = 1;
        }
        else if (rectangle.left - oldleft < -5)
        {
            state = 2;
        }

        animationManager.playAnimation(state);
        animationManager.update();
    }

}

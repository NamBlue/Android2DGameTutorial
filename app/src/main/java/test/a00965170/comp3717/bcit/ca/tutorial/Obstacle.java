package test.a00965170.comp3717.bcit.ca.tutorial;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by NamBlue on 1/19/2017.
 */

public class Obstacle implements GameObject
{
    //the obstacle will consist of two rectangles with a gap in-between
    private Rect rectangle;
    private Rect rectangle2;
    private int color;

    public Rect getRectangle()
    {
        return rectangle;
    }

    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap)
    {

        this.color = color;
        this.rectangle = new Rect(0, startY, startX, startY + rectHeight);
        this.rectangle2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);
    }

    public boolean playerCollided(RectPlayer player)
    {
        return Rect.intersects(rectangle, player.getRectangle()) || Rect.intersects(rectangle2, player.getRectangle());
    }

    public void incrementY(Float y)
    {
        rectangle.top += y;
        rectangle.bottom += y;

        rectangle2.top += y;
        rectangle2.bottom += y;
    }

    @Override
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2, paint);
    }

    @Override
    public void update()
    {

    }

}

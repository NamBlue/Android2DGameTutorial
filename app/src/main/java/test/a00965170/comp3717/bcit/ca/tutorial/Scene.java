package test.a00965170.comp3717.bcit.ca.tutorial;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by NamBlue on 1/20/2017.
 */

public interface Scene
{
    public void update();
    public void draw(Canvas canvas);
    //Whenever scene is ending will tell scene manager to switch the active scene
    public void terminate();
    public void receiveTouch(MotionEvent event);
}

package test.a00965170.comp3717.bcit.ca.tutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by namblue on 1/19/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    private MainThread thread;

    private SceneManager sceneManager;

    public GamePanel(Context context)
    {
        super(context);

        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;
        thread = new MainThread(getHolder(), this);
        sceneManager = new SceneManager();

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while (true)
        {
            try
            {
                thread.setRunning(false);
                thread.join();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        sceneManager.receiveTouch(event);
        return true;
        //return super.onTouchEvent(event);
    }

    public void update()
    {
        sceneManager.update();
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        sceneManager.draw(canvas);
    }

}

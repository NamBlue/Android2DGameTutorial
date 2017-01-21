package test.a00965170.comp3717.bcit.ca.tutorial;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class SceneManager
{

    //Work Done Here 5:24
    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;

    public SceneManager()
    {
        ACTIVE_SCENE = 0;
        scenes.add(new GameplayScene());
    }

    public void receiveTouch(MotionEvent event)
    {
        scenes.get(ACTIVE_SCENE).receiveTouch(event);
    }

    public void update()
    {
        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas)
    {
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
}

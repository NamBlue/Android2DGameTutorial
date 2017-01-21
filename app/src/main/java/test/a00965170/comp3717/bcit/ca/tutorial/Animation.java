package test.a00965170.comp3717.bcit.ca.tutorial;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class Animation
{
    private Bitmap[] frames;
    private int frameIndex;
    //Frame time in seconds, so much convert back to ms when using
    private float frameTime;
    private long lastFrame;
    private boolean isPlaying = false;

    public boolean isPlaying()
    {
        return isPlaying;
    }

    public void play()
    {
        isPlaying = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }
    public void stop()
    {
        isPlaying = false;
    }
    public Animation(Bitmap[] frames, float animTime)
    {
        this.frames = frames;
        frameIndex = 0;
        frameTime = animTime / frames.length;
        lastFrame = System.currentTimeMillis();
    }

    public void update()
    {
        if (!isPlaying)
        {
            return;
        }
        if(System.currentTimeMillis() - lastFrame > frameTime * 1000)
        {
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }

    public void draw(Canvas canvas, Rect destination)
    {
        if(!isPlaying)
        {
            return;
        }
        scaleRect(destination);
        //null for src rect if want to draw entire image
        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint());
    }

    //Scale image but keep aspect ratio
    private void scaleRect(Rect rect)
    {
        float widthHeightRatio = (float)frames[frameIndex].getWidth()/frames[frameIndex].getHeight();
        if(rect.width() > rect.height())
        {
            rect.left = rect.right - (int)(rect.height() * widthHeightRatio);
        }
        else
        {
            rect.top = rect.bottom - (int)(rect.width() * (1 / widthHeightRatio));
        }
    }
}

package test.a00965170.comp3717.bcit.ca.tutorial;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class GameplayScene implements Scene
{
    //Used to set the bounds for the gameover text box
    private Rect rectBound = new Rect();
    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private boolean movingPlayer = false;
    private boolean gameOver = false;
    private long gameOverTime;

    public GameplayScene()
    {
        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3 * Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);
    }

    public void reset()
    {
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3 * Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);
        movingPlayer = false;
    }


    private void drawCentreText(Canvas canvas, Paint paint, String text)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(rectBound);
        int cHeight = rectBound.height();
        int cWidth = rectBound.width();
        paint.getTextBounds(text, 0, text.length(), rectBound);
        float x = cWidth / 2f - rectBound.width() / 2f - rectBound.left;
        float y = cHeight / 2f - rectBound.height() / 2f - rectBound.bottom;
        canvas.drawText(text, x ,y ,paint);
    }

    @Override
    public void update()
    {
        if (!gameOver)
        {
            player.update(playerPoint);
            obstacleManager.update();
            if (obstacleManager.playerCollide(player))
            {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);

        player.draw(canvas);

        obstacleManager.draw(canvas);

        if (gameOver)
        {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.BLUE);
            drawCentreText(canvas, paint, "Game Over");
        }
    }

    @Override
    public void terminate()
    {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)event.getX(), (int) event.getY()))
                {
                    movingPlayer = true;
                }
                if(gameOver &&System.currentTimeMillis() - gameOverTime >= Constants.GAMEOVER_TIME)
                {
                    reset();
                    gameOver = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer)
                {
                    playerPoint.set((int) event.getX(), (int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }
}

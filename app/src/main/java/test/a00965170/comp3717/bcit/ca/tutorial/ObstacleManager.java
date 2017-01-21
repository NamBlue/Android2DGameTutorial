package test.a00965170.comp3717.bcit.ca.tutorial;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class ObstacleManager
{
    //Higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    //Gap between obstacles
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private long startTime;
    private long initTime;

    private int score = 0;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color)
    {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    private void populateObstacles()
    {
        int currY = -5 * Constants.SCREEN_HEIGHT/4;

        while(currY < 0)
        {
            //If xStart was all the way on the right side of screen, the gap
            //will be off-screen and there will be no room for the player to navigate around in
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public boolean playerCollide(RectPlayer player)
    {
        for(Obstacle obstacle : obstacles)
        {
            if(obstacle.playerCollided(player))
            {
                return true;
            }
        }
        return false;
    }

    public void update()
    {
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //10 seconds to move through screen height, increase speed as time goes on 2000.0 = two seconds per speed up
        float speed = (float)(Math.sqrt(1 + (startTime - initTime) / 2000.0) * Constants.SCREEN_HEIGHT/10000.0f);

        for (Obstacle obstacle : obstacles)
        {
            obstacle.incrementY(speed * elapsedTime);
        }
        if (obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT)
        {
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
            score++;
        }
    }

    public void draw(Canvas canvas)
    {
        for(Obstacle obstacle : obstacles)
        {
            obstacle.draw(canvas);
        }
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.BLUE);
        canvas.drawText("" + score, Constants.SCREEN_WIDTH * 0.10f, Constants.SCREEN_HEIGHT * 0.10f, paint);
    }
}

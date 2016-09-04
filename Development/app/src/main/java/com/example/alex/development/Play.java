package com.example.alex.development;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.alex.development.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * This app displays an order form to order coffee.
 */
public class Play extends AppCompatActivity implements View.OnTouchListener {

    ArrayList<Ball> ball = new ArrayList<Ball>();
    int numOfBall = 10;
    int userScore = 0;
    private final int secondConv = 1000;
    private final int converter = 60;
    Drawing display;
    float touchX, touchY;
    boolean touched;
    boolean overlap;
    String minAndSecs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        final CounterClass timer = new CounterClass(50000, 1000);
        timer.start();
        overlap = false;
        for (int i = 0; i < numOfBall; i++) {
            Bitmap ballimg;
            Random r = new Random();
            int color = r.nextInt(3) + 1;

            if (color == 1) {
                ballimg = BitmapFactory.decodeResource(getResources(), R.drawable.redball);
            } else if (color == 2) {
                ballimg = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
            } else {
                ballimg = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
            }
            touchX = 0;
            touchY = 0;
            ball.add(new Ball(ballimg));
            ball.get(i).ballInit();
        }
        display = new Drawing(this);
        display.setOnTouchListener(this);
        setContentView(display);
    }

    @Override
    protected void onPause() {
        super.onPause();
        display.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        display.resume();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public String increaseScore(String scoreBoard) {
        int incrementScore = 100;

        userScore += incrementScore;
        scoreBoard = scoreBoard.substring(0, 7) + userScore; //five characters in Score:;
        return scoreBoard;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayScore(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.Score_Keeper);
        quantityTextView.setText("" + number);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        try {
            Thread.sleep(16);       // 1000/ 16 = 60 FPS
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (motionEvent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                touchX = motionEvent.getX();
                touchY = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                overlap = false;

            default:
                touchX = -100;
                touchY = -100;
        }
        return true;
    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisOfTime, long countDownInterval) {
            super(millisOfTime, countDownInterval);
        }

        //Executes Tick every countdownInterval in this case interval is 1 second
        public void onTick(long millisRemaining) {
            long Tempseconds = millisRemaining / secondConv;
            long minutes = Tempseconds / converter;
            long seconds = (Tempseconds % converter);
            minAndSecs = String.format("Timer: %02d:%02d", minutes, seconds);

        }
        //Executed OnFinish once only when timer reaches 0
        public void onFinish() {
            minAndSecs = String.format("Timer: 00:00");
        }
    }



/*
* Draws on Canvas
* */


    public class Drawing extends SurfaceView implements Runnable {
        SurfaceHolder ourHolder;
        Thread ourThread = null;
        boolean isRunning = false;

        public Drawing(Context context) {
            super(context);
            ourHolder = getHolder();
            ourThread = new Thread(this);
            ourThread.start();
        }

        public void pause() {
            isRunning = false;
            while (true) {
                try {
                    ourThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            ourThread = null;
        }

        public void resume() {
            isRunning = true;
            ourThread = new Thread(this);
            ourThread.start();
        }


        @Override
        public void run() {
            String scoreBoard = "Score: ";
            Paint textEdit = new Paint();
            textEdit.setTextAlign(Paint.Align.RIGHT);
            textEdit.setTextSize(32);

            while (isRunning) {
                if (!ourHolder.getSurface().isValid())
                    continue;

                Canvas canvas = ourHolder.lockCanvas();
                canvas.drawColor(0xffffffff);
                int imgWidth, imgHeight;

                for (int i = 0; i < ball.size(); i++) {

                    //gets the dimensions of the ball plus position
                    //to check if we touched the ball area.
                    imgWidth = ball.get(i).getImage().getWidth();
                    int ballX = ball.get(i).getPositionX();
                    int ballY = ball.get(i).getPositionY();
                    imgHeight = ball.get(i).getImage().getHeight();

                    // check if ball is in range of touch
                    if (ballX < touchX && touchX < (ballX + imgWidth) &&
                            ballY < touchY && touchY < (ballY + imgHeight)) {


                        //Checks if there is an image above the current one pressed
                        //if so remove the top, else continue to remove i
                        for (int j = (ball.size() - 1); j > i; j--) {
                            imgWidth = ball.get(j).getImage().getWidth();
                            ballX = ball.get(j).getPositionX();
                            ballY = ball.get(j).getPositionY();
                            imgHeight = ball.get(j).getImage().getHeight();

                            if (ballX < touchX && touchX < (ballX + imgWidth) &&
                                    ballY < touchY && touchY < (ballY + imgHeight)) {
                                if (onTouchBall(ball, j)) {
                                    scoreBoard = increaseScore(scoreBoard);
                                    break;
                                }
                            }
                        }

                        if (onTouchBall(ball, i)) {
                            scoreBoard = increaseScore(scoreBoard);
                            i--;
                            continue;
                        }
                    }

                    canvas.drawBitmap(ball.get(i).getImage(), ball.get(i).getPositionX(), ball.get(i).getPositionY(), null);
                    changeBallPosition(ball.get(i), canvas);
                }
                    canvas.drawText(scoreBoard, canvas.getWidth(), 64, textEdit); //text Edit determines, size, align, color etc
                    canvas.drawText(minAndSecs, 200, 64, textEdit);
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        public boolean onTouchBall(ArrayList<Ball> ball, int i) {
            boolean status = false;
            if(!overlap)
            {
                overlap = true;
                status = true;
                ball.remove(i);
            }
            return status;
        }

        //Decides whether the border case is possible then changes the position of
        //the ball by SpeedY and SpeedX
        public void changeBallPosition(Ball ball, Canvas canvas){
            ball.move(canvas.getWidth(), canvas.getHeight());
            ball.setPositionY(ball.speedY);
            ball.setPositionX(ball.speedX);
        }


    }

}
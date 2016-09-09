package com.example.alex.development;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;


/**
 * This app displays an order form to order coffee.
 */
public class Play extends AppCompatActivity implements View.OnTouchListener {

    ArrayList<Ball> ball = new ArrayList<>();
    int userScore = 0;

    Drawing display;
    float touchX, touchY;
    int gameBall;
    int spriteMove = 0;
    long timeRemaining = 0;
    boolean overlap;
    String minAndSecs = "No Time Started";
    CounterClass timer;
    private Handler mUiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer = new CounterClass(50000, 1000);
        timer.start();
        overlap = false;
        Random r = new Random();

        int numOfBall = 20;
        for (int i = 0; i < numOfBall; i++) {
            Bitmap ballimg = null;
            int color = r.nextInt(3) + 1;

            if (color == 1) {
                ballimg = BitmapFactory.decodeResource(getResources(), R.drawable.redball);
            } else if (color == 2) {
                ballimg = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
            } else if (color == 3) {
                ballimg = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
            }
            touchX = 0;
            touchY = 0;
            ball.add(new Ball(ballimg, color));
            ball.get(i).ballInit();
        }
        gameBall = r.nextInt(3) + 1;
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
        if (scoreBoard.length() > 6) {
            userScore += incrementScore;
            scoreBoard = scoreBoard.substring(0, 7) + userScore; //five characters in Score:;
        }
        return scoreBoard;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
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
            final int secondConv = 1000;
            final int converter = 60;
            long Tempseconds = millisRemaining / secondConv;
            long minutes = Tempseconds / converter;
            long seconds = (Tempseconds % converter);
            timeRemaining = millisRemaining;
            minAndSecs = String.format(Locale.US, "Timer: %02d:%02d", minutes, seconds);
        }


        //Executed OnFinish once only when timer reaches 0
        public void onFinish() {
            minAndSecs = "Timer: 00:00";
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


        String scoreBoard = "Score: ";

        @Override
        public void run() {
            Paint textEdit = new Paint();
            textEdit.setTextAlign(Paint.Align.RIGHT);
            textEdit.setTextSize(32);
            textEdit.setColor(0xffffffff);
            Bitmap backGND = BitmapFactory.decodeResource(getResources(), R.drawable.nebula);
            int FrameRate = 30;
            int FPS = 1000 / FrameRate;
            Rect frame = new Rect(0, 0, backGND.getWidth(), backGND.getHeight());

            while (isRunning) {
                if (!ourHolder.getSurface().isValid())
                    continue;
                long delay = System.currentTimeMillis();


                Canvas canvas = ourHolder.lockCanvas();
                // canvas.drawBitmap(backGND, 0, 0, null);
                int centerX = canvas.getWidth();
                int centerY = canvas.getHeight();
                Rect spriteFrame = new Rect(0, 0, centerX, centerY);
/*
                spriteMove += 800;
                if(spriteMove >= backGND.getWidth())
                {
                    spriteMove = 0;
                }
* */
                canvas.drawBitmap(backGND, frame, spriteFrame, null);
                int imgWidth, imgHeight;
                int counter = 0;
                for (int i = 0; i < ball.size(); i++) {

                    //gets the dimensions of the ball plus position
                    //to check if we touched the ball area.
                    imgWidth = ball.get(i).getImage().getWidth();
                    int ballX = ball.get(i).getPositionX();
                    int ballY = ball.get(i).getPositionY();
                    imgHeight = ball.get(i).getImage().getHeight();
                    if (ball.get(i).getBallColor() == gameBall)
                    {
                        counter++;
                    }

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
                                    break;
                                }
                            }
                        }

                        if (onTouchBall(ball, i)) {
                            i--;
                            continue;
                        }
                    }

                    canvas.drawBitmap(ball.get(i).getImage(), ball.get(i).getPositionX(), ball.get(i).getPositionY(), null);
                    changeBallPosition(ball.get(i), canvas);
                }
                canvas.drawText(scoreBoard, canvas.getWidth(), 64, textEdit); //text Edit determines, size, align, color etc
                canvas.drawText(minAndSecs, 200, 64, textEdit);
                //ARbitrary defined colors to number
                final int red = 1;
                final int blue = 2;
                final int green = 3;
                Bitmap img;
                switch (gameBall) {
                    case red:
                        img = BitmapFactory.decodeResource(getResources(), R.drawable.redball);
                        break;
                    case blue:
                        img = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
                        break;
                    case green:
                        img = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
                        break;
                    default:
                        img = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
                }
                canvas.drawBitmap(img, centerX / 2 - (img.getWidth() / 2), 0, null);

                long stop = System.currentTimeMillis();
                int loadTime = (int) (stop - delay);
                try {
                    if (loadTime < FPS)
                        Thread.sleep(FPS - loadTime);
                    else
                        Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                gameStatus(counter);
                ourHolder.unlockCanvasAndPost(canvas);

            }
        }

        public String equalBalls(String score, Ball ball) {
            if (ball.getBallColor() == gameBall) {
                score = increaseScore(score);
            } else {
                //;
                score += ball.getBallColor();
            }
            return score;
        }

        public void gameStatus(int balls) {
            if(balls == 0) {
                // win game show score
            }
            if(timeRemaining < 10 ){
                // lose game show score
            }

        }
        public boolean onTouchBall(ArrayList<Ball> ball, int i) {
            boolean status = false;
            if (!overlap) {
                overlap = true;
                status = true;
                if (ball.get(i).getBallColor() == gameBall) {
                    scoreBoard = increaseScore(scoreBoard);
                } else {
                    changeTime();
                }

                ball.remove(i);
            }
            return status;
        }

        public void changeTime() {
            Thread myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            long pentaly = 5000;
                            timer.cancel();
                            timer = new CounterClass(timeRemaining - pentaly, 1000);
                            timer.start();
                        }
                    });
                }
            });
            myThread.start();
        }


    }

    //Decides whether the border case is possible then changes the position of
    //the ball by SpeedY and SpeedX
    public void changeBallPosition(Ball ball, Canvas canvas) {
        ball.move(canvas.getWidth(), canvas.getHeight());
        ball.setPositionY(ball.speedY);
        ball.setPositionX(ball.speedX);
    }


}


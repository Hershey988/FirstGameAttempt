package com.example.alex.development;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


/**
 * This app displays an order form to order coffee.
 */
public class Play extends AppCompatActivity implements View.OnTouchListener {

    ArrayList<Ball> ball = new ArrayList<>();
    int userScore = 0;

    Drawing display;
    float touchX, touchY;
    private static int game_ball_color;

    long timeRemaining = 0;

    String minAndSecs = "No Time Started";
    CounterClass timer;
    private static final Handler mUiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer = new CounterClass(5000, 1000);
        timer.start();

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
        game_ball_color = r.nextInt(3) + 1;
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
            timeRemaining = 0;
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

                try {
                    ourThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
            Bitmap backGND = BitmapFactory.decodeResource(getResources(), R.drawable.nebula_animate);

            int image_clip = 0;
            int next_clip  = 20;

            while (isRunning) {
                if (!ourHolder.getSurface().isValid())
                    continue;
                long delay = System.currentTimeMillis();
                boolean overlap = false;
                Canvas canvas = ourHolder.lockCanvas();
                canvas.drawColor(-1); // -1 is white

                int centerX = canvas.getWidth();
                int centerY = canvas.getHeight();
                Rect frame = new Rect(0, image_clip, backGND.getWidth(),400 + image_clip);
                Rect spriteFrame = new Rect(0, 0, centerX, centerY);

                image_clip += next_clip;
                if(image_clip >= backGND.getHeight() - 400)
                {
                    image_clip = next_clip;
                }

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
                    if (ball.get(i).getBallColor() == game_ball_color)
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
                                if (onTouchBall(ball, j, overlap)) {
                                    overlap = true;
                                    break;
                                }
                            }
                        }

                        if (onTouchBall(ball, i, overlap)) {
                            overlap = true;
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
                switch (game_ball_color) {
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

                game_FPS(loadTime);     //Gives constant FPS otherwise game is lagging
                gameStatus(counter);    //Checks if game is won or lost
                ourHolder.unlockCanvasAndPost(canvas);

            }
        }

        /*
        * LoadTime gives the diffence of time for the program to complete one cycle
        * From the top of it's while(gameIsRunning) Loop
        * Sleeps if programming is attempting to run faster then FPS
        * else the program continues with no sleep
        *
        * */
        private void game_FPS(int loadTime) {
            int FrameRate = 30;
            int frame_per_cycle = 1000 / FrameRate; //gives the time per frame cycle
            int load_FPS= 1000 / loadTime;
            String gameFPS = null;

                if (loadTime < frame_per_cycle) {
                    try {
                        Thread.sleep(frame_per_cycle - loadTime);
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    gameFPS = Integer.toString(FrameRate);
                }
                if(gameFPS == null)
                    gameFPS = Integer.toString(load_FPS);
                Log.i("Game FPS: ", gameFPS); //Prints out in console the current Game FPS

        }

        /*
        * gameStatus checks if the game is won or Lost
        * If the game is lost ie. there are no more game_ball_color
        * then move on to the win activity and past on the score
        * If there is no more time left and there is still a game_ball_color
        * then move to Lose activity and past on the score
        * */
        public void gameStatus(int balls) {
            String results = scoreBoard.substring(6, scoreBoard.length());
            if(balls == 0) {
                // win game show score
                Log.i("INFORMATION FOR SCORE", results);

                Intent openScore = new Intent(getApplicationContext(), Score.class);
                openScore.putExtra("Score", results);
                startActivity(openScore);
            }
                String time = Long.toString(timeRemaining);
            if(timeRemaining < 10 ){
                Log.i("Amount of time left ", time);
                Intent openLost = new Intent(getApplicationContext(), Lose.class);
                openLost.putExtra("Score", results);
                startActivity(openLost);
                // lose game show score
            }

        }

        /*
        * Checks If there is an overlap on the balls that user touch
        * One a ball is removed overlap becomes true for the duration of the touch
        * this prevents one Touch getting the whole stack of balls
        * If the ball removed is the game color then we increase our score
        * otherwise we reduce the time
        *
        * returns true if there hasn't been an overlap detected
        * returns false if there already is an overlap
        * */
        public boolean onTouchBall(ArrayList<Ball> ball, int i, boolean stack) {
            boolean status = false;

            if (!stack) {
                status = true;
                if (ball.get(i).getBallColor() == game_ball_color) {
                    scoreBoard = increaseScore(scoreBoard);
                } else {
                    changeTime();
                }
                ball.remove(i);
            }
            return status;
        }

        /*
        * Stop current Timer and replaces it with a new Timer with less time
        * The amount of time removed is indicated by the penalty value
        * */
        public void changeTime() {
            Thread myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            long penalty = 5000;

                            timer.cancel();
                            timer = new CounterClass(timeRemaining - penalty, 1000);
                            timer.start();
                        }
                    });
                }
            });
            myThread.setDaemon(true); //Thread is suspended once main thread is terminated.
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


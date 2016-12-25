package com.example.alex.development;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
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

    Drawing display;
    float touchX, touchY;
    private static int game_ball_color;
    int numOfBallColors = 6;
    String minAndSecs = "No Time Started";
    long timeRemaining = 0;
    int userScore = 0;

    private static final Handler mUiHandler = new Handler();
    final int RED = 1;
    final int GREEN = 2;
    final int BLUE = 3;
    final int ORANGE = 4;
    final int PURPLE = 5;
    final int YELLOW = 6;
    CounterClass timer;

    final int balldimensions = dpToPx(60);
    // Music, sound list
    MediaPlayer backgroundMusic;

    //Function was brought to you by stackOverflow
    //http://stackoverflow.com/questions/4605527/converting-pixels-to-dp
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    /*
    * Set's up the timer, the # of balls in game, the balls colors
    * creates instances of balls, initializes game_ball_color,
    * touch listener, and content view
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final long starting_time = 20000; // Orginally 50000
        final long one_second_interval = 1000;



        timer = new CounterClass(starting_time, one_second_interval);
        timer.start();
        Random r = new Random();
        Bundle getLevel = getIntent().getExtras();
        int level = getLevel.getInt("Level");
        int numOfBall = level * 2 - 1;
        int min = level + 1; //avoid zero possibility by + 1
        int max = 1 + level * 2;//avoid zero possibility
        Bitmap ballimg = null;
        Bundle getBallColor = getIntent().getExtras();
        game_ball_color = getBallColor.getInt("game_ball_color"); //Returns the same ball color as the one saved in LoadingScreen.

        int color;
        color = game_ball_color;
        for (int i = 0; i < numOfBall - level; i++) {
            while (color == game_ball_color) {
                color = r.nextInt(numOfBallColors) + 1; // there are 3 colors
            }
            setBall(ballimg, color, min, max);
            color = game_ball_color;
        }
        for (int i = 0; i < level; i++) {
            setBall(ballimg, game_ball_color, min, max);
        }
        display = new Drawing(this);
        display.setOnTouchListener(this);       //initiates the onTouchListener
        setContentView(display);
        backgroundMusic = MediaPlayer.create(this, R.raw.jazzelevator); //creates a music file of jazzeLevator and stores it to backgroungMusic.
        //music = (ToggleButton) findViewById(R.id.togMusic); //music is the variable for toggle button.
       // backgroundMusic.start();
        //backgroundMusic.setLooping(true);
    }


    //Sets up a new instance of ball
    public void setBall(Bitmap ball_image, int color, int min, int max) {
        switch (color) {
            case RED:
                ball_image = BitmapFactory.decodeResource(getResources(), R.drawable.redball);
                break;
            case GREEN:
                ball_image = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
                break;
            case BLUE:
                ball_image = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
                break;
            case ORANGE:
                ball_image = BitmapFactory.decodeResource(getResources(), R.drawable.orangeball);
                break;
            case PURPLE:
                ball_image = BitmapFactory.decodeResource(getResources(), R.drawable.purpleball);
                break;
            case YELLOW:
                ball_image = BitmapFactory.decodeResource(getResources(), R.drawable.yellowball);
                break;
            default:
                System.out.println("We got an invalid color in loading.java");
                break;

        }
        ball.add(new Ball(ball_image, color, min, max));
    }

    MediaPlayer backMusic; //Plays music in the background

    @Override
    protected void onPause() {
        super.onPause();
        //backMusic.stop();
        display.pause();
        backgroundMusic.release();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // backMusic = MediaPlayer.create(Play.this, R.raw.middleislandaldo);
        //backMusic.start();
        display.resume();
    }

    /**
     * Increases our current score  by 100
     */
    public int increaseScore(int currScore) {
        int incrementScore = 100;
        return currScore + incrementScore;
    }

    boolean overlap = true;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = motionEvent.getX();
                touchY = motionEvent.getY();
                overlap = true;
                break;
            default:
                touchX = touchY = 0;
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
* Drawing class is the meat of the program. The constructor sets up our new thread that will
* continously running to avoid blocking the main UI thread.
* When the app is tabbed out "pause" method will be executed
* when the app is tabbed back in "resume" method will execute
* While on screen the app continously runs the "run" method
* */


    public class Drawing extends SurfaceView implements Runnable {
        SurfaceHolder ourHolder;
        Thread ourThread = null;
        boolean game_is_running = false;


        MediaPlayer pop;
        MediaPlayer wrong_pop;

        public Drawing(Context context) {
            super(context);
            ourHolder = getHolder();
            ourThread = new Thread(this);
            ourThread.start();
        }

        public void pause() {
            if (pop != null) {
                pop.release();
            }
            if (wrong_pop != null) {
                wrong_pop.release();
            }
            game_is_running = false;

            try {
                ourThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ourThread = null;
        }

        public void resume() {
            game_is_running = true;
            ourThread = new Thread(this);  //No idea if this is actually needed or what this does
            ourThread.start();
        }


        int currScore = -1; // -1 is default unintialized


        /*
        * Run creates a blank canvas and redraws all the balls on the canvas the FPS is set in
        * the method "game_FPS" This function iterates through our ball array and changes the
        * positions of those balls base on their given speed.
        * If the onTouch method detects the finger press matches the ball position
        * we removed the ball from the array list
        *
        *
        * */
        @Override
        public void run() {
            //If currScore is unintialized, check if we had a previous score
            if (currScore == -1) {
                Bundle getScore = getIntent().getExtras();
                try {
                    currScore = getScore.getInt("Score");
                } catch (NullPointerException e) {
                    currScore = 0;
                }
            }
            Paint textEdit = new Paint();
            textEdit.setTextAlign(Paint.Align.RIGHT);
            textEdit.setTextSize(32);
            textEdit.setColor(0xffffffff);  // black
            Bitmap backGND = BitmapFactory.decodeResource(getResources(), R.drawable.gameplaybackground);

            int image_clip = 0; // Show's one clip of an image example image 1 in |1|2|3|4|5|
            final int next_clip = 20; //distance of the next image clip example from image 1 to 2 in |1|2|3|4|5|


            long delay;
            int centerX, centerY;
            Bitmap img = game_ball_image();

            Bitmap resizeImg = Bitmap.createScaledBitmap(img, balldimensions, balldimensions, true);
            Rect frame, spriteFrame;
            int imgWidth, imgHeight;
            int counter;
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            while (game_is_running) {
                if (!ourHolder.getSurface().isValid())
                    continue;
                delay = System.currentTimeMillis();

                Canvas canvas = ourHolder.lockCanvas();
                canvas.drawColor(-1); // -1 is white

                centerX = canvas.getWidth();
                centerY = canvas.getHeight();
//                frame = new Rect(0, image_clip, backGND.getWidth(), 400 + image_clip);
//                spriteFrame = new Rect(0, 0, centerX, centerY);

//                image_clip += next_clip;
//                if (image_clip >= backGND.getHeight() - 400) {
//                    image_clip = next_clip;
//                }
//
//                canvas.drawBitmap(backGND, frame, spriteFrame, null);
                Bitmap resizeBackGND = Bitmap.createScaledBitmap(backGND, centerX, centerY, false);
                canvas.drawBitmap(resizeBackGND, 0, 0, null);
                counter = 0;
                for (int i = 0; i < ball.size(); i++) {
                    if (ball.get(i).getBallColor() == game_ball_color) {
                        counter++;
                    }

                    //gets the dimensions of the ball plus position
                    //to check if we touched the ball area.
                    imgWidth = ball.get(i).getImage().getWidth();
                    imgHeight = ball.get(i).getImage().getHeight();
                    int ballX = ball.get(i).getPositionX();
                    int ballY = ball.get(i).getPositionY();

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

                    changeBallPosition(ball.get(i), canvas);
                    Bitmap ballImg = Bitmap.createScaledBitmap(ball.get(i).getImage(), balldimensions, balldimensions, true);
                    canvas.drawBitmap(ballImg, ball.get(i).getPositionX(), ball.get(i).getPositionY(), null);
                }
                canvas.drawText("Score: " + currScore, canvas.getWidth(), 64, textEdit); //text Edit determines, size, align, color etc
                canvas.drawText(minAndSecs, 200, 64, textEdit);
                //ARbitrary defined colors to number

                //canvas.drawBitmap(resizeImg, centerX / 2 - (img.getWidth() / 2), 0, null);    // Draws the game Ball
                canvas.drawBitmap(resizeImg, centerX / 2 - resizeImg.getWidth() / 2, 10, null);    // Draws the game Ball

                long stop = System.currentTimeMillis();
                int loadTime = (int) (stop - delay);

                game_FPS(loadTime);     //Gives constant FPS otherwise game is lagging
                gameStatus(counter);    //Checks if game is won or lost
                ourHolder.unlockCanvasAndPost(canvas);

            }
        }

        /*
        * Returns the image to the corresponding gameball color
        * */

        private Bitmap game_ball_image() {

            switch (game_ball_color) {
                case RED:
                    return BitmapFactory.decodeResource(getResources(), R.drawable.redl);
                case GREEN:
                    return BitmapFactory.decodeResource(getResources(), R.drawable.greenl);
                case BLUE:
                    return BitmapFactory.decodeResource(getResources(), R.drawable.bluel);
                case ORANGE:
                    return BitmapFactory.decodeResource(getResources(), R.drawable.orangel);
                case PURPLE:
                    return BitmapFactory.decodeResource(getResources(), R.drawable.purplel);
                case YELLOW:
                    return BitmapFactory.decodeResource(getResources(), R.drawable.yellowl);
                default:
                    return BitmapFactory.decodeResource(getResources(), R.drawable.greenl);
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
            int FrameRate = 60;
            int frame_per_cycle = 1000 / FrameRate; //gives the time per frame cycle
            int load_FPS = 1000 / loadTime;
            String gameFPS = null;

            if (loadTime < frame_per_cycle) {
                try {
                    Thread.sleep(frame_per_cycle - loadTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gameFPS = Integer.toString(FrameRate);
            }
            if (gameFPS == null)
                gameFPS = Integer.toString(load_FPS);
            //Log.i("Game FPS: ", gameFPS); //Prints out in console the current Game FPS

        }

        /*
        * gameStatus checks if the game is won or Lost
        * If the game is lost ie. there are no more game_ball_color
        * then move on to the win activity and past on the score
        * If there is no more time left and there is still a game_ball_color
        * then move to Lose activity and past on the score
        * */
        public void gameStatus(int balls) {
            Bundle getLevel = getIntent().getExtras();
            int level = getLevel.getInt("Level");
            if (balls == 0) {
                // win game pass down the currScore to add to the next level
//                Intent openScore = new Intent(getApplicationContext(), Score.class);
//                openScore.putExtra("Score", currScore);
//                startActivity(openScore);


                Intent intent = new Intent(getApplicationContext(), LoadingScreen.class);
                intent.putExtra("Score", currScore);
                intent.putExtra("Level", level);
                startActivity(intent);
                finish();
            }

            if (timeRemaining == 0) {
                // lose game show score

                Intent openLost = new Intent(getApplicationContext(), Lose.class);
                openLost.putExtra("Score", currScore);
                openLost.putExtra("Level", level);
                finish();
                startActivity(openLost);
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
        public boolean onTouchBall(ArrayList<Ball> ball, int i) {
            boolean status = false;


            pop = MediaPlayer.create(Play.this, R.raw._correct_pop);
            wrong_pop = MediaPlayer.create(Play.this, R.raw._wrong_pop3);
            if (overlap) {
                status = true;
                overlap = false;
                if (ball.get(i).getBallColor() == game_ball_color) {
                    pop.start();                //Special effect when corret ball is pop
                    currScore = increaseScore(currScore);
                } else {
                    wrong_pop.start();
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

        //Decides whether the border case is possible then changes the position of
        //the ball by SpeedY and SpeedX
        public void changeBallPosition(Ball ball, Canvas canvas) {
            ball.move(canvas.getWidth(), canvas.getHeight());
            ball.setPositionY(ball.speedY);
            ball.setPositionX(ball.speedX);
        }

    }


}


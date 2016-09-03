package com.example.alex.development;
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
    int userScore = 0000;
    private final int secondConv = 1000;
    private final int converter = 60;
    Drawing display;
    float touchX, touchY;
    boolean touched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        // final CounterClass timer = new CounterClass(50000, 1000);
        //timer.start();

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
    public void increaseScore(View view) {
        int incrementScore = 100;

        userScore += incrementScore;
        displayScore(userScore);
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
        switch (motionEvent.getAction()){

        case MotionEvent.ACTION_DOWN:
            touchX = motionEvent.getX();
            touchY = motionEvent.getY();
        break;
        default:
            touchX = 0;
            touchY = 0;
        }
        return true;
    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisOfTime, long countDownInterval) {
            super(millisOfTime, countDownInterval);
        }

        public void onTick(long millisRemaining) {
            long Tempseconds = millisRemaining / secondConv;
            long minutes = Tempseconds / converter;
            long seconds = (Tempseconds % converter);
            String minAndSecs = String.format("%02d:%02d", minutes, seconds);
            TextView quantityTextView = (TextView) findViewById(R.id.Timer);
            quantityTextView.setText(minAndSecs);
        }

        public void onFinish() {
            TextView TimesUp = (TextView) findViewById(R.id.Timer);
            TimesUp.setText("00:00");
        }
    }

    private void displayTime(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.Timer);
        quantityTextView.setText("" + number);
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
            while (isRunning) {
                if (!ourHolder.getSurface().isValid())
                    continue;

                Canvas canvas = ourHolder.lockCanvas();
                canvas.drawColor(0xffffffff);

                int imgWidth, imgHeight;

                for (int i = 0; i < ball.size(); i++) {
                    imgWidth = ball.get(i).getImage().getWidth();
                    int ballX = ball.get(i).getPositionX();
                    int ballY = ball.get(i).getPositionY();
                    imgHeight = ball.get(i).getImage().getHeight();

                    // check if ball is in range of touch
                    if (ballX < touchX && touchX < (ballX + imgWidth) &&
                            ballY < touchY && touchY < (ballY + imgHeight)) {
                        ball.remove(i);
                        i--;
                        continue;
                    }

                    canvas.drawBitmap(ball.get(i).getImage(), ball.get(i).getPositionX(), ball.get(i).getPositionY(), null);
                    ball.get(i).move(canvas.getWidth(), canvas.getHeight());
                    ball.get(i).setPositionY(ball.get(i).speedY);
                    ball.get(i).setPositionX(ball.get(i).speedX);
                }
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }


    }

}
package com.example.alex.development;
/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p/>
 * package com.example.android.justjava;
 */

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.alex.development.R;

/**
 * This app displays an order form to order coffee.
 */
public class Play extends AppCompatActivity {


    int userScore = 0000;
    private final int secondConv = 1000;
    private final int converter = 60;

    DrawBall[] balls = new DrawBall[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
       // final CounterClass timer = new CounterClass(50000, 1000);
        //timer.start();
        for(int i = 0; i < balls.length; i++) {
            balls[i] = new DrawBall(this);
        }
        setContentView(balls[1]);
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

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisOfTime, long countDownInterval) {
            super(millisOfTime, countDownInterval);
        }

        public void onTick(long millisRemaining) {
            long Tempseconds = millisRemaining / secondConv;
            long minutes = Tempseconds/ converter;
            long seconds = (Tempseconds % converter) ;
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
}
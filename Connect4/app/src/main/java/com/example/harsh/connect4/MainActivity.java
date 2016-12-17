package com.example.harsh.connect4;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int player = 0;         // 0=green  1=red
    boolean active = true;  //checks if the game is active
    int [] gameState= {2,2,2,2,2,2,2,2,2}; //2 means unlayed
    int [][] WinningPositions = {{0,1,2}, {3,4,5}, {6,7,8},        //Horizontal Possibilities
                                    {0,3,6}, {1,4,7}, {2,5,8},      //Vertical Possibilities
                                        {0,4,8},{2,4,6}}  ;         //Diagonal Possibilities

    //dropIn Function is called everytime there is a click
    public void dropIn(View view){
        ImageView center = (ImageView) view;
        int tapped = Integer.parseInt(center.getTag().toString());  // getting the tag and converting it to string and then to an integer.
        System.out.println("Preessed at : " + tapped);

        if(gameState[tapped] == 2 && active){
            gameState[tapped] = player;
            center.setTranslationY(-1000f);
            if(player == 0){
                center.setImageResource(R.drawable.green);
                player = 1;
            }
            else {
                center.setImageResource(R.drawable.red);
                player = 0;
            }
            center.animate().translationYBy(1000f).rotation(360f).setDuration(300);
        }

        for(int [] win : WinningPositions){
            if(gameState[win[0]] == gameState[win[1]] && gameState[win[1]] == gameState[win[2]] && gameState[win[0]] != 2){
                System.out.println(gameState[win[0]]);

                active = false; //game isn't active
                //Someone has Won
                String winner = "Red";
                LinearLayout popup = (LinearLayout) findViewById(R.id.playAgainPop);
                popup.setBackgroundColor(Color.parseColor("#FF5050"));
                if(gameState[win[0]] == 0){
                    winner = "Green";
                    System.out.println("Changed to Green!");
                    popup.setBackgroundColor(Color.parseColor("#50FF50"));
                }

                TextView text = (TextView)findViewById(R.id.WonText);
                text.setText(winner + " has Won!");


                popup.setVisibility(View.VISIBLE);
            }
            else{
                boolean draw = true;
                for(int state: gameState){
                    if(state == 2){
                        draw = false;
                        System.out.println("Not a Win Yet");
                    }
                }

                if(draw){
                    System.out.println("DRAWWWW");
                    TextView text = (TextView)findViewById(R.id.WonText);
                    text.setText("It's a DRAW!");

                    LinearLayout popup = (LinearLayout) findViewById(R.id.playAgainPop);
                    popup.setBackgroundColor(Color.parseColor("#5050FF"));
                    popup.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view){
        LinearLayout popup = (LinearLayout) findViewById(R.id.playAgainPop);
        popup.setVisibility(View.INVISIBLE);

        active = true;
        player = 0;
        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout layout = (GridLayout) findViewById(R.id.gridLay);
        for(int i = 0; i < layout.getChildCount(); i++){
            ((ImageView)layout.getChildAt(i)).setImageResource(0);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

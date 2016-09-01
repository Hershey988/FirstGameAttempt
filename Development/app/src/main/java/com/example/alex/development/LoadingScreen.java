package com.example.alex.development;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

/**
 * Created by Alex on 8/30/2016.
 */
public class LoadingScreen extends Activity {
    int Seconds = 9;
    int milliPerSec = 1000;
    MediaPlayer themeSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beautiful);
        themeSong = MediaPlayer.create(LoadingScreen.this, R.raw.fireindbyze);
        themeSong.start();
        Thread Launch = new Thread (){
            public void run(){
                try{
                    sleep(Seconds*milliPerSec);

                }catch(InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    Intent open = new Intent("com.example.alex.development.MENU");//the action of the xml
                    startActivity(open);
                }
            }
        };
        Launch.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        themeSong.release();
        finish();
    }
}

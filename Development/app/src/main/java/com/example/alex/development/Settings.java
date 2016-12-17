package com.example.alex.development;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.ToggleButton;

/**
 * Created by Alex on 9/1/2016.
 */
public class Settings extends Activity {
    MediaPlayer backgroundMusic;
    ToggleButton music;
    Thread threadSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslayout);

        backgroundMusic = MediaPlayer.create(Settings.this, R.raw.jazzelevator);
        music = (ToggleButton) findViewById(R.id.togMusic);

        SharedPreferences pref = getSharedPreferences("musicSet", Context.MODE_PRIVATE);

        music.setChecked(pref.getBoolean("isMusicOn", true));
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toggleOn()) {
                    backgroundMusic.start();
                }
                else {
                    backgroundMusic.pause();
                }
            }
        });
        if(toggleOn()) {
            backgroundMusic.start();
        } else {
            backgroundMusic.pause();
            }
    }


    public boolean toggleOn(){

        return music.isChecked();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences pref = getSharedPreferences("musicSet", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isMusicOn", toggleOn());
        editor.apply();
        backgroundMusic.release();
        finish();

    }
}

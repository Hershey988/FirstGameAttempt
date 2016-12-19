package com.example.alex.development;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

/**
 * Created by Alex on 9/1/2016.
 */
public class Settings extends Activity {
    MediaPlayer backgroundMusic;
    ToggleButton music;
    AudioManager manage;
    boolean musicOn;
    Thread threadSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslayout);

        backgroundMusic = MediaPlayer.create(Settings.this, R.raw.jazzelevator); //creates a music file of jazzeLevator and stores it to backgroungMusic.
        //music = (ToggleButton) findViewById(R.id.togMusic); //music is the variable for toggle button.
        backgroundMusic.start();
        manage = (AudioManager) getSystemService(Context.AUDIO_SERVICE); //store the audio service into manage(AudioManager)
        int maxVol = manage.getStreamMaxVolume(AudioManager.STREAM_MUSIC); //store the max volume of the system into maxVol
        int curVol = manage.getStreamVolume(AudioManager.STREAM_MUSIC); //store the current volume of the system into curVol

        SeekBar volumeControl = (SeekBar) findViewById(R.id.volumeBar);
        volumeControl.setMax(maxVol);  //setting the game volume max to the system maximum value.
        volumeControl.setProgress(curVol); //sets the seekBar to Current Volume.
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //Log.i("SeekBar Value: ", Integer.toString(i));
                manage.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0); //sets the volume according to the seekbar.
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}

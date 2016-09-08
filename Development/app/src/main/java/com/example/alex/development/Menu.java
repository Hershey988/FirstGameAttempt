package com.example.alex.development;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Alex on 8/31/2016.
 */
public class Menu extends Activity{
    String classes[] = {"Play", "Settings", "PlaySurface"};
    MediaPlayer ourSong;
    boolean music;  //Checks if we should play music

    public void playPress(View view) {
        Intent intent = new Intent(getApplicationContext(), Play.class);
        startActivity(intent);
    }

    public void playSurfacePress(View view) {
        Intent intent = new Intent(getApplicationContext(), PlaySurface.class);
        startActivity(intent);
    }

    public void settingsPressed(View view) {
        Intent intent = new Intent(getApplicationContext(), Settings.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ourSong = MediaPlayer.create(Menu.this, R.raw.jazzelevator);

        SharedPreferences getPrefs = getSharedPreferences("musicSet", Context.MODE_PRIVATE);
        music = getPrefs.getBoolean("isMusicOn", true);
        if(music)
        {
            ourSong.start();
        }
    }

    /*
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String currentClass = classes[position];
        try{
            Class ourClass = Class.forName("com.example.alex.development." + currentClass);
            Intent myIntent = new Intent(Menu.this, ourClass);
            startActivity(myIntent);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

    }
    * */

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences getPrefs = getSharedPreferences("musicSet", Context.MODE_PRIVATE);
        music = getPrefs.getBoolean("isMusicOn", true);
        if(music)
        {
            ourSong.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ourSong.pause();

    }
}

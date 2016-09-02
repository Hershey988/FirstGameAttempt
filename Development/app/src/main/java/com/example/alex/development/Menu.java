package com.example.alex.development;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Alex on 8/31/2016.
 */
public class Menu extends ListActivity{
    String classes[] = {"Play", "Settings", "PlaySurface"};
    MediaPlayer ourSong;
    boolean music;  //Checks if we should play music
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, classes));
        ourSong = MediaPlayer.create(Menu.this, R.raw.metroidechoes);

        SharedPreferences getPrefs = getSharedPreferences("musicSet", Context.MODE_PRIVATE);
        music = getPrefs.getBoolean("isMusicOn", true);
        if(music)
        {
            ourSong.start();
            ourSong.seekTo(18000);
        }
    }

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

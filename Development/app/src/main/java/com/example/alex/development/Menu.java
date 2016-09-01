package com.example.alex.development;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Alex on 8/31/2016.
 */
public class Menu extends ListActivity{
    String classes[] = {"Play", "HighScore", "Settings"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, classes));
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


}

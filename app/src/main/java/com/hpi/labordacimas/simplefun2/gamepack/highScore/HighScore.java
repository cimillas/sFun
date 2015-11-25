package com.hpi.labordacimas.simplefun2.gamepack.highScore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.hpi.labordacimas.simplefun2.mainpack.MainActivity;
import com.hpi.labordacimas.simplefun2.R;
import com.hpi.labordacimas.simplefun2.changeTheme.Utils;
import com.hpi.labordacimas.simplefun2.gamepack.game.Game;

import java.util.ArrayList;

public class HighScore extends Activity implements View.OnClickListener{

    private ListView listview;
    private Button main_menu_button, play_again_button;
    private HScore userscore;
    private ArrayList<HScore> storedhighscores = new ArrayList<>();
    private String [] obtainedhighscores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Last Game Score
        int level;
        String name;
        Intent intent = getIntent();
        //time = intent.getIntExtra("Player Score Level", 0);
        level = intent.getIntExtra("Player Score Time", 0);
        name = intent.getStringExtra("Player Name");
        userscore = new HScore(level,name);

        Utils.setThemeToActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        //Buttons
        main_menu_button = (Button) findViewById(R.id.main_menu_button);
        play_again_button = (Button) findViewById(R.id.play_again_button);
        main_menu_button.setOnClickListener(this);
        play_again_button.setOnClickListener(this);



        //Obtaining highscores from sharedpreference file:
        SharedPreferences preferences = this.getSharedPreferences("HighscoreFile", Context.MODE_PRIVATE);
        //Saving highscores in sharedpreferences file.
        SharedPreferences.Editor editor = preferences.edit();
        //editor
        //orderhighscores(highscore,storedhighscores);
        //ListView
        String highscores [] = {level +"\t" + name};
        listview = (ListView) findViewById(R.id.listView_highscore);
        ArrayAdapter<String> highscoreadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, highscores);
        listview.setAdapter(highscoreadapter);
/*
        SharedPreferences settings = getSharedPreferences("FontChange",0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ChosenFont", font);
        editor.commit();
        */
    }

    private ArrayList<String> orderhighscores(String h,ArrayList<String> l){
        ArrayList<String> result = new ArrayList<>();
        String head = l.get(0);
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.main_menu_button):
                Intent intent1;
                intent1 = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent1);
                finish();
                break;
            case (R.id.play_again_button):
                Intent intent2;
                intent2 = new Intent(getBaseContext(), Game.class);
                startActivity(intent2);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent;
        backIntent = new Intent (getBaseContext(), MainActivity.class);
        startActivity(backIntent);
        finish();
    }
}

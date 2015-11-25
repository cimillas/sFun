package com.hpi.labordacimas.simplefun2.gamepack.highScore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hpi.labordacimas.simplefun2.mainpack.MainActivity;
import com.hpi.labordacimas.simplefun2.R;
import com.hpi.labordacimas.simplefun2.changeTheme.Utils;
import com.hpi.labordacimas.simplefun2.gamepack.game.Game;

public class PopupScore extends Activity implements View.OnClickListener{

    private int level;
    private int time;
    private ImageButton close_button, save_button, restart_button;
    private TextView score_textview, time_textview;
    private EditText player_name_edittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Game Score
        Intent intent = getIntent();
        level = intent.getIntExtra("Level", 0);
        time = intent.getIntExtra("Time",0);


        Utils.setThemeToActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_score);

        //Obtaining views.
        close_button = (ImageButton) findViewById(R.id.imagebutton_close_highscore);
        save_button = (ImageButton) findViewById(R.id.save_score_imagebutton);
        restart_button = (ImageButton) findViewById(R.id.button_restart_highscore);
        score_textview = (TextView) findViewById(R.id.player_score);
        time_textview = (TextView) findViewById(R.id.player_time);
        player_name_edittext = (EditText) findViewById(R.id.editText_Player_name);

        //Setting score and time values on layout
        score_textview.setText(String.valueOf(level));
        time_textview.setText(String.valueOf(time));

        //Adding button listeners

        close_button.setOnClickListener(this);
        save_button.setOnClickListener(this);
        restart_button.setOnClickListener(this);




/*
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width *.8),(int) (height *0.8));
        */
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imagebutton_close_highscore:
                Intent intent1;
                intent1 = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.save_score_imagebutton:
                //Obtaining player's name
                Intent intent2;
                intent2 = new Intent(getBaseContext(), HighScore.class);
                //Adding score to highscore
               // intent2.putExtra("Player Score Time", time);
                intent2.putExtra("Player Score Level", level);
                intent2.putExtra("Player Name", player_name_edittext.getText().toString());
                startActivity(intent2);
                finish();
                break;
            case R.id.button_restart_highscore:
                //Obtaining player's name
                Intent intent3;
                intent3 = new Intent(getBaseContext(), Game.class);
                //Restarting game
                startActivity(intent3);
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent;
        backIntent = new Intent (getBaseContext(), HighScore.class);
        startActivity(backIntent);
        finish();
    }
}

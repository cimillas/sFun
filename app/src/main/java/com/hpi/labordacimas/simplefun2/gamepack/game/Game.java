package com.hpi.labordacimas.simplefun2.gamepack.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.os.Vibrator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;
import android.view.WindowManager;

import com.hpi.labordacimas.simplefun2.R;
import com.hpi.labordacimas.simplefun2.changeTheme.Utils;
import com.hpi.labordacimas.simplefun2.gamepack.highScore.PopupScore;
import com.hpi.labordacimas.simplefun2.mainpack.MainActivity;

public class Game extends Activity implements View.OnClickListener{

    private ColorButton[] button = new ColorButton[9];
    private Button hint_button;
    private Sequence sequence ;
    private TextView score_textview;
    public  MediaPlayer game_music;
    public  MediaPlayer correct_sound;
    public MediaPlayer button_sound;
    public MediaPlayer wrong_sound;
    public MediaPlayer [] s = new MediaPlayer[9];
    private int music_length;
    public static Animation animButton;
    private Chronometer chronometer;
    /**
     * Number of times -1 the user has completed the sequence correctly.
     */
    private int level_int = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Utils.setThemeToActivity(this);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ///////////////////Adding Buttons//////////////////////////

        //Color Buttons
        button[0] = (ColorButton) findViewById(R.id.game_button1);
        button[1] = (ColorButton) findViewById(R.id.game_button2);
        button[2] = (ColorButton) findViewById(R.id.game_button3);
        button[3] = (ColorButton) findViewById(R.id.game_button4);
        button[4] = (ColorButton) findViewById(R.id.game_button5);
        button[5] = (ColorButton) findViewById(R.id.game_button6);
        button[6] = (ColorButton) findViewById(R.id.game_button7);
        button[7] = (ColorButton) findViewById(R.id.game_button8);
        button[8] = (ColorButton) findViewById(R.id.game_button9);
        //Hint Button
        hint_button = (Button) findViewById(R.id.hint_button);
        hint_button.setOnClickListener(this);
        //Adding listeners to buttons.
        for(int i = 0; i<button.length; i++){
            //button[i].setIdentification(i);
            button[i].setOnClickListener(this);
        }

        ///////////////////////ANIMATION///////////////////////////////

        //Adding button animation
        animButton = AnimationUtils.loadAnimation(this, R.anim.anim_button);

        //Starting new Sequence
        sequence = new Sequence(button);

        //Adding text to SequenceTextView and score_textview
        //sequenceText = (TextView) findViewById(R.id.SequenceTextView);
        //addSequenceText();

        score_textview = (TextView) findViewById((R.id.score_textview));
        addLevelText();

        //Chronometer
        chronometer = (Chronometer) findViewById(R.id.score_chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        //Music Music Music
        //Creating Sounds for the buttons

        game_music = MediaPlayer.create(this, R.raw.gamemusic);
        correct_sound = MediaPlayer.create(this, R.raw.correctsound);
        wrong_sound = MediaPlayer.create(this, R.raw.errorsound);

        //Starting game music
        //game_music.setLooping(true);
        //game_music.start();

        //Button Sounds
        s[0] = MediaPlayer.create(this, R.raw.b1);
        s[1] = MediaPlayer.create(this, R.raw.b2);
        s[2] = MediaPlayer.create(this, R.raw.b3);
        s[3] = MediaPlayer.create(this, R.raw.b4);
        s[4] = MediaPlayer.create(this, R.raw.b5);  //sonidos de los botones
        s[5] = MediaPlayer.create(this, R.raw.b6);
        s[6] = MediaPlayer.create(this, R.raw.b7);
        s[7] = MediaPlayer.create(this, R.raw.b8);
        s[8] = MediaPlayer.create(this, R.raw.b9);

        //Adding music and animations to the buttons
        for(int i = 0; i<button.length; i++){
            button[i].setIdentification(i);
            button[i].setSound(s[i]);
            button[i].setButtonanim(animButton);
        }
        //Sequence animation starts.
        sequence.start();
    }

    /**
     * User clicks one of the color botons of the game.
     * @param v Button the user clicks.
     */
    @Override
    public void onClick(View v) {
        //Maybe there can be a conflict here with the sequence...
        //Checking we are pressing when of the game buttons
        if(v instanceof ColorButton){
            //Clearing any possible animation being executed in the game
            for(int i = 0; i< this.button.length; i++){
                button[i].clearAnimation();
            }
            v.startAnimation(animButton);
            //((ColorButton) v).startSound();

            //Pausing any possible button sound.
            ColorButton.pauseButtonSounds(button);
            checkAnswer((ColorButton) v);
        }
        //Hint listener
        if(v.getId() == R.id.hint_button){
            Toast t = Toast.makeText(getBaseContext(), sequence.toString(), Toast.LENGTH_LONG);
            t.show();
            sequence.start();
        }

    }




    private void checkAnswer(ColorButton x){

        if(sequence.checkAnswer(x)){
            //Correct Answer

            //We start the color button sound
            ColorButton.buttonMusicStart(x);
            //correct_sound.start();  //Music sounds
            //buttonMusicStart(x);
            sequence.increaseIndex();   //index points to the next element

            if(sequence.levelCompleted()){
                //Level Completed
                level_int++;
                addLevelText();
                //Activating hint button
                if(level_int >= 7){
                    hint_button.setEnabled(true);
                    hint_button.setClickable(true);
                }

                //We add a new random button to the sequence
                sequence.increaseSequence();
                //We reset the button pointer of the sequence.
                sequence.resetIndexGame();
                //addSequenceText();

                //Animations starts now.
                sequence.start();
            }


        }
        //Wrong Answer
        else {
            //Add score window popout
            wrong_sound.start();
            gameOver();
        }


    }

    private void gameOver(){

        chronometer.stop();
        vibrate();
        game_music.stop();
        Intent intent1 = new Intent(this, PopupScore.class);
        intent1.putExtra("Time", SystemClock.elapsedRealtime() - chronometer.getBase());
        intent1.putExtra("Level", this.level_int);
        //intent1 = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent1);
        finish();
    }


   /* private void addSequenceText(){
        sequenceText.setText(sequence.toString());
    }
    */

    private void vibrate(){
        Vibrator vibrate = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrate.vibrate(1000);
    }

    private void addLevelText(){
        String level_string = getString(R.string.level);
        score_textview.setText(level_string + " " + (this.level_int));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent;
        backIntent = new Intent (getBaseContext(), MainActivity.class);
        startActivity(backIntent);
        finish();
    }
    /*
    @Override

    public void  onPause(){
        super.onPause();
        if (game_music != null && game_music.isPlaying()){
            game_music.pause();
            music_length = game_music.getCurrentPosition();
            game_music.stop();
        }

        if (correct_sound != null && correct_sound.isPlaying())
            correct_sound.stop();
        if (wrong_sound != null && wrong_sound.isPlaying())
            wrong_sound.stop();
    }
    @Override
    public void onResume(){
        super.onResume();
        if (game_music != null && !game_music.isPlaying()) {
            game_music.seekTo(music_length);
            game_music.start();
        }
    }
    public void onStop(){
        super.onStop();
        if (game_music != null && game_music.isPlaying()) {
            game_music.pause();
            music_length = game_music.getCurrentPosition();
            game_music.stop();
            }
        if (correct_sound != null && correct_sound.isPlaying())
            correct_sound.stop();
        if (wrong_sound != null && wrong_sound.isPlaying())
            wrong_sound.stop();

    }
    public void onRestart(){
        super.onRestart();
        if (game_music != null && !game_music.isPlaying()){
            game_music.seekTo(music_length);
            game_music.start();
        }

    }
    */

}

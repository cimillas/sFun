package com.hpi.labordacimas.simplefun2.gamepack.game;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.Button;

/**
 * Created by jorge on 31/10/15.
 */
public class ColorButton extends Button {
    /**
     * Identification by number (0 to 8).
     */
    int identification;
    /**
     * Sound the button does when clicked.
     */
    public MediaPlayer sound;
    /**
     * Animation of the button.
     */
    private Animation buttonanim;


    public ColorButton(Context context) {
        super(context);
    }

    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIdentification(int identification){
        this.identification = identification;
    }
    public int getIdentification(){
        return identification;
    }

    public void setSound(MediaPlayer sound){
        this.sound = sound;
    }

    public MediaPlayer getSound (){
        return this.sound;
    }

    public Animation getButtonanim() {
        return buttonanim;
    }

    public void setButtonanim(Animation buttonanim) {
        this.buttonanim = buttonanim;
    }

    public void sAnimation(){
        super.setAnimation(buttonanim);
        super.startAnimation(buttonanim);
    }
    //BOOOOOOOKKKKKMAAAAARKKKK!!!!!
    public static void buttonMusicStart(ColorButton b){
        //b.getSound().start();
        final MediaPlayer ss=b.getSound();
        ss.start();

        CountDownTimer counter = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                ss.pause();
                ss.seekTo(0);
            }
        }.start();

    }

    public static void pauseButtonSounds(ColorButton [] b){
        for(int i = 0; i<b.length; i++){
            if(b[i].getSound().isPlaying()){
                b[i].getSound().pause();
                b[i].getSound().seekTo(0);
            }
        }
    }

}

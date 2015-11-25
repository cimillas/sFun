package com.hpi.labordacimas.simplefun2.gamepack.game;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by jorge on 23/10/15.
 */
public class Sequence implements Animation.AnimationListener{


    /**
     * Sequence of the chosen buttons.
     */
    private ArrayList<ColorButton> sequence = new ArrayList <> ();
    /**
     * Index used when checking the users answer. The index acceses the button in the sequence.
     */
    private int index_game = 0; //Index used for game purposes.
    /**
     * Index used while animating the sequence of buttons.
     */
    private int indexA = 0;    //Index used to animate the buttons.
    private Random random = new Random();
    /**
     * Complete number of buttons in the game.
     * Buttons in the game screen, we will use them in the sequence.
     */
    private ColorButton [] buttons;

    private Handler handler = new Handler();
    private CountDownTimer counter;

    private ColorButton aux_button;


    /**
     * Sequence creates a sequence of buttons, afterwards the user will have to guess the sequence.
     * @param buttons game buttons
     */
    public Sequence(ColorButton[] buttons){
        int aux = (random.nextInt(9));
        this.buttons = buttons;
        //Adding the first element in the sequence.
        sequence.add(buttons[aux]);
    }

    /**
     * Adds one button into the sequence.
     */
    public void increaseSequence(){
        int aux = (random.nextInt(9));
        sequence.add(buttons[aux]);
    }

    /**
     * Adds one to the index of the sequence array-list.
     * Next time the user checks the answer, it will point to the next button
     * in the sequence.
     */
    public void increaseIndex(){
        index_game++;
    }

    /**
     * Returns the game_index.
     * @return game index.
     */
    public int getIndex_game(){
        return index_game;
    }

    /**
     * Resets the gameIndex. Now the level has been completed
     * and the sequence starts over again.
     */
    public void resetIndexGame(){
        index_game = 0;
    }

    /**
     * Returns the number of elements in the sequence.
     * @return
     */
    public int getSize(){
        return sequence.size();
    }

    /**
     * Returns the whole sequence
     * @param <Button>
     * @return The current sequence.
     */
    public <Button> ArrayList getSequence(){
        return sequence;
    }

    /**
     * Checks if the button the user clicked is correct or not.
     * @param x Button the user has pressed.
     * @return True if the user is correct, False otherwise.
     */
    public boolean checkAnswer(Button x){
        return sequence.get(index_game).equals(x);
    }



    //Recursive startAnimation DOES NOT WORK//
    /*
    public void startAnimation(ArrayList<ColorButton> b){
        if(b == null)
            return;
        else{
            //ArrayList<ColorButton> list = (ArrayList<ColorButton>)b.clone();
            aux_button = null;
            //Get the first element of the arraylist
            aux_button = sequence.get(0);
            //Reproducing the animation
            bAnimation();
            if(b.size() >1)
                b.remove(0);
            startAnimation(b);
        }
    }
    */
    /**
     * Starts the sequence animation. This animation is reproduced when the game begins
     * and when the level has been completed.
     */

    public void start() {
        //Now with sounds included
        //Iterator<ColorButton> it = sequence.iterator();
        //while(it.hasNext()){
        //    aux_button = it.next();

                /////THIS KIND OF WORKS!!!
        //ColorButton.pauseButtonSounds(this.buttons);
        for(int i = 0; i< sequence.size(); i++){
            aux_button = sequence.get(i);
            //ColorButton.buttonMusicStart(aux_button);
            //aux_button.setAnimation(Game.animButton);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    aux_button.startAnimation(Game.animButton);
                }
            }, 1000);

        }


    }
    /*
    //Trial of a new start... Not wotking
    public void start2(){
        indexA = 0;
        aux_button = sequence.get(indexA);
        Animation anim = Game.animButton;
        indexA++;
        aux_button.startAnimation(anim);
        aux_button.clearAnimation();
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(indexA < sequence.size()){
                    aux_button = sequence.get(indexA);
                    indexA ++;
                    aux_button.startAnimation(animation);
                    //aux_button.clearAnimation();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
*/
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }



    /**
     * Sequence converted to a string
     * @return sequence in a string.
     */
    @Override
    public String toString(){
        String aux = "";
        int aux2;
        Iterator<ColorButton> it = sequence.iterator();
        while(it.hasNext()){


            aux = aux + getButtonText(it.next());
        }
        return aux;
    }

    public boolean levelCompleted(){
        return (sequence.size()) <= index_game;
    }

    private boolean animationCompleted(){
        return (sequence.size()) == indexA;
    }



    ///////////////Animation Listener methods///////////////////////



    private String getButtonText(ColorButton b){
        String res ="";
        if(b.equals(buttons[0]))
            res = "1";
        if(b.equals(buttons[1]))
            res = "2";
        if(b.equals(buttons[2]))
            res = "3";
        if(b.equals(buttons[3]))
            res = "4";
        if(b.equals(buttons[4]))
            res = "5";
        if(b.equals(buttons[5]))
            res = "6";
        if(b.equals(buttons[6]))
            res = "7";
        if(b.equals(buttons[7]))
            res = "8";
        if(b.equals(buttons[8]))
            res = "9";

        return res;
    }

}


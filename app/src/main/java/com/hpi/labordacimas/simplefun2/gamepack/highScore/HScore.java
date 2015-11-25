package com.hpi.labordacimas.simplefun2.gamepack.highScore;

/**
 * Created by Jorge on 19/11/2015.
 */
public class HScore {

    private int level;
    private String name;

    public HScore(int level, String name){
        this.level = level;
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String toString(){
        return level + "\t" + name;
    }
}

package com.hpi.labordacimas.simplefun2.changeTheme;

import android.app.Activity;
import android.util.Log;

import com.hpi.labordacimas.simplefun2.R;

/**
 * Created by Jorge on 08/11/2015.
 */
public class Utils {
    public static String SIZE="DEFAULT";
    public static boolean settingChanged=false;
    public static boolean isloggedIn=false;
    public static String THEME="";
    public static String COLORBLINDNESS="NORMAL";

    public static final String NORMAL = "NORMAL";
    public static final String PROTANOPIA = "PROTANOPIA";
    public static final String DEUTERANOPIA = "DEUTERANOPIA";
    public static final String TRITANOPIA = "TRITANOPIA";
    public static boolean isFirstConnection = true;

    public static void setThemeToActivity(Activity act )
    {

        try {

            if(Utils.COLORBLINDNESS.equalsIgnoreCase(NORMAL)) {
                if (Utils.SIZE.equalsIgnoreCase("LARGE")) {
                    act.setTheme(R.style.Theme_LargeText_NColor);
                    Log.d(" ", "Theme Large test Size and Normal Color is to be is applied.");
                }
                if (Utils.SIZE.equalsIgnoreCase("SMALL")) {
                    act.setTheme(R.style.Theme_SmallText_NColor);
                    Log.d(" ", "Theme Small text Size and Normal Color is to be is applied.");
                }

                if (Utils.SIZE.equalsIgnoreCase("DEFAULT")) {
                    act.setTheme(R.style.Theme_DefaultText_NColor);
                    Log.d("", "theme default text size and Normal Color is applied.");
                }
            }

            if(Utils.COLORBLINDNESS.equalsIgnoreCase(PROTANOPIA)) {
                if (Utils.SIZE.equalsIgnoreCase("LARGE")) {
                    act.setTheme(R.style.Theme_LargeText_PColor);
                    Log.d(" ", "Theme Large test Size and Normal Color is to be is applied.");
                }
                if (Utils.SIZE.equalsIgnoreCase("SMALL")) {
                    act.setTheme(R.style.Theme_SmallText_PColor);
                    Log.d(" ", "Theme Small text Size and Normal Color is to be is applied.");
                }

                if (Utils.SIZE.equalsIgnoreCase("DEFAULT")) {
                    act.setTheme(R.style.Theme_DefaultText_PColor);
                    Log.d("", "theme default text size and Normal Color is applied.");
                }
            }
            if(Utils.COLORBLINDNESS.equalsIgnoreCase(DEUTERANOPIA)) {
                if (Utils.SIZE.equalsIgnoreCase("LARGE")) {
                    act.setTheme(R.style.Theme_LargeText_DColor);
                    Log.d(" ", "Theme Large test Size and Normal Color is to be is applied.");
                }
                if (Utils.SIZE.equalsIgnoreCase("SMALL")) {
                    act.setTheme(R.style.Theme_SmallText_DColor);
                    Log.d(" ", "Theme Small text Size and Normal Color is to be is applied.");
                }

                if (Utils.SIZE.equalsIgnoreCase("DEFAULT")) {
                    act.setTheme(R.style.Theme_DefaultText_DColor);
                    Log.d("", "theme default text size and Normal Color is applied.");
                }
            }
            if(Utils.COLORBLINDNESS.equalsIgnoreCase(TRITANOPIA)) {
                if (Utils.SIZE.equalsIgnoreCase("LARGE")) {
                    act.setTheme(R.style.Theme_LargeText_TColor);
                    Log.d(" ", "Theme Large test Size and Normal Color is to be is applied.");
                }
                if (Utils.SIZE.equalsIgnoreCase("SMALL")) {
                    act.setTheme(R.style.Theme_SmallText_TColor);
                    Log.d(" ", "Theme Small text Size and Normal Color is to be is applied.");
                }

                if (Utils.SIZE.equalsIgnoreCase("DEFAULT")) {
                    act.setTheme(R.style.Theme_DefaultText_TColor);
                    Log.d("", "theme default text size and Normal Color is applied.");
                }
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}

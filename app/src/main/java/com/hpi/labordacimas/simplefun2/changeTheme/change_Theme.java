package com.hpi.labordacimas.simplefun2.changeTheme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hpi.labordacimas.simplefun2.mainpack.MainActivity;
import com.hpi.labordacimas.simplefun2.R;
import com.hpi.labordacimas.simplefun2.mainpack.OptionsActivity;


public class change_Theme extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setThemeToActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_theme);


        startActivity(new Intent(this, OptionsActivity.class));
        finish();
    }
}

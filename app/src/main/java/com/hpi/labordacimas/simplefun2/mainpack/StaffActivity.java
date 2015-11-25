package com.hpi.labordacimas.simplefun2.mainpack;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.hpi.labordacimas.simplefun2.R;
import com.hpi.labordacimas.simplefun2.changeTheme.Utils;
import com.hpi.labordacimas.simplefun2.gamepack.game.Game;

/**
 * Created by cimas on 23/11/2015.
 */
public class StaffActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Utils.setThemeToActivity(this);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_staff);
        //if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
          //  setContentView(R.layout.activity_staff_land);
       // }
        //else {
          //  setContentView(R.layout.activity_staff);
        //}

        ImageButton backButton = (ImageButton) findViewById(R.id.buttonBack);
        backButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonBack){
            Intent intent1;
            intent1 = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent1);
            finish();
        }

    }
}
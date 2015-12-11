package com.hpi.labordacimas.simplefun2.mainpack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hpi.labordacimas.simplefun2.R;
import com.hpi.labordacimas.simplefun2.changeTheme.Utils;

public class IdentificationActivity extends Activity implements View.OnClickListener{

    private EditText username_editText, password_editText;
    private Button  logInButton;
    private ImageButton closeButton;
    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setThemeToActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);


        username_editText = (EditText) findViewById(R.id.username_editText);
        password_editText = (EditText) findViewById(R.id.password_editText);
        closeButton = (ImageButton) findViewById(R.id.closeButton);
        logInButton = (Button) findViewById(R.id.logInButton);

        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());

        logInButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.closeButton):
                finish();
                break;

            case (R.id.logInButton):
                Utils.isloggedIn = true;
                SharedPreferences pref = this.getSharedPreferences("LogInData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("Username",username_editText.getText().toString());
                editor.putString("Password",password_editText.getText().toString());
                editor.apply();
                finish();
                break;
        }
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe left' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

         /*
         Toast.makeText(getBaseContext(),
          event1.toString() + "\n\n" +event2.toString(),
          Toast.LENGTH_SHORT).show();
         */

            if(event2.getY() < event1.getY()){
                Intent intent2;
                intent2 = new Intent (getBaseContext(), MainActivity.class);
                startActivity(intent2);
                finish();
            }

            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

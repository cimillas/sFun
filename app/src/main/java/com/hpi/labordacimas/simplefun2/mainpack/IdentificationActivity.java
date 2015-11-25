package com.hpi.labordacimas.simplefun2.mainpack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setThemeToActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);


        username_editText = (EditText) findViewById(R.id.username_editText);
        password_editText = (EditText) findViewById(R.id.password_editText);
        closeButton = (ImageButton) findViewById(R.id.closeButton);
        logInButton = (Button) findViewById(R.id.logInButton);

        logInButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

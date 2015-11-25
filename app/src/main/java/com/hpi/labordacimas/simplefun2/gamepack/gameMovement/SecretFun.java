package com.hpi.labordacimas.simplefun2.gamepack.gameMovement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hpi.labordacimas.simplefun2.R;
import com.hpi.labordacimas.simplefun2.mainpack.MainActivity;

public class SecretFun extends Activity implements View.OnClickListener{

    Button play_secret_button;

    //Accelerometer sensors
    private SensorManager mSensorManager;
    private Sensor accSensor;

    private long lastUpdate = 0;
    private float last_accX, last_accY, last_accZ;
    private static final int SHAKE_THRESHOLD = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_fun);

        play_secret_button = (Button) findViewById(R.id.play_secret_button);
        play_secret_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.play_secret_button){
            Intent intent;
            intent = new Intent(getBaseContext(), MovementGameActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent;
        intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }


}

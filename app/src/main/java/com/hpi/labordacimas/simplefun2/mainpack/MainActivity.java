package com.hpi.labordacimas.simplefun2.mainpack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.hpi.labordacimas.simplefun2.R;
import com.hpi.labordacimas.simplefun2.changeTheme.Utils;
import com.hpi.labordacimas.simplefun2.gamepack.game.Game;
import com.hpi.labordacimas.simplefun2.gamepack.gameMovement.SecretFun;
import com.hpi.labordacimas.simplefun2.qrpack.QrSelectionActivity;

public class MainActivity extends Activity implements View.OnClickListener , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, SensorEventListener{

    //Views
    private ImageButton play_button, staffbutton;
    private ImageButton option_button, qrbutton,logInButton;
    private Location userLocation;
    private TextView locationText;

    private GestureDetectorCompat gestureDetectorCompat;

    //Accelerometer sensors
    private SensorManager mSensorManager;
    private Sensor accSensor;

    private long lastUpdate = 0;
    private float last_accX, last_accY, last_accZ;
    private static final int SHAKE_THRESHOLD = 4;
    int counter;

    //Location
    public GoogleApiClient mGoogleApiClient;

  //  public MediaPlayer intro_music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setThemeToActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_button = (ImageButton) findViewById(R.id.play_button);
        option_button = (ImageButton) findViewById(R.id.option_imagebutton);
        locationText = (TextView) findViewById(R.id.locationTextView);
        qrbutton = (ImageButton) findViewById(R.id.qr_imageButton);
        logInButton = (ImageButton) findViewById(R.id.logIn_main_imageButton);
        staffbutton = (ImageButton) findViewById(R.id.about_button);
        //Music Starts
    //    intro_music = MediaPlayer.create(this, R.raw.gamemusic);
        //intro_music.setLooping(true);
        //intro_music.start();

        //Listeners added
        play_button.setOnClickListener(this);
        option_button.setOnClickListener(this);
        qrbutton.setOnClickListener(this);
        logInButton.setOnClickListener(this);
        staffbutton.setOnClickListener(this);

      /*  OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                Intent intent2;
                intent2 = new Intent (getBaseContext(), OptionsActivity.class);
                startActivity(intent2);
                finish();
            }
            @Override
            public void onSwipeRight(){
                Intent intent1;
                intent1 = new Intent(getBaseContext(), Game.class);
                startActivity(intent1);
                finish();
            }

        }; */

        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());

        //Getting location of the user
        buildGoogleApiClient();
        mGoogleApiClient.connect();

        //Initializing accelerometer
        //Initializing the gyroscope
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, accSensor , SensorManager.SENSOR_DELAY_NORMAL);

        //Initiating log in activity
        if(Utils.isFirstConnection) {
            Utils.isFirstConnection = false;
            Intent login_intent;
            login_intent = new Intent(getBaseContext(), IdentificationActivity.class);
            startActivity(login_intent);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_button:
               // intro_music.stop();
                Intent intent1;
                intent1 = new Intent(getBaseContext(), Game.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.option_imagebutton:
                Intent intent2;
                intent2 = new Intent (getBaseContext(), OptionsActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.qr_imageButton:
                Intent intent3;
                intent3 = new Intent (getBaseContext(), QrSelectionActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.logIn_main_imageButton:
                Intent intent4;
                intent4 = new Intent (getBaseContext(), IdentificationActivity.class);
                startActivity(intent4);
                break;
            case R.id.about_button:
                Intent intent5;
                intent5 = new Intent(getBaseContext(), StaffActivity.class);
                startActivity(intent5);
                finish();
                break;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    public void onConnected(Bundle bundle) {
        userLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (userLocation != null) {
            locationText.setText(String.valueOf(userLocation.getLatitude()) +"\n"
                    + String.valueOf(userLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void onSensorChanged(SensorEvent event) {

        Sensor mySensor = event.sensor;
        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //We obtaing the acceleration of each axis.
            float accX = event.values[0];   //Ax -Gx
            float accY = event.values[1];   //Ay -Gy
            float accZ = event.values[2];   //Az -Gz

            //Since we are sampling too much, we will sample in intervals. We create a manual timer with System.currentTimeMillis()
            long now = System.currentTimeMillis();
            //Interval of 0.1s
            if((now - lastUpdate) > 100){
                long deltaTime = (now - lastUpdate); //We obtaing the dT for this interval.
                lastUpdate = now;   //We change the last update time.

                // |delta(V)| = delta(A) * deltaTime (in seconds). We should obtain m/s.
                float speed = Math.abs (accX + accY + accZ - last_accX - last_accY - last_accZ) * deltaTime /1000;

                Log.d("Speed = " ,String.valueOf(speed));

                //SECRET FUN
                if(speed > SHAKE_THRESHOLD){
                    counter++;
                    Log.d("Ohh yeah", "Shake that booty");
                    //Initializing Secret Fun
                    if(counter>2) {
                        initializeSecret();
                        counter = 0;
                    }
                }
                last_accX = accX;
                last_accY = accY;
                last_accZ = accZ;
            }



        }

    }

    private void initializeSecret(){
       /* AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        builder.setTitle(R.string.secret_dialog_title);
        builder.setMessage(R.string.secret_dialog_message);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //User clicked ok button
                Intent intent1;
                intent1 = new Intent(getBaseContext(), SecretFun.class);
                startActivity(intent1);
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.create();
        */
        Intent intent1;
        intent1 = new Intent(getBaseContext(), SecretFun.class);
        startActivity(intent1);
        finish();

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

            if(event2.getX() > event1.getX()){ //left swiping
                Intent intent2;
                intent2 = new Intent (getBaseContext(), OptionsActivity.class);
                startActivity(intent2);
                finish();
            }
            else {
                if (event2.getX() < event1.getX()) { //Right swiping
                    Intent intent1;
                    intent1 = new Intent(getBaseContext(), StaffActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
            return true;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    protected void onPause() {
        super.onPause();
        if(mSensorManager != null)
            mSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        if((mSensorManager != null) && (accSensor != null))
            mSensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

}




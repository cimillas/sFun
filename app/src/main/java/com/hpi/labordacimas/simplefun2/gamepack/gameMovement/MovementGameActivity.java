package com.hpi.labordacimas.simplefun2.gamepack.gameMovement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hpi.labordacimas.simplefun2.R;
import com.hpi.labordacimas.simplefun2.mainpack.MainActivity;

import java.util.Random;

public class MovementGameActivity extends Activity implements SensorEventListener{

    private final int UPARROW = R.drawable.ic_up_arrow;
    private final int RIGHTARROW = R.drawable.ic_right_arrow;
    private final int LEFTARROW = R.drawable.ic_left_arrow;
    private final int DOWNARROW = R.drawable.ic_down_arrow;

    private int correctAnswer;


    //Sensors
    private SensorManager mSensorManager;
    private Sensor accelerometerSensor;
    private Sensor magnetometer;

    float[] inclineGravity = new float[3];
    float[] mGravity;   //Values for the gravity acceleration registered by the accelerometer.
    float[] mGeomagnetic;  //Values for the magnetic field registered by the magnetometer.

    float orientation[] = new float[3]; //Orientation of the device.
    float pitch = 0;
    float roll = 0;
    float azimut = 0;
    int inclinationRoll;
    int inclinationPitch;
    int inclinationAzimut;

    long lastUpdate = 0;

    /*
    // Create a constant to convert nanoseconds to seconds.
    private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];
    private float currentRotVector[] =  { 1, 0, 0, 0 };
    private float timestamp;
    private float EPSILON = 100f;
*/

    private ImageView userArrow;
    private ImageButton arrowUp, arrowRight, arrowDown, arrowLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_game);

        userArrow = (ImageView) findViewById(R.id.userArrow_imageView);
        arrowUp = (ImageButton) findViewById(R.id.up_arrow_imagebutton);
        arrowRight = (ImageButton) findViewById(R.id.right_arror_imageButton);
        arrowDown = (ImageButton) findViewById(R.id.down_arrow_imageButton);
        arrowLeft = (ImageButton) findViewById(R.id.left_arrow_imageButton);

       /* arrowUp.setOnClickListener(this);
        arrowRight.setOnClickListener(this);
        arrowDown.setOnClickListener(this);
        arrowLeft.setOnClickListener(this);
        */

        //Initializing the accelerometer
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this, accelerometerSensor , SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, magnetometer , SensorManager.SENSOR_DELAY_NORMAL);

        //Initializing game
        setImage();
    }


    private void setImage(){
        Random rand = new Random();
        int aux = rand.nextInt(4);
        switch(aux){
            case 0:
                correctAnswer = UPARROW;
                userArrow.setImageResource(UPARROW);
                break;
            case 1:
                correctAnswer = RIGHTARROW;
                userArrow.setImageResource(RIGHTARROW);
                break;
            case 2:
                correctAnswer = DOWNARROW;
                userArrow.setImageResource(DOWNARROW);
                break;
            case 3:
                correctAnswer = LEFTARROW;
                userArrow.setImageResource(LEFTARROW);
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onSensorChanged(SensorEvent event){
        //If type is accelerometer only assign values to global property mGravity
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            mGravity = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic = event.values;
        }
            processInfo();

            if (isTiltDownward())
            {
                Log.d("test", "downwards");
                checkAnswer(DOWNARROW);
            }
            if (isTiltUpward())
            {
                Log.d("test", "upwards");
                checkAnswer(UPARROW);
            }
            if(isTiltRightward()){
                Log.d("test", "rightwards");
                checkAnswer(RIGHTARROW);
            }
            if(isTiltLeftward()){
                Log.d("test", "leftwards");
                checkAnswer(LEFTARROW);
            }

    }


    private void processInfo() {
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                pitch = orientation[1];
                roll = orientation[2];


                inclineGravity = mGravity.clone();

                double norm_Of_g = Math.sqrt(inclineGravity[0] * inclineGravity[0] + inclineGravity[1] * inclineGravity[1] + inclineGravity[2] * inclineGravity[2]);

                // Normalize the accelerometer vector
                inclineGravity[0] = (float) (inclineGravity[0] / norm_Of_g);
                inclineGravity[1] = (float) (inclineGravity[1] / norm_Of_g);
                inclineGravity[2] = (float) (inclineGravity[2] / norm_Of_g);

                //Checks if device is flat on groud or not
                inclinationPitch = (int) Math.abs(Math.round(Math.toDegrees(Math.asin(inclineGravity[1]))));
                inclinationRoll = (int) Math.abs(Math.round(Math.toDegrees(Math.asin(inclineGravity[0]))));//inclinationRoll = (int) Math.round(Math.toDegrees(Math.acos(inclineGravity[2])));   //Este esta bien
                //inclinationAzimut = (int) Math.round(Math.toDegrees(Math.acos(inclineGravity[0])));
                //int aux =(int) Math.round(Math.toDegrees(Math.acos(inclineGravity[2])));

                long now = System.currentTimeMillis();
                //Interval of 0.1s
                if ((now - lastUpdate) > 3000) {
                    Log.d("TiltRoll: ", String.valueOf(roll));
                    Log.d("TiltPitch: ", String.valueOf(pitch));
                    Log.d("InclinationPitch: ", String.valueOf(inclinationPitch));
                    Log.d("InclinationRoll1: ", String.valueOf(inclinationRoll));
                    //Log.d("InclinationRoll2: ", String.valueOf(aux));
                    //Log.d("InclinationAzimut: ", String.valueOf(inclinationAzimut));

                    lastUpdate = now;
                }

            }
        }
    }


    public boolean isTiltUpward()
    {

                if((inclinationRoll > 30) && (inclinationRoll <50) && (roll > 0) )
                    return true;
                else
                    return false;
    }
    public boolean isTiltDownward()
    {
                if ((inclinationRoll > 30) && (inclinationRoll <50) && (roll <= 0))
                    return true;
                else
                    return false;
    }
    public boolean isTiltRightward()
    {
        if ((inclinationPitch > 30) && (inclinationPitch <50) && (pitch <= 0))
            return true;
        else
            return false;
    }
    public boolean isTiltLeftward()
    {
        if ((inclinationPitch > 30) && (inclinationPitch <50) && (pitch > 0))
            return true;
        else
            return false;

    }


private void checkAnswer(int answer){
    if(answer == correctAnswer){
        setImage();
    }

}
    /*
    private void vibrate(){
        Vibrator vibrate = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrate.vibrate(1000);
    }
    */

    public void onDestroy() {
        mSensorManager.unregisterListener(this);
        super.onDestroy();
    }
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();

    }

    protected void onResume() {
        mSensorManager.registerListener(this, accelerometerSensor , SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, magnetometer , SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
        }

    public void onBackPressed() {
        mSensorManager.unregisterListener(this);
        Intent intent;
        intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
        finish();
    }

    /**
     * Calculates the product of two Quaternion vectors. This is used to know the rotation produced with the gyroscope
     * The product divided into its four components (w, x, y ,z) is:
     *
     * currentRotVector[0] = deltaRotationVector[0] * currentRotVector[0] -
     * deltaRotationVector[1] * currentRotVector[1] -
     * deltaRotationVector[2] * currentRotVector[2] -
     * deltaRotationVector[3] * currentRotVector[3];
     *
     * currentRotVector[1] = deltaRotationVector[0] * currentRotVector[1] +
     * deltaRotationVector[1] * currentRotVector[0] +
     * deltaRotationVector[2] * currentRotVector[3] -
     * deltaRotationVector[3] * currentRotVector[2];
     *
     * currentRotVector[2] = deltaRotationVector[0] * currentRotVector[2] -
     * deltaRotationVector[1] * currentRotVector[3] +
     * deltaRotationVector[2] * currentRotVector[0] +
     * deltaRotationVector[3] * currentRotVector[1];
     *
     * currentRotVector[3] = deltaRotationVector[0] * currentRotVector[3] +
     * deltaRotationVector[1] * currentRotVector[2] -
     * deltaRotationVector[2] * currentRotVector[1] +
     * deltaRotationVector[3] * currentRotVector[0];
     *
     * @param currentRotVector Last known state of the rotation vector.
     * @param deltaRotationVector New state produced in the rotation vector. This vector shows the difference between them
     * @return Actual Quaternion vector for the rotation.
     */
    private float [] calculateCurrentRotVector(float[] currentRotVector, float[] deltaRotationVector)throws NullPointerException{

        if((currentRotVector.length !=4) || (currentRotVector.length !=4)) {
            throw new NullPointerException();
        }

        currentRotVector[0] = deltaRotationVector[0] * currentRotVector[0] -
                deltaRotationVector[1] * currentRotVector[1] -
                deltaRotationVector[2] * currentRotVector[2] -
                deltaRotationVector[3] * currentRotVector[3];

        currentRotVector[1] = deltaRotationVector[0] * currentRotVector[1] +
                deltaRotationVector[1] * currentRotVector[0] +
                deltaRotationVector[2] * currentRotVector[3] -
                deltaRotationVector[3] * currentRotVector[2];

        currentRotVector[2] = deltaRotationVector[0] * currentRotVector[2] -
                deltaRotationVector[1] * currentRotVector[3] +
                deltaRotationVector[2] * currentRotVector[0] +
                deltaRotationVector[3] * currentRotVector[1];

        currentRotVector[3] = deltaRotationVector[0] * currentRotVector[3] +
                deltaRotationVector[1] * currentRotVector[2] -
                deltaRotationVector[2] * currentRotVector[1] +
                deltaRotationVector[3] * currentRotVector[0];


        return currentRotVector;
    }


    /*
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        if(mySensor.getType() == Sensor.TYPE_GYROSCOPE){
            // This timestep's delta rotation to be multiplied by the current rotation
            // after computing it from the gyro sample data.
            if (timestamp != 0) {
                final float dT = (event.timestamp - timestamp) * NS2S;
                // Axis of the rotation sample, not normalized yet.
                float axisX = event.values[0];
                float axisY = event.values[1];
                float axisZ = event.values[2];

                // Calculate the angular speed of the sample
                float omegaMagnitude =(float) Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

                // Normalize the rotation vector if it's big enough to get the axis
                // (that is, EPSILON should represent your maximum allowable margin of error)
                if (omegaMagnitude > EPSILON) {
                    axisX /= omegaMagnitude;
                    axisY /= omegaMagnitude;
                    axisZ /= omegaMagnitude;
                }

                // Integrate around this axis with the angular speed by the timestep
                // in order to get a delta rotation from this sample over the timestep
                // We will convert this axis-angle representation of the delta rotation
                // into a quaternion before turning it into the rotation matrix.
                float thetaOverTwo = omegaMagnitude * dT / 2.0f;
                float sinThetaOverTwo = (float) Math.sin(thetaOverTwo);
                float cosThetaOverTwo = (float) Math.cos(thetaOverTwo);
                deltaRotationVector[0] = cosThetaOverTwo;
                deltaRotationVector[1] = sinThetaOverTwo * axisY;
                deltaRotationVector[2] = sinThetaOverTwo * axisZ;
                deltaRotationVector[3] = sinThetaOverTwo * axisX;
            }
            timestamp = event.timestamp;

            currentRotVector[0] =
                    deltaRotationVector[0] * currentRotVector[0] -
                    deltaRotationVector[1] * currentRotVector[1] -
                    deltaRotationVector[2] * currentRotVector[2] -
                    deltaRotationVector[3] * currentRotVector[3];

            currentRotVector[1] =
                    deltaRotationVector[0] * currentRotVector[1] +
                    deltaRotationVector[1] * currentRotVector[0] +
                    deltaRotationVector[2] * currentRotVector[3] -
                    deltaRotationVector[3] * currentRotVector[2];

            currentRotVector[2] =
                    deltaRotationVector[0] * currentRotVector[2] -
                    deltaRotationVector[1] * currentRotVector[3] +
                    deltaRotationVector[2] * currentRotVector[0] +
                    deltaRotationVector[3] * currentRotVector[1];

            currentRotVector[3] =
                    deltaRotationVector[0] * currentRotVector[3] +
                    deltaRotationVector[1] * currentRotVector[2] -
                    deltaRotationVector[2] * currentRotVector[1] +
                    deltaRotationVector[3] * currentRotVector[0];
            /*try {
                currentRotVector = calculateCurrentRotVector(currentRotVector, deltaRotationVector);
            }catch(NullPointerException e){
                Log.d("NullPointerRotation", "Problem with the reading of the gyroscope");
            }


            final float rad2deg = (float) (180.0f / Math.PI);
           float RotAngle = currentRotVector[0] * rad2deg;
           float axisX = currentRotVector[1];
           float axisY = currentRotVector[2];
           float axisZ = currentRotVector[3];

            Log.i("Sensor Gyroscope: ", "axisX: " + axisX + //
                    " axisY: " + axisY + //
                    " axisZ: " + axisZ + //
                    " RotAngle: " + RotAngle);


        }
           // float[] deltaRotationMatrix = new float[9];
            //SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
            // User code should concatenate the delta rotation we computed with the current rotation
            // in order to get the updated rotation.
            // rotationCurrent = rotationCurrent * deltaRotationMatrix;



    }
*/
}

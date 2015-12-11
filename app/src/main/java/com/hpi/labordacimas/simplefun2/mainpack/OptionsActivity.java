package com.hpi.labordacimas.simplefun2.mainpack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.hpi.labordacimas.simplefun2.R;
import com.hpi.labordacimas.simplefun2.changeTheme.Utils;
import com.hpi.labordacimas.simplefun2.qrpack.QrSelectionActivity;

public class OptionsActivity extends Activity implements View.OnClickListener , AdapterView.OnItemSelectedListener{

    private CheckBox mute ;
    private Spinner daltonic_mode ;
    private CheckBox vibration ;
    //private Spinner language;
    private RadioButton font_size_small, font_size_medium, font_size_large;
    private SeekBar sfx;
    private SeekBar music;
    private ImageButton go_back,save_button;
    private GestureDetectorCompat gestureDetectorCompat;

    private RadioGroup font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Utils.setThemeToActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        mute = (CheckBox) findViewById(R.id.mute_checkBox);
        daltonic_mode = (Spinner) findViewById(R.id.spinner_color_blindness);
        vibration = (CheckBox) findViewById(R.id.vibration_checkBox);
        //language = (Spinner) findViewById(R.id.language_spinner);
        //sfx = (SeekBar) findViewById(R.id.sfx_seekbar);
        //music = (SeekBar) findViewById(R.id.music_seekbar);
        go_back = (ImageButton) findViewById(R.id.return_button);
        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());



        ////////////////////////////////COLOR BLINDNESS///////////////////////////////
        String [] colorarraySpinner = {getString(R.string.normal_colors),getString(R.string.protanopia),getString(R.string.deuteranopia),getString(R.string.tritanopia)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, colorarraySpinner);
        daltonic_mode.setAdapter(adapter);


        ////////////////////////////////Language Selection////////////////////////////

        /*String [] languagearraySpinner = {"English", "Español"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, languagearraySpinner);
        language.setAdapter(adapter);
        /*
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lang = (String) parent.getItemAtPosition(position);
                switch(lang){
                    case ("Español"):
                        Locale localeES = new Locale("es_ES");
                        Locale.setDefault(localeES);
                        Configuration configES = new Configuration();
                        configES.locale = localeES;
                        view.getContext().getApplicationContext().getResources().updateConfiguration(configES, null);

                        break;
                    case ("English"):
                        Locale localeEN= new Locale("en_EN");
                        Locale.setDefault(localeEN);
                        Configuration configEN = new Configuration();
                        configEN.locale = localeEN;
                        view.getContext().getApplicationContext().getResources().updateConfiguration(configEN, null);
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */

        //////////////////////////FontSizes////////////////////////////////////////////
        font = (RadioGroup) findViewById(R.id.radioGroup_font);
        font_size_small = (RadioButton) findViewById(R.id.radioButton_smallfont);
        font_size_medium = (RadioButton) findViewById(R.id.radioButton_mediumfont);
        font_size_large = (RadioButton) findViewById(R.id.radioButton_largefont);
        save_button = (ImageButton) findViewById(R.id.button_change_font);

        //Adding listeners to go_back button and change font
        go_back.setOnClickListener(this);
        save_button.setOnClickListener(this);

        //Changing checked radiobutton.
        if(Utils.settingChanged){
            font_size_large.setChecked(false);
            font_size_medium.setChecked(false);
            font_size_small.setChecked(false);

            switch (Utils.SIZE){
                case("LARGE"):
                    font_size_large.setChecked(true);
                    break;
                case ("SMALL"):
                    font_size_small.setChecked(true);
                    break;
                case ("DEFAULT"):
                    font_size_medium.setChecked(true);
                    break;
            }
            Utils.settingChanged = false;
        }

    }


    private void changeTheme(){
        changeColorTheme();
        switch (font.getCheckedRadioButtonId()){
            case (R.id.radioButton_largefont):
                Utils.SIZE="LARGE";
                Utils.settingChanged=true;
                startActivity(new Intent(OptionsActivity.this, com.hpi.labordacimas.simplefun2.changeTheme.change_Theme.class));
                break;
            case (R.id.radioButton_mediumfont):
                Utils.SIZE="DEFAULT";
                Utils.settingChanged=true;
                startActivity(new Intent(OptionsActivity.this, com.hpi.labordacimas.simplefun2.changeTheme.change_Theme.class));
                break;
            case (R.id.radioButton_smallfont):
                Utils.SIZE="SMALL";
                Utils.settingChanged=true;
                startActivity(new Intent(OptionsActivity.this, com.hpi.labordacimas.simplefun2.changeTheme.change_Theme.class));
                break;
            default:
                return;

        }
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    private void changeColorTheme(){
        String mode =  daltonic_mode.getSelectedItem().toString();
        if(mode.equalsIgnoreCase(getString(R.string.normal_colors))){
            Utils.COLORBLINDNESS = Utils.NORMAL;
        }
        if(mode.equalsIgnoreCase(getString(R.string.deuteranopia))){
            Utils.COLORBLINDNESS = Utils.DEUTERANOPIA;
        }
        if(mode.equalsIgnoreCase(getString(R.string.protanopia))){
            Utils.COLORBLINDNESS = Utils.PROTANOPIA;
        }
        if(mode.equalsIgnoreCase(getString(R.string.tritanopia))){
            Utils.COLORBLINDNESS = Utils.TRITANOPIA;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case (R.id.return_button):
                Intent intent1;
                intent1 = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent1);
                finish();
                break;
            case (R.id.button_change_font):
                Toast t = Toast.makeText(getBaseContext(), getString(R.string.changing_theme_toast), Toast.LENGTH_SHORT);
                t.show();
                changeTheme();
                break;
            default:
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

            if(event2.getX() < event1.getX()){
                Intent intent2;
                intent2 = new Intent (getBaseContext(), MainActivity.class);
                startActivity(intent2);
                finish();
            }

            return true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast t = Toast.makeText(getBaseContext(), getString(R.string.changing_color_toast), Toast.LENGTH_SHORT);
        t.show();

        finish();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent;
        backIntent = new Intent (getBaseContext(), MainActivity.class);
        startActivity(backIntent);
        finish();
    }

}

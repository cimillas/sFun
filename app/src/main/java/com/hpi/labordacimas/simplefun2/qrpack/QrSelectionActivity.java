package com.hpi.labordacimas.simplefun2.qrpack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hpi.labordacimas.simplefun2.R;
import com.hpi.labordacimas.simplefun2.changeTheme.Utils;
import com.hpi.labordacimas.simplefun2.mainpack.IdentificationActivity;

public class QrSelectionActivity extends Activity implements View.OnClickListener{

    Button qrReadButton, qrGenerateButton;
    ImageButton QRlogInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setThemeToActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_selection);

        qrGenerateButton = (Button) findViewById(R.id.button_generateqr);
        qrReadButton = (Button) findViewById(R.id.button_read_qrcode);
        try {
            QRlogInButton = (ImageButton) findViewById(R.id.qr_log_imageButton);
            Log.d("Checking..." ,QRlogInButton.toString());
            qrGenerateButton.setOnClickListener(this);
            qrReadButton.setOnClickListener(this);
            QRlogInButton.setOnClickListener(this);

        }catch (NullPointerException e){
            if(QRlogInButton == null)
                Log.d("LogInButton Null" ,"Esta vacio...");
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_generateqr:

                if(!Utils.isloggedIn) {
                    Toast t = Toast.makeText(getBaseContext(), getString(R.string.need_to_log_in_string), Toast.LENGTH_SHORT);
                    t.show();
                }
                else {
                    Intent intent1;
                    intent1 = new Intent(getBaseContext(), QrGeneratorActivity.class);
                    startActivity(intent1);
                    finish();
                }
                break;
            case R.id.button_read_qrcode:
                Intent intent2;
                intent2 = new Intent (getBaseContext(), QrReaderActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.qr_log_imageButton:
                Intent intent3;
                intent3 = new Intent (getBaseContext(), IdentificationActivity.class);
                startActivity(intent3);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent;
        backIntent = new Intent (getBaseContext(), com.hpi.labordacimas.simplefun2.mainpack.MainActivity.class);
        startActivity(backIntent);
        finish();
    }
    @Override
    public String toString(){
        if(this == null)
            return "null";
        else
            return "Something is in here";
    }

}
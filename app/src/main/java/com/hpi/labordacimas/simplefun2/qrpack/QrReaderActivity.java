package com.hpi.labordacimas.simplefun2.qrpack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.*;
import com.hpi.labordacimas.simplefun2.R;
import com.hpi.labordacimas.simplefun2.changeTheme.Utils;

public class QrReaderActivity extends Activity implements View.OnClickListener{

    private TextView formatTxt, contentTxt;
    private Button scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setThemeToActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_reader);

        scanButton = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);

        scanButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.scan_button:
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);

        if(scanningResult != null){
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent;
        backIntent = new Intent (getBaseContext(), QrSelectionActivity.class);
        startActivity(backIntent);
        finish();
    }

}

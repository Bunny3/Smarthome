package com.example.buste.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class Light extends AppCompatActivity {
    Switch sw1,sw2,sw3;
    ImageButton ib1,ib2,ib3;
    Button b1;
    ProgressBar progressBar;
    WebView wv1;
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        sw1 = (Switch) findViewById(R.id.switch1);
        b1 = (Button) findViewById(R.id.button);
        sw2 = (Switch) findViewById(R.id.switch2);
        sw3 = (Switch) findViewById(R.id.switch3);
        ib1 = (ImageButton) findViewById(R.id.imageButton);
        ib2 = (ImageButton) findViewById(R.id.imageButton2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ib3 = (ImageButton) findViewById(R.id.imageButton3);
        //ib3.setOnClickListener(this);

        wv1 = (WebView) findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());
        final String define = "https://us01.proxy.teleduino.org/api/1.0/328.php?k=83B969F2ACA9F58DC25EDCE5AFC766FD&r=definePinMode&pin=7&mode=1";
        final String toggle = "https://us01.proxy.teleduino.org/api/1.0/328.php?k=83B969F2ACA9F58DC25EDCE5AFC766FD&r=setDigitalOutput&pin=7&output=2&expire_time=0&save=1";
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadURL(define);
            }
        });
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(Light.this, "ON Baby", Toast.LENGTH_SHORT).show();
                    loadURL(toggle);
                } else {
                    String url = "https://us01.proxy.teleduino.org/api/1.0/328.php?k=83B969F2ACA9F58DC25EDCE5AFC766FD&r=setDigitalOutput&pin=7&output=2&expire_time=0&save=1";
                    loadURL(toggle);
                }
            }
        });
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) {
                    Toast.makeText(Light.this, "ON Baby", Toast.LENGTH_SHORT).show();
                    loadURL(toggle);
                } else {
                    Toast.makeText(Light.this, "OFF Baby", Toast.LENGTH_SHORT).show();
                    loadURL(toggle);
                }
            }
        });
        sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) {
                    Toast.makeText(Light.this, "ON Baby", Toast.LENGTH_SHORT).show();
                    loadURL(toggle);
                } else {
                    Toast.makeText(Light.this, "OFF Baby", Toast.LENGTH_SHORT).show();
                    loadURL(toggle);
                }
            }
        });
    }
        public void loadURL(String url)
        {
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(url);
        }
    public void informationMenu() {
        startActivity(new Intent("android.intent.action.INFOSCREEN"));
    }
    public void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }
    public void onClickIB(View v) {
        // TODO Auto-generated method stub
        startVoiceRecognitionActivity();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String on1 = "https://us01.proxy.teleduino.org/api/1.0/328.php?k=83B969F2ACA9F58DC25EDCE5AFC766FD&r=setDigitalOutput&pin=7&output=1&expire_time=0&save=1";
        final String off1 = "https://us01.proxy.teleduino.org/api/1.0/328.php?k=83B969F2ACA9F58DC25EDCE5AFC766FD&r=setDigitalOutput&pin=7&output=0&expire_time=0&save=1";


        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
           // mList.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, matches));
            Log.i("MainActivity", matches.get(0)+"");
            String match1 = matches.get(0)+"";
            if (match1.equals("onn")||match1.equals("on"))
            {
                Toast.makeText(Light.this, "ON Baby", Toast.LENGTH_SHORT).show();
                loadURL(on1);
            }
            else if(match1.equals("of")||match1.equals("off"))
            {
                Toast.makeText(Light.this, "OFF Baby", Toast.LENGTH_SHORT).show();
                loadURL(off1);
            }
            if (matches.contains("information")) {
                informationMenu();
            }
        }
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}

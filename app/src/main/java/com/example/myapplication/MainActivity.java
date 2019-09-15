package com.example.myapplication;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, View.OnClickListener {
    TextToSpeech tts;
    EditText edit;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = findViewById(R.id.edit);
        text = findViewById(R.id.text);

        tts = new TextToSpeech(this, this);

        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                text.setText("지원하지 않는 언어입니다.");
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            } else {
                text.setText("초기화 성공");
            }
        } else {
            text.setText("초기화 실패");
        }
    }

    @Override
    public void onClick(View v) {
        String utteranceId = this.hashCode() + "";
        int status = tts.speak(edit.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, utteranceId);
        if (status == TextToSpeech.SUCCESS) {
            text.setText("성공");
        } else {
            text.setText("실패");
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        super.onDestroy();
    }
}

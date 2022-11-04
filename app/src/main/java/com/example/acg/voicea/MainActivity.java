package com.example.acg.voicea;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioRecord;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    
    private SpeechRecognizer speechRecognizer;
    private Intent intentRecognizer;
    private TextView textView, textView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);
        textView = findViewById(R.id.myText);
        textView1 = findViewById(R.id.myText1);


        intentRecognizer = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);


        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }
            @Override
            public void onBeginningOfSpeech() {

            }
            @Override
            public void onRmsChanged(float rmsdB) {

            }
            @Override
            public void onBufferReceived(byte[] buffer) {

            }
            @Override
            public void onEndOfSpeech() {

            }
            @Override
            public void onError(int error) {

            }
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String myRecording = "";
                if(matches!=null){
                    myRecording = matches.get(0);
                    textView.setText(myRecording.toLowerCase());
                    if(myRecording.toLowerCase().equals("google")){
                        Uri webpage = Uri.parse("https://www.google.com");
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                        startActivity(webIntent);
                    }
                    if(myRecording.toLowerCase().equals("youtube")){
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com")));
                    }
                    if(myRecording.toLowerCase().equals("gmail")){
                        Intent mailClient = new Intent(Intent.ACTION_VIEW);
                        mailClient.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivityGmail");
                        mailClient.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mailClient);
                    }
                    if(myRecording.toLowerCase().equals("spotify")){
                            Intent spotify = new Intent(Intent.ACTION_MAIN);
                            spotify.setComponent(new ComponentName("com.spotify.music", "com.spotify.music.MainActivity"));
                            spotify.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(spotify);
                    }
                }
            }
            @Override
            public void onPartialResults(Bundle partialResults) {

            }
            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
        
    }

    public void StartButton(View view){
        speechRecognizer.startListening(intentRecognizer);
    }
    public void StopButton(View view){
        speechRecognizer.stopListening();
    }
}
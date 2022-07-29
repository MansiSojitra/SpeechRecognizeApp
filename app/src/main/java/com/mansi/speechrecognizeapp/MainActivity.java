package com.mansi.speechrecognizeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    EditText et;
    TextView tv;
    Button bt1,bt2;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et =findViewById(R.id.et);
        tv=findViewById(R.id.tv);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        tts=new TextToSpeech(getApplicationContext(),this);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = et.getText().toString();
                if(msg.equals("")){
                    tts.speak("WRITE MESSAGE FIRST : ",TextToSpeech.QUEUE_ADD,null,null);
                }
                else{
                    tts.speak(msg,TextToSpeech.QUEUE_ADD,null,null);
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                ii.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                startActivityForResult(ii,121);
            }
        });

    }
    @Override
    protected  void onActivityResult(int reqcode , int rescode, Intent data){
         if(reqcode == 121){
             if(rescode == RESULT_OK){
                 ArrayList<String> al = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                 String msg="";

                 for(int i=0;i<al.size();i++){
                     msg = msg+al.get(i);
                 }
               tv.setText(msg);

               if(msg.indexOf("open YouTube")!= -1){
                   Intent i1 =new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com"));
                   startActivity(i1);
               }
               else if(msg.equals("call nidhi")){
                   Intent i2 = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"9428328204"));
                   startActivity(i2);
               }

             }
         }
    }

    @Override
    public void onInit(int i) {

    }
}


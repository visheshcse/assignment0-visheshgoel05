package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CheckActivity extends AppCompatActivity {
    String status = "You are not safe";
    Intent intent;
    String state = "";
    String DEBUG_TAG = "CheckActivityDebugInfo";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Toast.makeText(CheckActivity.this,"State of activity " +"CheckActivity "+ "changed from " + state + " to Create", Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, "State of activity " +"CheckActivity "+ "changed from " + state + " to Create");
        state = "Create";
        setContentView(R.layout.check_activity);

        intent = getIntent();
        ArrayList<String> answers = intent.getExtras().getStringArrayList("answers");
        TextView textView = findViewById(R.id.textView);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);
        TextView textView4 = findViewById(R.id.textView4);
        TextView textView5 = findViewById(R.id.textView5);
        Button buttonCheck = findViewById(R.id.button_check);
        TextView[] textViews = {textView, textView2, textView3, textView4, textView5};
        System.out.println(answers.size());
        for(int i = 0; i < answers.size(); i++){
            textViews[i].setText(answers.get(i));
        }
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(answers.size() == 5){
                    status = "You are safe";
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        intent.putExtra("status", status);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(CheckActivity.this,"State of activity " +"CheckActivity "+ "changed from " + state + " to Start", Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, "State of activity " +"CheckActivity "+ "changed from " + state + " to Start");
        state = "Start";
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(CheckActivity.this,"State of activity " +"CheckActivity "+ "changed from " + state + " to Resume", Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, "State of activity " +"CheckActivity "+ "changed from " + state + " to Resume");
        state = "Resume";
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(CheckActivity.this,"State of activity " +"CheckActivity "+ "changed from " + state + " to Pause", Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, "State of activity " +"CheckActivity "+ "changed from " + state + " to Pause");
        state = "Pause";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(CheckActivity.this,"State of activity " +"CheckActivity "+ "changed from " + state + " to Restart", Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, "State of activity " +"CheckActivity "+ "changed from " + state + " to Restart");
        state = "Restart";
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(CheckActivity.this,"State of activity " +"CheckActivity "+ "changed from " + state + " to Stop", Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, "State of activity " +"CheckActivity "+ "changed from " + state + " to Stop");
        state = "Stop";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(CheckActivity.this,"State of activity " +"CheckActivity "+ "changed from " + state + " to Destroy", Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, "State of activity " +"CheckActivity "+ "changed from " + state + " to Destroy");
        state = "Destroy";
    }

}

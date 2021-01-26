package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String status = "";
    TextView textViewStatus;
    String state = "";
    String DEBUG_TAG = "MainActivityDebugInfo";
    EditText editTextName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(MainActivity.this,"State of activity " +"MainActivity "+ "changed from " + state + " to Create", Toast.LENGTH_SHORT).show();
        state = "Create";
        setContentView(R.layout.activity_main);
        textViewStatus = findViewById(R.id.textView_status);
        editTextName = findViewById(R.id.editText_name);
        int requestCode = 0;
        if(savedInstanceState!=null){
            status=savedInstanceState.getString("status");
            textViewStatus.setText(status);
            editTextName.setText(savedInstanceState.getString("name"));
        }

        final Button buttonClear = findViewById(R.id.clear_button);
        final Button buttonSubmit = findViewById(R.id.submit_button);
        final CheckBox checkBoxFirstQuestion = findViewById(R.id.checkBox_first_question);
        final CheckBox checkBoxSecQuestion = findViewById(R.id.checkBox_second_question);
        final CheckBox checkBoxThirdQuestion = findViewById(R.id.checkBox_third_question);
        final CheckBox checkBoxFourthQuestion = findViewById(R.id.checkBox_fourth_question);
        final CheckBox checkBoxFifthQuestion = findViewById(R.id.checkBox_fifth_question);

        final CheckBox questions[] = {checkBoxFirstQuestion, checkBoxSecQuestion, checkBoxThirdQuestion,
                                        checkBoxFourthQuestion, checkBoxFifthQuestion};
        buttonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for(CheckBox checkBox: questions){
                    if(checkBox.isChecked()){
                        checkBox.setChecked(false);
                    }
                }
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> answers = new ArrayList<>();
                Intent intent = new Intent(MainActivity.this, CheckActivity.class);
                for(CheckBox question : questions){
                    if (question.isChecked()){
                        answers.add(question.getText().toString());
                    }
                }
                intent.putExtra("answers" , answers);
                startActivityForResult(intent, requestCode);
                //startActivity(intent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("status",status);
        savedInstanceState.putString("name", editTextName.getText().toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if(resultCode == RESULT_OK) {
                 status = data.getStringExtra("status");
                 textViewStatus.setText(status);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(MainActivity.this,"State of activity " +"MainActivity "+ "changed from " + state + " to Start", Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, "State of activity " +"MainActivity "+ "changed from " + state + " to Start");
        state = "Start";
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this,"State of activity " +"MainActivity "+ "changed from " + state + " to Resume", Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, "State of activity " +"MainActivity "+ "changed from " + state + " to Resume");
        state = "Resume";
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(MainActivity.this,"State of activity " +"MainActivity "+ "changed from " + state + " to Pause", Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, "State of activity " +"MainActivity "+ "changed from " + state + " to Pause");
        state = "Pause";
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(MainActivity.this,"State of activity " +"MainActivity "+ "changed from " + state + " to Restart", Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, "State of activity " +"MainActivity "+ "changed from " + state + " to Restart");
        state = "Restart";
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(MainActivity.this,"State of activity " +"MainActivity "+ "changed from " + state + " to Stop", Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, "State of activity " +"MainActivity "+ "changed from " + state + " to Stop");
        state = "Stop";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(MainActivity.this,"State of activity " +"MainActivity "+ "changed from " + state + " to Destroy", Toast.LENGTH_SHORT).show();
        Log.i(DEBUG_TAG, "State of activity " +"MainActivity "+ "changed from " + state + " to Destroy");
        state = "Destroy";
    }
}
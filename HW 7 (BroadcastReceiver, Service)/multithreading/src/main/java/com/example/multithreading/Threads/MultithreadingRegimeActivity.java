package com.example.multithreading.Threads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;

import com.example.multithreading.R;

public class MultithreadingRegimeActivity extends AppCompatActivity {

    private RadioButton executorHandler;
    private RadioButton executorFuture;
    private RadioButton rxJava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multithreading_regime);

        executorHandler = findViewById(R.id.executorHandler);
        executorFuture = findViewById(R.id.executorFuture);
        rxJava = findViewById(R.id.rxJava);

        if(executorHandler.isChecked()){
            /// save prefs
        }else if(executorFuture.isChecked()){
            /// save prefs
        }else if(rxJava.isChecked()){
            /// save prefs
        }
    }
}
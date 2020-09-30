package com.example.multithreading.Threads;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.multithreading.R;

public class MultithreadingRegimeActivity extends AppCompatActivity {

    private RadioButton executorHandler;
    private RadioButton executorFuture;
    private RadioButton rxJava;
    private MultithreadingRegimeManager regimeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multithreading_regime);

        Toolbar toolbar = findViewById(R.id.toolbarMultithreadingRegime);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        executorHandler = findViewById(R.id.executorHandler);
        executorFuture = findViewById(R.id.executorFuture);
        rxJava = findViewById(R.id.rxJava);
        regimeManager = MultithreadingRegimeManager.getRegimeManager(MultithreadingRegimeActivity.this);

        getMultithreadingRegime();
    }

    private void getMultithreadingRegime() {
        int regimeId = regimeManager.loadMultithreadingRegime();
        switch (regimeId) {
            case R.id.executorHandler:
                executorHandler.setChecked(true);
                break;
            case R.id.executorFuture:
                executorFuture.setChecked(true);
                break;
            case R.id.rxJava:
                rxJava.setChecked(true);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (executorHandler.isChecked()) {
                regimeManager.saveMultithreadingRegime(executorHandler.getId());
            } else if (executorFuture.isChecked()) {
                regimeManager.saveMultithreadingRegime(executorFuture.getId());
            } else if (rxJava.isChecked()) {
                regimeManager.saveMultithreadingRegime(rxJava.getId());
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
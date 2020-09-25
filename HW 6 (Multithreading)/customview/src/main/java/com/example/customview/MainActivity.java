package com.example.customview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import static com.example.customview.NOTIFICATION_TYPE.SNACKBAR;
import static com.example.customview.NOTIFICATION_TYPE.TOAST;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private NotificationManager notificationManager = NotificationManager.getNotificationManager(this);
    private NOTIFICATION_TYPE notifcationType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        notifcationType = NOTIFICATION_TYPE.valueOf(notificationManager.loadChoice());

        ((CustomView) findViewById(R.id.customView)).setTouchActionListener(new CustomView.TouchActionListener() {
            @Override
            public void onTouchDown(int x, int y) {
                switch (notifcationType) {
                    case TOAST:
                        Toast.makeText(MainActivity.this, "Нажаты координаты [" + x + " : " + y + "]", Toast.LENGTH_SHORT).show();
                        break;
                    case SNACKBAR:
                        Snackbar.make(findViewById(R.id.parentContainer), "Нажаты координаты [" + x + " : " + y + "]", BaseTransientBottomBar.LENGTH_SHORT).show();
                        break;
                    }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if(itemID == R.id.chooseNotificationType){
            startNotificationTypeActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startNotificationTypeActivity(){
        Intent intent = new Intent(MainActivity.this, NotificationTypeActivity.class);
        startActivity(intent);
    }
}

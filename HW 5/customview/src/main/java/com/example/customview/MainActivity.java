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

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NotificationTypeActivity.NOTIFICATION_TYPE notificationType;
    private static final int REQUEST_CODE_FOR_NOTIFICATION_TYPE_ACTIVITY = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((CustomView) findViewById(R.id.customView)).setTouchActionListener(new CustomView.TouchActionListener() {
            @Override
            public void onTouchDown(int x, int y) {
                if(notificationType == NotificationTypeActivity.NOTIFICATION_TYPE.TOAST){
                    Toast.makeText(MainActivity.this, "Нажаты координаты [" + x + " : " + y + "]", Toast.LENGTH_SHORT).show();
                }else if(notificationType == NotificationTypeActivity.NOTIFICATION_TYPE.SNACKBAR){
                    Snackbar.make(findViewById(R.id.parentContainer), "Нажаты координаты [" + x + " : " + y + "]", BaseTransientBottomBar.LENGTH_SHORT).show();
                }else if(notificationType == null){
                    Snackbar.make(findViewById(R.id.parentContainer), "Нажаты координаты [" + x + " : " + y + "]", BaseTransientBottomBar.LENGTH_SHORT).show();
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
        startActivityForResult(NotificationTypeActivity.newIntent(this), REQUEST_CODE_FOR_NOTIFICATION_TYPE_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_FOR_NOTIFICATION_TYPE_ACTIVITY && resultCode == Activity.RESULT_OK && data != null){
            notificationType = (NotificationTypeActivity.NOTIFICATION_TYPE) data.getExtras()
                            .getSerializable(NotificationTypeActivity.KEY_NOTIFICATION_TYPE_RESPONSE);
        }
    }
}

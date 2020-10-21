package com.example.customview;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;


public class NotificationTypeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SwitchCompat switchButton;
    private ImageView saveButton;
    private NotificationManager notificationManager = NotificationManager.getNotificationManager(this);
    private NOTIFICATION_TYPE notifcationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_type);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        saveButton = findViewById(R.id.save);
        switchButton = findViewById(R.id.switchNotificationType);
        notifcationType = NOTIFICATION_TYPE.valueOf(notificationManager.loadChoice());

        implementNotificationType(notifcationType);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSwitchButton();
                finish();
            }
        });
    }

    private void setSwitchButton() {
        if (switchButton.isChecked()) {
            notificationManager.saveChoice(NOTIFICATION_TYPE.SNACKBAR);
        } else {
            notificationManager.saveChoice(NOTIFICATION_TYPE.TOAST);
        }
    }

    private void implementNotificationType(NOTIFICATION_TYPE notificationType) {
        switch (notificationType) {
            case SNACKBAR:
                switchButton.setChecked(true);
            case TOAST:
                switchButton.setChecked(false);
        }
    }
}
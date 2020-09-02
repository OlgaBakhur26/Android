package com.example.customview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;


public class NotificationTypeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private SwitchCompat switchNotificationType;
    private ImageView save;

    public static final String KEY_NOTIFICATION_TYPE_RESPONSE = "KEY_NOTIFICATION_TYPE_ANSWER";
    private static final String KEY_PREFERENCE_CHECKED = "KEY_PREFERENCE_CHECKED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_type);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        save = findViewById(R.id.save);

        switchNotificationType = findViewById(R.id.switchNotificationType);
        switchNotificationType.setChecked(loadChoice());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                implementNotificationType(switchNotificationType);
            }
        });
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, NotificationTypeActivity.class);
        return intent;
    }

    private void implementNotificationType(SwitchCompat switchView) {
        if (switchView != null) {
            Intent intent = new Intent();

            if (switchView.isChecked()) {
                intent.putExtra(KEY_NOTIFICATION_TYPE_RESPONSE, NOTIFICATION_TYPE.SNACKBAR);
            } else {
                intent.putExtra(KEY_NOTIFICATION_TYPE_RESPONSE, NOTIFICATION_TYPE.TOAST);
            }
            setResult(Activity.RESULT_OK, intent);
            saveChoice(switchView.isChecked());
            finish();
        }
    }

    public enum NOTIFICATION_TYPE implements Serializable {
        SNACKBAR,
        TOAST;
    }

    private void saveChoice(Boolean isChecked) {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_PREFERENCE_CHECKED, isChecked);
        editor.apply();
    }

    private Boolean loadChoice() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_PREFERENCE_CHECKED, switchNotificationType.isChecked());
    }
}
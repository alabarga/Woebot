package com.mounica.woebot.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mounica.woebot.viewmodel.MessageViewModel;
import com.mounica.woebot.R;

/**
 * Loading Activity. Loads the data from JSON file and starts Main Activity
 */
public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Button start = findViewById(R.id.button_start);
        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                new MessageViewModel(getApplication());
                Intent loadMainActivity = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(loadMainActivity);
            }
        });
    }
}

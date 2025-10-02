package com.example.clarihearassist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button takeTestButton = findViewById(R.id.takeTestButton);
        Button viewResultsButton = findViewById(R.id.viewResultsButton);
        Button startAssistButton = findViewById(R.id.startAssistButton);

        takeTestButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HearingTestActivity.class);
            startActivity(intent);
        });

        viewResultsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            startActivity(intent);
        });

        startAssistButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HearingAssistActivity.class);
            startActivity(intent);
        });
    }
}
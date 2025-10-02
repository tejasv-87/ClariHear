package com.example.clarihearassist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class HearingTestActivity extends AppCompatActivity {

    private TextView frequencyTextView, earTextView;
    private Button yesButton, noButton;

    private LinkedHashMap<Integer, Integer> frequenciesToTest;
    private Iterator<Integer> frequencyIterator;
    private int currentFrequency;
    private int currentDbLevel;

    private boolean isTestingLeftEar = true;
    private HashMap<Integer, Integer> leftEarResults = new HashMap<>();
    private HashMap<Integer, Integer> rightEarResults = new HashMap<>();

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_test);

        frequencyTextView = findViewById(R.id.frequencyTextView);
        earTextView = findViewById(R.id.earTextView); // This line will now work
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);

        setupFrequencies();
        startTestForCurrentEar();

        yesButton.setOnClickListener(v -> handleResponse(true));
        noButton.setOnClickListener(v -> handleResponse(false));
    }

    private void setupFrequencies() {
        frequenciesToTest = new LinkedHashMap<>();
        // Frequencies in Hz
        int[] freqs = {250, 500, 1000, 2000, 4000, 6000, 8000};
        for (int freq : freqs) {
            frequenciesToTest.put(freq, 0); // Key: Freq, Value: dB level (initially 0)
        }
    }

    private void startTestForCurrentEar() {
        frequencyIterator = frequenciesToTest.keySet().iterator();
        if (isTestingLeftEar) {
            earTextView.setText("Testing: Left Ear");
            Toast.makeText(this, "Starting test for the LEFT ear.", Toast.LENGTH_SHORT).show();
        } else {
            earTextView.setText("Testing: Right Ear");
            Toast.makeText(this, "Starting test for the RIGHT ear.", Toast.LENGTH_SHORT).show();
        }
        nextFrequency();
    }

    private void nextFrequency() {
        if (frequencyIterator.hasNext()) {
            currentFrequency = frequencyIterator.next();
            currentDbLevel = 30; // Start at a moderate volume
            frequencyTextView.setText(currentFrequency + " Hz");
            playTone();
        } else {
            finishEarTest();
        }
    }

    private void playTone() {
        // SIMULATED: In a real app, you would play a tone at currentFrequency and currentDbLevel
        handler.postDelayed(() -> {
            // After a delay, we pretend the tone has played.
        }, 1000); // 1-second tone duration
    }

    private void handleResponse(boolean heard) {
        // This is a simplified logic for threshold finding (descending method)
        if (heard) {
            // They heard it, so we record this level and move on.
            if (isTestingLeftEar) {
                leftEarResults.put(currentFrequency, currentDbLevel);
            } else {
                rightEarResults.put(currentFrequency, currentDbLevel);
            }
            nextFrequency();
        } else {
            // They didn't hear it, increase volume and try again.
            currentDbLevel += 10;
            if (currentDbLevel > 100) { // Max dB level
                // Can't hear even at max, record max and move on
                if (isTestingLeftEar) {
                    leftEarResults.put(currentFrequency, 100);
                } else {
                    rightEarResults.put(currentFrequency, 100);
                }
                nextFrequency();
            } else {
                playTone(); // Replay at a louder volume
            }
        }
    }

    private void finishEarTest() {
        if (isTestingLeftEar) {
            isTestingLeftEar = false;
            startTestForCurrentEar(); // Start test for the right ear
        } else {
            // Both ears tested, save results and move to results screen
            saveResults();
            Intent intent = new Intent(HearingTestActivity.this, ResultsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void saveResults() {
        SharedPreferences prefs = getSharedPreferences("ClariHearPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();

        String leftEarJson = gson.toJson(leftEarResults);
        String rightEarJson = gson.toJson(rightEarResults);

        editor.putString("leftEarResults", leftEarJson);
        editor.putString("rightEarResults", rightEarJson);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null); // Clean up handler
    }
}
package com.example.clarihearassist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ResultsActivity extends AppCompatActivity {

    private TextView leftEarResultsText, rightEarResultsText, summaryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        leftEarResultsText = findViewById(R.id.leftEarResultsText);
        rightEarResultsText = findViewById(R.id.rightEarResultsText);
        summaryText = findViewById(R.id.summaryText);

        loadAndDisplayResults();
    }

    private void loadAndDisplayResults() {
        SharedPreferences prefs = getSharedPreferences("ClariHearPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String leftEarJson = prefs.getString("leftEarResults", "{}");
        String rightEarJson = prefs.getString("rightEarResults", "{}");

        Type type = new TypeToken<HashMap<Integer, Integer>>(){}.getType();
        HashMap<Integer, Integer> leftEarResults = gson.fromJson(leftEarJson, type);
        HashMap<Integer, Integer> rightEarResults = gson.fromJson(rightEarJson, type);

        displayResults(leftEarResults, leftEarResultsText, "Left Ear");
        displayResults(rightEarResults, rightEarResultsText, "Right Ear");

        generateSummary(leftEarResults, rightEarResults);
    }

    private void displayResults(HashMap<Integer, Integer> results, TextView textView, String ear) {
        StringBuilder sb = new StringBuilder(ear + " Results:\n");
        if (results != null && !results.isEmpty()) {
            // Sort the keys to display in order, though not strictly necessary
            results.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> sb.append(entry.getKey()).append(" Hz: ").append(entry.getValue()).append(" dB\n"));
        } else {
            sb.append("No data available.");
        }
        textView.setText(sb.toString());
    }

    private void generateSummary(HashMap<Integer, Integer> leftResults, HashMap<Integer, Integer> rightResults) {
        String leftSummary = getEarSummary(leftResults);
        String rightSummary = getEarSummary(rightResults);

        summaryText.setText("Summary:\nLeft Ear: " + leftSummary + "\nRight Ear: " + rightSummary);
    }

    private String getEarSummary(HashMap<Integer, Integer> results) {
        if (results == null || results.isEmpty()) {
            return "No test data.";
        }
        double totalDb = 0;
        int count = 0;
        for (int db : results.values()) {
            totalDb += db;
            count++;
        }
        double averageDb = totalDb / count;

        if (averageDb <= 25) {
            return "Normal Hearing";
        } else if (averageDb <= 40) {
            return "Mild Hearing Loss";
        } else if (averageDb <= 55) {
            return "Moderate Hearing Loss";
        } else if (averageDb <= 70) {
            return "Moderately Severe Hearing Loss";
        } else {
            return "Severe Hearing Loss";
        }
    }
}
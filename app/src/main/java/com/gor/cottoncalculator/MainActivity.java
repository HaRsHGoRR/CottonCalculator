package com.gor.cottoncalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private EditText inputKapas, inputExpenses, inputCottonSeed, inputUtaro, inputShortage;
    private TextView resultText;
    private AdView adView1,adView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputKapas = findViewById(R.id.input_kapas);
        inputExpenses = findViewById(R.id.input_expenses);
        inputCottonSeed = findViewById(R.id.input_cotton_seed);
        inputUtaro = findViewById(R.id.input_utaro);
        inputShortage = findViewById(R.id.input_shortage);
        resultText = findViewById(R.id.result_text);

        Button calculateButton = findViewById(R.id.calculate_button);
        Button reset = findViewById(R.id.resetbtn);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCost();
            }
        });

        // Initialize the Mobile Ads SDK
        MobileAds.initialize(this, initializationStatus -> {});

        // Find the AdView and load an ad
        adView1 = findViewById(R.id.adView1);
        adView2 = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView1.loadAd(adRequest);
        adView2.loadAd(adRequest);
    }
    private void reset() {
        inputKapas.setText("");
        inputExpenses.setText("");
        inputCottonSeed.setText("");
        inputUtaro.setText("");
        inputShortage.setText("");
        resultText.setText("Cost of Candy:");
    }

    private void calculateCost() {
        try {
            double kapas = Double.parseDouble(inputKapas.getText().toString());
            double expenses = Double.parseDouble(inputExpenses.getText().toString());
            double cottonSeed = Double.parseDouble(inputCottonSeed.getText().toString());
            double utaro = Double.parseDouble(inputUtaro.getText().toString());
            double shortage = Double.parseDouble(inputShortage.getText().toString());

            // Calculate cost of candy
            double costOfCandy = ((((kapas + expenses) * 100) - (((100 - utaro) - shortage)) * cottonSeed) / utaro) * 17.78;

            resultText.setText(String.format("Cost of Candy: %.2f Rs./Candy", costOfCandy));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }
}
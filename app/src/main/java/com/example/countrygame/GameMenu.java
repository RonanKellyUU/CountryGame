package com.example.countrygame;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Open Guess The Country Page
        findViewById(R.id.guess_the_country_button).setOnClickListener(v -> {
            startActivity(new Intent(this, GuessTheCountry.class));
        });
        //Open Guess Hints Page
        findViewById(R.id.guess_hints_button).setOnClickListener(v -> {
            startActivity(new Intent(this, GuessHints.class));
        });
        //Open Guess The Flag Page
        findViewById(R.id.guess_the_flag_button).setOnClickListener(v -> {
            startActivity(new Intent(this, GuessTheFlag.class));
        });
        //Open Advanced Level Page
        findViewById(R.id.advanced_level_button).setOnClickListener(v -> {
            startActivity(new Intent(this, AdvancedLevel.class));
        });
        //Return to Game menu
        findViewById(R.id.home_button).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });


    }
}
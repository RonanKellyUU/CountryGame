package com.example.countrygame;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Open Game menu
        findViewById(R.id.game_button).setOnClickListener(v -> {
            startActivity(new Intent(this, GameMenu.class));
        });
        //Open About Menu
        findViewById(R.id.about_button).setOnClickListener(v -> {
            startActivity(new Intent(this, AboutMenu.class));
        });




    }
}
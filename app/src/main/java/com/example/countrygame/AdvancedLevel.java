package com.example.countrygame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class AdvancedLevel extends AppCompatActivity {

    private ImageView flagImage1;
    private ImageView flagImage2;
    private ImageView flagImage3;
    private boolean answered = false;
    private String correctAnswer1;
    private String correctAnswer2;
    private String correctAnswer3;


    private final int[] flags = {
            R.drawable.flag_algeria,
            R.drawable.flag_australia,
            R.drawable.flag_belgium,
            R.drawable.flag_brazil,
            R.drawable.flag_canada,
            R.drawable.flag_colombia,
            R.drawable.flag_croatia,
            R.drawable.flag_denmark,
            R.drawable.flag_egypt,
            R.drawable.flag_finland,
            R.drawable.flag_france,
            R.drawable.flag_germany,
            R.drawable.flag_greece,
            R.drawable.flag_portugal,
            R.drawable.flag_singapore,
            R.drawable.flag_spain,
            R.drawable.flag_sweden,
            R.drawable.flag_switzerland,
            R.drawable.flag_turkey,
            R.drawable.flag_ukraine,
            R.drawable.flag_zimbabwe,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advanced_level);

        //Connect ImageView in the layout
        flagImage1 = findViewById(R.id.AdvancedFlag1);
        flagImage2 = findViewById(R.id.AdvancedFlag2);
        flagImage3 = findViewById(R.id.AdvancedFlag3);

        //displays first flag
        startNewRound();

        findViewById(R.id.home_button).setOnClickListener(v -> {
            startActivity(new Intent(this, GameMenu.class));
        });


        //Return to Game menu
        findViewById(R.id.AdvancedGuessButton).setOnClickListener(v -> {
            if(answered){
                startNewRound();
            } else {
                checkAnswers();
            }
        });
    }



    private void startNewRound() {
        answered = false;

        Button guessButton = findViewById(R.id.AdvancedGuessButton);
        guessButton.setText("Guess");

        EditText answer1 = findViewById(R.id.AdvancedAnswer1);
        EditText answer2 = findViewById(R.id.AdvancedAnswer2);
        EditText answer3 = findViewById(R.id.AdvancedAnswer3);

        answer1.setText("");
        answer2.setText("");
        answer3.setText("");

        answer1.setError("");
        answer2.setError("");
        answer3.setError("");

        answer1.setTextColor(Color.BLACK);
        answer2.setTextColor(Color.BLACK);
        answer3.setTextColor(Color.BLACK);

        int firstIndex = (int) (Math.random() * flags.length);

        int secondIndex;
        do {
            secondIndex = (int) (Math.random() * flags.length);
        } while (secondIndex == firstIndex);

        int thirdIndex;
        do {
            thirdIndex = (int) (Math.random() * flags.length);
        } while (thirdIndex == firstIndex || thirdIndex == secondIndex);

        flagImage1.setImageResource(flags[firstIndex]);
        flagImage2.setImageResource(flags[secondIndex]);
        flagImage3.setImageResource(flags[thirdIndex]);

        String[] countries = getResources().getStringArray(R.array.countries_dropdown);

        correctAnswer1 = countries[firstIndex];
        correctAnswer2 = countries[secondIndex];
        correctAnswer3 = countries[thirdIndex];
    }

        private void checkAnswers () {
            EditText answer1 = findViewById(R.id.AdvancedAnswer1);
            EditText answer2 = findViewById(R.id.AdvancedAnswer2);
            EditText answer3 = findViewById(R.id.AdvancedAnswer3);

            String guess1 = answer1.getText().toString().trim();
            String guess2 = answer2.getText().toString().trim();
            String guess3 = answer3.getText().toString().trim();

            if (guess1.equalsIgnoreCase(correctAnswer1)) {
                answer1.setTextColor(Color.GREEN);
                answer1.setError(null);
            } else {
                answer1.setTextColor(Color.RED);
                answer1.setError("Incorrect");
            }

            if (guess2.equalsIgnoreCase(correctAnswer2)) {
                answer2.setTextColor(Color.GREEN);
                answer2.setError(null);
            } else {
                answer2.setTextColor(Color.RED);
                answer2.setError("Incorrect");
            }

            if (guess3.equalsIgnoreCase(correctAnswer3)) {
                answer3.setTextColor(Color.GREEN);
                answer3.setError(null);
            } else {
                answer3.setTextColor(Color.RED);
                answer3.setError("Incorrect");
            }

            if (guess1.equalsIgnoreCase(correctAnswer1) && guess2.equalsIgnoreCase(correctAnswer2) && guess3.equalsIgnoreCase(correctAnswer3)){

                answered = true;

                Button guessButton = findViewById(R.id.AdvancedGuessButton);
                guessButton.setText("Next Flags");
            }
        }

    }


package com.example.countrygame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.graphics.Color;
import java.util.Locale;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import java.util.Locale;

public class GuessHints extends AppCompatActivity {

    private TextView hiddenCountryText;
    private String[] countries;
    private String countryName;
    private char[] hiddenName;
    private ImageView flagImage; //displays the current flag
    private int score = 0;
    private TextView scoreText;
    private int correctCountryIndex; // the array posistion of current flag
    private boolean pointAwarded = false;
    private EditText letterInput;
    private Button submitButton;
    private TextView resultText;
    private int incorrectGuesses = 0;
    private boolean roundEnded = false;


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
        setContentView(R.layout.activity_guess_hints);

        scoreText = findViewById(R.id.scoreText);
        scoreText.setText("Score: " + score);

        //Connect ImageView in the layout
        flagImage = findViewById(R.id.flagImage);
        hiddenCountryText = findViewById(R.id.hidden_country_text);
        letterInput = findViewById(R.id.letter_input);
        submitButton = findViewById(R.id.submit_button);
        resultText = findViewById(R.id.Result_Text);
        countries = getResources().getStringArray(R.array.countries_dropdown);
        startNewRound();

        submitButton.setOnClickListener(v -> {
            if (roundEnded) {
                startNewRound();
            } else {
                checkLetter();
            }
        });

        //Return to Game menu
        findViewById(R.id.home_button).setOnClickListener(v -> {
            startActivity(new Intent(this, GameMenu.class));



        });
    }

    private void clearResultFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);

        if (fragment != null){
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    private void loadFragment (Fragment fragment) {
        //create a fragment manager
        FragmentManager fm = getSupportFragmentManager();
        //create a fragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //Replace the FrameLayout with the new fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();//saves the changes
    }

    private void startNewRound() {
        incorrectGuesses = 0;
        roundEnded = false;

        letterInput.setEnabled(true);
        letterInput.setText("");
        letterInput.setError(null);

        submitButton.setEnabled(true);
        submitButton.setText("Submit");

        resultText.setText("");

        clearResultFragment();

        pointAwarded = false; //allows a new flag to get a new score
        correctCountryIndex = (int) (Math.random() * flags.length);
        //find flag in array position
        flagImage.setImageResource(flags[correctCountryIndex]);

        countryName = countries[correctCountryIndex]
                .toUpperCase(Locale.ROOT);

        hiddenName = new char[countryName.length()];
        java.util.Arrays.fill(hiddenName, '-');

        hiddenCountryText.setText(new String(hiddenName));
    }

    private void checkLetter() {
        String input = letterInput.getText().toString().trim();

        if (!input.matches("[a-zA-Z]")) {
            letterInput.setError("Enter a letter from A - Z");
            return;


        }

        char guessedLetter = input.toUpperCase(Locale.ROOT).charAt(0);

        boolean found = false;

        for (int i = 0; i < countryName.length(); i++) {
            if (countryName.charAt(i) == guessedLetter) {
                hiddenName[i] = guessedLetter;
                found = true;
            }
        }

        hiddenCountryText.setText(new String(hiddenName));
        letterInput.setText("");

        if (found) {
            loadFragment(new CorrectGuess());
        } else {
            incorrectGuesses++;
            loadFragment(new IncorrectGuess());
        }

        if (new String(hiddenName).equals(countryName)) {
            roundEnded = true;
            resultText.setText("Correct! The country was guessed.");

            if(!pointAwarded){
                score++;
                scoreText.setText("Score: " + score);
                pointAwarded = true;
            }
        } else if (incorrectGuesses >=3) {
            roundEnded = true;
            resultText.setText("No attempts left. Answer:" + countryName);
        } else {
            resultText.setText(3 - incorrectGuesses + " Remaining attempts");
        }


        resultText.setVisibility(View.VISIBLE);

        if(roundEnded){
            letterInput.setEnabled(false);
            submitButton.setText("Next Flag");
        }

        submitButton.setEnabled(false);

                flagImage.postDelayed(() -> {
                    clearResultFragment();
                    submitButton.setEnabled(true);
                }, 1000);

                    }
                }






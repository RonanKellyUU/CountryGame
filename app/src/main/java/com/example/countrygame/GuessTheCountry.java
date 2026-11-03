package com.example.countrygame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.graphics.Color;

//Beginning of code
public class GuessTheCountry extends AppCompatActivity {

    private ImageView flagImage; //displays the current flag
    private Spinner countrySpinner; //dropdown to select a country
    private int correctCountryIndex; // the array posistion of current flag

    private int score = 0;
    private TextView scoreText;
    private boolean pointAwarded = false;
    private int roundsCompleted = 0;
    private String countryName;


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
        setContentView(R.layout.activity_guess_the_country);

        scoreText = findViewById(R.id.score_text);

        //home button
        findViewById(R.id.home_button).setOnClickListener(v -> {
            startActivity(new Intent(this, GameMenu.class));
        });

        //Connect ImageView in the layout
        flagImage = findViewById(R.id.flagImage);
        //displays first flag
        startNewRound();

        //connects to dropdown
        countrySpinner = findViewById(R.id.country_spinner);

        //guess button
        findViewById(R.id.guess_button).setOnClickListener(v -> {
            //finds country position in array
            int selectedCountryIndex = countrySpinner.getSelectedItemPosition();

            //compare drop down and flag position
            if (selectedCountryIndex == correctCountryIndex) {
                loadFragment(new CorrectGuess());

                if(!pointAwarded){
                    score++; // gain one point
                    scoreText.setText("Score: " + score);
                    pointAwarded = true;

                    //disable buttons while moving to next page
                    findViewById(R.id.guess_button).setEnabled(false);
                    findViewById(R.id.next_flag_button).setEnabled(false);

                    flagImage.postDelayed(() -> {
                        roundsCompleted++;

                        if (roundsCompleted >=3){
                            startActivity(new Intent(this, Splash_Screen.class));
                            finish();
                        } else   {
                            startNewRound();
                        }

                        //enable the buttons again
                        findViewById(R.id.guess_button).setEnabled(true);
                        findViewById(R.id.next_flag_button).setEnabled(true);
                        }, 1000);
                }
            } else {
                loadFragment(new IncorrectGuess());

                TextView answerText = findViewById(R.id.correct_answer);
                answerText.setText("Answer: " + countryName);
                answerText.setVisibility(View.VISIBLE);

            }


        });



        // This currently opens the menu when Next Flag is pressed.
        // To show another flag instead, replace startActivity below with startNewRound();
        findViewById(R.id.next_flag_button).setOnClickListener(v -> {
            startNewRound();

            findViewById(R.id.guess_button).setEnabled(true);
            findViewById(R.id.next_flag_button).setEnabled(true);
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
        clearResultFragment();

        pointAwarded = false; //allows a new flag to get a new score
        correctCountryIndex = (int) (Math.random() * flags.length);
        //find flag in array position
        flagImage.setImageResource(flags[correctCountryIndex]);

        countryName = getResources().getStringArray(R.array.countries_dropdown)[correctCountryIndex];



    }

}

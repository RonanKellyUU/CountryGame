package com.example.countrygame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.graphics.Color;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class GuessTheFlag extends AppCompatActivity {

    private ImageView flagImage1;
    private ImageView flagImage2;
    private ImageView flagImage3;


    private Spinner countrySpinner; //dropdown to select a country
    private int correctCountryIndex; // the array posistion of current flag
    private TextView resultText; // Displays the answer result.
    private int score = 0;
    private TextView scoreText;
    private boolean pointAwarded = false;
    private int roundsCompleted = 0;
    private ImageView correctFlagImage;
    private boolean answered = false;

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
        setContentView(R.layout.activity_guess_the_flag);

        scoreText = findViewById(R.id.Guess_the_flag_score);
        scoreText.setText("Score: " + score);


        //Connect ImageView in the layout
        flagImage1 = findViewById(R.id.CountryFlag1);
        flagImage2 = findViewById(R.id.CountryFlag2);
        flagImage3 = findViewById(R.id.CountryFlag3);
        //displays first flag
        startNewRound();


        //Return to Game menu
        findViewById(R.id.home_button).setOnClickListener(v -> {
            startActivity(new Intent(this, GameMenu.class));
        });

        findViewById(R.id.next_flag_button).setOnClickListener(v -> {
            roundsCompleted++;

            if (roundsCompleted >=3){
                startActivity(new Intent(this, GameMenu.class));
                finish();
            } else {
                startNewRound();
            }

        });

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
        answered = false;

        findViewById(R.id.next_flag_button).setVisibility(View.INVISIBLE);
        findViewById(R.id.next_flag_button).setEnabled(false);

        flagImage1.setBackground(null);
        flagImage2.setBackground(null);
        flagImage3.setBackground(null);


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

        ImageView[] images = {flagImage1, flagImage2, flagImage3};
        int[] indexes = {firstIndex, secondIndex, thirdIndex};

        int correctPosition = (int) (Math.random()* images.length);
        correctFlagImage = images[correctPosition];
        correctCountryIndex = indexes[correctPosition];

        String[] countries = getResources().getStringArray(R.array.countries_dropdown);

        TextView questionText = findViewById(R.id.question_text);
        questionText.setText("Tap the flag of " + countries[correctCountryIndex]);
    }

    public void onFlagClicked(View view){
        if (answered){
            return;
        }
        answered = true;

        ImageView selectedFlag = (ImageView) view;

        if (selectedFlag == correctFlagImage) {
            loadFragment(new CorrectGuess());
            score++;
            scoreText.setText("Score: " +score);
        } else {
            loadFragment(new IncorrectGuess());
            highlightFlag(correctFlagImage, Color.BLUE);
        }

        flagImage1.postDelayed(()-> {
            clearResultFragment();
        }, 1000);

        findViewById(R.id.next_flag_button).setVisibility(View.VISIBLE);
        findViewById(R.id.next_flag_button).setEnabled(true);
    }

    private void clearResultFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);

        if (fragment != null){
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    private void highlightFlag(ImageView image, int colour){
        image.setBackgroundColor(colour);
    }
}





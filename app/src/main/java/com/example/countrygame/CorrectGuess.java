package com.example.countrygame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

public class CorrectGuess extends Fragment {
    View view;
    Button first_button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflate teh layout for this fragment
        view = inflater.inflate(R.layout.correct_guess, container, false);
        first_button = view.findViewById(R.id.firstButton);
        //get the reference of button
        first_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                //display a message by using a toast
                Toast.makeText(getActivity(), "this is the first fragment",
                    Toast.LENGTH_LONG).show();
            }
        });
        return view;

    }
}

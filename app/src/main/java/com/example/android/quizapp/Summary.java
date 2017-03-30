package com.example.g.quizzz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Summary extends AppCompatActivity {
    private int correctQuestions;
    private int howManyQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        // Loads saved settings from previous Activities
        Bundle extras = getIntent().getExtras();
        howManyQuestions = extras.getInt("max");
        Log.d("SumA", "Max questions: " + howManyQuestions);
        correctQuestions = extras.getInt("correctQuestions");
        Log.d("SumA", "Correct questions: " + correctQuestions);
        //Allocates GUI
        TextView scoreg = (TextView) findViewById(R.id.scoreg);
        TextView scorem = (TextView) findViewById(R.id.scorem);

        if (correctQuestions < 10) {
            scoreg.setText("0" + correctQuestions);
        } else {
            scoreg.setText("" + correctQuestions);
        }
        if ((howManyQuestions + 1) < 10) {
            scorem.setText("0" + (howManyQuestions + 1));
        } else {
            scorem.setText("" + (howManyQuestions + 1));
        }
        Toast toast = Toast.makeText(getApplicationContext(), "Answers you got correct: " + correctQuestions + "!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void Redo(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}
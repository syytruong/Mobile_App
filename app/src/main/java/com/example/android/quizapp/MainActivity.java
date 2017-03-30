package com.example.g.quizzz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int maxQuestions = 12;
    private int currentQuestion;
    private String[] questions = new String[12];
    private String[] chosen_questions;

    public static int randInt(int max) {
        int randomNum = new Random().nextInt(max + 1);
        Log.d("Randomizer", "Random numer is: " + randomNum);
        return randomNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // gui allocation
        final Button incQ = (Button) findViewById(R.id.inc_questions);
        final Button decQ = (Button) findViewById(R.id.dec_questions);
        Button start_button = (Button) findViewById(R.id.start);

        // button listeners with logic
        incQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestion < maxQuestions - 1) {
                    incQ.setEnabled(true);
                    decQ.setEnabled(true);
                } else {
                    incQ.setEnabled(false);
                }
                currentQuestion = currentQuestion + 1;
                displayWhenClicked(currentQuestion);
                Log.d("Iteration", "User wants to answer:" + currentQuestion + "questions.");
            }
        });
        decQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestion > 2) {
                    incQ.setEnabled(true);
                    decQ.setEnabled(true);
                } else {
                    decQ.setEnabled(false);
                }
                currentQuestion = currentQuestion - 1;
                displayWhenClicked(currentQuestion);
            }
        });
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateArray();
                startQuiz();
            }
        });
    }

    //displays number of questions when value changed
    private void displayWhenClicked(int currQ) {
        TextView tmq = (TextView) findViewById(R.id.question_counter);
        if (currQ < 10) {
            tmq.setText("0" + currQ);
        } else {
            tmq.setText("" + currQ);
        }
    }

    private void populateArray() {
        questions[0] = "single;What is the name of paranoid android in the Hitchhiker's Guide to the Galaxy?;Marvin;Flippo;Bob;Misery;Marvin";
        questions[1] = "multi;Which three countries have both an Atlantic and Mediterranean coastline?;France;Spain;Italy;Morocco;true;true;false;true";
        questions[2] = "single;Who do the Italians call Topolino? ;God Father;Mickey Mouse;Tourists;Children;Mickey Mouse";
        questions[3] = "text;Which is the third letter of the greek alphabet?;gamma";
        questions[4] = "single;Which part of the body is most sensitive to radiation?;Brain;Skin;Blood;Lungs;Blood";
        questions[5] = "multi;On the flags of which two countries would you find the most stars ?;Cuba;USA;Brazil;Venezuela;false;true;true;false";
        questions[6] = "text;Which is the largest airline in Ireland?;ryanair";
        questions[7] = "multi;The RMS Titanic had two sister ships. What were their names?;Adelaide;Victory;Brittanic;Olympic;flase;false;true;true";
        questions[8] = "single;What is the official language or languages of the United States?;English;German;French;None;None";
        questions[9] = "text;What colour is worn for funerals in Egypt?;yellow";
        questions[10] = "single;Who called himself 8th wonder of world cos of his big dick?;Hitler;Charlie Chaplain;Trumph;Arnold Schwarzenegger;Charlie Chaplain";
        questions[11] = "multi;Where was Barack Obama born?;Mexico;Honolulu;USA;Hawaii;false;true;false;true";
    }

    private void startQuiz() {
        TextView tmq = (TextView) findViewById(R.id.question_counter);
        if (tmq.getText().equals("00")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Number of questtions cannot be less than 1", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            randomizeQuestions(currentQuestion);
            Intent intent = new Intent(getBaseContext(), Questions.class);
            intent.putExtra("questions", chosen_questions);
            intent.putExtra("currentQuestion", currentQuestion);
            startActivity(intent);
        }
    }

    private void randomizeQuestions(int howManyQuestions) {
        chosen_questions = new String[12];
        List<Integer> chosenQ = new ArrayList();
        boolean repeats;
        // randomly takes 1 question and makes sure that it does not repeat
        if (howManyQuestions < maxQuestions) {
            for (int i = 0; i <= howManyQuestions; i++) {
                do {
                    int random_number = randInt(howManyQuestions);
                    if (chosenQ.contains(random_number)) {
                        repeats = true;
                    } else {
                        chosenQ.add(random_number);
                        chosen_questions[i] = questions[random_number];
                        repeats = false;
                        Log.d("Randomizer", "Added question" + random_number + " to list of questions");
                    }
                } while (repeats);
            }
        } else if (howManyQuestions == 12) {
            int temp = howManyQuestions - 1;
            for (int i = 0; i <= temp; i++) {
                chosen_questions[i] = questions[i];
            }
        }
    }
}
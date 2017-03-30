package com.example.g.quizzz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Questions extends AppCompatActivity {
    private String[] questions;
    private int currentQuestion = 0;
    private int howManyQuestions;
    private CountDownTimer cDown;
    private int correctQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //added
        Bundle extras = getIntent().getExtras();
        howManyQuestions = extras.getInt("currQ");
        questions = new String[howManyQuestions];
        questions = extras.getStringArray("questions");

        //Log.d("CurrQ", "" + howManyQuestions);
        //Log.d("Array", questions[11]);

        howManyQuestions = howManyQuestions - 1;
        QuestionEngine();
    }

    public String[] SplitQuestion(String[] questions) {
        String[] split_question = questions[currentQuestion].split(";");
        Log.d("Splitter", "Splitted question is : " + currentQuestion);
        return split_question;
    }

    public void CDown() {
        cDown = new CountDownTimer(16000, 0001) {

            TextView cDTimer = (TextView) findViewById(R.id.timer);
            ProgressBar pBar = (ProgressBar) findViewById(R.id.progress_bar);

            public void onTick(long millisUntilFinished) {
                Integer clock = (int) (long) millisUntilFinished / 1000;
                cDTimer.setText("" + millisUntilFinished / 1000);
                pBar.setProgress(clock);
                pBar.setMax(16);
            }

            public void onFinish() {
                CheckAnswer();
            }
        };
        cDown.start();
        ButtonHander();
    }

    public void QuestionEngine() {
        String[] split_question = SplitQuestion(questions);
        if (split_question[0].equals("single")) {
            setContentView(R.layout.activity_questions_radio);
            RadioButton answer_one_radio = (RadioButton) findViewById(R.id.answer_one_radio);
            RadioButton answer_two_radio = (RadioButton) findViewById(R.id.answer_two_radio);
            RadioButton answer_three_radio = (RadioButton) findViewById(R.id.answer_three_radio);
            RadioButton answer_four_radio = (RadioButton) findViewById(R.id.answer_four_radio);
            TextView question = (TextView) findViewById(R.id.question);
            TextView questionLabel = (TextView) findViewById(R.id.questionLabel);
            questionLabel.setText("Question " + (currentQuestion + 1) + " of " + (howManyQuestions + 1));
            question.setText(split_question[1]);
            answer_one_radio.setText(split_question[2]);
            answer_two_radio.setText(split_question[3]);
            answer_three_radio.setText(split_question[4]);
            answer_four_radio.setText(split_question[5]);
            CDown();
        } else if (split_question[0].equals("multi")) {
            setContentView(R.layout.activity_questions_checkbox);
            CheckBox answer_one_box = (CheckBox) findViewById(R.id.answer_one_box);
            CheckBox answer_two_box = (CheckBox) findViewById(R.id.answer_two_box);
            CheckBox answer_three_box = (CheckBox) findViewById(R.id.answer_three_box);
            CheckBox answer_four_box = (CheckBox) findViewById(R.id.answer_four_box);
            TextView question = (TextView) findViewById(R.id.question);
            TextView questionLabel = (TextView) findViewById(R.id.questionLabel);
            questionLabel.setText("Question " + (currentQuestion + 1) + " of " + (howManyQuestions + 1));
            question.setText(split_question[1]);
            answer_one_box.setText(split_question[2]);
            answer_two_box.setText(split_question[3]);
            answer_three_box.setText(split_question[4]);
            answer_four_box.setText(split_question[5]);
            CDown();
        } else if (split_question[0].equals("text")) {
            setContentView(R.layout.activity_questions_input);
            TextView question = (TextView) findViewById(R.id.question);
            TextView questionLabel = (TextView) findViewById(R.id.questionLabel);
            questionLabel.setText("Question " + (currentQuestion + 1) + " of " + (howManyQuestions + 1));
            question.setText(split_question[1]);
            CDown();
        }
    }

    public void CheckAnswer() {
        if (currentQuestion < howManyQuestions) {
            String[] split_question = SplitQuestion(questions);
            if (split_question[0].equals("single")) {
                RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);
                int selectedId = rg.getCheckedRadioButtonId();
                RadioButton answer = (RadioButton) findViewById(selectedId);
                if (rg.getCheckedRadioButtonId() == -1) {
                    Log.d("SA", "No answer was chosen");
                    currentQuestion = currentQuestion + 1;
                    QuestionEngine();
                } else if (answer.getText().equals(split_question[6])) {
                    correctQuestions = correctQuestions + 1;
                    currentQuestion = currentQuestion + 1;
                    QuestionEngine();

                    Log.d("SA", "Single answer is correct");
                } else {
                    currentQuestion = currentQuestion + 1;
                    QuestionEngine();
                    Log.d("SA", "Single Answer is not correct");
                }
            } else if (split_question[0].equals("multi")) {
                CheckBox answer_one_box = (CheckBox) findViewById(R.id.answer_one_box);
                CheckBox answer_two_box = (CheckBox) findViewById(R.id.answer_two_box);
                CheckBox answer_three_box = (CheckBox) findViewById(R.id.answer_three_box);
                CheckBox answer_four_box = (CheckBox) findViewById(R.id.answer_four_box);
                boolean cb1 = Boolean.parseBoolean(split_question[6]);
                boolean cb2 = Boolean.parseBoolean(split_question[7]);
                boolean cb3 = Boolean.parseBoolean(split_question[8]);
                boolean cb4 = Boolean.parseBoolean(split_question[9]);
                if (answer_one_box.isChecked() == cb1 && answer_two_box.isChecked() == cb2 && answer_three_box.isChecked() == cb3 && answer_four_box.isChecked() == cb4) {
                    correctQuestions = correctQuestions + 1;
                    currentQuestion = currentQuestion + 1;
                    QuestionEngine();
                    Log.d("ChA", "Multi answer is correct");
                } else {
                    currentQuestion = currentQuestion + 1;
                    QuestionEngine();
                    Log.d("ChA", "Multi answer is not correct");
                }
            } else if (split_question[0].equals("text")) {
                TextView userInput = (TextView) findViewById(R.id.user_input);
                String txtAnswer = userInput.getText().toString();
                txtAnswer = txtAnswer.toLowerCase();
                Log.d("USRINPT", "Answer is: " + split_question[2]);
                Log.d("USRINPT", "User entered answer : " + txtAnswer);
                if (txtAnswer.equals(split_question[2])) {
                    Log.d("USRINPT", "Answer is: " + split_question[2]);
                    Log.d("USRINPT", "text answer correct");
                    correctQuestions = correctQuestions + 1;
                    currentQuestion = currentQuestion + 1;
                    QuestionEngine();
                } else {
                    Log.d("USRINPT", "text answer incorrect");
                    currentQuestion = currentQuestion + 1;
                    QuestionEngine();
                }
            }
        } else {
            Intent intent = new Intent(getBaseContext(), Summary.class);
            intent.putExtra("correctQuestions", correctQuestions);
            intent.putExtra("max", howManyQuestions);
            startActivity(intent);
        }
    }

    public void ButtonHander() {
        Button nextQ = (Button) findViewById(R.id.apply_answer);
        nextQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cDown.cancel();
                CheckAnswer();
            }
        });
    }
}
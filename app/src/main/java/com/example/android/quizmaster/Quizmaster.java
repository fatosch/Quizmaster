package com.example.android.quizmaster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import static android.view.View.GONE;

public class Quizmaster extends AppCompatActivity {

    int[] hintsUsed = {0, 0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizmaster);
    }

    /**
     * makes the ScrollView visible and 'removes' start_button and introduction_text
     */
    public void startQuiz(View view) {
        ScrollView quizLayout = (ScrollView) findViewById(R.id.quiz_visibility);
        quizLayout.setVisibility(View.VISIBLE);
        TextView quizIntroduction = (TextView) findViewById(R.id.introduction_text);
        quizIntroduction.setVisibility(GONE);
        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setVisibility(GONE);
        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setVisibility(View.VISIBLE);
    }

    /**
     * defines if the hints were used
     *
     *
     */
    public void help(View view) {
        CheckBox helpBox = (CheckBox) findViewById(view.getId());
        switch (view.getId()) {
            case (R.id.help1):
                helpBox.setText(getString(R.string.answer_hint_1));
                if (!helpBox.isChecked()) {
                    helpBox.setText(getString(R.string.help));
                }
                hintsUsed[0] = 1;
                break;
            case (R.id.help2):
                helpBox.setText(getString(R.string.answer_hint_2));
                if (!helpBox.isChecked()) {
                    helpBox.setText(getString(R.string.help));
                }
                hintsUsed[1] = 1;
                break;
            case (R.id.help3):
                helpBox.setText(getString(R.string.answer_hint_3));
                if (!helpBox.isChecked()) {
                    helpBox.setText(getString(R.string.help));
                }
                hintsUsed[2] = 1;
                break;
            case (R.id.help4):
                helpBox.setText(getString(R.string.answer_hint_4));
                if (!helpBox.isChecked()) {
                    helpBox.setText(getString(R.string.help));
                }
                hintsUsed[3] = 1;
                break;
            case (R.id.help5):
                helpBox.setText(getString(R.string.answer_hint_5));
                if (!helpBox.isChecked()) {
                    helpBox.setText(getString(R.string.help));
                }
                hintsUsed[4] = 1;
                break;
            default:
                break;
        }
    }

    /**
     * This method checks if the answers are correct!
     */
    public void validateAnswers(View view) {
        boolean firstQuestion = answerChecker(R.id.complete_q1);
        boolean secondQueston = answerChecker(R.id.complete_q2);
        boolean thirdQuestion = answerChecker(R.id.complete_q3);
        boolean fourthQuestion = answerChecker(R.id.complete_q4);
        boolean fifthQuestion = answerChecker(R.id.complete_q5);
        boolean[] validation = {firstQuestion, secondQueston, thirdQuestion, fourthQuestion, fifthQuestion};
        String endResault = validateQuiz(validation, hintsUsed);
        Toast.makeText(this, endResault, Toast.LENGTH_LONG).show();
    }

    /**
     * @param idQuestion Id of the question which is going to be checked
     * @return if the answer was correct!
     * This method validates each answer by using id of the question as a argument
     */
    private boolean answerChecker(int idQuestion) {
        if (idQuestion == R.id.complete_q1) {
            RadioGroup answerGroup = (RadioGroup) findViewById(R.id.a1_group);
            int idAnswer = answerGroup.getCheckedRadioButtonId();
            if (idAnswer == R.id.answer1c) {
                return true;
            }
        }
        if (idQuestion == R.id.complete_q2) {
            boolean[] selectedChoice = {false, false, false, false};
            boolean[] correctChoice = {true, false, false, true};
            int[] idsAnswer = {R.id.answer2a, R.id.answer2b, R.id.answer2c, R.id.answer2d};
            for (int i = 0; i < 4; i++) {
                CheckBox choices = (CheckBox) findViewById(idsAnswer[i]);
                if (choices.isChecked()) {
                    selectedChoice[i] = true;
                }
            }
            if (Arrays.equals(correctChoice, selectedChoice)) {
                return true;
            }
        }
        if (idQuestion == R.id.complete_q3) {
            EditText answer3 = (EditText) findViewById(R.id.answer3);
            if (answer3.getText().toString().equals(getString(R.string.answer3))) {
                return true;
            }
        }
        if (idQuestion == R.id.complete_q4) {
            RadioGroup answerGroup = (RadioGroup) findViewById(R.id.a4_group);
            int idAnswer = answerGroup.getCheckedRadioButtonId();
            if (idAnswer == R.id.answer4b) {
                return true;
            }
        }
        if (idQuestion == R.id.complete_q5) {
            EditText answer5 = (EditText) findViewById(R.id.answer5);
            if (answer5.getText().toString().equals(getString(R.string.answer5))) {
                return true;

            }
        }
        return false;
    }

    /**
     * @param answers contains as a boolean array all answers
     * @param usedHints contains as an integer array if for a question i a hint was used
     * This method is calculating the final score of the quiz!
     */
    private String validateQuiz(boolean[] answers, int[] usedHints) {
        int points[] = {0, 0, 0, 0, 0, 0};
        for (int i = 0; i <= 4; i++) {
            if (answers[i]) {
                points[i] = 2 - usedHints[i];
                points[5] += points[i];
            } else {
                points[i] -= usedHints[i];
                points[5] -= usedHints[i];
            }
        }
        double totalScore = 100 - points[5] * 10;
        if (totalScore > 100) {
            totalScore = 100;
        }
        String resault = getString(R.string.summary_massage_intro, totalScore);
        for (int i = 0; i <= 4; i++) {
            String answer;
            if (points[i] > 0) {
                answer = getString(R.string.summary_right);
            } else {
                answer = getString(R.string.summary_wrong);
            }
            resault += getString(R.string.summary_answer, i + 1, points[i], answer);
        }
        resault += getString(R.string.summary_end);
        return resault;
    }
}

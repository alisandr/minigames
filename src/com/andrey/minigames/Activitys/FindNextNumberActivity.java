package com.andrey.minigames.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.andrey.minigames.R;

public class FindNextNumberActivity extends Activity {

    private int mChoise;
    private int mLife;
    private int mLevel;
    private String[] mQuestionBook;
    private int[] mAnswerBook;
    private TextView mMainTable; // Табло с вопросами
    private EditText mInputField; // Поле ввода
    private Button mRequestButton;
    private TextView mLifeCount; // Счётчик жизней
    private TextView mLevelCount; // Указатель уровня
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View pView) {
            mChoise = Integer.parseInt(mInputField.getText().toString());
            reviewAnswerAndRefreshInternalData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_next_number);
        initView();
        initGameData();
    }

    private void initView() {
        // init main UI element
        mMainTable = (TextView) findViewById(R.id.fnn_maintable);
        mLevelCount = (TextView) findViewById(R.id.fnn_level);
        mLifeCount = (TextView) findViewById(R.id.fnn_life);
        mInputField = (EditText) findViewById(R.id.fnn_inputfield);
        mRequestButton = (Button) findViewById(R.id.fnn_requesbutton);
        mRequestButton.setOnClickListener(mOnClickListener);
    }

    private void initGameData() {
        mQuestionBook = new String[getResources().getInteger(R.integer.fnn_all_level_count)];
        mQuestionBook[0] = getString(R.string.fnn_quest_1);
        mQuestionBook[1] = getString(R.string.fnn_quest_2);
        mQuestionBook[2] = getString(R.string.fnn_quest_3);
        mQuestionBook[3] = getString(R.string.fnn_quest_4);
        mQuestionBook[4] = getString(R.string.fnn_quest_5);

        mAnswerBook[0] = getResources().getInteger(R.integer.fnn_unlock_1);
        mAnswerBook[1] = getResources().getInteger(R.integer.fnn_unlock_2);
        mAnswerBook[2] = getResources().getInteger(R.integer.fnn_unlock_3);
        mAnswerBook[3] = getResources().getInteger(R.integer.fnn_unlock_4);
        mAnswerBook[4] = getResources().getInteger(R.integer.fnn_unlock_5);

        mLife = getResources().getInteger(R.integer.fnn_start_life);
        mLevel = getResources().getInteger(R.integer.fnn_start_level);
    }

    private void reviewAnswerAndRefreshInternalData() {
        boolean answerWin = false;

        if (mChoise == mAnswerBook[mLevel]) {
            mLevel++;
            refreshGameQuestAndLevelCount();

            if (mLevel >= 4) {
                mMainTable.setText("Правильно! Победа !");
                mLevel = 1;
                mRequestButton.setClickable(false);
            }
        } else {
            mLife--;
            if (mLife <= 0) {
                mMainTable.setText("Увы, попытки исчерпаны");
                mLevel = 1;
                mRequestButton.setClickable(false);
            }
        }

        Toast.makeText(FindNextNumberActivity.this, answerWin ? "Верно!" : "Ошиблись", Toast.LENGTH_SHORT).show();
        mInputField.setText("");
        refreshLife();

    }

    private void refreshGameQuestAndLevelCount() {
        mMainTable.setText(mQuestionBook[mLevel]);
        mLevelCount.setText(getString(R.string.fnn_level_coun_prefix) + " " + String.valueOf(mLevel));
    }

    private void refreshLife() {
        mLifeCount.setText(getString(R.string.fnn_life_count_prefix) + " " + String.valueOf(mLife));
    }


}

package com.andrey.minigames.Activitys;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.andrey.minigames.R;
import enums.GameCode;

import java.util.Random;

public class FieldOfDreamsActivity extends Activity {


    private TextView mGameTableView;  //Игровое табло
    private TextView mLifeCountView; //Табло жизьней игрока
    private TextView mWordDescription; //Описание слова
    private EditText mInputTextField;  //Поле для ввода
    private Button mConfimButton;  //Проверка буквы
    private Button mMakeBaraban; //Крутить барабан
    private Button mOpenWord; //Открыть слово
    private TextView mScore;  //Очки барабана
    private TextView mTotalScore; //Всего очков
    private String[] mQuestionBook; //Массив с вопросами
    private String[] mQuestionDescr; //Массив с описанием
    private char[] mQuestWord; //Массив с загадыным словом в виде отдельных символов
    private boolean[] mOpendLetter; //Массив True False
    private int[] mScorePool; //Очки которые выпадают на барабане
    private byte mAvaiableLife; //Жизьни игрока
    private byte mTurnCount; //Переменная туров
    private char mInputLetter; //Буквы загаданые
    private int mMyScore; //Количевство очков
    private int mBarabanScore; //Очки выпавшие на барабане
    private String mAvaiableLifePrefix;  //Жизьней в начале игры
    private String mBarabanPrefix;   //Очков в начале игры на  барабане
    private String mTotalScorePrefix; //Очков в начале игры
    private String mFullWord;  //Полное слово
    private Random mRandom; //Генератор случайных цифер

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_of_dreams);

        initViews();
        initGameData();
        createQuest(mTurnCount);
         //TODO перевести все цифровые значения в файл ИНТЕГЕРОВ (уровень, жизни)
    }

    private void initViews() {
        mGameTableView = (TextView) findViewById(R.id.fod_game_tablo);
        mLifeCountView = (TextView) findViewById(R.id.fod_avaiable_life);
        mWordDescription = (TextView) findViewById(R.id.fod_word_description);
        mScore = (TextView) findViewById(R.id.fod_avaiable_score);
        mTotalScore = (TextView) findViewById(R.id.fod_avaiable_total_score);


        mInputTextField = (EditText) findViewById(R.id.fod_word_input_field);
        mConfimButton = (Button) findViewById(R.id.fod_confim_letter_button);
        mMakeBaraban = (Button) findViewById(R.id.fod_confim_button_make_baraban);
        mOpenWord = (Button) findViewById(R.id.fod_confim_button_openword);

        View.OnClickListener gameButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.fod_confim_button_make_baraban:
                        roundBaraban();
                        break;

                    case R.id.fod_confim_letter_button:

                        if (!TextUtils.isEmpty(mInputTextField.getText().toString())) {
                            mInputLetter = mInputTextField.getText().toString().toLowerCase().charAt(0);
                            mInputTextField.setText("");
                            reviewLetter(mInputLetter);
                            switchButtonStatus(true);
                        }

                        break;

                    case R.id.fod_confim_button_openword:
                        if (!TextUtils.isEmpty(mInputTextField.getText().toString())) {    //Проверка поля ввода на наличие буквы/слова
                            mFullWord = mInputTextField.getText().toString().toLowerCase();  //Запись слова в переменную перевод слова в ижний регистр
                            mInputTextField.setText("");
                            reviewFullWord();
                            switchButtonStatus(true);
                        } else {
                            Toast.makeText(FieldOfDreamsActivity.this, getString(R.string.fod_game_win), Toast.LENGTH_SHORT).show();
                        }
                        break;

                }

            }
        };

        mConfimButton.setOnClickListener(gameButtonListener);
        mMakeBaraban.setOnClickListener(gameButtonListener);
        mOpenWord.setOnClickListener(gameButtonListener);
    }

    private void initGameData() {
        mRandom = new Random();
        mScorePool = new int[5];
        mQuestionBook = new String[6];
        mQuestionDescr = new String[6];

        mScorePool[0] = 0;
        mScorePool[1] = 100;
        mScorePool[2] = 200;
        mScorePool[3] = 300;
        mScorePool[4] = 500;

        mQuestionBook[0] = getString(R.string.fod_quest_1);
        mQuestionBook[1] = getString(R.string.fod_quest_2);
        mQuestionBook[2] = getString(R.string.fod_quest_3);
        mQuestionBook[3] = getString(R.string.fod_quest_4);
        mQuestionBook[4] = getString(R.string.fod_quest_5);
        mQuestionBook[5] = getString(R.string.fod_quest_6);


        mQuestionDescr[0] = getString(R.string.fod_quest_1_discr);
        mQuestionDescr[1] = getString(R.string.fod_quest_2_discr);
        mQuestionDescr[2] = getString(R.string.fod_quest_3_discr);
        mQuestionDescr[3] = getString(R.string.fod_quest_4_discr);
        mQuestionDescr[4] = getString(R.string.fod_quest_5_discr);
        mQuestionDescr[5] = getString(R.string.fod_quest_6_discr);

        mAvaiableLife = 5;
        mTurnCount = 0;
        mMyScore = 0;
        mBarabanScore = 0;


        mAvaiableLifePrefix = getString(R.string.fod_life_count_prefix);
        mBarabanPrefix = getString(R.string.fod_baraban_score_prefix);
        mTotalScorePrefix = getString(R.string.fod_total_score_prefix);

        setLifeNumberView();
        setScores();

    }

    private void makeTurn() {
        if (mAvaiableLife <= 0) {
            Toast.makeText(this, getString(R.string.fod_game_over), Toast.LENGTH_SHORT).show();
            startGameOver(GameCode.lifeIsOut);
            finish();
            return;
        }
        setLifeNumberView();
        setScores();
        mGameTableView.setText(setGameTablo());
        Toast.makeText(this, getString(R.string.fod_make_turn), Toast.LENGTH_SHORT).show();
    }

    private void reviewLetter(char pInputLetter) {
        byte openLiterIndicator = 0;
        byte openWordInicator = 0;

        for (int i = 0; i < mQuestWord.length; i++) {
            if (pInputLetter == mQuestWord[i]) {
                mOpendLetter[i] = true;
                openLiterIndicator++;
            }
        }

        switch (openLiterIndicator) {
            case 0:
                Toast.makeText(this, getString(R.string.fod_target_miss), Toast.LENGTH_SHORT).show();
                mAvaiableLife--;
                break;
            case 1:
                Toast.makeText(this, getString(R.string.fod_one_target_hit), Toast.LENGTH_SHORT).show();
                mMyScore = mMyScore + mBarabanScore;
                mBarabanScore = 0;
                break;
            default:
                Toast.makeText(this, getString(R.string.fod_many_target_hit), Toast.LENGTH_SHORT).show();
                mMyScore = mMyScore + mBarabanScore;
                mBarabanScore = 0;
                break;

        }

        for (int i = 0; i < mOpendLetter.length; i++) {
            if (mOpendLetter[i] == true) {
                openWordInicator++;
            }
        }

        if (openWordInicator == mQuestWord.length) {
            Toast.makeText(this, getString(R.string.fod_game_win), Toast.LENGTH_SHORT).show();
            isQuestionBookIsEnd();
        }
        makeTurn();
    }

    private void createQuest(byte pTour) {
        mQuestWord = mQuestionBook[pTour].toCharArray();
        mOpendLetter = new boolean[mQuestWord.length];
        mWordDescription.setText(mQuestionDescr[pTour]);

        mGameTableView.setText(setGameTablo());
    }

    private void startGameOver(GameCode pCode) {
        Intent i = new Intent(this, SplashActivity.class);
        startActivity(i);
        finish();
    }

    private void setLifeNumberView() {
        mLifeCountView.setText(mAvaiableLifePrefix + " " + String.valueOf(mAvaiableLife));
    }

    private void setScores() {

        mScore.setText(mBarabanPrefix + " " + String.valueOf(mBarabanScore));
        mTotalScore.setText(mTotalScorePrefix + " " + String.valueOf(mMyScore));
    }

    private String setGameTablo() {
        char[] mStringTablo = new char[mOpendLetter.length];
        for (int i = 0; i < mOpendLetter.length; i++) {
            if (mOpendLetter[i] == false) {
                mStringTablo[i] = '*';
            } else {
                mStringTablo[i] = mQuestWord[i];
            }
        }
        String returnTablo = String.copyValueOf(mStringTablo);
        return returnTablo;
    }

    private void makeNextTurn() {
        createQuest(mTurnCount);
        makeTurn();
    }

    private void roundBaraban() {
        mBarabanScore = mScorePool[mRandom.nextInt(5)];

        if (mBarabanScore == 0) {
            mMyScore = 0;
            mAvaiableLife--;
            Toast.makeText(this, getString(R.string.fod_false_turn), Toast.LENGTH_SHORT).show();
            makeTurn();
            return;
        }

        switchButtonStatus(false);
        setScores();
        makeTurn();
    }

    private void switchButtonStatus(boolean isBarabanTour) {
        if (isBarabanTour) {
            mMakeBaraban.setEnabled(true);
            mInputTextField.setEnabled(false);
            mConfimButton.setEnabled(false);
            mOpenWord.setEnabled(false);
        } else {
            mMakeBaraban.setEnabled(false);
            mInputTextField.setEnabled(true);
            mConfimButton.setEnabled(true);
            mOpenWord.setEnabled(true);
        }
    }

    private void reviewFullWord() {
        if (mFullWord.equals(mQuestionBook[mTurnCount])) {
            mMyScore = mMyScore + mBarabanScore;
            mTurnCount++;
            isQuestionBookIsEnd();
        } else {
            mAvaiableLife = 0;
            makeTurn();
        }
    }

    private void isQuestionBookIsEnd() {   //TODO Дз задание 1
        if (mTurnCount >= mQuestionBook.length) {
            startGameOver(GameCode.wordIsOpend);
        } else {
            makeNextTurn();
        }
    }

}

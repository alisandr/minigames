package com.andrey.minigames.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.andrey.minigames.R;

public class SplashActivity extends Activity {

    View.OnClickListener fodButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.field_of_dream_button:
                    startFieldOfDream();
                    break;
                case R.id.find_next_number_button:
                    startFindNextNumber();
                    break;
            }

        }
    };
    private Button mStartFieldOfDreamGame;
    private Button mStartFindNextNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initView();
    }

    private void initView() {
        mStartFieldOfDreamGame = (Button) findViewById(R.id.field_of_dream_button);
        mStartFieldOfDreamGame.setOnClickListener(fodButtonListener);

        mStartFindNextNumber = (Button) findViewById(R.id.find_next_number_button);
        mStartFindNextNumber.setOnClickListener(fodButtonListener);
    }

    private void startFieldOfDream() {
        Intent intent = new Intent(this, FieldOfDreamsActivity.class);
        startActivity(intent);
    }

    private void startFindNextNumber() {
        Intent intent = new Intent(this, FindNextNumberActivity.class);
        startActivity(intent);
    }

}

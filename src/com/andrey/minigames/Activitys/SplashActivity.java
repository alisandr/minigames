package com.andrey.minigames.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.andrey.minigames.R;

public class SplashActivity extends Activity {

    private Button mStartFieldOfDreamGame;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initView();
    }

    View.OnClickListener fodButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startFieldOfDream();
        }
    };

    private void initView(){
        mStartFieldOfDreamGame = (Button) findViewById(R.id.field_of_dream_button);
        mStartFieldOfDreamGame.setOnClickListener(fodButtonListener);
    }

    private void startFieldOfDream(){
        Intent intent = new Intent(this, FieldOfDreamsActivity.class);
        startActivity(intent);
    }
    private void startFindNextNumber(){

    }

}

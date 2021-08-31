package com.gameso.blackandred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EndGameActivity extends AppCompatActivity {

    private Button buttonRestart;
    private Button buttonQuitGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        buttonRestart = findViewById(R.id.buttonRestart);
        buttonQuitGame = findViewById(R.id.buttonQuit);

        Button[] buttons = new Button[]{buttonRestart, buttonQuitGame};
        for(Button b: buttons){
            b.setOnClickListener(onClickListener);
        }

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int selectedItem =  Integer.parseInt(view.getTag().toString());
            switch(selectedItem) {
                case 5:
                    Intent intentStartGame = new Intent(EndGameActivity.this, GameActivity.class);
                    startActivity(intentStartGame);
                    finish();
                    break;
                case 6:
                    finishAffinity();
                    break;
            }
        }
    };

}
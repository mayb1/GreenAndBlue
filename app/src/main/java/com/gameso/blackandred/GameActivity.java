package com.gameso.blackandred;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    private ImageView imageViewRandom;
    private Button buttonBet25, buttonBet50, buttonBet100;
    private Button buttonEndGame;
    private Button buttonBackToMenu;
    private TextView textViewPoints;
    private TextView textViewBet;
    private final int DEFAULT_POINTS = 1000;
    private final static int MINIMAL_BET = 25;
    private ImageView[] balls;
    private int gameBet = MINIMAL_BET;
    private int points = DEFAULT_POINTS;
    private ImageView imageViewGreen;
    private ImageView imageViewBlue;
    private boolean gameRunning = false;
    private String selectedItemName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        buttonEndGame = findViewById(R.id.buttonEndGame);
        buttonBackToMenu = findViewById(R.id.buttonBackToMenu);
        imageViewRandom = findViewById(R.id.imageViewRandom);
        buttonBet25 = findViewById(R.id.buttonBet25);
        buttonBet50 = findViewById(R.id.buttonBet50);
        buttonBet100 = findViewById(R.id.buttonBet100);
        Button[] betButtons = new Button[]{buttonBet25, buttonBet50,buttonBet100};
        textViewPoints = findViewById(R.id.textViewPoints);
        textViewBet = findViewById(R.id.textViewBet);
        imageViewGreen = findViewById(R.id.imageViewGreen);
        imageViewBlue = findViewById(R.id.imageViewBlue);

        balls = new ImageView[]{imageViewGreen, imageViewBlue};

        textViewPoints.setText(String.format(Locale.getDefault(), "Your points : %d", DEFAULT_POINTS));
        textViewBet.setText(String.format(Locale.getDefault(), "Your bet is : %d", gameBet));

        for (ImageView balls : balls){
            balls.setOnClickListener(onClickListener);
            String imageName = getResources().getResourceEntryName(balls.getId());
            balls.setTag(imageName);
        }

        for(Button betButton: betButtons){
            betButton.setOnClickListener(view -> {
                gameBet = Integer.parseInt(view.getTag().toString());
                Toast.makeText(getApplicationContext(), "Bet set to " + gameBet, Toast.LENGTH_SHORT).show();
                textViewBet.setText(String.format(Locale.getDefault(), "Your current bet is : %d", gameBet));
            });

        }

        buttonBackToMenu.setOnClickListener(view -> {
            Intent menuIntent = new Intent(GameActivity.this, MainActivity.class);
            startActivity(menuIntent);
            finish();
        });

        buttonEndGame.setOnClickListener(view -> {
            finish();
        });

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!gameRunning) {
                selectedItemName = view.getTag().toString();
                startAnimation();
            } else {
                Toast.makeText(getApplicationContext(), "Just wait, you already start!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void changePoints(int value){
        points += value;
        textViewPoints.setText(String.format(Locale.getDefault(), "Your points : %d", points));
        if(points < 0){
            Intent intent = new Intent(this, EndGameActivity.class);
            startActivity(intent);
            finish();
        }
    }


    public void startAnimation(){
        if(!gameRunning) {
            gameRunning = true;
            CountDownTimer countDownTimer = new CountDownTimer(3000, 50) {
                @Override
                public void onTick(long l) {
                    int random = (int) (Math.random() * balls.length);
                    imageViewRandom.setImageDrawable(balls[random].getDrawable());
                    imageViewRandom.setTag(balls[random].getTag().toString());
                }

                @Override
                public void onFinish() {
                    gameRunning = false;
                    int value;
                    if (imageViewRandom.getTag().equals(selectedItemName)) {
                        Toast.makeText(GameActivity.this, "You win!", Toast.LENGTH_SHORT).show();
                        value = gameBet;
                    } else {
                        Toast.makeText(GameActivity.this, "You loose :(", Toast.LENGTH_SHORT).show();
                        value = -gameBet;
                    }
                    changePoints(value);
                }
            }.start();
        }
    }
}
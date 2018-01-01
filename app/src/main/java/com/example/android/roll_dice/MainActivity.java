package com.example.android.roll_dice;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int userTotalScore;
    private int userTurnScore;
    private int computerTotalScore;
    private int computerTurnScore;
    private TextView turnScoreText;
    private TextView computerTotalScoreText;
    private TextView userTotalScoreText;
    private ImageView diceImage;
    private Button rollButton;
    private Button holdButton;
    private Button resetButton;
    private Random random = new Random();
    private boolean rolledOne = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userTotalScoreText = (TextView) findViewById(R.id.userTotalScoreText);
        computerTotalScoreText = (TextView) findViewById(R.id.computerTotalScoreText);
        turnScoreText = (TextView) findViewById(R.id.turnScoreText);

        diceImage = (ImageView) findViewById(R.id.diceImage);

        rollButton = (Button) findViewById(R.id.rollButton);
        holdButton = (Button) findViewById(R.id.holdButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        rollButton.setOnClickListener(this);
        holdButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();

        // roll
        if (i == R.id.rollButton) {
            int rolledNumber = random.nextInt(6) + 1;
            setDiceImage(diceImage, rolledNumber);

            if (rolledNumber == 1) {
                userTurnScore = 0;
                Toast.makeText(this, "You rolled 1",
                        Toast.LENGTH_SHORT).show();

                turnScoreText.setText("Your Turn Score: 0");
                rolledOne = false;
                computerTurn();
            }
            else {
                userTurnScore += rolledNumber;
                turnScoreText.setText("Your Turn Score: " + Integer.toString(userTurnScore));
            }
        }
        // reset
        else if (i == R.id.resetButton) {
            reset();
        }
        // hold
        else if (i == R.id.holdButton) {
            userTotalScore += userTurnScore;
            userTotalScoreText.setText("Your Score: " + Integer.toString(userTotalScore));
            userTurnScore = 0;
            turnScoreText.setText("Your Turn Score: 0");
            if(checkWinner()) {
            }
            else {
                computerTurn();
            }
            rolledOne = false;
        }
    }
    public void reset() {
        userTotalScore = 0;
        userTurnScore = 0;
        computerTotalScore = 0;
        computerTurnScore = 0;

        userTotalScoreText.setText("Your Score: 0");
        turnScoreText.setText("Your Turn Score: 0");
        computerTotalScoreText.setText("Computer Score: 0");

        setDiceImage(diceImage, 1);
    }
    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {

            rollButton.setEnabled(false);
            holdButton.setEnabled(false);
            if (computerTurnScore < 20 && !rolledOne)
            {
                int rolledNumber = random.nextInt(6) + 1;
                setDiceImage(diceImage, rolledNumber);

                if (rolledNumber == 1) {
                    computerTurnScore = 0;
                    Toast.makeText(getApplicationContext(), "Computer rolled 1",
                            Toast.LENGTH_SHORT).show();
                    turnScoreText.setText("Computer Turn Score: 0");
                    rolledOne = true;
                    //timerHandler.removeCallbacks(timerRunnable);
                }
                else {
                    computerTurnScore += rolledNumber;
                    turnScoreText.setText("Computer Turn Score: " + Integer.toString(computerTurnScore));
                }
                timerHandler.postDelayed(this, 500);
            }
            else if (computerTurnScore >= 20 || rolledOne) {
                computerTotalScore += computerTurnScore;
                computerTotalScoreText.setText("Computer Score: " + Integer.toString(computerTotalScore));
                computerTurnScore = 0;
                turnScoreText.setText("Computer Turn Score: 0");

                if(!rolledOne) {
                    Toast.makeText(getApplicationContext(), "Computer Holds",
                            Toast.LENGTH_SHORT).show();
                    checkWinner();
                }
                rollButton.setEnabled(true);
                holdButton.setEnabled(true);
                //timerHandler.removeCallbacks(timerRunnable);
            }
        }
    };
    public boolean checkWinner() {
        if (userTotalScore >= 100) {
            Toast.makeText(getApplicationContext(), "You Win..!!",
                    Toast.LENGTH_SHORT).show();
            reset();
            return true;
        } else if(computerTotalScore >= 100) {
            Toast.makeText(getApplicationContext(), "Computer Wins..!!",
                    Toast.LENGTH_SHORT).show();
            reset();
            return true;
        }
        return false;
    }

    public void computerTurn() {
        //runnable is run after 3000 ms
        timerHandler.postDelayed(timerRunnable, 3000);
    }

    public void setDiceImage(ImageView mImage, int rolledNumber) {
        switch (rolledNumber) {
            case 1:
                mImage.setImageResource(R.drawable.dice1);
                break;
            case 2:
                mImage.setImageResource(R.drawable.dice2);
                break;
            case 3:
                mImage.setImageResource(R.drawable.dice3);
                break;
            case 4:
                mImage.setImageResource(R.drawable.dice4);
                break;
            case 5:
                mImage.setImageResource(R.drawable.dice5);
                break;
            case 6:
                mImage.setImageResource(R.drawable.dice6);
                break;
            default:
                mImage.setImageResource(R.drawable.dice1);
                break;
        }
    }
}

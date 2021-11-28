package com.umg.pomodoroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 1500000;
    private static final long START_TIME_IN_MILLIS_BREAK = 300000;

    private TextView mTextViewCountDown, mTextViewBreak;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mTextViewCountDown = findViewById(R.id.text_view_countdown);
       mTextViewBreak = findViewById(R.id.text_home);

       mButtonStartPause = findViewById(R.id.button_start_pause);
       mButtonReset = findViewById(R.id.button_reset);

        mTextViewBreak.setText("Trabaja");

       mButtonStartPause.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                if(mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
           }
       });

       mButtonReset.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                resetTimer();
           }
       });
       updateCountDownText();
    }
    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("START");
                mButtonStartPause.setVisibility(View.VISIBLE);

                timerBreak();
                mButtonReset.setVisibility(View.VISIBLE);

            }
        }.start();
        mTimerRunning = true;
        mButtonStartPause.setText("PAUSE");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("START");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        mTextViewBreak.setText("Trabaja");
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void timerBreak(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS_BREAK;

        mTextViewBreak.setText("Descansa");
        updateCountDownText();
        startTimer();

    }

    private void updateCountDownText(){
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }
}
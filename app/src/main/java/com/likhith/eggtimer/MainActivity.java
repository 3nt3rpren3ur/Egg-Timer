package com.likhith.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.DecimalFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timersetter;
    TextView runner;
    Boolean runnerIsActive=false;
    Button btn;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timersetter=(SeekBar) findViewById(R.id.timersetter);
        runner=(TextView)findViewById(R.id.runner);
        btn=(Button)findViewById(R.id.btn);

        timersetter.setProgress(1800);
        timersetter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updatetimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void starttimer(View view)
    {
        if(runnerIsActive==false) {
            runnerIsActive = true;
            timersetter.setEnabled(false);
            btn.setText("Reset");
            countDownTimer= new CountDownTimer(timersetter.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updatetimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    runner.setText("00:00:00");
                    final ImageView imageView = findViewById(R.id.egg);
                    imageView.setImageResource(R.drawable.hatched);
                    MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.kuckoo);
                    player.start();
                    timersetter.setEnabled(true);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageResource(R.drawable.egg);
                            runner.setText("00:30:00");
                            timersetter.setProgress(1800);
                            runnerIsActive=false;
                        }
                    },2000);
                    btn.setText("Go!");
                }
            }.start();
        }
        else
        {
            runner.setText("00:30:00");
            timersetter.setProgress(1800);
            timersetter.setEnabled(true);
            countDownTimer.cancel();
            btn.setText("Go!");
            runnerIsActive=false;
        }
    }
    public void updatetimer(int secondsleft)
    {
        int hrs=(int) secondsleft/3600;
        int min=(int) ((secondsleft-(hrs*3600))/60);
        int sec=(int) (secondsleft%60);
        DecimalFormat format = new DecimalFormat("00");
        runner.setText(format.format(Double.valueOf(hrs))+":"+format.format(Double.valueOf(min))+":"+format.format(Double.valueOf(sec)));
    }
}

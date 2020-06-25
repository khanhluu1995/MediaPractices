package com.example.mediapractices;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.silient);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//        Log.i("info", "song duration: " + mediaPlayer.getDuration());
        setVolumeSeekBar();
        setTimeTracker();
    }

    private void setTimeTracker() {
        final SeekBar timeTracker = findViewById(R.id.timeTrackSeekBar);
        timeTracker.setMax(mediaPlayer.getDuration());
        timeTracker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeTracker.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 500);
    }

    private void setVolumeSeekBar() {
        SeekBar volumeControl = findViewById(R.id.volumeSeekBar);
        //This will set the max volume that user can modify
        volumeControl.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        //This will set the current volume
        volumeControl.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void play(View view) {
        mediaPlayer.start();
    }

    public void pause(View view){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            Toast.makeText(this,"Nothing currently playing",Toast.LENGTH_LONG).show();
    }
}
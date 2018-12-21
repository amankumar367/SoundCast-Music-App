package com.dev.aman.soundcast;

import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PlayMusicActivity extends AppCompatActivity {

    private TextView mTitle, mCreated;
    private ImageView next, pause_play, previous, back, thumbnailPlay;
    private String link, thumbnail;
    private MediaPlayer mediaPlayer;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        init();
        setContentToScreen();
        onClick();
        playMusic();

        Picasso.get()
                .load(thumbnail)
                .into(thumbnailPlay);
        mSeekBar.setMax(mediaPlayer.getDuration());

        final Handler mSeekbarUpdateHandler = new Handler();
        Runnable mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                mSeekbarUpdateHandler.postDelayed(this, 50);
            }
        };

        mSeekBar.postDelayed(mUpdateSeekbar, 0);
        mSeekBar.removeCallbacks(mUpdateSeekbar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int process, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(process);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                stopMusic();
            }
        });
        pause_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopMusic();
            }
        });
    }

    private void playMusic() {
        pause_play.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_outline_white_24dp));
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(link);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    private void stopMusic() {
        pause_play.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle_black_24dp));
        mediaPlayer.stop();
    }

    private void init() {
        mTitle = findViewById(R.id.title_Play);
        mCreated = findViewById(R.id.created_Play);
        next = findViewById(R.id.next);
        pause_play = findViewById(R.id.pause);
        previous = findViewById(R.id.previous);
        back = findViewById(R.id.back);
        thumbnailPlay = findViewById(R.id.thumbnailPlay);
        mSeekBar = findViewById(R.id.seekBar);
    }

    private void setContentToScreen() {
        String title = getIntent().getStringExtra("TITLE");
        String created = getIntent().getStringExtra("CREATEDAT");
        link = getIntent().getStringExtra("LINK");
        thumbnail = getIntent().getStringExtra("THUMBNAIL");
        mTitle.setText(title);
        mCreated.setText(created);
    }
}

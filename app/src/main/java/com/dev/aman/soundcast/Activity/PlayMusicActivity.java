package com.dev.aman.soundcast.Activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dev.aman.soundcast.R;
import com.dev.aman.soundcast.model.Results;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class PlayMusicActivity extends AppCompatActivity {

    private TextView mTitle, mCreated;
    private ImageView next, pause_play, previous, back, thumbnailPlay;
    private String link, thumbnail;
    private MediaPlayer mediaPlayer;
    private SeekBar mSeekBar;
    private Handler mSeekbarUpdateHandler;
    private Runnable mUpdateSeekbar;
    private Boolean isPlaying = true;
    private List<Results> lists;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        init();
        getDataFromSongList();
        setContentToScreen(position);
        onClick();
        startMusic(link);
    }

    private void getDataFromSongList() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        lists = bundle.getParcelableArrayList("mylist");
        position = getIntent().getIntExtra("POSITION", 0);
    }

    private void startMusic(String link) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(link);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        setUpSeekBar();
    }

    private void setUpSeekBar() {
        mSeekBar.setMax(mediaPlayer.getDuration());
        mSeekbarUpdateHandler = new Handler();
        mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                mSeekbarUpdateHandler.postDelayed(this, 50);
            }
        };
        mSeekBar.postDelayed(mUpdateSeekbar, 0);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void playMusic() {
        pause_play.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_outline_white_24dp));
        mediaPlayer.start();
        mSeekBar.postDelayed(mUpdateSeekbar, 0);
    }

    private void pauseMusic() {
        pause_play.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle_black_24dp));
        mediaPlayer.pause();
        mSeekBar.removeCallbacks(mUpdateSeekbar);
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

    private void setContentToScreen(int position) {
        String title = lists.get(position).getTitle();
        String created = lists.get(position).getCreatedAt();
        link = lists.get(position).getLink();
        thumbnail = lists.get(position).getThumbnail();
        mTitle.setText(title);
        mCreated.setText(created);
        Picasso.get()
                .load(thumbnail)
                .into(thumbnailPlay);
    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        pause_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlaying){
                    pauseMusic();
                    isPlaying = false;
                }else {
                    playMusic();
                    isPlaying = true;
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                if(position == (lists.size() - 1)){
                    setContentToScreen(position);
                }else {
                    position = position + 1;
                    setContentToScreen(position);
                }
                startMusic(link);
                pause_play.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_outline_white_24dp));
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                if(position == 0){
                    setContentToScreen(position);
                }else {
                    position = position - 1;
                    setContentToScreen(position);
                }
                startMusic(link);
                pause_play.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_outline_white_24dp));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}

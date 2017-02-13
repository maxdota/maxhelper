package com.maxdota.maxhelper.example;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maxdota.maxhelper.base.BaseActivity;
import com.maxdota.maxhelper.model.MaxAudioData;

/**
 * Created by Nguyen Hong Ngoc on 2/6/2017.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    private static final int REQUEST_CODE_PICK_AUDIO = 101;

    private TextView mAudioText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAudioText = (TextView) findViewById(R.id.audio_text);
        findViewById(R.id.select_audio).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.select_audio:
                mMaxHelper.showAudioPicker(this, REQUEST_CODE_PICK_AUDIO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PICK_AUDIO) {
            String audioPath = mMaxHelper.getAudioPath(this, data.getData());

            mMaxHelper.playAudio(audioPath, this, null);

            MaxAudioData audioData = mMaxHelper.retrieveAudioData(audioPath);
            if (audioData == null) {
                mAudioText.setText(R.string.reading_audio_error);
            } else {
                mAudioText.setText(getString(R.string.audio_text_format,
                        audioData.getTitle(), audioData.getArtist(), audioData.getDuration()));
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mAudioText.setText(R.string.audio_finish);
    }
}

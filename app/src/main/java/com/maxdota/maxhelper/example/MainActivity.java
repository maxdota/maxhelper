package com.maxdota.maxhelper.example;

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxdota.maxhelper.base.BaseActivity;
import com.maxdota.maxhelper.model.MaxAudioData;
import com.maxdota.maxhelper.transformation.RoundCornerTransformation;
import com.maxdota.maxhelper.widget.CellWidget;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Nguyen Hong Ngoc on 2/6/2017.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    private enum AppStatus {
        PICK_AUDIO, SELECT_PHOTO
    }

    private static final int REQUEST_CODE_PICK_AUDIO = 101;
    private static final int REQUEST_CODE_SELECT_PHOTO = 102;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 201;

    private static final String SAMPLE_IMAGE_PHOTO = "http://graph.facebook.com/100000117780569/picture?type=large";

    private TextView mAudioText;
    private ImageView mPickedImage;

    private AppStatus mAppStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAudioText = (TextView) findViewById(R.id.audio_text);
        mPickedImage = (ImageView) findViewById(R.id.picked_image);
        findViewById(R.id.select_audio).setOnClickListener(this);
        findViewById(R.id.select_photo).setOnClickListener(this);
        ((CellWidget) findViewById(R.id.profile_cell)).setCellImage(SAMPLE_IMAGE_PHOTO);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.select_audio) {
            if (mMaxHelper.checkAndRequestPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                "The app needs to read your files for audio data",
                PERMISSION_REQUEST_READ_EXTERNAL_STORAGE)) {
                mMaxHelper.showAudioPicker(this, REQUEST_CODE_PICK_AUDIO);
            } else {
                mAppStatus = AppStatus.PICK_AUDIO;
            }
        } else if (id == R.id.select_photo) {
            if (mMaxHelper.checkAndRequestPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                "The app needs to read your files for audio data",
                PERMISSION_REQUEST_READ_EXTERNAL_STORAGE)) {
                mMaxHelper.dispatchSelectPhotoIntent(this, REQUEST_CODE_SELECT_PHOTO);
            } else {
                mAppStatus = AppStatus.SELECT_PHOTO;
            }
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
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SELECT_PHOTO) {
            String imagePath = mMaxHelper.getImagePathFromSelectUri(this, data.getData());
            Picasso.get()
                .load(new File(imagePath))
                .fit().centerCrop()
                .transform(new RoundCornerTransformation(getResources()
                    .getDimensionPixelSize(R.dimen.corner_radius)))
                .into(mPickedImage);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mMaxHelper.onRequestPermissionsResult(requestCode, grantResults, PERMISSION_REQUEST_READ_EXTERNAL_STORAGE)) {
            if (mAppStatus == AppStatus.PICK_AUDIO) {
                mMaxHelper.showAudioPicker(this, REQUEST_CODE_PICK_AUDIO);
            } else if (mAppStatus == AppStatus.SELECT_PHOTO) {
                mMaxHelper.dispatchSelectPhotoIntent(this, REQUEST_CODE_SELECT_PHOTO);
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mAudioText.setText(R.string.audio_finish);
    }
}

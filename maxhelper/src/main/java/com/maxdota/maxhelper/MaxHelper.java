package com.maxdota.maxhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Nguyen Hong Ngoc on 2/6/2017.
 */

public class MaxHelper {
    private static MediaPlayer sMediaPlayer;
    private static MediaPlayer.OnPreparedListener sOnPreparedListener;

    public static void showAudioPicker(Activity activity, int requestCode) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, requestCode);
    }

    public static String getAudioPath(Context context, Uri uri) {
        String[] queries = {MediaStore.Audio.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver()
                    .query(Uri.parse("content://media" + uri.getPath()), queries, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public static MaxAudioData retrieveAudioData(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        try {
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(path);
            String title = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String artist = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String duration = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            return new MaxAudioData(title, artist, duration, path);
        } catch (IllegalArgumentException e) {
            Log.d("MaxHelper", "Audio error " + e.getMessage());
            return null;
        }
    }

    public static void playAudio(String audioPath, MediaPlayer.OnCompletionListener onCompletionListener) {
        if (sMediaPlayer != null) {
            sMediaPlayer.release();
        }
        sMediaPlayer = new MediaPlayer();

        if (sOnPreparedListener == null) {
            sOnPreparedListener = new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mp == sMediaPlayer) {
                        mp.start();
                    }
                }
            };
        }
        try {
            sMediaPlayer.setDataSource(audioPath);
            sMediaPlayer.setOnPreparedListener(sOnPreparedListener);
            sMediaPlayer.setOnCompletionListener(onCompletionListener);
            sMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.d("MaxHelper", "Audio error " + e.getMessage());
        }
    }
}

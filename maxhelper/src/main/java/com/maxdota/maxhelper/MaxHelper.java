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
    public enum LogLevel {
        D, E, I, V
    }

    private static final String LOG_TAG = "MaxHelper";

    public static LogLevel sLogLevel;
    private static MediaPlayer sMediaPlayer;
    private static MediaPlayer.OnPreparedListener sOnPreparedListener;
    private static int sAudioStartTime;

    public static Class<? extends MaxAudioData> sAudioClass;

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

    public static MaxAudioData retrieveAudioDataWithChildClass(String path) {
        if (TextUtils.isEmpty(path) || sAudioClass == null) {
            return null;
        }
        try {
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(path);
            String title = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String artist = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String duration = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            MaxAudioData audioData = sAudioClass.newInstance();
            audioData.setTitle(title);
            audioData.setArtist(artist);
            audioData.setDuration(duration);
            return audioData;
        } catch (IllegalArgumentException e) {
            log("Audio error " + e.getMessage());
        } catch (InstantiationException e) {
            log("Instance error " + e.getMessage());
        } catch (IllegalAccessException e) {
            log("Instance error " + e.getMessage());
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
            log("Audio error " + e.getMessage());
            return null;
        }
    }

    public static MediaPlayer playAudio(String audioPath,
                                        MediaPlayer.OnCompletionListener onCompletionListener,
                                        MediaPlayer.OnErrorListener onErrorListener) {
        return playAudio(audioPath, 0, onCompletionListener, onErrorListener);
    }

    public static MediaPlayer playAudio(String audioPath, final int startTime,
                                        MediaPlayer.OnCompletionListener onCompletionListener,
                                        MediaPlayer.OnErrorListener onErrorListener) {
        sAudioStartTime = startTime;
        if (sMediaPlayer != null) {
            sMediaPlayer.release();
        }
        sMediaPlayer = new MediaPlayer();

        if (sOnPreparedListener == null) {
            sOnPreparedListener = new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mp == sMediaPlayer) {
                        mp.seekTo(sAudioStartTime);
                        mp.start();
                    }
                }
            };
        }
        try {
            sMediaPlayer.setDataSource(audioPath);
            sMediaPlayer.setOnPreparedListener(sOnPreparedListener);
            sMediaPlayer.setOnCompletionListener(onCompletionListener);
            sMediaPlayer.setOnErrorListener(onErrorListener);
            sMediaPlayer.prepareAsync();
            return sMediaPlayer;
        } catch (IOException e) {
            log("Audio error " + e.getMessage());
            return null;
        }
    }

    public static MediaPlayer prepareAudio(String audioPath, MediaPlayer.OnPreparedListener onPreparedListener,
                                           MediaPlayer.OnCompletionListener onCompletionListener,
                                           MediaPlayer.OnErrorListener onErrorListener) {
        if (sMediaPlayer != null) {
            sMediaPlayer.release();
        }
        sMediaPlayer = new MediaPlayer();

        try {
            sMediaPlayer.setDataSource(audioPath);
            sMediaPlayer.setOnPreparedListener(onPreparedListener);
            sMediaPlayer.setOnCompletionListener(onCompletionListener);
            sMediaPlayer.setOnErrorListener(onErrorListener);
            sMediaPlayer.prepareAsync();
            return sMediaPlayer;
        } catch (IOException e) {
            log("Audio error " + e.getMessage());
            return null;
        }
    }

    public static void log(String message) {
        if (sLogLevel != null && message != null) {
            if (sLogLevel == LogLevel.D) {
                Log.d(LOG_TAG, message);
            } else if (sLogLevel == LogLevel.E) {
                Log.e(LOG_TAG, message);
            } else if (sLogLevel == LogLevel.I) {
                Log.i(LOG_TAG, message);
            } else if (sLogLevel == LogLevel.V) {
                Log.v(LOG_TAG, message);
            }
        }
    }
}

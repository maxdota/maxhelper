package com.maxdota.maxhelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.maxdota.maxhelper.base.BaseActivity;
import com.maxdota.maxhelper.model.MaxAudioData;

import java.io.IOException;

/**
 * Created by Nguyen Hong Ngoc on 2/6/2017.
 */

public class MaxHelper {
    public enum LogLevel {
        D, E, I, V
    }

    private final String LOG_TAG = "MaxHelper";

    private LogLevel mLogLevel;
    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnPreparedListener mOnPreparedListener;
    private int mAudioStartTime;

    public Class<? extends MaxAudioData> mAudioClass;

    private Handler mHandler;

    public MaxHelper(BaseActivity activity) {
        mHandler = activity.getHandler();
    }

    public void setLogLevel(LogLevel logLevel) {
        mLogLevel = logLevel;
    }

    public void showAudioPicker(Activity activity, int requestCode) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, requestCode);
    }

    public String getAudioPath(Context context, Uri uri) {
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

    public MaxAudioData retrieveAudioDataWithChildClass(String path) {
        if (TextUtils.isEmpty(path) || mAudioClass == null) {
            return null;
        }
        try {
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(path);
            String title = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String artist = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String duration = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            MaxAudioData audioData = mAudioClass.newInstance();
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

    public MaxAudioData retrieveAudioData(String path) {
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

    public MediaPlayer playAudio(String audioPath,
                                 MediaPlayer.OnCompletionListener onCompletionListener,
                                 MediaPlayer.OnErrorListener onErrorListener) {
        return playAudio(audioPath, 0, onCompletionListener, onErrorListener);
    }

    public MediaPlayer playAudio(String audioPath, final int startTime,
                                 MediaPlayer.OnCompletionListener onCompletionListener,
                                 MediaPlayer.OnErrorListener onErrorListener) {
        mAudioStartTime = startTime;
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        mMediaPlayer = new MediaPlayer();

        if (mOnPreparedListener == null) {
            mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (mp == mMediaPlayer) {
                        mp.seekTo(mAudioStartTime);
                        mp.start();
                    }
                }
            };
        }
        try {
            mMediaPlayer.setDataSource(audioPath);
            mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
            mMediaPlayer.setOnCompletionListener(onCompletionListener);
            mMediaPlayer.setOnErrorListener(onErrorListener);
            mMediaPlayer.prepareAsync();
            return mMediaPlayer;
        } catch (IOException e) {
            log("Audio error " + e.getMessage());
            return null;
        }
    }

    public MediaPlayer prepareAudio(String audioPath, MediaPlayer.OnPreparedListener onPreparedListener,
                                    MediaPlayer.OnCompletionListener onCompletionListener,
                                    MediaPlayer.OnErrorListener onErrorListener) {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        mMediaPlayer = new MediaPlayer();

        try {
            mMediaPlayer.setDataSource(audioPath);
            mMediaPlayer.setOnPreparedListener(onPreparedListener);
            mMediaPlayer.setOnCompletionListener(onCompletionListener);
            mMediaPlayer.setOnErrorListener(onErrorListener);
            mMediaPlayer.prepareAsync();
            return mMediaPlayer;
        } catch (IOException e) {
            log("Audio error " + e.getMessage());
            return null;
        }
    }

    public void log(String message) {
        if (mLogLevel != null && message != null) {
            if (mLogLevel == LogLevel.D) {
                Log.d(LOG_TAG, message);
            } else if (mLogLevel == LogLevel.E) {
                Log.e(LOG_TAG, message);
            } else if (mLogLevel == LogLevel.I) {
                Log.i(LOG_TAG, message);
            } else if (mLogLevel == LogLevel.V) {
                Log.v(LOG_TAG, message);
            }
        }
    }

    // return true if app already gains the permission
    public boolean checkAndRequestPermission(final Activity activity, final String permission,
                                             String explanation, final int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            } else {
                showYesNoDialog(activity, explanation, activity.getString(R.string.ok),
                        activity.getString(R.string.cancel), new YesNoDialog() {
                            @Override
                            public void onSelected(boolean isYes) {
                                if (isYes) {
                                    ActivityCompat.requestPermissions(activity,
                                            new String[]{permission}, requestCode);
                                }
                            }
                        });
            }
            return false;
        }
        return true;
    }

    // return true if permission is granted
    public boolean onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults,
                                              int appRequestCode) {
        if (requestCode == appRequestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    public void showYesNoDialog(final Context context,
                                final String action, final String yes, final String no,
                                final YesNoDialog listener) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onSelected(which == Dialog.BUTTON_POSITIVE);
                        }
                    }
                };

                Dialog dialog = builder.setPositiveButton(yes, onClickListener)
                        .setNegativeButton(no, onClickListener)
                        .setMessage(action).setCancelable(false).create();

                dialog.show();
            }
        });
    }

    public void showExplanationDialog(final Activity activity, final String message) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                Dialog dialog = builder.setPositiveButton(R.string.ok, null)
                        .setMessage(message).create();
                dialog.show();
            }
        });
    }

    public void showConfirmationDialog(final Activity activity, final String action,
                                       final Confirmable confirmable) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String question = activity.getResources().getString(R.string.do_you_want_to_s, action);
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                Dialog dialog = builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmable.onConfirmed();
                    }
                }).setNegativeButton(R.string.no, null).setMessage(question).setCancelable(false).create();
                dialog.show();
            }
        });
    }

    public void showInputDialog(final Activity activity,
                                final String title, final String action, final String textHolder,
                                final InputDialog listener) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                View view = activity.getLayoutInflater().inflate(R.layout.dialog_content_input, null);
                final EditText input = (EditText) view.findViewById(R.id.input);
                input.setText(textHolder);

                DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            if (which == Dialog.BUTTON_POSITIVE) {
                                listener.onInput(input.getText().toString());
                            } else {
                                listener.onCancel();
                            }
                        }
                    }
                };

                Dialog dialog = builder.setPositiveButton(R.string.ok, onClickListener)
                        .setNegativeButton(R.string.cancel, onClickListener)
                        .setTitle(title).setView(view)
                        .setMessage(action).setCancelable(false).create();

                dialog.show();
            }
        });
    }

    public void showErrorDialog(final Activity activity, final String message) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                Dialog dialog = builder.setPositiveButton(R.string.ok, null)
                        .setTitle(R.string.error)
                        .setMessage(message).setCancelable(false).create();
                dialog.show();
            }
        });
    }

    public interface YesNoDialog {
        void onSelected(boolean isYes);
    }

    public interface Confirmable {
        void onConfirmed();
    }

    public interface InputDialog {
        void onInput(String text);

        void onCancel();
    }

    public void hideKeyboard(Context context, View view) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

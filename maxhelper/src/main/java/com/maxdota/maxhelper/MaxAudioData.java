package com.maxdota.maxhelper;

import java.util.Locale;

/**
 * Created by Nguyen Hong Ngoc on 2/6/2017.
 */
public class MaxAudioData {
    private static final long TIME_PER_SECOND = 1000;
    private static final long TIME_PER_MINUTE = TIME_PER_SECOND * 60;
    private static final long TIME_PER_HOUR = TIME_PER_MINUTE * 60;

    private String mTitle;
    private String mArtist;
    private String mDuration;
    private String mPath;

    private long mDurationTime;

    public MaxAudioData(String title, String artist, String duration, String path) {
        mTitle = title;
        mArtist = artist;
        mPath = path;

        mDurationTime = Long.parseLong(duration);
        int hour = (int) (mDurationTime / TIME_PER_HOUR);
        int minute = (int) (mDurationTime % TIME_PER_HOUR / TIME_PER_MINUTE);
        int second = (int) (mDurationTime % TIME_PER_MINUTE / TIME_PER_SECOND);
        if (hour > 0) {
            mDuration = String.format(Locale.US, "%d:%02d:%02d", hour, minute, second);
        } else {
            mDuration = String.format(Locale.US, "%d:%02d", minute, second);

        }
    }

    public String getTitle() {
        return mTitle;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getDuration() {
        return mDuration;
    }

    public String getPath() {
        return mPath;
    }
}

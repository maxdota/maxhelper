package com.maxdota.maxhelper;

import java.util.Locale;

/**
 * Created by Nguyen Hong Ngoc on 2/6/2017.
 */
public class MaxAudioData {
    private static final long TIME_PER_SECOND = 1000;
    private static final long TIME_PER_MINUTE = TIME_PER_SECOND * 60;
    private static final long TIME_PER_HOUR = TIME_PER_MINUTE * 60;

    protected String mTitle;
    protected String mArtist;
    protected String mDuration;
    protected String mPath;

    protected long mDurationTime;

    public MaxAudioData() {
    }

    public MaxAudioData(String title, String artist, String duration, String path) {
        mTitle = title;
        mArtist = artist;
        mPath = path;

        mDurationTime = Long.parseLong(duration);
        convertDurationTime(mDurationTime);
    }

    public void convertDurationTime(long durationTime) {
        mDurationTime = durationTime;
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

    public long getDurationTime() {
        return mDurationTime;
    }

    public String getDurationTimeString() {
        return String.valueOf(mDurationTime);
    }

    public String getPath() {
        return mPath;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public void setPath(String path) {
        mPath = path;
    }
}

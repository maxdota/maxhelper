package com.maxdota.maxhelper.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxdota.maxhelper.MaxHelper;

/**
 * Created by Nguyen Hong Ngoc on 2/13/2017.
 */

public abstract class BaseFragment<T extends BaseActivity> extends Fragment {
    protected String mName;
    protected T mActivity;
    protected Handler mHandler;
    protected MaxHelper mMaxHelper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (T) context;
        mHandler = mActivity.getHandler();
        mMaxHelper = mActivity.getMaxHelper();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
        mHandler = null;
        mMaxHelper = null;
    }

    public String getName() {
        return mName;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        init(view);
        return view;
    }

    public abstract int getLayoutResId();

    public abstract void init(View view);
}

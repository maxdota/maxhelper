package com.maxdota.maxhelper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maxdota.maxhelper.R;
import com.maxdota.maxhelper.base.BaseActivity;
import com.maxdota.maxhelper.transformation.CircleTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by Nguyen Hong Ngoc on 12/05/2017.
 */

public class CellWidget extends RelativeLayout implements View.OnClickListener {
    private BaseActivity mActivity;

    private SwitchCompat mCellSwitch;
    private TextView mCellTitle;
    private TextView mCellDescription;
    private ImageView mCellImage;

    public CellWidget(Context context) {
        super(context);
        init();
    }

    public CellWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        processAttrs(context, attrs);
    }

    public CellWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        processAttrs(context, attrs);
    }

    private void init() {
        mActivity = (BaseActivity) getContext();

        inflate(mActivity, R.layout.widget_cell, this);
        mCellSwitch = (SwitchCompat) findViewById(R.id.cell_switch);
        mCellTitle = (TextView) findViewById(R.id.cell_title);
        mCellDescription = (TextView) findViewById(R.id.cell_description);
        mCellImage = (ImageView) findViewById(R.id.cell_image);
    }

    private void processAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CellWidget, 0, 0);

        try {
            String cellTitle = a.getString(R.styleable.CellWidget_cellTitle);
            String cellDescription = a.getString(R.styleable.CellWidget_cellDescription);
            boolean cellHasSwitch = a.getBoolean(R.styleable.CellWidget_cellHasSwitch, false);
            Drawable defaultImage = a.getDrawable(R.styleable.CellWidget_cellDefaultImage);
            int titleColor = a.getColor(R.styleable.CellWidget_cellTitleColor, -1);
            int descriptionColor = a.getColor(R.styleable.CellWidget_cellDescriptionColor, -1);

            mCellTitle.setText(cellTitle);
            mCellSwitch.setVisibility(cellHasSwitch ? VISIBLE : GONE);
            if (titleColor != -1) {
                mCellTitle.setTextColor(titleColor);
            }
            if (descriptionColor != -1) {
                mCellDescription.setTextColor(descriptionColor);
            }
            if (cellHasSwitch) {
                setOnClickListener(this);
            }
            if (defaultImage != null) {
                mCellImage.setVisibility(VISIBLE);
                mCellImage.setImageDrawable(defaultImage);
            }
            setCellDescription(cellDescription);
        } finally {
            a.recycle();
        }
    }

    public void setCellData(String title, String description, String imageUrl) {
        setCellTitle(title);
        setCellImage(imageUrl);
        setCellDescription(description);
    }

    public void setCellTitle(String title) {
        if (title != null) {
            mCellTitle.setText(title);
        }
    }

    public void setCellImage(final String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.get().load(imageUrl)
                    .transform(new CircleTransformation())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(mCellImage, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(imageUrl)
                                    .transform(new CircleTransformation())
                                    .into(mCellImage);
                        }
                    });
        }
    }

    public void setCellDescription(String description) {
        if (TextUtils.isEmpty(description)) {
            mCellDescription.setVisibility(GONE);
        } else {
            mCellDescription.setVisibility(VISIBLE);
            mCellDescription.setText(description);
        }
    }

    public void setCellSwitch(boolean isOn) {
        mCellSwitch.setChecked(isOn);
    }

    public void setOnCheckChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        mCellSwitch.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    @Override
    public void onClick(View v) {
        mCellSwitch.toggle();
    }
}

package com.farukcankaya.expandablefilter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by farukcankaya on 12/05/2017.
 */

public class DefaultFilterItem extends LinearLayout {
    private Context mContext;
    private String mEmoji;
    private String mLabel;
    private Config mConfig;

    private FilterItem mEmojiFilterItem;
    private FilterItem mLabelFilterItem;

    public DefaultFilterItem(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public DefaultFilterItem(Context context, Config config) {
        super(context);
        this.mConfig = config;
        initializeViews(context, null);
    }

    public DefaultFilterItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, R.attr.expandableFilterDefaultFilterStyle);
        initializeViews(context, attrs);
    }

    public DefaultFilterItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context, attrs);
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableFilterDefaultFilter);
        mEmoji = a.getString(R.styleable.ExpandableFilterDefaultFilter_emoji);
        mLabel = a.getString(R.styleable.ExpandableFilterDefaultFilter_label);
        a.recycle();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ViewCompat.setBackground(this, ViewUtil.getBackground(mConfig.getActiveBackgroundColor(), mConfig.getRadius(), ViewUtil.BACKGROUND_TYPE_LEFT));

        setLayoutParams(layoutParams);
        setClickable(true);
        setOrientation(HORIZONTAL);

        mEmojiFilterItem = new FilterItem(mContext, mConfig);
        mEmojiFilterItem.setClickable(false);
        mEmojiFilterItem.setDuplicateParentStateEnabled(true);
        mEmojiFilterItem.setText(mEmoji == null ? "" : mEmoji);
        mEmojiFilterItem.setFont(mConfig.getEmojiFont());
        mEmojiFilterItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, mConfig.getEmojiFontSize());
        int paddingLeft = mEmojiFilterItem.getPaddingLeft();
        int paddingTop = mEmojiFilterItem.getPaddingTop();
        int paddingRight = mEmojiFilterItem.getPaddingRight();
        int paddingBottom = mEmojiFilterItem.getPaddingBottom();

        if (ViewUtil.isRtl()) {
            paddingLeft = 0;
        } else {
            paddingRight = 0;
        }

        mEmojiFilterItem.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        ViewCompat.setBackground(mEmojiFilterItem, null);
        addView(mEmojiFilterItem);

        mLabelFilterItem = new FilterItem(mContext, mConfig);
        mLabelFilterItem.setClickable(false);
        mLabelFilterItem.setDuplicateParentStateEnabled(true);
        mLabelFilterItem.setText(mLabel == null ? "" : mLabel);
        mLabelFilterItem.setFont(mConfig.getLabelFont());
        mLabelFilterItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, mConfig.getLabelFontSize());
        ViewCompat.setBackground(mLabelFilterItem, null);
        addView(mLabelFilterItem);
    }

    public void setEmoji(String emoji) {
        this.mEmoji = emoji;
        mEmojiFilterItem.setText(emoji);
    }

    public void setLabel(String label) {
        this.mLabel = label;
        mLabelFilterItem.setText(label);
    }

    public FilterItem getEmojiFilterItem() {
        return mEmojiFilterItem;
    }

    public FilterItem getLabelFilterItem() {
        return mLabelFilterItem;
    }

    public void setConfig(Config config) {
        this.mConfig = config;
    }
}

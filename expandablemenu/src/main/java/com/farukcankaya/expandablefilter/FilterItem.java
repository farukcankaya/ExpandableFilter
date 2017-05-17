package com.farukcankaya.expandablefilter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by farukcankaya on 12/05/2017.
 */

public class FilterItem extends AppCompatTextView {
    private int mPadding;
    private int mPaddingLeft;
    private int mPaddingTop;
    private int mPaddingRight;
    private int mPaddingBottom;
    private int mMarginLeft;
    private int mMarginRight;
    private int mMarginTop;
    private int mMarginBottom;
    private ColorStateList mTextColorStateList;
    private float mTextSize;
    private Drawable mBackground;
    private Config mConfig;

    private int mDefaultPadding;
    private int mDefaultMarginLeft;
    private int mDefaultMarginRight;
    private int mDefaultMarginTop;
    private int mDefaultMarginBottom;
    private ColorStateList mDefaultTextColorStateList;
    private int mDefaultTextSize;
    private Drawable mDefaultBackground;

    public FilterItem(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public FilterItem(Context context, Config config) {
        super(context);
        this.mConfig = config;
        initializeViews(context, null);
    }

    public FilterItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, R.attr.expandableFilterTextViewStyle);
        initializeViews(context, attrs);
    }

    public FilterItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context, attrs);
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        mDefaultPadding = (int) getResources().getDimension(R.dimen.ef_filter_item_default_padding);
        mDefaultMarginLeft = (int) getResources().getDimension(R.dimen.ef_filter_item_default_margin_left);
        mDefaultMarginRight = (int) getResources().getDimension(R.dimen.ef_filter_item_default_margin_right);
        mDefaultMarginTop = (int) getResources().getDimension(R.dimen.ef_filter_item_default_margin_top);
        mDefaultMarginBottom = (int) getResources().getDimension(R.dimen.ef_filter_item_default_margin_bottom);
        mDefaultBackground = ViewUtil.getBackground(mConfig.getActiveBackgroundColor(), mConfig.getRadius(), ViewUtil.BACKGROUND_TYPE_CENTER);
        mDefaultTextColorStateList = ContextCompat.getColorStateList(context, R.color.ef_filter_item_default_color);
        mDefaultTextSize = getResources().getDimensionPixelSize(R.dimen.ef_filter_item_default_text_size);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableFilterTextView);
        mPadding = (int) a.getDimension(R.styleable.ExpandableFilterTextView_android_padding, mDefaultPadding);
        mPaddingLeft = (int) a.getDimension(R.styleable.ExpandableFilterTextView_android_paddingLeft, mPadding);
        mPaddingTop = (int) a.getDimension(R.styleable.ExpandableFilterTextView_android_paddingTop, mPadding);
        mPaddingRight = (int) a.getDimension(R.styleable.ExpandableFilterTextView_android_paddingRight, mPadding);
        mPaddingBottom = (int) a.getDimension(R.styleable.ExpandableFilterTextView_android_paddingBottom, mPadding);
        mMarginLeft = (int) a.getDimension(R.styleable.ExpandableFilterTextView_android_layout_marginLeft, mDefaultMarginLeft);
        mMarginRight = (int) a.getDimension(R.styleable.ExpandableFilterTextView_android_layout_marginRight, mDefaultMarginRight);
        mMarginTop = (int) a.getDimension(R.styleable.ExpandableFilterTextView_android_layout_marginTop, mDefaultMarginTop);
        mMarginBottom = (int) a.getDimension(R.styleable.ExpandableFilterTextView_android_layout_marginBottom, mDefaultMarginBottom);
        mTextColorStateList = a.getColorStateList(R.styleable.ExpandableFilterTextView_android_textColor);
        ColorStateList appTextColor = a.getColorStateList(R.styleable.ExpandableFilterTextView_textColor);
        mTextColorStateList = appTextColor == null ? mTextColorStateList : appTextColor;
        mTextColorStateList = mTextColorStateList == null ? mDefaultTextColorStateList : mTextColorStateList;
        mTextSize = a.getDimensionPixelSize(R.styleable.ExpandableFilterTextView_android_textSize, mDefaultTextSize);
        mBackground = a.getDrawable(R.styleable.ExpandableFilterTextView_android_background);
        Drawable appBackground = a.getDrawable(R.styleable.ExpandableFilterTextView_background);
        mBackground = appBackground == null ? mBackground : appBackground;
        mBackground = mBackground == null ? mDefaultBackground : mBackground;
        a.recycle();

        buildConfig();

        /**
         * TODO: there should be better way to do this:
         */
        if (ViewUtil.isRtl()) {
            int tempMargin = mMarginLeft;
            mMarginLeft = mMarginRight;
            mMarginRight = tempMargin;

            int tempPadding = mPaddingLeft;
            mPaddingLeft = mPaddingRight;
            mPaddingRight = tempPadding;
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(mMarginLeft, mMarginTop, mMarginRight, mMarginBottom);
        setLayoutParams(layoutParams);
        setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
        setGravity(Gravity.CENTER_VERTICAL);
        setTextColor(mTextColorStateList);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        setClickable(true);
        ViewCompat.setBackground(this, mBackground);
    }

    private void buildConfig() {
        // optional values
        if (mConfig != null) {
            /**
             * see {@link Config.ConfigBuilder#setItemTextColor(ColorStateList)}
             */
            if (mConfig.getItemTextColor() != null) {
                mTextColorStateList = mConfig.getItemTextColor();
            }

            /**
             * see {@link Config.ConfigBuilder#setDefaultTextColor(int)} and {@link Config.ConfigBuilder#setActiveTextColor(int)}
             */
            if (mConfig.getDefaultTextColor() != 0 || mConfig.getActiveTextColor() != 0) {
                int[] selectedState = {android.R.attr.state_selected};
                int defaultColor = mTextColorStateList.getDefaultColor();
                defaultColor = mConfig.getDefaultTextColor() != 0 ? mConfig.getDefaultTextColor() : defaultColor;

                int activeColor = mTextColorStateList.getColorForState(selectedState, defaultColor);
                activeColor = mConfig.getActiveTextColor() != 0 ? mConfig.getActiveTextColor() : activeColor;

                int[][] states = new int[][]{
                        new int[]{android.R.attr.state_selected},
                        new int[]{android.R.attr.state_hovered},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{} // default color
                };

                int[] colors = new int[]{
                        activeColor,
                        activeColor,
                        activeColor,
                        defaultColor
                };
                mTextColorStateList = new ColorStateList(states, colors);
            }

            mMarginLeft = mConfig.getItemDividerMargin();
            mPadding = mConfig.getItemPadding();
            mPaddingLeft = mConfig.getItemPaddingLeft();
            mPaddingTop = mConfig.getItemPaddingTop();
            mPaddingRight = mConfig.getItemPaddingRight();
            mPaddingBottom = mConfig.getItemPaddingBottom();

            if (mConfig.getDefaultBackgroundColor() != 0 || mConfig.getActiveBackgroundColor() != 0) {
                //...
            }
        }
    }

    public int getPadding() {
        return mPadding;
    }

    public int getmPaddingLeft() {
        return mPaddingLeft;
    }

    public int getmPaddingTop() {
        return mPaddingTop;
    }

    public int getmPaddingRight() {
        return mPaddingRight;
    }

    public int getmPaddingBottom() {
        return mPaddingBottom;
    }

    /**
     * Sets the padding. The view may add on the space required to display
     * the scrollbars, depending on the style and visibility of the scrollbars.
     * So the values returned from {@link #getPadding}
     *
     * @param padding the 4-corners padding in pixels
     * @attr ref android.R.styleable#ExpandableFilterTextView_android_padding
     */
    public void setPadding(int padding) {
        this.mPadding = padding;
        requestLayout();
        invalidate();
    }

    /**
     * Sets the margins, in pixels. A call to {@link android.view.View#requestLayout()} needs
     * to be done so that the new margins are taken into account. Left and right margins may be
     * overriden by {@link android.view.View#requestLayout()} depending on layout direction.
     * Margin values should be positive.
     *
     * @param left   the left margin size
     * @param top    the top margin size
     * @param right  the right margin size
     * @param bottom the bottom margin size
     * @attr ref android.R.styleable#ExpandableFilterTextView_android_layout_marginLeft
     * @attr ref android.R.styleable#ExpandableFilterTextView_android_layout_marginTop
     * @attr ref android.R.styleable#ExpandableFilterTextView_android_layout_marginRight
     * @attr ref android.R.styleable#ExpandableFilterTextView_android_layout_marginBottom
     */
    public void setMargin(int left, int top, int right, int bottom) {
        this.mMarginLeft = left;
        this.mMarginRight = right;
        this.mMarginTop = top;
        this.mMarginBottom = bottom;
        requestLayout();
        invalidate();
    }

    public int getMarginLeft() {
        return mMarginLeft;
    }

    /**
     * @param marginLeft the left margin pixels
     * @attr ref android.R.styleable#ExpandableFilterTextView_android_marginLeft
     */
    public void setMarginLeft(int marginLeft) {
        this.mMarginLeft = marginLeft;
        requestLayout();
        invalidate();
    }

    public int getMarginRight() {
        return mMarginRight;
    }

    /**
     * @param marginRight the right margin pixels
     * @attr ref android.R.styleable#ExpandableFilterTextView_android_marginRight
     */
    public void setMarginRight(int marginRight) {
        this.mMarginRight = marginRight;
        requestLayout();
        invalidate();
    }

    public int getMarginTop() {
        return mMarginTop;
    }

    /**
     * @param marginTop the top margin pixels
     * @attr ref android.R.styleable#ExpandableFilterTextView_android_marginTop
     */
    public void setMarginTop(int marginTop) {
        this.mMarginTop = marginTop;
        requestLayout();
        invalidate();
    }

    public int getMarginBottom() {
        return mMarginBottom;
    }

    /**
     * @param marginBottom the bottom margin pixels
     * @attr ref android.R.styleable#ExpandableFilterTextView_android_marginBottom
     */
    public void setMarginBottom(int marginBottom) {
        this.mMarginBottom = marginBottom;
        requestLayout();
        invalidate();
    }

    public ColorStateList getmTextColorStateList() {
        return mTextColorStateList;
    }

    public void setTextColorStateList(ColorStateList textColorStateList) {
        this.mTextColorStateList = textColorStateList;
        requestLayout();
        invalidate();
    }

    public float getTextSize() {
        return mTextSize;
    }

    /**
     * @param textSize the text size in pixels
     */
    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
        requestLayout();
        invalidate();
    }

    public void setConfig(Config config) {
        this.mConfig = config;
        requestLayout();
        invalidate();
    }
}

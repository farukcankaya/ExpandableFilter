package com.farukcankaya.expandablefilter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farukcankaya on 12/05/2017.
 * Inspired from https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/widget/LinearLayout.java
 */

public class ExpandableFilter extends LinearLayout {
    private static final int IDLE = 0;
    private static final int EXPANDING = 1;
    private static final int COLLAPSING = 2;
    public static final String KEY_SUPER_STATE = "super_state";
    public static final String KEY_EXPANSION = "expansion";
    public static final String KEY_ITEMS = "items";
    public static final String KEY_ITEM_STATE = "item_states";
    private static final int DEFAULT_DURATION = 300;
    private int duration = DEFAULT_DURATION;
    private float expansion;
    private float defaultExpansion;
    private int state = IDLE;
    private int selectedChildCount;
    private Context mContext;

    private String mEmoji;
    private String mLabel;
    private ArrayList<String> mItems = new ArrayList<>(0);
    private ArrayList<Integer> mSelectedItems = new ArrayList<>(0);
    private int mMaxSelectableItemCount;

    private Drawable mBackground;
    private int mItemPadding;
    private int mItemPaddingLeft;
    private int mItemPaddingTop;
    private int mItemPaddingRight;
    private int mItemPaddingBottom;
    private int mItemDividerMargin;
    private String mEmojiFont = null;
    private String mLabelFont = null;
    private int mEmojiFontSize;
    private int mLabelFontSize;
    private ColorStateList mItemTextColor;
    private int mRadius;
    private int mDefaultTextColor;
    private int mActiveTextColor;
    private int mDefaultBackgroundColor;
    private int mActiveBackgroundColor;

    private Drawable mDefaultBackground;
    private int mDefaultPadding;
    private int mDefaultDividerMargin;
    private int mDefaultRadius;
    private int mDefaultEmojiFontSize;
    private int mDefaultLabelFontSize;
    private int mDefaultDefaultTextColor;
    private int mDefaultActiveTextColor;
    private int mDefaultDefaultBackgroundColor;
    private int mDefaultActiveBackgroundColor;
    private int mDefaultMaxSelectableItemCount;

    private Config mConfig;

    private Interpolator interpolator = new FastOutSlowInInterpolator();
    private ValueAnimator animator;

    private OnExpansionUpdateListener listener;

    public ExpandableFilter(Context context) {
        this(context, null);
    }

    public ExpandableFilter(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.expandableFilterExpandableFilterStyle);
    }

    public ExpandableFilter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initializeViews(attrs);
    }

    private void initializeViews(AttributeSet attrs) {
        mDefaultPadding = (int) getResources().getDimension(R.dimen.ef_filter_item_default_padding);
        mDefaultEmojiFontSize = getResources().getDimensionPixelSize(R.dimen.ef_filter_item_default_text_size);
        mDefaultLabelFontSize = getResources().getDimensionPixelSize(R.dimen.ef_filter_item_default_text_size);
        mDefaultPadding = (int) getResources().getDimension(R.dimen.ef_filter_item_default_padding);
        mDefaultDividerMargin = (int) getResources().getDimension(R.dimen.ef_filter_item_divider_margin);
        mDefaultRadius = (int) getResources().getDimension(R.dimen.ef_button_radius);
        mDefaultDefaultTextColor = ContextCompat.getColor(mContext, R.color.ef_filter_item_default_text_color);
        mDefaultActiveTextColor = ContextCompat.getColor(mContext, R.color.ef_filter_item_default_text_color_active);
        mDefaultDefaultBackgroundColor = ContextCompat.getColor(mContext, R.color.ef_background_color);
        mDefaultActiveBackgroundColor = ContextCompat.getColor(mContext, R.color.ef_background_color_active);
        mDefaultMaxSelectableItemCount = getResources().getInteger(R.integer.ef_filter_max_selectable_items);

        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.ExpandableFilter);
        mEmojiFont = a.getString(R.styleable.ExpandableFilter_emojiFont);
        mLabelFont = a.getString(R.styleable.ExpandableFilter_labelFont);
        mItemPadding = (int) a.getDimension(R.styleable.ExpandableFilter_itemPadding, mDefaultPadding);
        mItemPaddingLeft = (int) a.getDimension(R.styleable.ExpandableFilter_itemPaddingLeft, mItemPadding);
        mItemPaddingTop = (int) a.getDimension(R.styleable.ExpandableFilter_itemPaddingTop, mItemPadding);
        mItemPaddingRight = (int) a.getDimension(R.styleable.ExpandableFilter_itemPaddingRight, mItemPadding);
        mItemPaddingBottom = (int) a.getDimension(R.styleable.ExpandableFilter_itemPaddingBottom, mItemPadding);
        mItemDividerMargin = (int) a.getDimension(R.styleable.ExpandableFilter_itemDividerMargin, mDefaultDividerMargin);
        mItemTextColor = a.getColorStateList(R.styleable.ExpandableFilter_itemTextColor);
        mEmojiFontSize = a.getDimensionPixelSize(R.styleable.ExpandableFilter_emojiFontSize, mDefaultEmojiFontSize);
        mLabelFontSize = a.getDimensionPixelSize(R.styleable.ExpandableFilter_labelFontSize, mDefaultLabelFontSize);
        mRadius = (int) a.getDimension(R.styleable.ExpandableFilter_radius, mDefaultRadius);
        mDefaultTextColor = a.getColor(R.styleable.ExpandableFilter_defaultTextColor, 0);
        mActiveTextColor = a.getColor(R.styleable.ExpandableFilter_activeTextColor, 0);
        mDefaultBackgroundColor = a.getColor(R.styleable.ExpandableFilter_defaultBackgroundColor, mDefaultDefaultBackgroundColor);
        mActiveBackgroundColor = a.getColor(R.styleable.ExpandableFilter_activeBackgroundColor, mDefaultActiveBackgroundColor);
        mDefaultBackground = ViewUtil.getDefaultBackground(mDefaultBackgroundColor, mActiveBackgroundColor, mDefaultRadius);
        mMaxSelectableItemCount = a.getInteger(R.styleable.ExpandableFilter_maxSelectableItemCount, mDefaultMaxSelectableItemCount);

        mBackground = a.getDrawable(R.styleable.ExpandableFilter_android_background);
        Drawable appBackground = a.getDrawable(R.styleable.ExpandableFilter_background);
        mBackground = appBackground == null ? mBackground : appBackground;
        mBackground = mBackground == null ? mDefaultBackground : mBackground;
        CharSequence[] items = a.getTextArray(R.styleable.ExpandableFilter_items);
        a.recycle();

        a = mContext.obtainStyledAttributes(attrs, R.styleable.ExpandableFilterDefaultFilter);
        mEmoji = a.getString(R.styleable.ExpandableFilterDefaultFilter_emoji);
        mLabel = a.getString(R.styleable.ExpandableFilterDefaultFilter_label);
        a.recycle();

        mConfig = new Config.ConfigBuilder()
                .setItemPadding(mItemPadding)
                .setItemPaddingLeft(mItemPaddingLeft)
                .setItemPaddingTop(mItemPaddingTop)
                .setItemPaddingRight(mItemPaddingRight)
                .setItemPaddingBottom(mItemPaddingBottom)
                .setItemDividerMargin(mItemDividerMargin)
                .setItemTextColor(mItemTextColor)
                .setRadius(mRadius)
                .setEmojiFontSize(mEmojiFontSize)
                .setLabelFontSize(mLabelFontSize)
                .setDefaultTextColor(mDefaultTextColor)
                .setActiveTextColor(mActiveTextColor)
                .setDefaultBackgroundColor(mDefaultBackgroundColor)
                .setActiveBackgroundColor(mActiveBackgroundColor)
                .setEmojiFont(mEmojiFont)
                .setLabelFont(mLabelFont)
                .build();

        if (items != null) {
            setItemStringList(getItemStringList(items));
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putFloat(KEY_EXPANSION, expansion);
        if (isSaveEnabled()) {
            bundle.putStringArrayList(KEY_ITEMS, mItems);
            bundle.putIntegerArrayList(KEY_ITEM_STATE, mSelectedItems);
        }
        bundle.putParcelable(KEY_SUPER_STATE, superState);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        expansion = bundle.getFloat(KEY_EXPANSION);
        ArrayList<String> items = bundle.getStringArrayList(KEY_ITEMS);
        if (items != null) {
            mItems = items;
        }
        ArrayList<Integer> states = bundle.getIntegerArrayList(KEY_ITEM_STATE);
        if (states != null) {
            mSelectedItems = states;
        }
        Parcelable superState = bundle.getParcelable(KEY_SUPER_STATE);

        super.onRestoreInstanceState(superState);
        requestLayout();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setClickable(true);
        ViewCompat.setBackground(this, mBackground);

        if (getChildCount() == 0 || !(getChildAt(0) instanceof DefaultFilterItem)) {
            addDefaultFilterItem();
        }

        if (mItems.size() > 0) {
            addFilterItems();
        }
    }

    private void addDefaultFilterItem() {
        DefaultFilterItem defaultFilterItem = new DefaultFilterItem(mContext, mConfig);
        defaultFilterItem.setEmoji(mEmoji);
        defaultFilterItem.setLabel(mLabel);
        addView(defaultFilterItem, 0);
    }

    private void addFilterItems() {
        int itemCount = mItems.size();
        for (int i = 0; i < itemCount; i++) {
            FilterItem filterItem = new FilterItem(mContext, mConfig);
            filterItem.setId(i);
            filterItem.setText(mItems.get(i));
            filterItem.setFont(mConfig.getEmojiFont());
            filterItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, mConfig.getEmojiFontSize());
            if (i == (itemCount - 1)) {
                Drawable drawable = ViewUtil.getBackground(mConfig.getActiveBackgroundColor(),
                        mConfig.getRadius(), ViewUtil.BACKGROUND_TYPE_RIGHT);
                ViewCompat.setBackground(filterItem, drawable);
            }
            filterItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer itemIndex = v.getId();
                    boolean isSelected = mSelectedItems.contains(itemIndex);
                    /**
                     * remove oldest selected item
                     */
                    if (!isSelected && mMaxSelectableItemCount > 0 && getSelectedItemCount() >= mMaxSelectableItemCount) {
                        Integer unSelectedItemIndex = mSelectedItems.remove(0);
                        getChildAt(unSelectedItemIndex.intValue() + 1).setSelected(false);
                    }
                    if (isSelected) {
                        mSelectedItems.remove(itemIndex);
                    } else {
                        mSelectedItems.add(itemIndex);
                    }

                    v.setSelected(!isSelected);
                }
            });
            addView(filterItem);
        }
    }

    public List<String> getItems() {
        return mItems;
    }

    private int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    private List<String> getItemStringList(CharSequence[] items) {
        List<String> stringList = new ArrayList<>(items.length);
        for (CharSequence item : items) {
            stringList.add(item.toString());
        }
        return stringList;
    }

    private void setItemStringList(List<String> items) {
        this.mItems.clear();
        this.mItems.addAll(items);
        this.mSelectedItems.clear();
    }

    public void setItems(List<String> items) {
        setItemStringList(items);
        removeViews(1, getChildCount() - 1);
        addFilterItems();
        requestLayout();
    }

    public String getEmoji() {
        return mEmoji;
    }

    public void setEmoji(String emoji) {
        this.mEmoji = emoji;
        ((DefaultFilterItem) getChildAt(0)).setEmoji(emoji);
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        ((DefaultFilterItem) getChildAt(0)).setLabel(label);
        this.mLabel = label;
    }

    public String getEmojiFont() {
        return mEmojiFont;
    }

    public void setEmojiFont(String emojiFont) {
        this.mEmojiFont = emojiFont;
        mConfig.setEmojiFont(emojiFont);
        DefaultFilterItem defaultFilterItem = (DefaultFilterItem) getChildAt(0);
        defaultFilterItem.getEmojiFilterItem().setFont(emojiFont);
        for (int i = 1; i < getChildCount(); i++) {
            FilterItem filterItem = (FilterItem) getChildAt(i);
            filterItem.setFont(emojiFont);
        }
    }

    public String getLabelFont() {
        return mLabelFont;
    }

    public void setLabelFont(String labelFont) {
        this.mLabelFont = labelFont;
        mConfig.setLabelFont(labelFont);
        DefaultFilterItem defaultFilterItem = (DefaultFilterItem) getChildAt(0);
        defaultFilterItem.getLabelFilterItem().setFont(labelFont);
    }

    public int getEmojiFontSize() {
        return mEmojiFontSize;
    }

    public void setEmojiFontSize(int emojiFontSize) {
        this.mEmojiFontSize = emojiFontSize;
        mConfig.setEmojiFontSize(emojiFontSize);
        DefaultFilterItem defaultFilterItem = (DefaultFilterItem) getChildAt(0);
        defaultFilterItem.getEmojiFilterItem().setTextSize(emojiFontSize);
        for (int i = 1; i < getChildCount(); i++) {
            FilterItem filterItem = (FilterItem) getChildAt(i);
            filterItem.setTextSize(emojiFontSize);
        }
    }

    public int getLabelFontSize() {
        return mLabelFontSize;
    }

    public void setLabelFontSize(int labelFontSize) {
        this.mLabelFontSize = labelFontSize;
        mConfig.setLabelFontSize(labelFontSize);
        DefaultFilterItem defaultFilterItem = (DefaultFilterItem) getChildAt(0);
        defaultFilterItem.getLabelFilterItem().setTextSize(labelFontSize);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int mTotalLength = 0;
        int mDefaultLength = 0;

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        final boolean isExactly = widthMode == MeasureSpec.EXACTLY;

        // See how wide everyone is. Also remember max height.
        selectedChildCount = 0;
        View firstChild = null;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            final View child = getChildAt(i);
            if (i > 0) {
                child.setSelected(mSelectedItems.contains(Integer.valueOf(i - 1)));
            }
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            final boolean useExcessSpace = lp.width == 0 && lp.weight > 0;
            if (widthMode == MeasureSpec.EXACTLY && useExcessSpace) {
                // Optimization: don't bother measuring children who are only
                // laid out using excess space. These views will get measured
                // later if we have space to distribute.
                if (isExactly) {
                    mTotalLength += lp.leftMargin + lp.rightMargin;
                    mDefaultLength += i != 0 ? lp.leftMargin + lp.rightMargin : 0;
                } else {
                    final int totalLength = mTotalLength;
                    mTotalLength = Math.max(totalLength, totalLength +
                            lp.leftMargin + lp.rightMargin);
                    mDefaultLength += i != 0 ? Math.max(totalLength, totalLength +
                            lp.leftMargin + lp.rightMargin) : 0;
                }
            } else {
                if (useExcessSpace) {
                    // The widthMode is either UNSPECIFIED or AT_MOST, and
                    // this child is only laid out using excess space. Measure
                    // using WRAP_CONTENT so that we can find out the view's
                    // optimal width. We'll restore the original width of 0
                    // after measurement.
                    lp.width = LayoutParams.WRAP_CONTENT;
                }

                final int childWidth = child.getMeasuredWidth();
                if (useExcessSpace) {
                    // Restore the original width and record how much space
                    // we've allocated to excess-only children so that we can
                    // match the behavior of EXACTLY measurement.
                    lp.width = 0;
                }

                if (isExactly) {
                    mTotalLength += childWidth + lp.leftMargin + lp.rightMargin;
                } else {
                    final int totalLength = mTotalLength;
                    mTotalLength = Math.max(totalLength, totalLength + childWidth + lp.leftMargin
                            + lp.rightMargin);
                }

                if (i != 0)
                    if (isExactly) {
                        mDefaultLength += childWidth + lp.leftMargin + lp.rightMargin;
                    } else {
                        final int totalLength = mDefaultLength;
                        mDefaultLength = Math.max(totalLength, totalLength + childWidth + lp.leftMargin
                                + lp.rightMargin);
                    }
            }

            if (i > 0 && child.isSelected()) {
                selectedChildCount++;
            }
            if (i == 0) {
                firstChild = child;
            }
        }

        mTotalLength += getPaddingLeft() + getPaddingRight();
        int size = mTotalLength;

        defaultExpansion = ((float) mTotalLength - mDefaultLength) / (mTotalLength);
        if (expansion == 0.0 && mTotalLength > 0) {
            expansion = defaultExpansion;
        }

        setVisibility(expansion == 0 && size == 0 ? GONE : VISIBLE);
        int expansionDelta = size - Math.round(size * expansion);
        setMeasuredDimension(width - expansionDelta, height);

        if (firstChild != null) {
            Drawable backgroundDrawable = ViewUtil.getBackground(mConfig.getActiveBackgroundColor(),
                    mConfig.getRadius(), ViewUtil.BACKGROUND_TYPE_LEFT);
            if (!isExpanded() || getChildCount() == 1) {
                backgroundDrawable = ViewUtil.getDefaultBackground(mConfig.getDefaultBackgroundColor(),
                        mConfig.getActiveBackgroundColor(), mConfig.getRadius());
            }
            ViewCompat.setBackground(firstChild, backgroundDrawable);
            boolean sta = isThereSelectedItem() && !isExpanded();
            firstChild.setSelected(sta);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        if (animator != null) {
            animator.cancel();
        }
        super.onConfigurationChanged(newConfig);
    }

    public boolean isExpanded() {
        return state == EXPANDING || (Math.abs(expansion - 1) < 0.0001f);
    }

    public boolean isThereSelectedItem() {
        return selectedChildCount > 0;
    }

    public void toggle() {
        toggle(true);
    }

    public void toggle(boolean animate) {
        if (isExpanded()) {
            collapse(animate);
        } else {
            expand(animate);
        }
    }

    public void expand() {
        expand(true);
    }

    public void expand(boolean animate) {
        setExpanded(true, animate);
    }

    public void collapse() {
        collapse(true);
    }

    public void collapse(boolean animate) {
        setExpanded(false, animate);
    }

    /**
     * Convenience method - same as calling setExpanded(expanded, true)
     */
    public void setExpanded(boolean expand) {
        setExpanded(expand, true);
    }

    public void setExpanded(boolean expand, boolean animate) {
        if (expand && (state == EXPANDING || expansion == 1)) {
            return;
        }

        if (!expand && (state == COLLAPSING || expansion == defaultExpansion)) {
            return;
        }

        float targetExpansion = expand ? 1 : defaultExpansion;
        if (animate) {
            animateSize(targetExpansion);
        } else {
            setExpansion(targetExpansion);
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getExpansion() {
        return expansion;
    }

    public void setExpansion(float expansion) {
        if (this.expansion == expansion) {
            return;
        }

        setVisibility(expansion == 0 ? GONE : VISIBLE);

        this.expansion = expansion;
        requestLayout();

        if (listener != null) {
            listener.onExpansionUpdate(expansion);
        }
    }

    private void animateSize(final float targetExpansion) {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }

        animator = ValueAnimator.ofFloat(expansion, targetExpansion);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setExpansion((float) valueAnimator.getAnimatedValue());
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                state = targetExpansion == 0 ? COLLAPSING : EXPANDING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                state = IDLE;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                state = IDLE;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        animator.start();
    }

    public interface OnExpansionUpdateListener {
        void onExpansionUpdate(float expansionFraction);
    }

    public void setListener(OnExpansionUpdateListener listener) {
        this.listener = listener;
    }

    public OnExpansionUpdateListener getListener() {
        return listener;
    }

    /**
     * Interpolator corresponding to {@link android.R.interpolator#fast_out_slow_in}.
     * <p>
     * Uses a lookup table for the Bezier curve from (0,0) to (1,1) with control points:
     * P0 (0, 0)
     * P1 (0.4, 0)
     * P2 (0.2, 1.0)
     * P3 (1.0, 1.0)
     */
    public static class FastOutSlowInInterpolator extends LookupTableInterpolator {

        /**
         * Lookup table values sampled with x at regular intervals between 0 and 1 for a total of
         * 201 points.
         */
        private static final float[] VALUES = new float[]{
                0.0000f, 0.0001f, 0.0002f, 0.0005f, 0.0009f, 0.0014f, 0.0020f,
                0.0027f, 0.0036f, 0.0046f, 0.0058f, 0.0071f, 0.0085f, 0.0101f,
                0.0118f, 0.0137f, 0.0158f, 0.0180f, 0.0205f, 0.0231f, 0.0259f,
                0.0289f, 0.0321f, 0.0355f, 0.0391f, 0.0430f, 0.0471f, 0.0514f,
                0.0560f, 0.0608f, 0.0660f, 0.0714f, 0.0771f, 0.0830f, 0.0893f,
                0.0959f, 0.1029f, 0.1101f, 0.1177f, 0.1257f, 0.1339f, 0.1426f,
                0.1516f, 0.1610f, 0.1707f, 0.1808f, 0.1913f, 0.2021f, 0.2133f,
                0.2248f, 0.2366f, 0.2487f, 0.2611f, 0.2738f, 0.2867f, 0.2998f,
                0.3131f, 0.3265f, 0.3400f, 0.3536f, 0.3673f, 0.3810f, 0.3946f,
                0.4082f, 0.4217f, 0.4352f, 0.4485f, 0.4616f, 0.4746f, 0.4874f,
                0.5000f, 0.5124f, 0.5246f, 0.5365f, 0.5482f, 0.5597f, 0.5710f,
                0.5820f, 0.5928f, 0.6033f, 0.6136f, 0.6237f, 0.6335f, 0.6431f,
                0.6525f, 0.6616f, 0.6706f, 0.6793f, 0.6878f, 0.6961f, 0.7043f,
                0.7122f, 0.7199f, 0.7275f, 0.7349f, 0.7421f, 0.7491f, 0.7559f,
                0.7626f, 0.7692f, 0.7756f, 0.7818f, 0.7879f, 0.7938f, 0.7996f,
                0.8053f, 0.8108f, 0.8162f, 0.8215f, 0.8266f, 0.8317f, 0.8366f,
                0.8414f, 0.8461f, 0.8507f, 0.8551f, 0.8595f, 0.8638f, 0.8679f,
                0.8720f, 0.8760f, 0.8798f, 0.8836f, 0.8873f, 0.8909f, 0.8945f,
                0.8979f, 0.9013f, 0.9046f, 0.9078f, 0.9109f, 0.9139f, 0.9169f,
                0.9198f, 0.9227f, 0.9254f, 0.9281f, 0.9307f, 0.9333f, 0.9358f,
                0.9382f, 0.9406f, 0.9429f, 0.9452f, 0.9474f, 0.9495f, 0.9516f,
                0.9536f, 0.9556f, 0.9575f, 0.9594f, 0.9612f, 0.9629f, 0.9646f,
                0.9663f, 0.9679f, 0.9695f, 0.9710f, 0.9725f, 0.9739f, 0.9753f,
                0.9766f, 0.9779f, 0.9791f, 0.9803f, 0.9815f, 0.9826f, 0.9837f,
                0.9848f, 0.9858f, 0.9867f, 0.9877f, 0.9885f, 0.9894f, 0.9902f,
                0.9910f, 0.9917f, 0.9924f, 0.9931f, 0.9937f, 0.9944f, 0.9949f,
                0.9955f, 0.9960f, 0.9964f, 0.9969f, 0.9973f, 0.9977f, 0.9980f,
                0.9984f, 0.9986f, 0.9989f, 0.9991f, 0.9993f, 0.9995f, 0.9997f,
                0.9998f, 0.9999f, 0.9999f, 1.0000f, 1.0000f
        };

        public FastOutSlowInInterpolator() {
            super(VALUES);
        }

    }

    /**
     * An {@link Interpolator} that uses a lookup table to compute an interpolation based on a
     * given input.
     */
    public static abstract class LookupTableInterpolator implements Interpolator {

        private final float[] mValues;
        private final float mStepSize;

        public LookupTableInterpolator(float[] values) {
            mValues = values;
            mStepSize = 1f / (mValues.length - 1);
        }

        @Override
        public float getInterpolation(float input) {
            if (input >= 1.0f) {
                return 1.0f;
            }
            if (input <= 0f) {
                return 0f;
            }

            // Calculate index - We use min with length - 2 to avoid IndexOutOfBoundsException when
            // we lerp (linearly interpolate) in the return statement
            int position = Math.min((int) (input * (mValues.length - 1)), mValues.length - 2);

            // Calculate values to account for small offsets as the lookup table has discrete values
            float quantized = position * mStepSize;
            float diff = input - quantized;
            float weight = diff / mStepSize;

            // Linearly interpolate between the table values
            return mValues[position] + weight * (mValues[position + 1] - mValues[position]);
        }

    }
}
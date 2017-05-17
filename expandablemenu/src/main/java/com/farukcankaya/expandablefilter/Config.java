package com.farukcankaya.expandablefilter;

import android.content.res.ColorStateList;

/**
 * Created by farukcankaya on 15/05/2017.
 */

public class Config {
    private int itemPadding;
    private int itemPaddingLeft;
    private int itemPaddingTop;
    private int itemPaddingRight;
    private int itemPaddingBottom;
    private int itemDividerMargin;
    private ColorStateList itemTextColor;
    private int radius;
    private int defaultTextColor;
    private int activeTextColor;
    private int defaultBackgroundColor;
    private int activeBackgroundColor;
    private String emojiFont;
    private String labelFont;


    private Config(ConfigBuilder configBuilder) {
        itemPadding = configBuilder.itemPadding;
        itemPaddingLeft = configBuilder.itemPaddingLeft;
        itemPaddingTop = configBuilder.itemPaddingTop;
        itemPaddingRight = configBuilder.itemPaddingRight;
        itemPaddingBottom = configBuilder.itemPaddingBottom;
        itemDividerMargin = configBuilder.itemDividerMargin;
        itemTextColor = configBuilder.itemTextColor;
        radius = configBuilder.radius;
        defaultTextColor = configBuilder.defaultTextColor;
        activeTextColor = configBuilder.activeTextColor;
        defaultBackgroundColor = configBuilder.defaultBackgroundColor;
        activeBackgroundColor = configBuilder.activeBackgroundColor;
        emojiFont = configBuilder.emojiFont;
        labelFont = configBuilder.labelFont;
    }

    public int getItemPadding() {
        return itemPadding;
    }

    public int getItemPaddingLeft() {
        return itemPaddingLeft;
    }

    public int getItemPaddingTop() {
        return itemPaddingTop;
    }

    public int getItemPaddingRight() {
        return itemPaddingRight;
    }

    public int getItemPaddingBottom() {
        return itemPaddingBottom;
    }

    public int getItemDividerMargin() {
        return itemDividerMargin;
    }

    public ColorStateList getItemTextColor() {
        return itemTextColor;
    }

    public int getRadius() {
        return radius;
    }

    public int getDefaultTextColor() {
        return defaultTextColor;
    }

    public int getActiveTextColor() {
        return activeTextColor;
    }

    public int getDefaultBackgroundColor() {
        return defaultBackgroundColor;
    }

    public int getActiveBackgroundColor() {
        return activeBackgroundColor;
    }

    public String getEmojiFont() {
        return emojiFont;
    }

    public String getLabelFont() {
        return labelFont;
    }

    public void setItemPadding(int itemPadding) {
        this.itemPadding = itemPadding;
    }

    public void setItemPaddingLeft(int itemPaddingLeft) {
        this.itemPaddingLeft = itemPaddingLeft;
    }

    public void setItemPaddingTop(int itemPaddingTop) {
        this.itemPaddingTop = itemPaddingTop;
    }

    public void setItemPaddingRight(int itemPaddingRight) {
        this.itemPaddingRight = itemPaddingRight;
    }

    public void setItemPaddingBottom(int itemPaddingBottom) {
        this.itemPaddingBottom = itemPaddingBottom;
    }

    public void setItemDividerMargin(int itemDividerMargin) {
        this.itemDividerMargin = itemDividerMargin;
    }

    public void setItemTextColor(ColorStateList itemTextColor) {
        this.itemTextColor = itemTextColor;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setDefaultTextColor(int defaultTextColor) {
        this.defaultTextColor = defaultTextColor;
    }

    public void setActiveTextColor(int activeTextColor) {
        this.activeTextColor = activeTextColor;
    }

    public void setDefaultBackgroundColor(int defaultBackgroundColor) {
        this.defaultBackgroundColor = defaultBackgroundColor;
    }

    public void setActiveBackgroundColor(int activeBackgroundColor) {
        this.activeBackgroundColor = activeBackgroundColor;
    }

    public void setEmojiFont(String emojiFont) {
        this.emojiFont = emojiFont;
    }

    public void setLabelFont(String labelFont) {
        this.labelFont = labelFont;
    }

    public static class ConfigBuilder {
        private int itemPadding;
        private int itemPaddingLeft;
        private int itemPaddingTop;
        private int itemPaddingRight;
        private int itemPaddingBottom;
        private int itemDividerMargin;
        private ColorStateList itemTextColor;
        private int radius;
        private int defaultTextColor;
        private int activeTextColor;
        private int defaultBackgroundColor;
        private int activeBackgroundColor;
        private String emojiFont = null;
        private String labelFont = null;

        /**
         * @param itemPadding in pixels
         * @return
         */
        public ConfigBuilder setItemPadding(int itemPadding) {
            this.itemPadding = itemPadding;
            return this;
        }

        /**
         * @param itemPaddingLeft in pixels
         * @return
         */
        public ConfigBuilder setItemPaddingLeft(int itemPaddingLeft) {
            this.itemPaddingLeft = itemPaddingLeft;
            return this;
        }

        /**
         * @param itemPaddingTop in pixels
         * @return
         */
        public ConfigBuilder setItemPaddingTop(int itemPaddingTop) {
            this.itemPaddingTop = itemPaddingTop;
            return this;
        }

        /**
         * @param itemPaddingRight in pixels
         * @return
         */
        public ConfigBuilder setItemPaddingRight(int itemPaddingRight) {
            this.itemPaddingRight = itemPaddingRight;
            return this;
        }

        /**
         * @param itemPaddingBottom in pixels
         * @return
         */
        public ConfigBuilder setItemPaddingBottom(int itemPaddingBottom) {
            this.itemPaddingBottom = itemPaddingBottom;
            return this;
        }

        /**
         * @param itemDividerMargin between two filter item in pixels
         * @return
         */
        public ConfigBuilder setItemDividerMargin(int itemDividerMargin) {
            this.itemDividerMargin = itemDividerMargin;
            return this;
        }

        /**
         * @param itemTextColor is {@link ColorStateList}.
         *                      You can specify different color for different state with this attribute.
         *                      Also see {@link #setDefaultTextColor(int)} and {@link #setActiveTextColor(int)}.
         *                      <p>
         *                      These attributes can be used with @param itemTextColor but they override the default and active state.
         * @return
         */
        public ConfigBuilder setItemTextColor(ColorStateList itemTextColor) {
            this.itemTextColor = itemTextColor;
            return this;
        }

        /**
         * @param radius in pixels
         * @return
         */
        public ConfigBuilder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        /**
         * @param defaultTextColor
         * @return
         */
        public ConfigBuilder setDefaultTextColor(int defaultTextColor) {
            this.defaultTextColor = defaultTextColor;
            return this;
        }

        /**
         * @param activeTextColor
         * @return
         */
        public ConfigBuilder setActiveTextColor(int activeTextColor) {
            this.activeTextColor = activeTextColor;
            return this;
        }

        /**
         * @param defaultBackgroundColor
         * @return
         */
        public ConfigBuilder setDefaultBackgroundColor(int defaultBackgroundColor) {
            this.defaultBackgroundColor = defaultBackgroundColor;
            return this;
        }

        public ConfigBuilder setActiveBackgroundColor(int activeBackgroundColor) {
            this.activeBackgroundColor = activeBackgroundColor;
            return this;
        }

        /**
         * @param emojiFont font file path of emoji. Font file should be located in app/assets/font folder.
         * @return
         */
        public ConfigBuilder setEmojiFont(String emojiFont) {
            this.emojiFont = emojiFont;
            return this;
        }

        /**
         * @param labelFont font file path of label. Font file should be located in app/assets/font folder.
         * @return
         */
        public ConfigBuilder setLabelFont(String labelFont) {
            this.labelFont = labelFont;
            return this;
        }

        public Config build() {
            return new Config(this);
        }
    }

}
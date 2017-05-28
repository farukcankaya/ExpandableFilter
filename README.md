# ExpandableFilter
A simple library that creates an expandable filter with given items. Filter items can be collapsed/expanded. Also, it supports rtl views, changing color, text size, font family, etc...

 [ ![Download](https://api.bintray.com/packages/farukcankaya/maven/ExpandableFilter/images/download.svg) ](https://bintray.com/farukcankaya/maven/ExpandableFilter/_latestVersion)

<a href="https://youtu.be/8irq_WdFumo" target="_blank"><img src="https://github.com/farukcankaya/ExpandableFilter/raw/master/art/showcase.gif" /></a>

| LTR        | RTL           |
| ------------- |:-------------:|
| <img src="https://github.com/farukcankaya/ExpandableFilter/raw/master/art/ltr.png" /> | <img src="https://github.com/farukcankaya/ExpandableFilter/raw/master/art/rtl.png" /> |

# Usage

### Add dependecy
```java
dependencies {
    ...
    compile 'com.farukcankaya:expandablefilter:1.0.1'
}
```

### Add ExpandableFilter to your layout
`emoji`, `label` and `items` attributes are mandatory.

res/values/arrays.xml
```xml
<resources>
    <string-array name="items">
        <item>$</item>
        <item>$$</item>
        <item>$$$</item>
        <item>$$$$</item>
    </string-array>
</resources>
```

```xml
<com.farukcankaya.expandablefilter.ExpandableFilter
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:emoji="..."
            app:items="@array/items"
            app:label="..." />
```

That's it.

# Customization

<img src="https://github.com/farukcankaya/ExpandableFilter/raw/master/art/art.png" />

You can change many attributes listed below:
### Duration
Default duration is 300ms. 
```xml
<com.farukcankaya.expandablefilter.ExpandableFilter
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:emoji="..."
            app:label="..."
            app:items="@array/items"
            app:duration="1200"/>
```

### Font Family
You specify custom font for label and emoji separately. Fonts should be under `assets/font` directory. If you want to use different font for different language, you need to create a directory for that language. For example, we want to use different font for Arabic, we will create `font-ar` directory under `assets` directory. Then, we will put custom font in it.
```xml
<com.farukcankaya.expandablefilter.ExpandableFilter
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:emoji="..."
            app:label="..."
            app:items="@array/items"
            app:labelFont="label.ttf"
            app:emojiFont="emoji.ttf"/>
```

### Text Size
You specify text size for label and emoji separately.
```xml
<com.farukcankaya.expandablefilter.ExpandableFilter
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:emoji="..."
            app:label="..."
            app:items="@array/items"
            app:labelFontSize="16sp"
            app:emojiFontSize="20sp"/>
```

### Text Color
There are two ways to change text color. We can give color resource:

res/color/text_color.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- Selected -->
    <item android:color="#FFFFFF" android:state_selected="true" />
    <!-- Hovered -->
    <item android:color="#FFFFFF" android:state_hovered="true" />
    <!-- Pressed -->
    <item android:color="#FFFFFF" android:state_pressed="true" />
    <!-- Default -->
    <item android:color="#ff6969" />
</selector>
```
```xml
<com.farukcankaya.expandablefilter.ExpandableFilter
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:emoji="..."
            app:label="..."
            app:items="@array/items"
            app:itemTextColor="@color/text_color"/>
```

or we can specify default and active text color:
```xml
<com.farukcankaya.expandablefilter.ExpandableFilter
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:emoji="..."
            app:label="..."
            app:items="@array/items"
            app:activeTextColor="#5080EA"
            app:defaultTextColor="#FFFFFF"/>
```
**Note:** `activeTextColor` **and** `defaultTextColor` **attributes override** `itemTextColor`. 

### Background color
```xml
<com.farukcankaya.expandablefilter.ExpandableFilter
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:emoji="..."
            app:label="..."
            app:items="@array/items"
            app:activeBackgroundColor="#FFFFFF"
            app:defaultBackgroundColor="#5080EA"/>
```

### Item Padding
You can arrange padding of items. Default it is `8dp`.
```xml
<com.farukcankaya.expandablefilter.ExpandableFilter
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:emoji="..."
            app:label="..."
            app:items="@array/items"
            app:itemPadding="8dp"/>
```

### Divider Margin
You can arrange margin between items. Default it is `1dp`.
```xml
<com.farukcankaya.expandablefilter.ExpandableFilter
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:emoji="..."
            app:label="..."
            app:items="@array/items"
            app:itemDividerMargin="1dp"/>
```

### Radius
You can arrange radius. Default it is `4dp`.
```xml
<com.farukcankaya.expandablefilter.ExpandableFilter
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:emoji="..."
            app:label="..."
            app:items="@array/items"
            app:radius="4dp"/>
```

### Arrange maximum selectable item count
You can arrange arrange maximum selectable item count. Default, all filter items can be selected.
```xml
<com.farukcankaya.expandablefilter.ExpandableFilter
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:emoji="..."
            app:label="..."
            app:items="@array/items"
            app:maxSelectableItemCount="1"/>
```

### ExpandableFilter.OnItemSelectListener
You can add listener to ExpandableFilter. If there is no items and filter is selected/deselected, `onSelected` / `onDeselected` methods return `-1` as position.



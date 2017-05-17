package com.farukcankaya.expandablefilter;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;
import java.util.Locale;

/**
 * Created by farukcankaya on 17/05/2017.
 */

public class FontCache {
    private static Hashtable<String, Typeface> fontCache = new Hashtable<>();

    public static Typeface get(Context context, String font) {
        Typeface typeface = fontCache.get(font);
        if (typeface == null) {
            String fontDirectory = "font";
            String localeFontDirectory = fontDirectory.concat("-").concat(Locale.getDefault().getLanguage()).concat("/");
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), localeFontDirectory.concat(font));
            } catch (Exception e) {
            }
            if (typeface == null) {
                typeface = Typeface.createFromAsset(context.getAssets(), fontDirectory.concat("/").concat(font));
            }
            fontCache.put(font, typeface);
        }
        return typeface;
    }
}
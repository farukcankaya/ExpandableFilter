package com.farukcankaya.expandablefilter;

import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.text.TextUtilsCompat;
import android.support.v4.view.ViewCompat;

import java.util.Locale;

/**
 * Created by farukcankaya on 17/05/2017.
 */

public class ViewUtil {
    public static int BACKGROUND_TYPE_LEFT = -1;
    public static int BACKGROUND_TYPE_CENTER = 0;
    public static int BACKGROUND_TYPE_RIGHT = 1;

    public static boolean isRtl() {
        return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == ViewCompat.LAYOUT_DIRECTION_RTL;
    }

    /**
     * @param activeBackgroundColor int color
     * @param radius                in pixels
     * @param type                  can be {@link ViewUtil#BACKGROUND_TYPE_LEFT},
     *                              {@link ViewUtil#BACKGROUND_TYPE_CENTER},
     *                              {@link ViewUtil#BACKGROUND_TYPE_RIGHT}
     * @return
     */
    public static StateListDrawable getBackground(int activeBackgroundColor, int radius, int type) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        ShapeDrawable shapeDrawable = new ShapeDrawable();
        if (type == 0) {
            shapeDrawable.setShape(new RoundRectShape(new float[]{0, 0, 0, 0, 0, 0, 0, 0,},
                    new RectF(), new float[]{0, 0, 0, 0, 0, 0, 0, 0}));
        } else {
            if (type < 0 ^ isRtl()) {
                shapeDrawable.setShape(new RoundRectShape(new float[]{radius, radius,
                        0, 0, 0, 0, radius, radius},
                        new RectF(), new float[]{0, 0, 0, 0, 0, 0, 0, 0}));
            } else {
                shapeDrawable.setShape(new RoundRectShape(new float[]{0, 0, radius,
                        radius, radius, radius, 0, 0},
                        new RectF(), new float[]{0, 0, 0, 0, 0, 0, 0, 0}));
            }
        }
        shapeDrawable.getPaint().setColor(activeBackgroundColor);

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, shapeDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, shapeDrawable);

        return stateListDrawable;
    }

    public static StateListDrawable getDefaultBackground(int defaultBackgroundColor,
                                                         int activeBackgroundColor, int radius) {
        StateListDrawable stateListDrawable = new StateListDrawable();

        ShapeDrawable defaultBackground = new ShapeDrawable();
        defaultBackground.setShape(new RoundRectShape(new float[]{radius, radius, radius, radius,
                radius, radius, radius, radius},
                new RectF(), new float[]{0, 0, 0, 0, 0, 0, 0, 0}));
        defaultBackground.getPaint().setColor(defaultBackgroundColor);

        ShapeDrawable activeBackground = new ShapeDrawable();
        activeBackground.setShape(new RoundRectShape(new float[]{radius, radius, radius, radius,
                radius, radius, radius, radius},
                new RectF(), new float[]{0, 0, 0, 0, 0, 0, 0, 0}));
        activeBackground.getPaint().setColor(activeBackgroundColor);

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, activeBackground);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, activeBackground);
        stateListDrawable.addState(new int[]{}, defaultBackground);

        return stateListDrawable;
    }
}

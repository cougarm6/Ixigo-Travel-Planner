package com.kungfupandas.ixigotripplanner.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.kungfupandas.ixigotripplanner.R;

/**
 * Created by tushar on 08/04/17.
 */

public class FontEditText extends EditText {
    public FontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public FontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public FontEditText(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.fontTextView);
            String fontName = a.getString(R.styleable.fontTextView_fontName);
            if (fontName!=null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }
}
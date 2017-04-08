package com.kungfupandas.ixigotripplanner.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.kungfupandas.ixigotripplanner.R;

/**
 * Created by tushar on 08/04/17.
 */

public class FontButton extends Button {
    String fontName;
    public FontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    public FontButton(Context context){
        super(context);
        init(null);
    }
    public FontButton(Context context,AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        init(attrs);
    }
    private void init(AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontButton);
            fontName = a.getString(R.styleable.FontButton_textFontName);
            if (fontName!=null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }
    public void setTextFontName(String fontName){
        this.fontName=fontName;
        if (fontName!=null) {
            Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);
            setTypeface(myTypeface);
        }
    }
}

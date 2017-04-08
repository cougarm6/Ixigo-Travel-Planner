package com.kungfupandas.ixigotripplanner.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kungfupandas.ixigotripplanner.R;

/**
 * Created by tushar on 08/04/17.
 */

public abstract class ToolbarActivity extends BaseActivity {
    private FrameLayout mContentFrameLayout;
    private TextView mTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.toolbar);
        mContentFrameLayout = (FrameLayout) findViewById(R.id.container);
        mTitleTv = (TextView) findViewById(R.id.tv_toolbar_title);
        View view = LayoutInflater.from(this).inflate(layoutResID, mContentFrameLayout, false);
        mContentFrameLayout.addView(view);
    }

    protected void setToolbarTitle(String title) {
        mTitleTv.setText(title);
    }

}

package com.kungfupandas.ixigotripplanner.ui;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.util.CommonUtil;

/**
 * Created by aish on 8/4/17.
 */

public class FloatingView {
    private Context mContext;
    private View mExpandedView;
    private WindowManager mWindowManager;
    private TextView mFloatingView;
    private ImageView mCrossView;
    private LayoutParams mFloatingViewParams,mCrossViewParams,mExpandableParams, mBackgroundParams;
    private Point mDisplaySize = new Point();
    private int mScreenHeight,mScreenWidth;
    private ValueAnimator mAnimator;
    private int mFLoaterMarginTop,mFLoaterMarginLeft,mFLoaterDimen,mCrossDimen;
    private Thread mThread;
    private int i=0;
    private FrameLayout mBackground;
    private FloatingWindowListener floatingWindowListener;
    private CountDownTimer countDownTimer;
    private boolean firstTime=true;

    public interface FloatingWindowListener{
        public void onWindowDismissed();
    }

    public FloatingView(Context context, View expandedView){
        mContext = context;
        mExpandedView = expandedView;
        this.floatingWindowListener=(FloatingWindowListener)mContext;
        initData();
        initViews();
        addView();
        addListener();
        addLayoutChangeListener(mExpandedView);
        firstTime=true;
    }

    private void initData() {
        mFLoaterMarginTop = (int) CommonUtil.dpToPixel(mContext,100);
        mFLoaterMarginLeft = (int) CommonUtil.dpToPixel(mContext,0);
        mFLoaterDimen = (int) CommonUtil.dpToPixel(mContext,48);
        mCrossDimen = (int) CommonUtil.dpToPixel(mContext, 48);
    }

    private void initViews() {
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        if(CommonUtil.isAboveHoneyCombMR2()) {
            mWindowManager.getDefaultDisplay().getSize(mDisplaySize);
            mScreenWidth = mDisplaySize.x;
            mScreenHeight = mDisplaySize.y;
        }
        else{
            mScreenHeight = mWindowManager.getDefaultDisplay().getHeight();
            mScreenWidth = mWindowManager.getDefaultDisplay().getWidth();
        }
        mFloatingView = new TextView(mContext);
        mFloatingView.setHeight(mFLoaterDimen);
        mFloatingView.setWidth(mFLoaterDimen);
        //ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        if (Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            //mFloatingView.setBackground(shapeDrawable);
            mFloatingView.setBackgroundResource(R.drawable.ghost_icon);
        }
        else{
            //mFloatingView.setBackgroundDrawable(shapeDrawable);
            mFloatingView.setBackgroundResource(R.drawable.ghost_icon);
        }
        mFloatingView.setTextSize(22);
        mFloatingView.setGravity(Gravity.CENTER);
        mCrossView = new ImageView(mContext);
        mCrossView.setImageResource(R.drawable.ic_cross);
        mCrossViewParams = new LayoutParams( mCrossDimen,
                mCrossDimen,
                LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mCrossViewParams.gravity = Gravity.TOP | Gravity.LEFT;
        mCrossViewParams.y = mScreenHeight- mFLoaterMarginTop;
        mCrossViewParams.x = mScreenWidth/2- mCrossDimen/2;

        mAnimator = new ValueAnimator();
        mAnimator.addUpdateListener(new FloatingViewUpdateListener());
        mExpandableParams = new LayoutParams( LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mExpandableParams.gravity = Gravity.TOP|Gravity.LEFT;
        mExpandableParams.x = mFLoaterMarginLeft;
        mExpandableParams.y = mFLoaterMarginTop + mFLoaterDimen;

        mBackground = new FrameLayout(mContext);
        mBackgroundParams = new LayoutParams(
                1,
                1,
                LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        mBackgroundParams.gravity = Gravity.TOP|Gravity.LEFT;
        mBackground.setBackgroundColor(ContextCompat.getColor(mContext,R.color.half_gray));
        mWindowManager.addView(mBackground, mBackgroundParams);

        mBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDim(false);
                mWindowManager.removeView(mExpandedView);
            }
        });


    }

    private void addView() {
        mFloatingViewParams= new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        mFloatingViewParams.gravity = Gravity.TOP | Gravity.LEFT;
        mFloatingViewParams.x = mFLoaterMarginLeft;
        mFloatingViewParams.y = mFLoaterMarginTop;
        mWindowManager.addView(mFloatingView, mFloatingViewParams);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(null!=mExpandedView.getParent()) {
                    mWindowManager.removeView(mExpandedView);
                    //mBackground.setBackgroundColor(Color.TRANSPARENT);
                    showDim(false);
                }
                Log.e("remove view","");
            }
        }, 100);
        //mAnimator.addUpdateListener(new FloatingViewUpdateListener());

    }

    public void removeView(){
        if(mFloatingView.getParent()!=null) {
            mWindowManager.removeView(mFloatingView);
        }
        if(mExpandedView.getParent()!=null) {
            mWindowManager.removeView(mExpandedView);
            //mBackground.setBackgroundColor(Color.TRANSPARENT);
            showDim(false);
        }
        if(mCrossView.getParent()!=null) {
            mWindowManager.removeView(mCrossView);
        }
    }

    private void addLayoutChangeListener(final View view){

        view.addOnLayoutChangeListener(
                new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        //v.removeOnLayoutChangeListener(this);
                        if(v.getVisibility()==View.VISIBLE) {
                            if(!firstTime){
                                createCircularRevealAnimation();
                            }else {
                                // v.setVisibility(View.INVISIBLE);
                            }
                            Log.e("visible","layyout");
                        }
                    }
                });

    }


    private void addListener() {
        mFloatingView.setOnTouchListener(new View.OnTouchListener() {

            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            private boolean isExpandedRemoved,isCrossSmall=true;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(firstTime) {
                            firstTime=false;
                        }
                        stopFloatingHeadAnimation();
                        initialX = mFloatingViewParams.x;
                        initialY = mFloatingViewParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        if(mExpandedView.getParent()==null){
                            isExpandedRemoved = false;
                        }
                        else{
                            isExpandedRemoved = true;
                            mWindowManager.removeView(mExpandedView);
                            //mBackground.setBackgroundColor(Color.TRANSPARENT);
                            showDim(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        stopFloatingHeadAnimation();
                        if (mFloatingViewParams.x == mCrossViewParams.x && mFloatingViewParams.y == mCrossViewParams.y ) {
                            removeView();
                            if(null!=floatingWindowListener){
                                floatingWindowListener.onWindowDismissed();
                            }
                        }
                        if(mCrossView.getParent() != null) {
                            mWindowManager.removeView(mCrossView);
                        }
                        if (Math.abs(event.getRawX() - initialTouchX) < 5 && Math.abs(event.getRawY() - initialTouchY) < 5 && !isExpandedRemoved) {
                            PropertyValuesHolder xProperty = PropertyValuesHolder.ofInt("X value", mFloatingViewParams.x, mFLoaterMarginLeft);
                            PropertyValuesHolder yProperty = PropertyValuesHolder.ofInt("Y value", mFloatingViewParams.y, mFLoaterMarginTop);
                            mAnimator.setValues(xProperty, yProperty);
                            mAnimator.setDuration(Math.abs(Math.max(mFloatingViewParams.x,mFloatingViewParams.y- mFLoaterMarginTop))).start();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        stopFloatingHeadAnimation();
                        mFloatingViewParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                        mFloatingViewParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                        if(mFloatingView.getParent()!=null) {
                            mWindowManager.updateViewLayout(mFloatingView, mFloatingViewParams);
                        }
                        if(mCrossView.getParent()==null) {
                            mWindowManager.addView(mCrossView, mCrossViewParams);
                        }
                        if (Math.abs(mFloatingViewParams.x+mFLoaterDimen/2 - mScreenWidth/2) < mFLoaterDimen && Math.abs(mFloatingViewParams.y+mFLoaterDimen/2 - mCrossViewParams.y) < mFLoaterDimen ) {
                            mCrossViewParams.height = mFLoaterDimen;
                            mCrossViewParams.width = mFLoaterDimen;
                            mCrossViewParams.x = mScreenWidth/2- mFLoaterDimen/2;
                            mFloatingViewParams.x = mCrossViewParams.x;
                            mFloatingViewParams.y = mCrossViewParams.y;
                            if(mFloatingView.getParent()!=null) {
                                mWindowManager.updateViewLayout(mFloatingView, mCrossViewParams);
                            }
                            if(isCrossSmall){
                                if(mCrossView.getParent()!=null) {
                                    mWindowManager.updateViewLayout(mCrossView, mCrossViewParams);
                                }
                                isCrossSmall = false;
                            }
                        }
                        else if(!isCrossSmall){
                            mCrossViewParams.height = mCrossDimen;
                            mCrossViewParams.width = mCrossDimen;
                            mCrossViewParams.x = mScreenWidth/2- mCrossDimen/2;
                            isCrossSmall = true;
                            if(mCrossView.getParent()!=null) {
                                mWindowManager.updateViewLayout(mCrossView, mCrossViewParams);
                            }
                        }
                        break;
                }
                return false;
            }
        });
    }

    private class FloatingViewUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mFloatingViewParams.x = (int) animation.getAnimatedValue("X value");
            mFloatingViewParams.y = (int) animation.getAnimatedValue("Y value");
            if(mFloatingView.getParent()!=null) {
                mWindowManager.updateViewLayout(mFloatingView, mFloatingViewParams);
                if (mFloatingViewParams.x == mFLoaterMarginLeft && mFloatingViewParams.y == mFLoaterMarginTop && mExpandedView.getParent() == null) {
                    if(!firstTime) {
                        mWindowManager.addView(mExpandedView, mExpandableParams);
                        showDim(true);
                    }
                }
            }
        }
    }

    public void animateFLoatingHead() {
        countDownTimer=new CountDownTimer(3000, 200) {
            public void onTick(long millisUntilFinished) {
                if(i%2==0){
                    PropertyValuesHolder xProperty = PropertyValuesHolder.ofInt("X value", mFloatingViewParams.x, mFloatingViewParams.x + 15);
                    PropertyValuesHolder yProperty = PropertyValuesHolder.ofInt("Y value", mFloatingViewParams.y, mFLoaterMarginTop);
                    mAnimator.setValues(xProperty, yProperty);
                    mAnimator.setDuration(200).start();
                    i++;
                }else {
                    PropertyValuesHolder xProperty = PropertyValuesHolder.ofInt("X value", mFloatingViewParams.x, mFloatingViewParams.x-15);
                    PropertyValuesHolder yProperty = PropertyValuesHolder.ofInt("Y value", mFloatingViewParams.y, mFLoaterMarginTop);
                    mAnimator.setValues(xProperty, yProperty);
                    mAnimator.setDuration(200).start();
                    i++;
                }
            }
            public void onFinish() {
                i=0;
            }

        }.start();

    }

    private void stopFloatingHeadAnimation(){
        if(null!=countDownTimer){
            countDownTimer.cancel();
        }
    }

    private void createCircularRevealAnimation(){
        if(null!=mFloatingView && null!=mExpandedView) {
            int centerX = mFloatingView.getLeft();
            int centerY = mFloatingView.getRight();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                Animator anim = ViewAnimationUtils.createCircularReveal(mExpandedView, centerX, centerY, 0, (int) Math
                        .hypot(mExpandedView.getWidth(), mExpandedView.getHeight()));
                anim.start();
            }
        }
    }

    private void createCircularCollapseAnimation(){
        if(null!=mFloatingView && null!=mExpandedView) {
            int centerX = mFloatingView.getLeft();
            int centerY = mFloatingView.getRight();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                Animator anim = ViewAnimationUtils.createCircularReveal(mExpandedView, centerX, centerY,(int) Math
                        .hypot(mExpandedView.getWidth(), mExpandedView.getHeight()), 0);
                anim.start();
            }
        }
    }

    private void showDim(boolean shouldDim){
        if(shouldDim) {
            mBackgroundParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            mBackgroundParams.width = LayoutParams.MATCH_PARENT;
        }else {
            mBackgroundParams.height = 1;
            mBackgroundParams.width = 1;
        }
        mWindowManager.updateViewLayout(mBackground, mBackgroundParams);
    }
}

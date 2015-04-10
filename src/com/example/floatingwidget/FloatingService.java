
package com.example.floatingwidget;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FloatingService extends Service {
    private LayoutInflater layOutInflater;
    private WindowManager winMgr;
    private LayoutParams layOutParams;
    private View overLayview;
    private TextView mFloatingView;
    private float downX;
    private float downY;
    private Animation mFadeOut2;
    private FrameLayout mFloatinglayout;
    int x, y;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        layOutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        winMgr = (WindowManager) getSystemService(WINDOW_SERVICE);
        layOutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);
        layOutParams.x = 0;
        layOutParams.y = 100;
        overLayview = layOutInflater.inflate(R.layout.floating_view, null);
        mFloatingView = (TextView) overLayview.findViewById(R.id.folting_image);
        mFloatinglayout = (FrameLayout) overLayview.findViewById(R.id.farmLayout);
        mFloatingView.setTag("iamge");
        overLayview.setOnTouchListener(mFloatingLongListner);

        overLayview.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                System.out.println("ajay onClick Lisner");
            }
        });

        winMgr.addView(overLayview, layOutParams);

        return super.onStartCommand(intent, flags, startId);
    }

    private OnTouchListener mFloatingLongListner = new OnTouchListener() {

        private int initialX;
        private int initialY;
        private float initialTouchX;
        private float initialTouchY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            System.out.println("ajay +event.getAction()" + event.getAction());
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                  
                    layOutParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                    layOutParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                    winMgr.updateViewLayout(overLayview, layOutParams);
                    break;
                case MotionEvent.ACTION_DOWN:
                    initialX = layOutParams.x;
                    initialY = layOutParams.y;
                    initialTouchX = event.getRawX();
                    initialTouchY = event.getRawY();
                    if (initialTouchX == event.getRawX() && initialTouchY == event.getRawY()) {
                        return false;// to handle Click
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (initialTouchX == event.getRawX() && initialTouchY == event.getRawY()) {
                        return false;// to handle Click
                    }
                    break;

            }
            return true;

        }
    };

}

package com.pefdneves.bringmyumbrella.ui;

import android.os.Handler;
import android.os.Message;

import com.pefdneves.bringmyumbrella.utils.ui.UiUtils;

import dagger.android.DaggerActivity;

public class BaseActivity extends DaggerActivity {

    private void delayedHide(int delayMillis) {
        mHideHandler.removeMessages(0);
        mHideHandler.sendEmptyMessageDelayed(0, delayMillis);
    }

    private final Handler mHideHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            UiUtils.enableImmersiveMode(getWindow().getDecorView());
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            delayedHide(300);
        } else {
            mHideHandler.removeMessages(0);
        }
    }
}

package com.whoopedu.vrime.util;

import android.view.MotionEvent;
import android.view.View;

public abstract class ImpatientClickListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            onClick();
        }
        return false;
    }

    public abstract void onClick();
}

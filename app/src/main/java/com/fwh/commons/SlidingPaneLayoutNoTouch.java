package com.fwh.commons;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SlidingPaneLayoutNoTouch extends SlidingPaneLayout {
	public SlidingPaneLayoutNoTouch(Context context) {
		super(context);
	}

	public SlidingPaneLayoutNoTouch(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlidingPaneLayoutNoTouch(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (isOpen()) {
			return super.onInterceptTouchEvent(ev);
		}
		//System.out.println("滑动！！！");
		return false;
	}
}
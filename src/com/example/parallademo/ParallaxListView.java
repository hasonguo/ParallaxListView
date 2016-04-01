package com.example.parallademo;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

public class ParallaxListView extends ListView {
	
	private ImageView mIvHead;
	/* ImageView�ĸ߶� */
	private int height;
	/* Drawable�ĸ߶� */
	private int drawableHeight;

	public ParallaxListView(Context context) {
		this(context, null);
	}

	public ParallaxListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ParallaxListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setParallaxImage(ImageView mIvHead) {
		this.mIvHead = mIvHead;
		this.height = mIvHead.getHeight();
		this.drawableHeight = mIvHead.getDrawable().getIntrinsicHeight();
	}
	
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		
		// ����������ָ�Ӵ���deltaY���£�
		if (isTouchEvent && deltaY < 0){
			int absDy = Math.abs(deltaY) / 3; // �Ӳ���Ч
			int newHeight = mIvHead.getHeight();
			newHeight = newHeight + absDy;
			if (newHeight <= drawableHeight){
				mIvHead.getLayoutParams().height = newHeight;
				// ���ò��ֲ���
				mIvHead.requestLayout();
			}
		}
		
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}
	
	@SuppressLint("ClickableViewAccessibility") @Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_UP:
			final int startHeight = mIvHead.getHeight();
			final int endHeight = height;
			ValueAnimator animator  = ValueAnimator.ofInt(1);
			animator.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float fraction = animation.getAnimatedFraction();
					Float evaluate = new FloatEvaluator().evaluate(fraction, startHeight, endHeight);
					
					mIvHead.getLayoutParams().height = evaluate.intValue();
					mIvHead.requestLayout();
				}
			});
			animator.setDuration(300);
			animator.setInterpolator(new OvershootInterpolator(3)); // ����tension���ص�����
			animator.start();
			break;
		}
		return super.onTouchEvent(ev);
	}
}

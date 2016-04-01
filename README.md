## 前言

本文实现一个有趣效果，该效果在老版本的 QQ 空间和微信中可以见到。即：下拉带头部（HeaderView）的 ListView，头部的图片不断放大，松开手指缓慢回弹并伴有抖动效果。

示例代码：https://github.com/heshiweij/ParallaxListView

## 效果预览

![这里写图片描述](http://img.blog.csdn.net/20160401134024951)

## 基本原理

重写 ListView 的 overScrollBy ，当下拉 ListView 时，根据 deltaY（垂直方向上的增量） 不断改变 ImageView 的高度；松开手指时，执行动画，逐渐恢复 ImageView 的高度；并同时应用差值器（OvershootInterpolator）产生回弹后的抖动效果。

## 初始化成员

```
public void setParallaxImage(ImageView mIvHead) {
	this.mIvHead = mIvHead;
	// ImageView 的高度
	this.height = mIvHead.getHeight();
	// 图片本身高度（此处为 ListView 可下拉的最大高度）
	this.drawableHeight = mIvHead.getDrawable().getIntrinsicHeight();
}
```

## overScrollBy 

overScrollBy 是 View 的方法，当手指已经将 ScrollView 或者 ListView 上滑动最顶端或者最底部时，该方法会不断被回调，并传过来deltaX(Y)、scrollX(Y)、scrollRangeX(Y)、 isTouchEvent 等参数。

本例中用到的参数：

deltaY：竖直方向上的增量
isTouchEvent ：手指是否接触屏幕

```
@Override
protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
		int scrollY, int scrollRangeX, int scrollRangeY,
		int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
	
	// 下拉并且手指接触（deltaY往下）
	if (isTouchEvent && deltaY < 0){
		int absDy = Math.abs(deltaY) / 3; // 视差特效
		int newHeight = mIvHead.getHeight();
		newHeight = newHeight + absDy;
		
		// 将可拉动的范围限制在 drawableHeight 以下
		if (newHeight <= drawableHeight){
			mIvHead.getLayoutParams().height = newHeight;
			// 设置布局参数
			mIvHead.requestLayout();
		}
	}
	
	return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
			scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
}
```

**注意**：`mIvHead.requestLayout()` 更新布局参数

## 松手回弹
```
@Override
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
		animator.setInterpolator(new OvershootInterpolator(3)); // 参数tension：回弹张力
		animator.start();
		break;
	}
	return super.onTouchEvent(ev);
}
```


此处逻辑简单，不再赘述。

## 附录

示例代码：https://github.com/heshiweij/ParallaxListView


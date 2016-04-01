package com.example.parallademo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {

    private ParallaxListView listview;
	private ImageView mIvHead;
	
	 String[] namesStrings = { "AA","何世威","刘铁", "吕册", "王彦苏", "刘化峰", "沙丽珊", "王志会", "姜波",
				"王昕", "彭莉", "米云龙", "秦勤", "许华", "佟冬蕾", "兰庆伟", "曹宽", "孙成伟",
				"张大勇", "刘贤宇", "李月", "姚佳媛", "益长虹" };

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        View viewHeader = View.inflate(this, R.layout.view_header, null);
        mIvHead = (ImageView) viewHeader.findViewById(R.id.iv_head);
        listview = (ParallaxListView) findViewById(R.id.list);
        // 取消ListView的阴影
        listview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        
        listview.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				listview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				listview.setParallaxImage(mIvHead);
			}
		});
        listview.addHeaderView(viewHeader);
        listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, namesStrings));
    }
}

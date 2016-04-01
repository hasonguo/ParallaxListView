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
	
	 String[] namesStrings = { "AA","������","����", "����", "������", "������", "ɳ��ɺ", "��־��", "����",
				"���", "����", "������", "����", "��", "١����", "����ΰ", "�ܿ�", "���ΰ",
				"�Ŵ���", "������", "����", "Ҧ����", "�泤��" };

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        View viewHeader = View.inflate(this, R.layout.view_header, null);
        mIvHead = (ImageView) viewHeader.findViewById(R.id.iv_head);
        listview = (ParallaxListView) findViewById(R.id.list);
        // ȡ��ListView����Ӱ
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

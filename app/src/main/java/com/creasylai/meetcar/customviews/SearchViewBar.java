package com.creasylai.meetcar.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.creasylai.meetcar.R;

/**
 * Created by laicreasy on 15/12/13.
 */
public class SearchViewBar extends RelativeLayout {

	public SearchViewBar(Context context) {
		super(context);
		initView(context, null);
	}

	public SearchViewBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	public SearchViewBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context, attrs);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public SearchViewBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView(context, attrs);
	}

	private void initView(Context context, AttributeSet attrs) {
		LayoutInflater.from(context).inflate(R.layout.custom_search_view_bar, this);
		//context.obtainStyledAttributes(attrs, R.styleable.SearchViewBar);
	}

}

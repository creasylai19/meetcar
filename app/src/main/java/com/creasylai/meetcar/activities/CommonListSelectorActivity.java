package com.creasylai.meetcar.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.consts.AppConst;

import java.util.ArrayList;
import java.util.Collections;


public class CommonListSelectorActivity extends BaseActivity implements AdapterView.OnItemClickListener {

	private ListView lv_common;
	private ArrayList<String> datas;

	public static void startActivityForResult(Activity activity, int requestCode) {
		Intent intent = new Intent(activity, CommonListSelectorActivity.class);
		activity.startActivityForResult(intent, requestCode);
	}

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_common_list_selector);
		lv_common = (ListView)findViewById(R.id.lv_common);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		datas = new ArrayList<>();
		Collections.addAll(datas, getResources().getStringArray(R.array.jobs));
		ArrayAdapter<String> jobs = new ArrayAdapter<>(this, R.layout.common_textview, datas);
		lv_common.setAdapter(jobs);
		lv_common.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		intent.putExtra(AppConst.STATIC_STRING_KEY.COMMON_KEY, datas.get(position).toString());
		setResult(RESULT_OK, intent);
		this.finish();
	}
}

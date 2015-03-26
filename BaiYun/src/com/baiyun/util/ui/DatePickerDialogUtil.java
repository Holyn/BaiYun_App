package com.baiyun.util.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerDialogUtil {
	private Context context;
	private TextView textView;
	private Calendar calendar;//创建一个日历引用d，通过静态方法getInstance() 从指定时区 Locale.CHINA 获得一个日期实例
	
	public DatePickerDialogUtil(Context context, TextView textView) {
		this.context = context;
		this.textView = textView;
		
		// TODO Auto-generated constructor stub
		calendar = Calendar.getInstance(Locale.CHINA);
		//创建一个Date实例
		Date myDate=new Date();
		//设置日历的时间，把一个新建Date实例myDate传入
		calendar.setTime(myDate);
	}
	
	public void show() {
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH);
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		
		//获得日历中的 year month day
		DatePickerDialog datePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
	            // 每次保存设置的日期  
	            calendar.set(Calendar.YEAR, year);  
	            calendar.set(Calendar.MONTH, monthOfYear);  
	            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);  
	  
	            String str = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;  
	            System.out.println("set is " + str);  
	            textView.setText(year+"/"+ (monthOfYear + 1) + "/" + dayOfMonth);
			}
		}, year, month, day);
		datePickerDialog.show();
	}
	
}

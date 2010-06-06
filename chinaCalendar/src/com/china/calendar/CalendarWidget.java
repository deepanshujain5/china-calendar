package com.china.calendar;


import java.util.Calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;

public class CalendarWidget extends AppWidgetProvider {
	
	private static int lastDay;
	
	private static int lastHour;
	
	private static RemoteViews views;
	
	private static String[] weekArray ={"日","一","二","三","四","五","六"}; 
	

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            Log.i("ID", ""+i+":"+appWidgetId);
            updateAppWidget(context, appWidgetManager,appWidgetId);
        }
       
	}

	public static void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager,int appWidgetId) {
		 
		if(views == null){
			views = new RemoteViews(context.getPackageName(),R.layout.my_widget);
			
			// 创建一个Intent来启动 ExampleActivity
			Intent intent = new Intent(context, MainActivity.class);
			PendingIntent pendingIntent =PendingIntent.getActivity(context, 0, intent, 0);
			views.setOnClickPendingIntent(R.id.my_widget, pendingIntent);
		}
		  
		Calendar calendar = Calendar.getInstance();
		int curHour = calendar.get(Calendar.HOUR_OF_DAY);
		if(lastDay ==0 || (curHour==0 && lastHour!=0)){
			
			int month = calendar.get(Calendar.MONTH)+1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int week = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)-1;			
			Lunar lunar = new Lunar(calendar);  
			
			String month_week = month+"月"+"          周"+weekArray[week];			
			views.setTextViewText(R.id.month_week,month_week);
			views.setTextViewText(R.id.nongli,lunar.toString());
			views.setTextViewText(R.id.day,Html.fromHtml("<B>"+String.valueOf(day)+"</B>"));
			
		}
		
		
		appWidgetManager.updateAppWidget(appWidgetId, views);
		
	}
	
	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}
	
	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

}

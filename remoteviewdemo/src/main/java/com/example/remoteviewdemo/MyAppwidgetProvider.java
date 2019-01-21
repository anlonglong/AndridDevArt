package com.example.remoteviewdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.SystemClock;
import android.util.EventLog;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyAppwidgetProvider extends AppWidgetProvider {

    private String TAG = this.getClass().getSimpleName();
    private static final String CLIKC_ACTION = "com.action.click";

    public MyAppwidgetProvider() {super();}

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onReceive : action = " + intent.getAction());
       if (intent.getAction().equals(CLIKC_ACTION)) {
            Toast.makeText(context, "click it", Toast.LENGTH_LONG).show();
           Runnable target = new Runnable() {
               @Override
               public void run() {
                   Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.car_sideview_body_white01);
                   AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
                   for (int i = 0; i < 37; i++) {
                       float degree = (i * 10) % 360;
                       RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                       remoteViews.setImageViewBitmap(R.id.image_view, rotateBitmap(context, srcBitmap, degree));
                       PendingIntent pendingIntent = null;
                       if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                           Intent intentClick = new Intent();
                           intentClick.setAction(CLIKC_ACTION);
                           pendingIntent = PendingIntent.getBroadcast(context, 1, intentClick, 0);

                       } else {
                           //8.0以后Android系统比支持隐式广播的注册
                           Intent intentClick = new Intent(context, MyAppwidgetProvider.class);
                           intentClick.setAction(CLIKC_ACTION);
                           pendingIntent = PendingIntent.getBroadcast(context, 1, intentClick, 0);

                       }
                       remoteViews.setOnClickPendingIntent(R.id.image_view, pendingIntent);
                       widgetManager.updateAppWidget(new ComponentName(context, MyAppwidgetProvider.class), remoteViews);
                       SystemClock.sleep(30);

                   }
               }
           };
           new Thread(target).start();
      }
    }

    private Bitmap rotateBitmap(Context context, Bitmap srcBitmap, float degree) {
        Log.i(TAG, "degree = " + degree);
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        return Bitmap.createBitmap(srcBitmap,0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.i(TAG, "onUpdate");
        int counter  = appWidgetIds.length;
        for (int i = 0; i < counter ; i++) {
            onWidgetUpdate(context, appWidgetIds[i], appWidgetManager);
        }
    }

    private void onWidgetUpdate(Context context, int appWidgetId, AppWidgetManager appWidgetManager) {
        Log.i(TAG, "appWidgetId = " + appWidgetId);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Intent intentClick = new Intent();
            intentClick.setAction(CLIKC_ACTION);
            pendingIntent = PendingIntent.getBroadcast(context, 1, intentClick, 0);

        } else {
            //8.0以后Android系统比支持隐式广播的注册
            Intent intentClick = new Intent(context, MyAppwidgetProvider.class);
            intentClick.setAction(CLIKC_ACTION);
            pendingIntent = PendingIntent.getBroadcast(context, 1, intentClick, 0);
        }
        remoteViews.setOnClickPendingIntent(R.id.image_view, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

    }
}

package com.example.foodu;

import java.util.Timer;
import java.util.TimerTask;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.StringTokenizer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.example.foodu.R;
import com.example.foodu.NotificationUpdate;
import com.example.foodu.NotificationData;

public class GCMIntentService extends GCMBaseIntentService {
 
    private static final String TAG = "GCM::FoodU";
    LinkedList<NotificationData> notificationList;
 
    // Use your PROJECT ID from Google API into SENDER_ID
    public static final String SENDER_ID = "53340195483";
 
    public GCMIntentService() {
        super(SENDER_ID);
    }
 
	@Override
	protected void onError(Context arg0, String errorId) {
		Log.e(TAG, "onError: errorId=" + errorId);
	}
	
    static NotificationUpdate update;
    public static void resisterNotification(NotificationUpdate notificationUpdate){
    	update = notificationUpdate;
    }
    

	@Override
	protected void onRegistered(Context arg0, String registrationId) {
		Log.i(TAG, "onRegistered: registrationId=" + registrationId);
		
	}

	@Override
	protected void onUnregistered(Context arg0, String registrationId) {
		Log.i(TAG, "onUnregistered: registrationId=" + registrationId);
	}

	@Override
	protected void onMessage(Context context, Intent data) {
		String message;
        // Message from PHP server
        message = data.getStringExtra("message");
        // Open a new activity called GCMMessageView
        Intent intent = new Intent(this, com.example.foodu.Notification.class);
        // Pass data to the new activity
        //intent.putExtra("message", message);
        notificationList= new LinkedList<NotificationData>();
        parseData(message);
        if(update!=null){
        	update.updateNotificationData();
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Starts the activity on notification click
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // Create the notification with a notification builder
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_logo)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Food@U")
                .setContentText(message).setContentIntent(pIntent)
                .getNotification();
        // Remove the notification on click
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
 
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(R.string.app_name, notification);
 
        {
            // Wake Android Device when notification received
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            final PowerManager.WakeLock mWakelock = pm.newWakeLock(
                    PowerManager.FULL_WAKE_LOCK
                            | PowerManager.ACQUIRE_CAUSES_WAKEUP, "GCM_PUSH");
            mWakelock.acquire();
 
            // Timer before putting Android Device to sleep mode.
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    mWakelock.release();
                }
            };
            timer.schedule(task, 5000);
        }
		
	}
	
    private void parseData(String message) {
		/*
		 * data.title = "Free Food at Keller Hall."; data.venue =
		 * "Venue: Keller Hall, 3-210"; data.date = "Date: October/08/2014";
		 * data.time = "Time: 11 AM to 12 PM";
		 */
		// TODO write parsing logic here.
		// create notificationData object and assign the values,
		// read previously saving list into notificationList
		// append new notificationData to notificationList
		try {
			int len = 0;
			String[] stringArr = new String[100];
			StringTokenizer st = new StringTokenizer(message, ".");
			len = st.countTokens();
			for (int i = 0; i < len; i++) {
				if (st.hasMoreTokens()) {
					stringArr[i] = st.nextToken();
				}
			}

			NotificationData data = new NotificationData();
			data.title = stringArr[0];
			data.venue = stringArr[1];
			data.date = stringArr[2];
			data.time = stringArr[3];
			readFromFile();
			notificationList.add(data);
			writeToFile();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void readFromFile(){
		try{
		FileInputStream fis = openFileInput("varun");
		ObjectInputStream ois = new ObjectInputStream(fis);
		LinkedList<NotificationData> local = (LinkedList<NotificationData>) ois.readObject();
		ois.close();
		
		for (int i = 0; i < local.size(); i++) {
			notificationList.add(local.get(i));
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    
	void writeToFile(){
		FileOutputStream fos;
		try {
		fos = openFileOutput("varun", Context.MODE_PRIVATE);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(notificationList);
		oos.close();
	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
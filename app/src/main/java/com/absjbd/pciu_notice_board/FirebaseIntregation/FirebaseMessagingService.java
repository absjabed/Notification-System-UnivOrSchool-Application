package com.absjbd.pciu_notice_board.FirebaseIntregation;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.absjbd.pciu_notice_board.Activity.MainActivity;
import com.absjbd.pciu_notice_board.Activity.PushNotificationActivity;
import com.absjbd.pciu_notice_board.R;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


/**
 * Created by abs pc1 on 2018-01-17.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingServic";
    String extraData;
    public FirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();

        if(data.size() > 0){
            //String text= data.get("text");
            String title = data.get("title").trim(); //get title
            String message = data.get("text").trim(); //get message
            String click_action = data.get("click_action"); //get click_action
            extraData = data.get("extra_information").trim();
            //fcmBtnLink = data.containsKey("button_link") ? data.get("button_link").trim() : "";

            Log.d(TAG, "Message Notification Title: " + title);
            Log.d(TAG, "Message Notification Body: " + message);
            Log.d(TAG, "Message Notification Extra: " + extraData);
            Log.d(TAG, "Message Notification click_action: " + click_action);

            sendNotification(title, message,click_action);

        }
            //For by default notification
        /*String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        Log.d(TAG, "onMessageReceived: Message Received: \n" +
                "Title: " + title + "\n" +
                "Message: " + message);

        sendNotification(title,message);*/
    }

    @Override
    public void onDeletedMessages() {

    }

    private void sendNotification(String title,String messageBody, String click_action) {
        Intent intent;
        /*if(click_action.equals("WEB")){
            intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("URL",extraData); //JsonMessage
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        else */
        switch (click_action) {
            case "NOTIFICATION":
                intent = new Intent(this, PushNotificationActivity.class);
                intent.putExtra("Title", title);
                intent.putExtra("Body", messageBody);
                intent.putExtra("Extra", extraData);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_b)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    // for by default notification
    /*private void sendNotification(String title,String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_b)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());
    }*/
}

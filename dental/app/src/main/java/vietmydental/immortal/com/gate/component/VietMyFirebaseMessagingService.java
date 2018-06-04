package vietmydental.immortal.com.gate.component;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import vietmydental.immortal.com.gate.g00.view.G00LoginActivity;
import vietmydental.immortal.com.gate.model.NotificationBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

public class VietMyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Intent intent = new Intent(this, G00LoginActivity.class);// Tr
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);// Tr

        String dataStr = "";
        if ((remoteMessage.getData() != null)
                && (remoteMessage.getData().get(DomainConst.KEY_DATA) != null)) {
            dataStr = remoteMessage.getData().get(DomainConst.KEY_DATA);
        }
        if (!dataStr.isEmpty()) {
            Bundle data = new Bundle();
            data.putString(DomainConst.KEY_DATA, dataStr);
            intent.putExtras(data);
        }

        sendNotification(intent, remoteMessage.getNotification());
    }
    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param intent  Intent
     * @param message GCM message received.
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(Intent intent, RemoteMessage.Notification message) {
//        private void sendNotification(Intent intent, String message) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String title = message.getTitle();
        if (title.isEmpty()) {
            title = getString(R.string.app_name);
        }

        Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.iphone_ringstone);
        Notification noti = new Notification.Builder(this)
//                .setContentTitle(getString(R.string.app_name))
                .setContentTitle(title)
                .setContentText(message.getBody())
//                .setContentText(message.get(DomainConst.KEY_DATA))
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
//                .setStyle(new Notification.BigTextStyle()
//                        .bigText(message))
                .setAutoCancel(true)
                .setSound(sound)
                .setVibrate(new long[]{1000, 1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000, 500})   // Set vibration for notification
                .setContentIntent(pendingIntent)
                .build();

//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})   // Set vibration for notification
//                .setContentIntent(pendingIntent);
        /* Dec 21, 2015 sẽ makes a call to your server, to acknowledge the message was received,
            khi mà đã received thì chuyển qua table history với các status như sau
            1: new, 2: view
            http://stackoverflow.com/questions/25229454/get-response-from-gcm
          */

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // anh Dung Now 23, 2015 http://stackoverflow.com/questions/18102052/how-to-display-multiple-notifications-in-android
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        try {
            // Unique id for prevent duplicate notification
            m = Integer.parseInt(intent.getExtras().getString(DomainConst.KEY_NOTIFY_ID, DomainConst.BLANK));
        } catch (Exception ex) {
            // Do nothing
        }
        notificationManager.notify(m /* ID of notification */, noti);

//        vibrate();
        // playSound();
    }
}

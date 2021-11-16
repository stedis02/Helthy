package com.example.healthy.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.healthy.Activity.DoctorInformationActivity;
import com.example.healthy.BD.Constants;
import com.example.healthy.Doctors.Doctor;
import com.example.healthy.R;

public class DoctorNotificationReceiver extends BroadcastReceiver {

    private NotificationManager notificationManager;
    private static final String Chanel_id = "Doctor_id";
    private static final int Not_id = 100;
    private Doctor doctor;

    public static void createChannelIfNeeded(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(Chanel_id, Chanel_id, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }





    @Override
    public void onReceive(Context context, Intent intent) {
            doctor = (Doctor)intent.getSerializableExtra(Constants.DoctorListKey);
            String Specialty = doctor.getSpecialty();
      //  String Specialty = "ssss";
         Intent in = new Intent(context, DoctorInformationActivity.class);
           in.putExtra("Specialty", Specialty);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, Chanel_id)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setContentTitle("Вы создали новую запись")
                .setContentText(Specialty);
        createChannelIfNeeded(notificationManager);
        notificationManager.notify(Not_id, notificationBuilder.build());

    }
}
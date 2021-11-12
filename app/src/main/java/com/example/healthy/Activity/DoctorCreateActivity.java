package com.example.healthy.Activity;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.healthy.BD.Constants;
import com.example.healthy.BD.DBManager;
import com.example.healthy.Doctors.Doctor;

import com.example.healthy.R;
import com.example.healthy.adapter.DoctorAdater;


public class DoctorCreateActivity extends AppCompatActivity {

    private EditText surname, name, middleName, Speciality;
    private DBManager dbManager;
    private DoctorAdater doctorAdater;
    private Doctor doctor;

    private NotificationManager notificationManager;
    private static final int Not_id = 101;
    private static final String Chanel_id = "Doctor_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_create);
        init();
    }

    protected void init() {

        dbManager = new DBManager(this);
        doctorAdater = new DoctorAdater(this);
        surname = findViewById(R.id.surname);
        middleName = findViewById(R.id.middleName);
        name = findViewById(R.id.name);
        Speciality = findViewById(R.id.speciality);
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

    }


    @Override
    protected void onResume() {
        super.onResume();
        dbManager.DBOpen();
        doctorAdater.updateAdapter(dbManager.DBGetDoctor());


    }

    public static void createChannelIfNeeded(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(Chanel_id, Chanel_id, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }

    public void ClickSave(View view) {
        if (surname.getText().toString().equals("") || name.getText().toString().equals("")) {
            Toast.makeText(this, R.string.note_error_massage, Toast.LENGTH_SHORT).show();

        } else {
            dbManager.DBInsertDoctor(Speciality.getText().toString(), surname.getText().toString(), name.getText().toString() + " " + middleName.getText().toString());

            // нужно передавать нужный элемент коллекции доктор. пока думаю как это сделать.
            // при нажатии на уведомление программа падает
            // так как не понимает от какого элемента коллекции ей брать данные.

            Intent intent = new Intent(getApplicationContext(), DoctorInformationActivity.class);

            // я нашёл решение
            // иногда моя гениальность просто пугает

            doctorAdater.updateAdapter(dbManager.DBGetDoctor());
            int  NOT_Index;
            NOT_Index = doctorAdater.getDoctorlist().size()-1;
            intent.putExtra(Constants.ListKey, doctorAdater.getDoctorlist().get(NOT_Index));


            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), Chanel_id)
                    .setAutoCancel(false)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent)
                    .setContentTitle("Вы создали новую запись")
                    .setContentText(Speciality.getText().toString())
                    .setPriority(BIND_WAIVE_PRIORITY);
            createChannelIfNeeded(notificationManager);
            notificationManager.notify(Not_id, notificationBuilder.build());

            dbManager.DBClose();

            finish();
        }
    }

}




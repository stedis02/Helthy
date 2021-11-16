package com.example.healthy.Activity;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.healthy.service.DoctorNotificationReceiver;

import java.util.Calendar;


public class DoctorCreateActivity extends AppCompatActivity {

    private EditText surname, name, middleName, Speciality , date , time;
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
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

    }


    @Override
    protected void onResume() {
        super.onResume();
        dbManager.DBOpen();
        doctorAdater.updateAdapter(dbManager.DBGetDoctor());


    }

    public void ClickSave(View view) {
        if (surname.getText().toString().equals("") || name.getText().toString().equals("") || date.getText().toString().equals("")) {
            Toast.makeText(this, R.string.note_error_massage, Toast.LENGTH_SHORT).show();

        } else {
            dbManager.DBInsertDoctor(Speciality.getText().toString(), surname.getText().toString(), name.getText().toString() + " " + middleName.getText().toString() , date.getText().toString() , time.getText().toString());
            doctorAdater.updateAdapter(dbManager.DBGetDoctor());
            int  NOT_Index;
            NOT_Index = doctorAdater.getDoctorlist().size()-1;
            Intent intentNotification = new Intent(getApplicationContext(), DoctorNotificationReceiver.class);
            intentNotification.putExtra(Constants.DoctorListKey, doctorAdater.getDoctorlist().get(NOT_Index));
            sendBroadcast(intentNotification);
            //время установлено для тестов. в последствии нужно будет передавать значеения с активностии и высчитывать твремя и дату каждого напоминания
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 18);
            calendar.set(Calendar.MINUTE, 31);
            calendar.set(Calendar.SECOND, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intentNotification, PendingIntent.FLAG_IMMUTABLE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            dbManager.DBClose();


            finish();
        }
    }

}




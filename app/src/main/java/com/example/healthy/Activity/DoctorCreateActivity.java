package com.example.healthy.Activity;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthy.BD.Constants;
import com.example.healthy.BD.DBManager;
import com.example.healthy.Doctors.Doctor;

import com.example.healthy.R;
import com.example.healthy.adapter.DoctorAdater;
import com.example.healthy.service.DoctorNotificationReceiver;

import java.util.Calendar;


public class DoctorCreateActivity extends AppCompatActivity {

    private EditText surname, name, middleName, Speciality;
    private DBManager dbManager;
    private DoctorAdater doctorAdater;
    private Doctor doctor;

    private NotificationManager notificationManager;
    private static final int Not_id = 101;
    private static final String Chanel_id = "Doctor_id";
    TextView currentTime;
    TextView currentDate;
    private Calendar calendar=Calendar.getInstance();
    private Calendar calendar1 = Calendar.getInstance();
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
        currentDate = findViewById(R.id.DataCurent);
        currentTime = findViewById(R.id.TimeCurent);
        setInitialDateTime();

    }


    @Override
    protected void onResume() {
        super.onResume();
        dbManager.DBOpen();
        doctorAdater.updateAdapter(dbManager.DBGetDoctor());


    }

    private void setInitialDateTime() {

        currentDate.setText(DateUtils.formatDateTime(this,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
        currentTime.setText(DateUtils.formatDateTime(this,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }

    DatePickerDialog.OnDateSetListener mData=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };
    TimePickerDialog.OnTimeSetListener mTime =new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    public void DataClick(View view){

        new DatePickerDialog(DoctorCreateActivity.this, mData,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void TimeClick(View v) {
        new TimePickerDialog(DoctorCreateActivity.this, mTime,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true)
                .show();
    }

    public void ClickSave(View view) {
        if (surname.getText().toString().equals("") || name.getText().toString().equals("")) {
            Toast.makeText(this, R.string.note_error_massage, Toast.LENGTH_SHORT).show();

        } else {
            dbManager.DBInsertDoctor(Speciality.getText().toString(), surname.getText().toString(), name.getText().toString() + " " + middleName.getText().toString() , " " , " ");
            doctorAdater.updateAdapter(dbManager.DBGetDoctor());
            int  NOT_Index;
            NOT_Index = doctorAdater.getDoctorlist().size()-1;
            Intent intentNotification = new Intent(getApplicationContext(), DoctorNotificationReceiver.class);
            intentNotification.putExtra(Constants.DoctorListKey, doctorAdater.getDoctorlist().get(NOT_Index));
            sendBroadcast(intentNotification);

            calendar1.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
            calendar1.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
            calendar1.set(Calendar.SECOND, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intentNotification, PendingIntent.FLAG_IMMUTABLE );
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);

            dbManager.DBClose();


            finish();
        }
    }

}




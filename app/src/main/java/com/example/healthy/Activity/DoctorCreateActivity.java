package com.example.healthy.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthy.BD.DBManager;
import com.example.healthy.Doctors.Doctor;

import com.example.healthy.R;
import com.example.healthy.adapter.DoctorAdater;


public class DoctorCreateActivity extends AppCompatActivity {

    private EditText surname, name, middleName;
    private DBManager dbManager;
    private DoctorAdater doctorAdater;
    private Doctor doctor;

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


    }


    @Override
    protected void onResume() {
        super.onResume();
        dbManager.DBOpen();
       doctorAdater.updateAdapter(dbManager.DBGetDoctor());


    }

    public void ClickSave(View view) {
        if (surname.getText().toString().equals("") || name.getText().toString().equals("")) {
            Toast.makeText(this, R.string.note_error_massage, Toast.LENGTH_SHORT).show();

        } else {
            dbManager.DBInsertDoctor(surname.getText().toString(), name.getText().toString() + " "+ middleName.getText().toString());
            dbManager.DBClose();
            finish();
        }
    }

}




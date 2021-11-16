package com.example.healthy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthy.BD.Constants;
import com.example.healthy.BD.DBManager;
import com.example.healthy.Doctors.Doctor;
import com.example.healthy.Notes.Note;
import com.example.healthy.R;
import com.example.healthy.adapter.NotesAdater;

import java.util.Calendar;
import java.util.Date;

public class DoctorInformationActivity extends AppCompatActivity {
    private Doctor doctor;
    private TextView Speciality;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_information);
        init();
        getIntents();
    }

    protected void init(){

        Speciality = findViewById(R.id.specTitel);

    }

    public void getIntents(){
        Intent intent = getIntent();
        if(intent!= null){
           String Specialty = intent.getStringExtra("Specialty");
           Speciality.setText(Specialty);

        }

    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

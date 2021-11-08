package com.example.healthy.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.healthy.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

public void ClickTrans(View view){
    Intent intent = new Intent(MainActivity.this, NoteActivity.class);
    startActivity(intent);

}

    public void ClickTransDoctor(View view){
        Intent intent = new Intent(MainActivity.this, DoctorActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

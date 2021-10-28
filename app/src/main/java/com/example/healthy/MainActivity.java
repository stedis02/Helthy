package com.example.healthy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

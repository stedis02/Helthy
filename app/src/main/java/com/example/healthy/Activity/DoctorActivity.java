package com.example.healthy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthy.BD.DBManager;
import com.example.healthy.R;
import com.example.healthy.adapter.DoctorAdater;
import com.example.healthy.adapter.NotesAdater;

public class DoctorActivity extends AppCompatActivity {
    private DBManager dbManager;
    private RecyclerView recyclerView;
    private DoctorAdater doctorAdater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor);
        init();
    }

    protected void init(){

        dbManager = new DBManager(this);
        doctorAdater = new DoctorAdater(this);
        recyclerView = findViewById(R.id.recview);
        // ucazivaem polozenie blocov recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(doctorAdater);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.DBOpen();
        doctorAdater.updateAdapter(dbManager.DBGetDoctor());



    }



    public void OnClick(View view){
        Intent intent = new Intent(DoctorActivity.this, DoctorCreateActivity.class);
        startActivity(intent);



    }

    public ItemTouchHelper getItemTouchHelper(){

        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                doctorAdater.DeleteItem(viewHolder.getAdapterPosition(), dbManager);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.DBClose();
    }
}
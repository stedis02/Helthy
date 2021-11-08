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
import com.example.healthy.adapter.NotesAdater;

public class DoctorActivity extends AppCompatActivity {
    private DBManager dbManager;
    private RecyclerView recyclerView;
    private NotesAdater mainAdater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor);
        init();
    }

    protected void init(){

        dbManager = new DBManager(this);
        mainAdater = new NotesAdater(this);
        recyclerView = findViewById(R.id.recview);
        // ucazivaem polozenie blocov recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mainAdater);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.DBOpen();
       // mainAdater.updateAdapter(dbManager.DBGetNote());



    }



    public void OnClick(View view){
//dbManager.DBInsert(edTitle.getText().toString(), edTitle2.getText().toString());
        // создание перехода между активити
      //  Intent intent = new Intent(DoctorActivity.this, NotesActivity.class);
       // startActivity(intent);



    }

    public ItemTouchHelper getItemTouchHelper(){

        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mainAdater.DeleteItem(viewHolder.getAdapterPosition(), dbManager);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.DBClose();
    }
}
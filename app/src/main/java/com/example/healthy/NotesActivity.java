package com.example.healthy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthy.BD.Constants;
import com.example.healthy.BD.DBManager;
import com.example.healthy.Notes.Note;
import com.example.healthy.adapter.MainAdater;

public class NotesActivity extends AppCompatActivity {

    private EditText edTitle, edTitle2;
    private DBManager dbManager;
    private MainAdater mainAdater;
private boolean NoteEdit;
private Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        init();
       getIntents();
    }
    protected void init(){

        dbManager = new DBManager(this);
        mainAdater = new MainAdater(this);
        edTitle = findViewById(R.id.edTitle);
        edTitle2 = findViewById(R.id.edTitle2);

    }
    public void getIntents(){
       Intent intent = getIntent();
        if(intent!= null){

            note = (Note)intent.getSerializableExtra(Constants.ListKey);
           NoteEdit = intent.getBooleanExtra(Constants.NoteEditKey, true);
            if(!NoteEdit) {
                // вот это починить.
                // починил
                edTitle.setText(note.getTitle());
                 edTitle2.setText(note.getDiscription());
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        dbManager.DBOpen();
        mainAdater.updateAdapter(dbManager.DBGetNote());



    }

public void ClickSave(View view){
        if(edTitle.getText().toString().equals("")||edTitle2.getText().toString().equals("")){
            Toast.makeText(this, R.string.note_error_massage, Toast.LENGTH_SHORT).show();

        }
        else{
            if(NoteEdit) {
                dbManager.DBInsert(edTitle.getText().toString(), edTitle2.getText().toString());
                Intent intent = new Intent(NotesActivity.this, NoteActivity.class);
                startActivity(intent);

                dbManager.DBClose();
            }
            else{
                dbManager.DBUpdateNote(note.getId(), edTitle.getText().toString(), edTitle2.getText().toString());
                dbManager.DBClose();
                finish();
            }
        }

}



}
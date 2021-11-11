package com.example.healthy.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.healthy.Doctors.Doctor;
import com.example.healthy.Notes.Note;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    public DBManager(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public void DBOpen() {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void DBInsertNote(String TITLE, String DISC) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.Title, TITLE);
        contentValues.put(Constants.Disc, DISC);
        sqLiteDatabase.insert(Constants.TableName, null, contentValues);
    }

    public void DBInsertDoctor(String Speciality ,String surname, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.Specialization, Speciality);
        contentValues.put(Constants.SecondName, surname);
        contentValues.put(Constants.FirstName, name);
        sqLiteDatabase.insert(Constants.TableName1, null, contentValues);
    }

    public void DBUpdateNote(int id, String TITLE, String DISC){
        String selection = Constants._id + "=" + id;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.Title, TITLE);
        contentValues.put(Constants.Disc, DISC);
        sqLiteDatabase.update(Constants.TableName, contentValues,selection, null);

    }

    public void DBUpdateDoctor(int id, String surname, String name){
        String selection = Constants._id1 + "=" + id;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.Specialization," ");
        contentValues.put(Constants.SecondName, surname);
        contentValues.put(Constants.FirstName, name);
        sqLiteDatabase.update(Constants.TableName1, contentValues,selection, null);

    }

    public List<String> DBGetTitle() {
        List<String> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(Constants.TableName, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String TITLE = cursor.getString(cursor.getColumnIndexOrThrow(Constants.Title));
            list.add(TITLE);
        }
        cursor.close();
        return list;
    }




    public List<Note> DBGetNote() {
        List<Note> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(Constants.TableName, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Note note = new Note();
            note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(Constants.Title)));
            note.setDiscription(cursor.getString(cursor.getColumnIndexOrThrow(Constants.Disc)));
            note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Constants._id)));
            list.add(note);
        }
        cursor.close();
        return list;
    }
    public List<Doctor> DBGetDoctor() {
        List<Doctor> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(Constants.TableName1, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Doctor doctor = new Doctor();
            doctor.setSpecialty(cursor.getString(cursor.getColumnIndexOrThrow(Constants.Specialization)));
            doctor.setSurname(cursor.getString(cursor.getColumnIndexOrThrow(Constants.SecondName)));
            doctor.setName(cursor.getString(cursor.getColumnIndexOrThrow(Constants.FirstName)));
            doctor.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Constants._id1)));
            list.add(doctor);
        }
        cursor.close();
        return list;
    }

    public void DeleteNote(int id){
String selection = Constants._id + "=" + id;
        sqLiteDatabase.delete(Constants.TableName, selection, null);

    }

    public void DeleteDoctor(int id){
        String selection = Constants._id1 + "=" + id;
        sqLiteDatabase.delete(Constants.TableName1, selection, null);

    }


    public void DBClose() {
        dbHelper.close();
    }

}

package com.example.healthy.BD;

public class Constants {
    public static final String ListKey = "list_key";
    public static final String DoctorListKey = "Doctor_list_key";
    public static final String NoteEditKey = "Note_Edit_key";
    // создание таблицы базы данных для заметок
    public static final String _id = "_id";
    public static final String TableName = "WritingNote";
    public static final String Title = "title";
    public static final String Disc = "DiscTitle";
    public static final String DBName = "db.db";
    public static final String TableStructure =
            "CREATE TABLE " + TableName + " (" + _id + " INTEGER PRIMARY KEY," +
                    Title + " TEXT," +
                    Disc + " TEXT)";

    public static final String TableDrop = "DROP TABLE IF EXISTS " + TableName;

    // создание таблицы баазы данных для записи к врачам
    public static final String _id1 = "_id";
    public static final String TableName1 = "doctors";
    public static final String Specialization = "Specialization";
    public static final String FirstName = "FirstName";
    public static final String  SecondName = "SecondName";
    public static final String Date ="Date";
    public static final String Time = "Time";
    public static final String Table1Structure =
            "CREATE TABLE " + TableName1 + " (" + _id1 + " INTEGER PRIMARY KEY," +
                    Specialization + " TEXT," +
                    FirstName + " TEXT," +
                    SecondName + " TEXT," + Date + " Text," + Time + " Text)";

    public static final String Table1Drop = "DROP TABLE IF EXISTS " + TableName1;

     // не забывать менять версию при изменениях
    public static final int DBVersion = 3;


}

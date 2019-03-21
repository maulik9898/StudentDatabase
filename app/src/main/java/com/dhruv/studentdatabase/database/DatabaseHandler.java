package com.dhruv.studentdatabase.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StudentDB";
    private static final String TABLE_NAME = "Student";
    private static final String KEY_ID = "ID";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_NAME = "lastName";
    private static final String KEY_MARKS = "marks";
    private static final String[] COLUMNS = {KEY_ID, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_MARKS};

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FIRST_NAME + " TEXT, " + KEY_LAST_NAME + " TEXT, " + KEY_MARKS + " INTEGER )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public int deleteOne(int id) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRow = db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{Integer.toString(id)});

        db.close();
        return deletedRow;
    }

    public Student getStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null) cursor.moveToFirst();
        if (cursor.getCount() <= 0) {
            cursor.close();
            return null;
        }
        Student student = new Student();
        student.setID(Integer.parseInt(cursor.getString(0)));
        student.setFirstName(cursor.getString(1));
        student.setLastName(cursor.getString(2));
        student.setMarks(Integer.parseInt(cursor.getString(3)));
        cursor.close();
        return student;
    }

    public List<Student> allStudents() {

        List<Student> studentList = new LinkedList<Student>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Student student = null;

        if (cursor.moveToFirst()) {
            do {
                student = new Student();
                student.setID(Integer.parseInt(cursor.getString(0)));
                student.setFirstName(cursor.getString(1));
                student.setLastName(cursor.getString(2));
                student.setMarks(Integer.parseInt(cursor.getString(3)));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return studentList;
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, student.getFirstName());
        values.put(KEY_LAST_NAME, student.getLastName());
        values.put(KEY_MARKS, student.getMarks());
        // insert
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, student.getFirstName());
        values.put(KEY_LAST_NAME, student.getLastName());
        values.put(KEY_MARKS, student.getMarks());
        int id = student.getID();
        int i = db.update(TABLE_NAME, // table
                values, // column/value
                KEY_ID + " = ?", // selections
                new String[]{String.valueOf(id)});

        db.close();

        return i;
    }

}


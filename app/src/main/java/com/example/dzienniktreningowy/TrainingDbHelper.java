package com.example.dzienniktreningowy;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TrainingDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "workout.db";
    private static final int DATABASE_VERSION = 6;

    // Stałe dla nazw tabeli i kolumn
    public static final String TABLE_NAME = "trainings";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DIFFICULTY = "difficulty ";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT NOT NULL," +
                    COLUMN_REPS + " INTEGER ,"+
                    COLUMN_DURATION + " INTEGER,"+
                    COLUMN_DATE + " TEXT NOT NULL,"+
                    COLUMN_DIFFICULTY + " TEXT NOT NULL);";

    public TrainingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // W prostej wersji usuwamy tabelę i tworzymy nową przy zmianie wersji
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

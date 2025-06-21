package com.example.appagenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CompromissosDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "compromissos.db";

    public CompromissosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CompromissosDBSchema.CompromissoTable.NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CompromissosDBSchema.CompromissoTable.Cols.DATA + " TEXT, " +
                CompromissosDBSchema.CompromissoTable.Cols.HORA + " TEXT, " +
                CompromissosDBSchema.CompromissoTable.Cols.DESCRICAO + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CompromissosDBSchema.CompromissoTable.NAME);
        onCreate(db);
    }
}
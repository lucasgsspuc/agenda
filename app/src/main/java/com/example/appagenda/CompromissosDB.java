package com.example.appagenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CompromissosDB {
    private SQLiteDatabase database;

    public CompromissosDB(Context context) {
        database = new CompromissosDBHelper(context).getWritableDatabase();
    }

    public void adicionarCompromisso(Compromisso compromisso) {
        ContentValues values = new ContentValues();
        values.put(CompromissosDBSchema.CompromissoTable.Cols.DATA, compromisso.getData());
        values.put(CompromissosDBSchema.CompromissoTable.Cols.HORA, compromisso.getHora());
        values.put(CompromissosDBSchema.CompromissoTable.Cols.DESCRICAO, compromisso.getDescricao());
        database.insert(CompromissosDBSchema.CompromissoTable.NAME, null, values);
    }

    public List<Compromisso> buscarCompromissosPorData(String data) {
        List<Compromisso> compromissos = new ArrayList<>();
        Cursor cursor = database.query(
                CompromissosDBSchema.CompromissoTable.NAME,
                null,
                CompromissosDBSchema.CompromissoTable.Cols.DATA + " = ?",
                new String[]{data},
                null, null,
                CompromissosDBSchema.CompromissoTable.Cols.HORA + " ASC"
        );

        try {
            if (cursor.moveToFirst()) {
                do {
                    String hora = cursor.getString(cursor.getColumnIndexOrThrow(CompromissosDBSchema.CompromissoTable.Cols.HORA));
                    String descricao = cursor.getString(cursor.getColumnIndexOrThrow(CompromissosDBSchema.CompromissoTable.Cols.DESCRICAO));
                    compromissos.add(new Compromisso(data, hora, descricao));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return compromissos;
    }
}
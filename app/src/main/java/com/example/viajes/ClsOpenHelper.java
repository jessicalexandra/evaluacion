package com.example.viajes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClsOpenHelper  extends SQLiteOpenHelper {

    public ClsOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {



        String viajes = "create table tbl_viajes(" +
                "codigo_viaje text primary key, Ciudad_Destino text not null, " +
                "Cantidad text not null, valor integer not null ,activo not null default 'si')";
        sqLiteDatabase.execSQL(viajes);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE tbl_viajes"); {
            onCreate(sqLiteDatabase);
        }


    }
}
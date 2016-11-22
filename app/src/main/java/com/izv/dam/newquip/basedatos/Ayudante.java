package com.izv.dam.newquip.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;

public class Ayudante extends SQLiteOpenHelper {

    //sqlite
    //tipos de datos https://www.sqlite.org/datatype3.html
    //fechas https://www.sqlite.org/lang_datefunc.html
    //trigger https://www.sqlite.org/lang_createtrigger.html

    private static final int VERSION = 2;

    public Ayudante(Context context) {
        super(context, ContratoBaseDatos.BASEDATOS, null, VERSION);
    }

    public Ayudante(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        //NOTAS
        sql="create table if not exists " + ContratoBaseDatos.TablaNota.TABLA +
                " (" +
                ContratoBaseDatos.TablaNota._ID + " integer primary key autoincrement , " +
                ContratoBaseDatos.TablaNota.TITULO + " text, " +
                ContratoBaseDatos.TablaNota.NOTA + " text, " +
                ContratoBaseDatos.TablaNota.TIPO + " integer, " +
                ContratoBaseDatos.TablaNota.IMG + " text " +
                ")";
        Log.v("sql",sql);
        db.execSQL(sql);

        //LISTAS
        sql="create table if not exists " + ContratoBaseDatos.TablaLista.TABLA +
                " (" +
                ContratoBaseDatos.TablaLista._ID + " integer primary key autoincrement , " +
                ContratoBaseDatos.TablaLista.TIPO + " integer, " +
                ContratoBaseDatos.TablaLista.TITULO + " text " +
                ")";
        Log.v("sql", sql);
        db.execSQL(sql);

        //CONTENIDO LISTAS
        sql="create table if not exists " + ContratoBaseDatos.TablaContenidoLista.TABLA +
                " (" +
                ContratoBaseDatos.TablaContenidoLista._ID + " integer primary key autoincrement , " +
                ContratoBaseDatos.TablaContenidoLista.IDLISTA + " integer, " +
                ContratoBaseDatos.TablaContenidoLista.NOTA + " text " +
                ")";
        Log.v("sql", sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="drop table if exists " + ContratoBaseDatos.TablaNota.TABLA;
        db.execSQL(sql);
        Log.v("sql",sql);
        sql="drop table if exists " + ContratoBaseDatos.TablaLista.TABLA;
        db.execSQL(sql);
        Log.v("sql",sql);
        sql="drop table if exists " + ContratoBaseDatos.TablaContenidoLista.TABLA;
        db.execSQL(sql);
        Log.v("sql",sql);
    }
}
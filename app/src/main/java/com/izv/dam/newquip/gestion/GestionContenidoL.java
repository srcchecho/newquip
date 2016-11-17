package com.izv.dam.newquip.gestion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.pojo.ContenidoLista;

/**
 * Created by dam on 10/11/16.
 */

public class GestionContenidoL extends Gestion<ContenidoLista> {
    public GestionContenidoL(Context c) { //ok
        super(c);
    }

    public GestionContenidoL(Context c, boolean write) { //ok
        super(c, write);
    }


    @Override
    public long insert(ContenidoLista objeto) {
        return 0;
    }

    @Override
    public long insert(ContentValues objeto) {
        return 0;
    }

    @Override
    public int deleteAll() { //ok
        return this.deleteAll(ContratoBaseDatos.TablaContenidoLista.TABLA);
    }

    @Override
    public int delete(ContenidoLista objeto) {
        return 0;
    }

    @Override
    public int update(ContenidoLista objeto) {
        return 0;
    }

    @Override
    public int update(ContentValues valores, String condicion, String[] argumentos) {
        return 0;
    }

    @Override
    public Cursor getCursor() {
        return null;
    }

    @Override
    public Cursor getCursor(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return null;
    }
}

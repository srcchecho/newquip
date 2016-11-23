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
        return this.insert(ContratoBaseDatos.TablaContenidoLista.TABLA, objeto.getContentValues());
    }

    @Override
    public long insert(ContentValues objeto) {
        return this.insert(ContratoBaseDatos.TablaContenidoLista.TABLA, objeto);
    }

    @Override
    public int deleteAll() { //ok
        return this.deleteAll(ContratoBaseDatos.TablaContenidoLista.TABLA);
    }

    @Override
    public int delete(ContenidoLista objeto) {
        String condicion = ContratoBaseDatos.TablaContenidoLista._ID + " = ?";
        String[] argumentos = {objeto.getId() + ""};
        return this.delete(ContratoBaseDatos.TablaContenidoLista.TABLA, condicion, argumentos);
    }

    @Override
    public int update(ContenidoLista objeto) {
        ContentValues valores = objeto.getContentValues();
        String condicion = ContratoBaseDatos.TablaContenidoLista._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        return this.update(ContratoBaseDatos.TablaContenidoLista.TABLA, valores, condicion, argumentos);
    }

    @Override
    public int update(ContentValues valores, String condicion, String[] argumentos) {
        return this.update(ContratoBaseDatos.TablaContenidoLista.TABLA, valores, condicion, argumentos);
    }

    @Override
    public Cursor getCursor() {
        return this.getCursor(ContratoBaseDatos.TablaContenidoLista.TABLA, ContratoBaseDatos.TablaContenidoLista.PROJECTION_ALL, ContratoBaseDatos.TablaContenidoLista.SORT_ORDER_DEFAULT);
    }

    @Override
    public Cursor getCursor(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return this.getCursor(ContratoBaseDatos.TablaContenidoLista.TABLA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}

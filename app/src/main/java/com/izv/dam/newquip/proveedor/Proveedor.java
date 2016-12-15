package com.izv.dam.newquip.proveedor;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.gestion.GestionLista;
import com.izv.dam.newquip.gestion.GestionNota;
import com.izv.dam.newquip.gestion.GestionUnion;
import com.izv.dam.newquip.util.UtilCadenas;

/**
 * Created by dam on 2/11/16.
 */

public class Proveedor extends ContentProvider {


    private static final UriMatcher URI_MATCHER;
    private static final int TODO_NOTA = 0;
    private static final int CONCRETO_NOTA = 1;
    private static final int TODO_LISTA = 2;
    private static final int CONCRETO_LISTA = 3;
    private static final int TODO_JOIN = 4;

    private GestionNota gestorNota;
    private GestionLista gestorLista;
    private GestionUnion gestorUnion;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(ContratoBaseDatos.AUTORIDAD, ContratoBaseDatos.TablaNota.TABLA, TODO_NOTA);//cursor
        URI_MATCHER.addURI(ContratoBaseDatos.AUTORIDAD, ContratoBaseDatos.TablaNota.TABLA + "/#", CONCRETO_NOTA);

        URI_MATCHER.addURI(ContratoBaseDatos.AUTORIDAD, ContratoBaseDatos.TablaLista.TABLA, TODO_LISTA);//cursor
        URI_MATCHER.addURI(ContratoBaseDatos.AUTORIDAD, ContratoBaseDatos.TablaLista.TABLA + "/#", CONCRETO_LISTA);

        URI_MATCHER.addURI(ContratoBaseDatos.AUTORIDAD, ContratoBaseDatos.Join.TABLA, TODO_JOIN);
    }

    public Proveedor() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int borrados = 0;
        switch (URI_MATCHER.match(uri)) {
            case TODO_NOTA:
                borrados = gestorNota.delete(selection, selectionArgs);
                break;
            case CONCRETO_NOTA:
                String id = uri.getLastPathSegment();
                borrados = gestorNota.delete(ContratoBaseDatos.TablaNota._ID + " = ?", new String[]{id});
                break;
            case TODO_LISTA:
                borrados = gestorLista.delete(selection, selectionArgs);
                break;
            case CONCRETO_LISTA:
                id = uri.getLastPathSegment();
                borrados = gestorLista.delete(ContratoBaseDatos.TablaLista._ID + " = ?", new String[]{id});
                break;
        }
        if (borrados > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return borrados;

    }

    @Override
    public boolean onCreate() {
        gestorLista = new GestionLista(getContext());
        gestorNota = new GestionNota(getContext());
        gestorUnion = new GestionUnion(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor c = null;
        Log.v("depurando", uri.toString() + " " + URI_MATCHER.match(uri));
        switch (URI_MATCHER.match(uri)) {
            case TODO_NOTA:
                c = gestorNota.getCursor(projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CONCRETO_NOTA:
                String id = uri.getLastPathSegment();
                c = gestorNota.getCursor(projection, ContratoBaseDatos.TablaNota._ID + " = ?", new String[]{id}, null, null, sortOrder);
                break;
            case TODO_LISTA:
                c = gestorLista.getCursor(projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CONCRETO_LISTA:
                id = uri.getLastPathSegment();
                c = gestorLista.getCursor(projection, ContratoBaseDatos.TablaLista._ID + " = ?", new String[]{id}, null, null, sortOrder);
                break;
            case TODO_JOIN:
                c = gestorUnion.getCursor();
                break;
        }
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }


    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case TODO_NOTA:
                return ContratoBaseDatos.TablaNota.CONTENT_TYPE;
            case CONCRETO_NOTA:
                return ContratoBaseDatos.TablaNota.CONTENT_ITEM_TYPE;
            case TODO_LISTA:
                return ContratoBaseDatos.TablaLista.CONTENT_TYPE;
            case CONCRETO_LISTA:
                return ContratoBaseDatos.TablaLista.CONTENT_ITEM_TYPE;
            case TODO_JOIN:
                return ContratoBaseDatos.Join.CONTENT_TYPE;
        }
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = 0;
        switch (URI_MATCHER.match(uri)) {
            case TODO_NOTA:
                id = gestorNota.insert(values);
                break;
            case TODO_LISTA:
                id = gestorLista.insert(values);
                break;
        }
        if (id > 0) {
            Uri uriInsert = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(uriInsert, null);
            return uriInsert;
        }
        throw new IllegalArgumentException("");

    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int valor = 0;
        String id;
        String[] newSelectionArgs;
        switch (URI_MATCHER.match(uri)) {
            case TODO_NOTA:
                valor = gestorNota.update(values, selection, selectionArgs);
                break;
            case CONCRETO_NOTA:
                id = uri.getLastPathSegment();
                selection = UtilCadenas.getCondicionesSql(selection, ContratoBaseDatos.TablaNota._ID + " = ? ");
                newSelectionArgs = UtilCadenas.getNewArray(selectionArgs, id);
                valor = gestorNota.update(values, selection, newSelectionArgs);
                break;
            case TODO_LISTA:
                valor = gestorLista.update(values, selection, selectionArgs);
                break;
            case CONCRETO_LISTA:
                id = uri.getLastPathSegment();
                selection = UtilCadenas.getCondicionesSql(selection, ContratoBaseDatos.TablaLista._ID + " = ? ");
                newSelectionArgs = UtilCadenas.getNewArray(selectionArgs, id);
                valor = gestorLista.update(values, selection, newSelectionArgs);
                break;
        }
        if (valor > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return valor;

    }
}

package com.izv.dam.newquip.basedatos;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoNota;
import com.izv.dam.newquip.gestion.GestionNota;
import com.izv.dam.newquip.util.UtilCadenas;

import static com.izv.dam.newquip.contrato.ContratoBaseDatos.TablaNota.CONTENT_ITEM_TYPE;
import static com.izv.dam.newquip.contrato.ContratoBaseDatos.TablaNota.CONTENT_TYPE;

public class Proveedor extends ContentProvider {

    private static final UriMatcher URI_MATCHER;
    private static final int TODO = 0;
    private static final int CONCRETO = 1;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(ContratoBaseDatos.TablaNota.AUTORIDAD, ContratoBaseDatos.TablaNota.TABLA, TODO);//cursor
        URI_MATCHER.addURI(ContratoBaseDatos.TablaNota.AUTORIDAD, ContratoBaseDatos.TablaNota.TABLA + "/#", CONCRETO);
    }

    public Proveedor() {
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)){
            case TODO:
                return CONTENT_TYPE;
            case CONCRETO:
                return CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //uri -> TODO, CONCRETO
        switch (URI_MATCHER.match(uri)){
            case TODO:
                GestionNota gestor = new GestionNota(getContext());
                String id = uri.getLastPathSegment();

                /*delete from tabla where id = ?*/
                //borrados = gestor.deleteById(id);

                /*delete from tabla where id=:id and selection*/
                //selection = " edad = ? and color = ? "
            case CONCRETO:
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }


}

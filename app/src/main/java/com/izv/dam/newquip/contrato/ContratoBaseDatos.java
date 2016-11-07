package com.izv.dam.newquip.contrato;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ContratoBaseDatos {

    public final static String BASEDATOS = "quiip.sqlite";

    private ContratoBaseDatos(){
    }

    public static abstract class TablaNota implements BaseColumns {
        //BaseColumns incluye de forma predeterminada el campo _id
        public static final String TABLA = "nota";
        public static final String TITULO = "titulo";
        public static final String NOTA = "nota";
        public static final String IMG = "img";
        /*gestor*/
        public static final String[] PROJECTION_ALL = {_ID, TITULO, NOTA, IMG};
        public static final String SORT_ORDER_DEFAULT = _ID + " desc";
        /*proveedor*/
        public static final String AUTORIDAD = "com.izv.dam.newquip.basedatos";
        public final static Uri CONTENT_URI = Uri.parse("content://" + AUTORIDAD);
        /*MIME*/
        public final static String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
        public final static String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;

    }
}
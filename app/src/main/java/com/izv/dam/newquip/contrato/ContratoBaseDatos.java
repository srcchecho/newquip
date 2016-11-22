package com.izv.dam.newquip.contrato;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ContratoBaseDatos {

    public final static String BASEDATOS = "quiip.sqlite";
    public final static String AUTORIDAD = "com.izv.dam.newquip.proveedor";
    public final static Uri CONTENT_URI = Uri.parse("content://"+AUTORIDAD);

    public static final Uri NOTA_URI = Uri.withAppendedPath(CONTENT_URI, ContratoBaseDatos.TablaNota.TABLA);
    public static final Uri LISTA_URI = Uri.withAppendedPath(CONTENT_URI, ContratoBaseDatos.TablaLista.TABLA);
    public static final Uri CONTENIDO_L_URI = Uri.withAppendedPath(CONTENT_URI, TablaContenidoLista.TABLA);
    public static final Uri JOINNOTALISTA_URI = Uri.withAppendedPath(CONTENT_URI, Join.TABLA);

    private ContratoBaseDatos(){
    }

    //NOTAS
    public static abstract class TablaNota implements BaseColumns {
        //BaseColumns incluye de forma predeterminada el campo _id
        public static final String TABLA = "nota";
        public static final String TITULO = "titulo";
        public static final String NOTA = "nota";
        public static final String TIPO = "tipo";
        public static final String IMG = "img";
        /*gestor*/
        public static final String[] PROJECTION_ALL = {_ID, TITULO, NOTA, TIPO, IMG};
        public static final String SORT_ORDER_DEFAULT = _ID + " desc";
        /*MIME*/
        public final static String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
        public final static String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
    }

    //LISTAS
    public static abstract class TablaLista implements BaseColumns {
        public static final String TABLA = "lista";
        public static final String TITULO = "titulo";
        public static final String TIPO = "tipo";
        public static final String[] PROJECTION_ALL = {_ID, TITULO, TIPO};
        public static final String SORT_ORDER_DEFAULT = _ID + " desc";

        public final static String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
        public final static String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
    }

    //CONTENIDO LISTAS
    public static abstract class TablaContenidoLista implements BaseColumns {
        public static final String TABLA = "contenido";
        public static final String IDLISTA = "idlista";
        public static final String NOTA = "nota";
        public static final String[] PROJECTION_ALL = {_ID, IDLISTA, NOTA};
        public static final String SORT_ORDER_DEFAULT = _ID + " desc";

        public final static String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
        public final static String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
    }

    //JOIN
    public static abstract class Join {
        public static final String TABLA = "joinnotalista";
        public final static String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." +AUTORIDAD + "." + TABLA;

    }
}
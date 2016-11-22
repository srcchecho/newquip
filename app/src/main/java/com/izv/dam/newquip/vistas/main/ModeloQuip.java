package com.izv.dam.newquip.vistas.main;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.gestion.GestionLista;
import com.izv.dam.newquip.gestion.GestionNota;
import com.izv.dam.newquip.gestion.GestionUnion;
import com.izv.dam.newquip.pojo.Join;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;

public class ModeloQuip implements ContratoMain.InterfaceModelo {

    private Context c;
    private GestionNota gn = null;
    private GestionLista gl = null;
    private Cursor cursor, cursorl, cursor2;
    private GestionUnion gu = null;

    public ModeloQuip(Context c) {
        this.c = c;
        gn = new GestionNota(c);
        gu = new GestionUnion(c);
        gl = new GestionLista(c);
    }

    @Override
    public void close() {
        gn.close();
    }

    @Override
    public long deleteNota(Nota n) {
        return gn.delete(n);
    }

    @Override
    public long deleteLista(Lista l) {
        return gl.delete(l);
    }

    @Override
    public long deleteNota(int position) {
        cursor.moveToPosition(position);
        Nota n = Nota.getNota(cursor);
        return this.deleteNota(n);
    }

    @Override
    public long deleteJoin(Join j) {
        Uri u = null;
        if (j.getTipo() == 1) {
            u = Uri.withAppendedPath(ContratoBaseDatos.NOTA_URI, j.getId() + "");
        } else if (j.getTipo() == 2) {
            u = Uri.withAppendedPath(ContratoBaseDatos.LISTA_URI, j.getId() + "");
        }
        c.getContentResolver().delete(u, null, null);
        return 0;
    }


    @Override
    public Nota getNota(int position) {
        cursor.moveToPosition(position);
        Nota n = Nota.getNota(cursor);
        return n;
    }

    @Override
    public Lista getLista(int position) {
        cursor.moveToPosition(position);
        Lista l = Lista.getLista(cursor);
        return l;
    }

    @Override
    public Join getJoin(int position) {
        cursor.moveToPosition(position);
        Join n = Join.getJoin(cursor);
        return n;
    }

    @Override
    public void loadData(OnDataLoadListener listener) {
        cursor = gn.getCursor();
        listener.setCursor(cursor);
        cursorl = gl.getCursor();
        listener.setCursorL(cursorl);
        cursor2 = gu.getCursor();
        listener.setCursorJ(cursor2);
    }

    public void changeCursor(Cursor c){
        this.cursor=c;
    }
}
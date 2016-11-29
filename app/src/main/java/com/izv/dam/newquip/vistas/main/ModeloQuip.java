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
    private Cursor cursorN, cursorl, cursorJ;
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
        cursorN.moveToPosition(position);
        Nota n = Nota.getNota(cursorN);
        return this.deleteNota(n);
    }

    @Override
    public long deleteLista(int position) {
        cursorl.moveToPosition(position);
        Lista l = Lista.getLista(cursorl);
        return this.deleteLista(l);
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
        cursorN.moveToPosition(position);
        Nota n = Nota.getNota(cursorN);
        return n;
    }

    @Override
    public Lista getLista(int position) {
        cursorl.moveToPosition(position);
        Lista l = Lista.getLista(cursorl);
        return l;
    }

    @Override
    public Join getJoin(int position) {
        cursorJ.moveToPosition(position);
        Join n = Join.getJoin(cursorJ);
        return n;
    }

    @Override
    public void loadData(OnDataLoadListener listener) {
        cursorJ = gu.getCursor();
        listener.setCursorJ(cursorJ);
    }

    @Override
    public void loadDataN(OnDataLoadListenerN listener) {
        cursorN = gn.getCursor();
        listener.setCursorN(cursorN);
    }

    @Override
    public void loadDataL(OnDataLoadListenerL listener) {
        cursorl = gl.getCursor();
        listener.setCursorL(cursorl);
    }

}
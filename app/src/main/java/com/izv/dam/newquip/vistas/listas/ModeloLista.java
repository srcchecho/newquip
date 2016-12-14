package com.izv.dam.newquip.vistas.listas;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoLista;
import com.izv.dam.newquip.gestion.GestionContenidoL;
import com.izv.dam.newquip.gestion.GestionLista;
import com.izv.dam.newquip.gestion.GestionNota;
import com.izv.dam.newquip.pojo.ContenidoLista;
import com.izv.dam.newquip.pojo.Lista;

import static android.R.attr.id;

/**
 * Created by dam on 18/10/16.
 */

public class ModeloLista implements ContratoLista.InterfaceModelo {

    Context c;
    //GESTION
    private GestionLista gl = null;
    private GestionContenidoL gcl = null;
    private Cursor cursor, cursorcl;

    public ModeloLista(Context c) {
        this.c = c;
        gl = new GestionLista(c);
        gcl = new GestionContenidoL(c);
    }



    //METODOS CL

    @Override
    public void close() {
        gl.close();
    }


    @Override
    public long deleteLista(Lista l) {return gl.delete(l);}

    @Override
    public long saveLista(Lista l) {
        long r;
        if(l.getId()==0){
            r = this.insertLista(l);
        } else {
            r = this.updateLista(l);
        }
        return r;
    }

    private long updateLista(Lista l) {
        if(l.getTitulo().trim().compareTo("")==0 && l.getTitulo().trim().compareTo("")==0){
            Uri u = Uri.withAppendedPath(ContratoBaseDatos.NOTA_URI, l.getId() + "");
            c.getContentResolver().delete(u, null, null);
            return 0;
        }
        return c.getContentResolver().update(ContratoBaseDatos.LISTA_URI, l.getContentValues(), null, null);
    }

    private long insertLista(Lista l){
        if(l.getTitulo().trim().compareTo("")==0 && l.getTitulo().trim().compareTo("")==0){
            return 0;
        }
        Uri u = c.getContentResolver().insert(ContratoBaseDatos.LISTA_URI, l.getContentValues());
        if (u.toString() != null){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public long saveContenidoLista(ContenidoLista cl) {
        long r;
        if(cl.getId()==0){
            r = this.insertContenidoLista(cl);
        } else {
            r = this.updateContenidoLista(cl);
        }
        return r;
    }


    public void loadData(OnDataLoadListener listener, long idlista) {
        cursor = gcl.getCursor(idlista);
        listener.setCursor(cursor);
    }

    private long insertContenidoLista(ContenidoLista cl){
        if(cl.getNota().trim().compareTo("")==0 && cl.getNota().trim().compareTo("")==0){
            return 0;
        }
        return gcl.insert(cl);
    }

    private long updateContenidoLista(ContenidoLista cl) {
        if(cl.getNota().trim().compareTo("")==0 && cl.getNota().trim().compareTo("")==0){
            this.deleteContenidoLista(cl);
            gcl.delete(cl);
            return 0;
        }
        return gcl.update(cl);
    }

    private long deleteContenidoLista(ContenidoLista cl) {return gcl.delete(cl);}
}

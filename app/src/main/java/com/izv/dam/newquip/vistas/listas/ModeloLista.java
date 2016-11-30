package com.izv.dam.newquip.vistas.listas;

import android.content.Context;
import android.database.Cursor;

import com.izv.dam.newquip.contrato.ContratoLista;
import com.izv.dam.newquip.contrato.ContratoMain;
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


    //GESTION
    private GestionLista gl = null;
    private GestionContenidoL gcl = null;
    private Cursor cursor, cursorcl;

    public ModeloLista(Context c) {gl = new GestionLista(c);}

    Context c;


    //METODOS CL

    @Override
    public void close() {
        gl.close();
    }


    private long deleteLista(Lista l) {return gl.delete(l);}

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
            this.deleteLista(l);
            gl.delete(l);
            return 0;
        }
        return gl.update(l);
    }

    private long insertLista(Lista l){
        if(l.getTitulo().trim().compareTo("")==0 && l.getTitulo().trim().compareTo("")==0){
            return 0;
        }
        return gl.insert(l);
    }

    //contenido lista

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

    @Override
    public void loadData(OnDataLoadListener listener) {
        cursor = gcl.getCursor();
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

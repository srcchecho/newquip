package com.izv.dam.newquip.vistas.listas;

import android.content.Context;

import com.izv.dam.newquip.contrato.ContratoLista;
import com.izv.dam.newquip.gestion.GestionLista;
import com.izv.dam.newquip.gestion.GestionNota;
import com.izv.dam.newquip.pojo.Lista;

import static android.R.attr.id;

/**
 * Created by dam on 18/10/16.
 */

public class ModeloLista implements ContratoLista.InterfaceModelo {


    //GESTION
    private GestionLista gl = null;

    public ModeloLista(Context c) {gl = new GestionLista(c);}

    Context c;


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
}

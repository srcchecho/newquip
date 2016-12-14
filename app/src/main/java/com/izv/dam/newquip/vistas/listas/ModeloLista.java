package com.izv.dam.newquip.vistas.listas;

import android.content.Context;
import android.net.Uri;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoLista;
import com.izv.dam.newquip.gestion.GestionLista;
import com.izv.dam.newquip.gestion.GestionNota;
import com.izv.dam.newquip.pojo.Lista;

import static android.R.attr.id;

/**
 * Created by dam on 18/10/16.
 */

public class ModeloLista implements ContratoLista.InterfaceModelo {

    Context c;
    //GESTION
    private GestionLista gl = null;

    public ModeloLista(Context c){
        this.c = c;
        gl = new GestionLista(c);
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
        return gl.update(l);
    }

    private long insertLista(Lista l){
        if(l.getTitulo().trim().compareTo("")==0 && l.getTitulo().trim().compareTo("")==0){
            return 0;
        }
        System.out.println("ID: " + ContratoBaseDatos.LISTA_URI);
        Uri u = c.getContentResolver().insert(ContratoBaseDatos.LISTA_URI, l.getContentValues());
        if (u.toString() != null){
            return 1;
        }else {
            return 0;
        }
    }
}
package com.izv.dam.newquip.vistas.notas;

import android.content.Context;
import android.net.Uri;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoNota;
import com.izv.dam.newquip.gestion.GestionNota;
import com.izv.dam.newquip.pojo.Nota;

public class ModeloNota implements ContratoNota.InterfaceModelo {

    private GestionNota gn = null;
    Context c;

    public ModeloNota(Context c) {
        gn = new GestionNota(c);
        this.c = c;
    }

    @Override
    public void close() {
        gn.close();
    }

    @Override
    public Nota getNota(long id) {
        return gn.get(id);
    }

    @Override
    public long deleteNota(Nota n) {
        return gn.delete(n);
    }

    @Override
    public long saveNota(Nota n) {
        long r;
        if(n.getId()==0) {
             r = this.insertNota(n);
        } else {
            r = this.updateNota(n);
        }
        return r;
    }



    private long insertNota(Nota n) {
        if(n.getNota().trim().compareTo("")==0 && n.getTitulo().trim().compareTo("")==0) {
            return 0;
        }
        String where = "_ID" + n.getId();
        Uri u = c.getContentResolver().insert(ContratoBaseDatos.NOTA_URI, n.getContentValues());
        if (u.toString() != null){
            return 1;
        }else{
            return 0;
        }
    }

    private long updateNota(Nota n) {
        if(n.getNota().trim().compareTo("")==0 && n.getTitulo().trim().compareTo("")==0) {
            this.deleteNota(n);
            gn.delete(n);
            return 0;
        }
        return gn.update(n);
    }
}
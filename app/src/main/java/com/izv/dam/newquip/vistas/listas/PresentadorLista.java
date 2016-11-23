package com.izv.dam.newquip.vistas.listas;

import android.content.Context;

import com.izv.dam.newquip.contrato.ContratoLista;
import com.izv.dam.newquip.pojo.ContenidoLista;
import com.izv.dam.newquip.pojo.Lista;

/**
 * Created by dam on 18/10/16.
 */

public class PresentadorLista implements ContratoLista.InterfacePresentador {

    private ContratoLista.InterfaceModelo modelo;
    private ContratoLista.InterfaceVista vista;

    public PresentadorLista(ContratoLista.InterfaceVista vista){
        this.vista = vista;
        this.modelo = new ModeloLista((Context)vista);
    }

    public void onPause(){}

    public void onResume(){}

    public long onSaveLista(Lista l) { return this.modelo.saveLista(l); }

    public long onSaveContenidoLista(ContenidoLista cl) { return this.modelo.saveContenidoLista(cl); }
}

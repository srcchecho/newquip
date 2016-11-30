package com.izv.dam.newquip.vistas.listas;

import android.content.Context;
import android.database.Cursor;

import com.izv.dam.newquip.contrato.ContratoLista;
import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.pojo.ContenidoLista;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.vistas.main.PresentadorQuip;

/**
 * Created by dam on 18/10/16.
 */

public class PresentadorLista implements ContratoLista.InterfacePresentador {

    private ContratoLista.InterfaceModelo modelo;
    private ContratoLista.InterfaceVista vista;
    private ContratoLista.InterfaceModelo.OnDataLoadListener oyente;

    public PresentadorLista(ContratoLista.InterfaceVista vista){
        this.vista = vista;
        this.modelo = new ModeloLista((Context)vista);
        oyente = new ContratoLista.InterfaceModelo.OnDataLoadListener() {

            @Override
            public void setCursor(Cursor c) {
                PresentadorLista.this.vista.mostrarDatosCL(c);
            }

        };
    }

    public void onPause(){}

    public void onResume(){}

    public long onSaveLista(Lista l) { return this.modelo.saveLista(l); }

    public long onSaveContenidoLista(ContenidoLista cl) { return this.modelo.saveContenidoLista(cl); }
}

package com.izv.dam.newquip.vistas.main;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.pojo.Join;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;

public class PresentadorQuip implements ContratoMain.InterfacePresentador{

    private ContratoMain.InterfaceModelo modelo;
    private ContratoMain.InterfaceVista vista;
    private ContratoMain.InterfaceModelo.OnDataLoadListener oyente;
    private ContratoMain.InterfaceModelo.OnDataLoadListenerN oyente2;
    private ContratoMain.InterfaceModelo.OnDataLoadListenerL oyente3;

    public PresentadorQuip(ContratoMain.InterfaceVista vista) {
        this.vista = vista;
        this.modelo = new ModeloQuip((Context)vista);
        oyente = new ContratoMain.InterfaceModelo.OnDataLoadListener() {
            @Override
            public void setCursorJ(Cursor c) {
                PresentadorQuip.this.vista.mostrarDatosJ(c);
            }
        };

        oyente2 = new ContratoMain.InterfaceModelo.OnDataLoadListenerN() {
            @Override
            public void setCursorN(Cursor c) {
                PresentadorQuip.this.vista.mostrarDatosN(c);
            }
        };

        oyente3 = new ContratoMain.InterfaceModelo.OnDataLoadListenerL() {
            @Override
            public void setCursorL(Cursor c) {
                PresentadorQuip.this.vista.mostrarDatosL(c);
            }
        };
    }

    @Override
    public void onAddLista() {
        this.vista.mostrarAgregarLista();
    }

    @Override
    public void onAddNota() {
        this.vista.mostrarAgregarNota();
    }

    @Override
    public void onDeleteJoin(int position) {
        Join j = this.modelo.getJoin(position);
        this.modelo.deleteJoin(j);
        this.modelo.loadData(oyente);
    }

    @Override
    public void onDeleteJoin(Join j) {
        this.modelo.deleteJoin(j);
        this.modelo.loadData(oyente);
    }

    @Override
    public void onDeleteNotaJ(int position) {
        Join j = this.modelo.getJoin(position);
        this.modelo.deleteJoin(j);
        this.modelo.loadData(oyente);
    }

    @Override
    public void onDeleteNota(Nota n) {
        this.modelo.deleteNota(n);
        this.modelo.loadDataN(oyente2);
    }

    @Override
    public void onDeleteLista(Lista l) {
        this.modelo.deleteLista(l);
        this.modelo.loadDataL(oyente3);
    }

    @Override
    public void onEditNota(Nota n) {
        this.vista.mostrarEditarNota(n);
    }

    @Override
    public void onEditLista(Lista l) { this.vista.mostrarEditarLista(l); }

    @Override
    public void onPause() {
    }

    @Override
    public void onResume() {
        this.modelo.loadData(oyente);
        this.modelo.loadDataN(oyente2);
        this.modelo.loadDataL(oyente3);
    }

    @Override
    public void onShowBorrarJoin(int position) {
        Join j = this.modelo.getJoin(position);
        if(j.getTipo()==1) {
            this.vista.mostrarConfirmarBorrarJoin(j);
        }else {
            this.vista.mostrarConfirmarBorrarJoin(j);
        }
    }

    @Override
    public void onShowBorrarLista(int position) {
        Lista l = this.modelo.getLista(position);
        this.vista.mostrarConfirmarBorrarLista(l);
    }

    @Override
    public void onShowBorrarNota(int position) {
        Nota n = this.modelo.getNota(position);
        this.vista.mostrarConfirmarBorrarNota(n);
    }

    @Override
    public void onDeleteNota(int position) {
        this.modelo.deleteNota(position);
        this.modelo.loadDataN(oyente2);
    }

    @Override
    public void onDeleteLista(int position) {
        this.modelo.deleteLista(position);
        this.modelo.loadDataL(oyente3);
    }

    @Override
    public void onEditNota(int position) {
        Nota n = this.modelo.getNota(position);
        this.onEditNota(n);
    }

    @Override
    public void onEditLista(int position) {
        Lista l = this.modelo.getLista(position);
        this.onEditLista(l);
    }

    @Override
    public void onEditJoin(int position) {
        Join j = this.modelo.getJoin(position);
        if(j.getTipo()==1){
            Nota n = new Nota(j.getId(), j.getTitulo(), j.getTexto(), j.getImg());
            this.onEditNota(n);
        } else {
            Lista l = new Lista(j.getId(), j.getTitulo());
            this.onEditLista(l);
        }

    }
}
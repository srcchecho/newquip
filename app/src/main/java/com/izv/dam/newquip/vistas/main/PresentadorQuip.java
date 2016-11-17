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

    public PresentadorQuip(ContratoMain.InterfaceVista vista) {
        this.vista = vista;
        this.modelo = new ModeloQuip((Context)vista);
        oyente = new ContratoMain.InterfaceModelo.OnDataLoadListener() {
            @Override
            public void setCursor(Cursor c) {
                PresentadorQuip.this.vista.mostrarDatosN(c);
            }

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
    public void onDeleteNota(Nota n) {
        this.modelo.deleteNota(n);
        this.modelo.loadData(oyente);
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
    }

    @Override
    public void onShowBorrarJoin(int position) {
        Join j = this.modelo.getJoin(position);
        this.vista.mostrarConfirmarBorrarJoin(j);
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
        this.modelo.loadData(oyente);
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

    public void changeCursor(Cursor c){
        this.modelo.changeCursor(c);
    }

}

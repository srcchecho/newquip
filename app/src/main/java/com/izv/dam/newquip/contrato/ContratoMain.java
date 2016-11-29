package com.izv.dam.newquip.contrato;

import android.database.Cursor;

import com.izv.dam.newquip.pojo.Join;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;

public interface ContratoMain {

    interface InterfaceModelo {

        void close();

        long deleteLista(int position);

        long deleteJoin(Join j);

        long deleteLista(Lista l);

        long deleteNota(int position);

        long deleteNota(Nota n);

        Join getJoin(int position);

        Nota getNota(int position);

        Lista getLista(int position);

        void loadData(OnDataLoadListener listener);

        interface OnDataLoadListener {
            public void setCursorJ(Cursor c);
        }


        void loadDataN(OnDataLoadListenerN listener);

        interface OnDataLoadListenerN {
            public void setCursorN(Cursor c);
        }

        void loadDataL(OnDataLoadListenerL listener);

        interface OnDataLoadListenerL {
            public void setCursorL(Cursor c);
        }

    }

    interface InterfacePresentador {

        void onAddLista();

        void onAddNota();

        void onDeleteJoin(int position);

        void onDeleteJoin(Join n);

        void onDeleteNota(int position);

        void onDeleteNotaJ(int position);

        void onDeleteNota(Nota n);

        void onDeleteLista(int position);

        void onEditNota(int position);

        void onDeleteLista(Lista l);

        void onEditNota(Nota n);

        void onPause();

        void onResume();

        void onShowBorrarJoin(int position);

        void onShowBorrarLista(int position);

        void onShowBorrarNota(int position);

        void onEditLista(Lista l);

        void onEditLista(int position);

        void onEditJoin(int position);
    }

    interface InterfaceVista {

        void mostrarAgregarLista();

        void mostrarAgregarNota();

        void mostrarConfirmarBorrarJoin(Join n);

        void mostrarConfirmarBorrarNota(Nota n);

        void mostrarConfirmarBorrarLista(Lista l);

        void mostrarDatosN(Cursor c);

        void mostrarDatosL(Cursor c);

        void mostrarDatosJ(Cursor c);

        void mostrarEditarNota(Nota n);

        void mostrarEditarLista(Lista l);

        void onItemClickListenerL(int pos);
    }

}
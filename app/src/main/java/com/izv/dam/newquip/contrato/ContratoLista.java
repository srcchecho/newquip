package com.izv.dam.newquip.contrato;

import android.database.Cursor;

import com.izv.dam.newquip.pojo.ContenidoLista;
import com.izv.dam.newquip.pojo.Lista;

/**
 * Created by dam on 17/11/2016.
 */

public interface ContratoLista {
    interface InterfaceModelo {

        void close();

        long deleteLista(Lista l);

        long saveLista(Lista n);

        long saveContenidoLista(ContenidoLista cl);

        void loadData(OnDataLoadListener oyente, long id);

        long deleteContenidoLista(ContenidoLista cl);

        long deleteAllContenidoLista(long id);

        interface OnDataLoadListener {
            public void setCursor(Cursor c);
        }
    }

    interface InterfacePresentador {

        void onPause();

        void onResume();

        long onSaveLista(Lista n);

        long onDeleteLista(Lista n);
    }

    interface InterfaceVista {

        void mostrarLista(Lista n);

        void mostrarConfirmarBorrarLista(Lista l);

        void mostrarDatosCL(Cursor c);
    }
}

package com.izv.dam.newquip.contrato;

import com.izv.dam.newquip.pojo.ContenidoLista;
import com.izv.dam.newquip.pojo.Lista;

/**
 * Created by dam on 17/11/2016.
 */

public interface ContratoLista {
    interface InterfaceModelo {

        void close();

        long saveLista(Lista n);

        long saveContenidoLista(ContenidoLista cl);
    }

    interface InterfacePresentador {

        void onPause();

        void onResume();

        long onSaveLista(Lista n);

    }

    interface InterfaceVista {

        void mostrarLista(Lista n);

    }
}

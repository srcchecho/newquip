package com.izv.dam.newquip.contrato;

import com.izv.dam.newquip.pojo.ContenidoLista;

/**
 * Created by dam on 17/11/2016.
 */

public interface ContratoContenido {
    interface InterfaceModelo {

        void close();

        ContenidoLista getNota(long id);

        long saveNota(ContenidoLista n);

    }

    interface InterfacePresentador {

        void onPause();

        void onResume();

        long onSaveNota(ContenidoLista n);

    }

    interface InterfaceVista {

        void mostrarNota(ContenidoLista n);

    }
}

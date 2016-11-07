package com.izv.dam.newquip.contrato;

import com.izv.dam.newquip.pojo.Nota;

public interface ContratoNota {

    interface InterfaceModelo {

        void close();

        Nota getNota(long id);

        long saveNota(Nota n);

        long deleteNota(Nota n);

    }

    interface InterfacePresentador {

        void onPause();

        void onResume();

        long onSaveNota(Nota n);

        long onDeleteNota(Nota n);
    }

    interface InterfaceVista {

        void mostrarNota(Nota n);

        void mostrarConfirmarBorrarNota(Nota n);

        void onBorrarPossitiveButtonClick(Nota n);

        void onBorrarNegativeButtonClick();
    }

}
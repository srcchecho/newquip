package com.izv.dam.newquip.dialogo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;

/**
 * Created by Pilar on 26/09/2016.
 */

public class DialogoBorrar extends DialogFragment {
    private Nota n;
    private Lista l;
    // Interfaz de comunicación
    OnBorrarDialogListener listener;

    public DialogoBorrar() {
    }

    public static DialogoBorrar newInstance(Nota n) {
        DialogoBorrar fragment = new DialogoBorrar();
        Bundle args = new Bundle();
        args.putParcelable("objnota",n);
        args.putInt("nota", 1);
        fragment.setArguments(args);
        return fragment;
    }

    public static DialogoBorrar newInstance(Lista l) {
        DialogoBorrar fragment = new DialogoBorrar();
        Bundle args = new Bundle();
        args.putParcelable("objlista",l);
        args.putInt("lista", 2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().getInt("nota") == 1) {
            n=getArguments().getParcelable("objnota");
            System.out.println(n.toString());
        }if (getArguments() != null && getArguments().getInt("lista") == 2){
            l=getArguments().getParcelable("objlista");
            System.out.println(l.toString());
        }
        System.out.println("ARGS: " + getArguments().getInt("nota") + " y " + getArguments().getInt("lista"));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogBorrar();
    }
    public AlertDialog createDialogBorrar() {
        if (getArguments().getInt("nota") == 1) {
            String titulo_dialogo = String.format("%s %s", getString(R.string.etiqueta_dialogo_borrar), n.getTitulo());
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(titulo_dialogo);
            builder.setMessage(R.string.mensaje_confirm_borrar);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onBorrarPossitiveButtonClick(n);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onBorrarNegativeButtonClick();
                }
            });
            AlertDialog alertBorrar = builder.create();
            return alertBorrar;
        }
        if (getArguments().getInt("lista") == 2) {
            String titulo_dialogo2 = String.format("%s %s", getString(R.string.etiqueta_dialogo_borrar), l.getTitulo());
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(titulo_dialogo2);
            builder.setMessage(R.string.mensaje_confirm_borrar2);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onBorrarPossitiveButtonClickL(l);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onBorrarNegativeButtonClick();
                }
            });
            AlertDialog alertBorrar = builder.create();
            return alertBorrar;
        }
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnBorrarDialogListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnBorrarDialogListener");

        }
    }

}


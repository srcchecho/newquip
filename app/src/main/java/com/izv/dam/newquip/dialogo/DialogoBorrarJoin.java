package com.izv.dam.newquip.dialogo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.pojo.Join;

/**
 * Created by dam on 10/11/16.
 */

public class DialogoBorrarJoin extends DialogFragment {
    private Join n;

    OnBorrarJoinDialogListener listener;

    public DialogoBorrarJoin() {
    }

    public static DialogoBorrarJoin newInstance(Join n) {
        DialogoBorrarJoin fragment = new DialogoBorrarJoin();
        Bundle args = new Bundle();
        args.putParcelable("nota",n);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            n=getArguments().getParcelable("nota");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogBorrar();
    }

    public AlertDialog createDialogBorrar() {
        String titulo_dialogo= String.format("%s %s", getString(R.string.etiqueta_dialogo_borrar),n.getTitulo());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titulo_dialogo);
        builder.setMessage(R.string.mensaje_confirm_borrar);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onBorrarPossitiveButtonClickJ(n);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onBorrarNegativeButtonClickJ();
            }
        });
        AlertDialog alertBorrar = builder.create();
        return alertBorrar;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnBorrarJoinDialogListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnBorrarJoinDialogListener");
        }
    }


}

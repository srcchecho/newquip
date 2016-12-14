package com.izv.dam.newquip.dialogo;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.pojo.Lista;


/**
 * Created by dam on 29/11/16.
 */

public class DialogoLista extends DialogFragment{
    public DialogoLista(){}
    private OnDialogoListaListener listener;



    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_lista, null);
        builder.setView(v);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

              /*  EditText editText = (EditText) getActivity().findViewById(R.id.etDialog);
                String elemento = editText.getText().toString();*/

                listener.onPossitiveButtonClick(DialogoLista.this);
            }
        });



        return builder.create();
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnDialogoListaListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnDialogoListaListener");

        }
    }


}

package com.izv.dam.newquip.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.pojo.Join;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;

/**
 * Created by dam on 17/11/2016.
 */

public class AdaptadorJoin extends RecyclerView.Adapter<AdaptadorJoin.JoinViewHolder> {
    private Context context;
    private Cursor dataCursor;
    private static AdaptadorClickJoin click;
    private static int tipo;


    public AdaptadorJoin(Context context,Cursor cursor, AdaptadorClickJoin click) {
        this.context=context;
        this.dataCursor = cursor;
        this.click = click;

        if(dataCursor != null){
            this.tipo = cursor.getInt(3);
        }
    }

    @Override
    public JoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(dataCursor != null) {
            if (viewType == 1) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            }
            if (viewType == 2) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent, false);
            }
        }
        JoinViewHolder join = new JoinViewHolder(v, viewType);
        return join;
    }

    @Override
    public int getItemViewType(int position) {
        if(dataCursor != null) {
            dataCursor.moveToPosition(position);
            return dataCursor.getInt(3);
        }else return -1;
    }

    @Override
    public void onBindViewHolder(JoinViewHolder holder, int position) {
        final Cursor cursor = getItem(position);
        if(dataCursor != null) {
            if (getItemViewType(position) == 1) {
                System.out.println("NOTAS");
                holder.tvTitulo.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.TITULO)));
                holder.notaT.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.NOTA)));
                if (cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.TITULO)).trim().compareTo("") == 0) {
                    holder.tvTitulo.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.TITULO)));
                } else {
                    holder.tvTitulo.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.TITULO)));
                }
            }
            if (getItemViewType(position) == 2) {
                System.out.println("LISTAS");
                holder.tituloL.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaLista.TITULO)));
                if (cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaLista.TITULO)).trim().compareTo("") == 0) {
                    holder.tituloL.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaLista.TITULO)));
                } else {
                    holder.tituloL.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaLista.TITULO)));
                }
            }
        }
    }

    public Cursor getItem(int position) {
        if (dataCursor != null) {
            dataCursor.moveToPosition(position);
            return dataCursor;
        } else {
            return null;
        }
    }

    public Cursor changeCursorJ(Cursor cursor){
        Log.v("Creo que casco aqui", "asdadsa");
        if(dataCursor == cursor){
            return null;
        }
        this.dataCursor = cursor;
        if(cursor != null){
            this.notifyDataSetChanged();
        }
        return dataCursor;
    }

    @Override
    public int getItemCount() {
        if(dataCursor != null){
            return dataCursor.getCount();
        }
        return 0;
    }

    static class JoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tvTitulo, notaT, tituloL;
        int type;

        public JoinViewHolder(View itemView, int viewType) {
            super(itemView);
            type = viewType;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            if (viewType == 1){
                tvTitulo = (TextView) itemView.findViewById(R.id.tvTituloNota);
                notaT = (TextView) itemView.findViewById(R.id.notaT);
            }
            if (viewType == 2){
                tituloL = (TextView) itemView.findViewById(R.id.tituloLista);
            }

        }

        @Override
        public void onClick(View v) {
            int posicion = getAdapterPosition();
            System.out.println("TIPOx:" + type);
            click.onItemClickListenerJ(posicion);
        }

        @Override
        public boolean onLongClick(View v) {
            int posicion = getAdapterPosition();
            System.out.println("TIPOy:" + type);
            click.onItemLongClickListenerJ(posicion);
            return false;
        }
    }
}

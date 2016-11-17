package com.izv.dam.newquip.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.contrato.ContratoBaseDatos;

/**
 * Created by dam on 17/11/2016.
 */

public class AdaptadorLista extends RecyclerView.Adapter<AdaptadorLista.ListaViewHolder> {
    private Context context;
    private Cursor dataCursor;
    private static AdaptadorClickLista click;


    public AdaptadorLista(Context context,Cursor cursor, AdaptadorClickLista click) {
        this.context=context;
        this.dataCursor = cursor;
        this.click = click;
    }

    @Override
    public ListaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent, false);
        ListaViewHolder lista = new ListaViewHolder(v);
        return lista;
    }

    @Override
    public void onBindViewHolder(ListaViewHolder holder, int position) {
        final Cursor cursor = getItem(position);
        holder.tvTituloL.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaLista.TITULO)));
        if(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaLista.TITULO)).trim().compareTo("")==0){
            holder.tvTituloL.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaLista.TITULO)));
        }else{
            holder.tvTituloL.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaLista.TITULO)));
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

    public Cursor changeCursorL(Cursor cursor){
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

    static class ListaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tvTituloL;

        public ListaViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvTituloL = (TextView) itemView.findViewById(R.id.tituloLista);
        }

        @Override
        public void onClick(View v) {
            int posicion = getAdapterPosition();
            click.onItemClickListenerL(posicion);
        }

        @Override
        public boolean onLongClick(View v) {
            int posicion = getAdapterPosition();
            click.onItemLongClickListenerL(posicion);
            return true;
        }
    }
}
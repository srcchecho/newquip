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

public class AdaptadorContenidoLista extends RecyclerView.Adapter<AdaptadorContenidoLista.CListaViewHolder> {
    private Context context;
    private Cursor dataCursor;
    private static AdaptadorClickLista click;


    public AdaptadorContenidoLista(Context context, Cursor cursor, AdaptadorClickLista click) {
        this.context=context;
        this.dataCursor = cursor;
        //this.click = click;
    }

    @Override
    public CListaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent, false);
        CListaViewHolder lista = new CListaViewHolder(v);
        return lista;
    }

    @Override
    public void onBindViewHolder(CListaViewHolder holder, int position) {
        final Cursor cursor = getItem(position);
        holder.etTituloCL.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaContenidoLista.NOTA)));
        if(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaContenidoLista.NOTA)).trim().compareTo("")==0){
            holder.etTituloCL.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaContenidoLista.NOTA)));
        }else{
            holder.etTituloCL.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaContenidoLista.NOTA)));
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

    public Cursor changeCursor(Cursor cursor){
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

    static class CListaViewHolder extends RecyclerView.ViewHolder {

        TextView etTituloCL;

        public CListaViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);
            //etTituloCL = (TextView) itemView.findViewById(R.id.ellista);
        }

        /*@Override
        public void onClick(View v) {
            int posicion = getAdapterPosition();
            click.onItemClickListenerL(posicion);
        }

        @Override
        public boolean onLongClick(View v) {
            int posicion = getAdapterPosition();
            click.onItemLongClickListenerL(posicion);
            return true;
        }*/
    }
}
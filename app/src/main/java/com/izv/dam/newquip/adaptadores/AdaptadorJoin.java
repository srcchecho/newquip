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

public class AdaptadorJoin extends RecyclerView.Adapter<AdaptadorJoin.JoinViewHolder> {
    private Context context;
    private Cursor dataCursor;
    private static AdaptadorClickJoin click;


    public AdaptadorJoin(Context context,Cursor cursor, AdaptadorClickJoin click) {
        this.context=context;
        this.dataCursor = cursor;
        this.click = click;
    }

    @Override
    public JoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        JoinViewHolder join = new JoinViewHolder(v);
        return join;
    }

    @Override
    public void onBindViewHolder(JoinViewHolder holder, int position) {
        final Cursor cursor = getItem(position);
        holder.tvTitulo.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.TITULO)));
        holder.notaT.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.NOTA)));
        if(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.TITULO)).trim().compareTo("")==0){
            holder.tvTitulo.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.TITULO)));
        }else{
            holder.tvTitulo.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.TITULO)));
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

    static class JoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tvTitulo, notaT;

        public JoinViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvTituloNota);
            notaT = (TextView) itemView.findViewById(R.id.notaT);
        }

        @Override
        public void onClick(View v) {
            int posicion = getAdapterPosition();
            click.onItemClickListenerJ(posicion);
        }

        @Override
        public boolean onLongClick(View v) {
            int posicion = getAdapterPosition();
            click.onItemLongClickListenerJ(posicion);
            return true;
        }
    }
}

package com.izv.dam.newquip.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.pojo.Nota;

public class AdaptadorNota extends RecyclerView.Adapter<AdaptadorNota.NotaViewHolder> {

    private Context context;
    private Cursor dataCursor;
    private static AdaptadorClick click;


    public AdaptadorNota(Context context,Cursor cursor, AdaptadorClick click) {
        this.context=context;
        this.dataCursor = cursor;
        this.click = click;
    }

    @Override
    public NotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        NotaViewHolder nota = new NotaViewHolder(v);
        return nota;
    }

    @Override
    public void onBindViewHolder(NotaViewHolder holder, int position) {
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

    static class NotaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tvTitulo, notaT;

        public NotaViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvTituloNota);
            notaT = (TextView) itemView.findViewById(R.id.notaT);
        }

        @Override
        public void onClick(View v) {
            int posicion = getAdapterPosition();
            click.onItemClickListener(posicion);
        }

        @Override
        public boolean onLongClick(View v) {
            int posicion = getAdapterPosition();
            click.onItemLongClickListener(posicion);
            return true;
        }
    }
}
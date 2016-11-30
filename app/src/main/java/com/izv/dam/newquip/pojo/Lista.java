package com.izv.dam.newquip.pojo;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;

/**
 * Created by dam on 21/10/16.
 */

public class Lista implements Parcelable{
    private long id;
    private String titulo;

    public Lista(long id, String titulo) {
        this.id = id;
        this.titulo = titulo;

    }

    public Lista() {
        this(0, "");
    }

    protected Lista(Parcel in) {
        id = in.readLong();
        titulo = in.readString();
    }

    public static final Creator<Lista> CREATOR = new Creator<Lista>() {
        public Lista createFromParcel(Parcel in) { return new Lista(in); }

        public Lista[] newArray(int size) { return new Lista[size]; }
    };


    //GET Y SET
    public long getId() { return id; }

    public void setId(long id) {this.id = id; }

    public void setId(String id){
        try {
            this.id = Long.parseLong(id);
        } catch(NumberFormatException e){
            this.id = 0;
        }
    }

    public String getTitulo(){ return titulo; }

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public ContentValues getContentValues() { return this.getContentValues(false); }

    public ContentValues getContentValues(boolean withId) {
        ContentValues valores = new ContentValues();
        if(withId){
            valores.put(ContratoBaseDatos.TablaContenidoLista._ID, this.getId());
        }
        valores.put(ContratoBaseDatos.TablaLista.TITULO, this.getTitulo());
        return valores;
    }


    public static Lista getLista(Cursor c){
        Lista objeto = new Lista();
        objeto.setId(c.getLong(c.getColumnIndex(ContratoBaseDatos.TablaLista._ID)));
        objeto.setTitulo(c.getString(c.getColumnIndex(ContratoBaseDatos.TablaLista.TITULO)));
        return objeto;
    }

    @Override
    public String toString() {
        return "Lista{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                '}';
    }

    //METODOS PARCELABLE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(titulo);
    }



}

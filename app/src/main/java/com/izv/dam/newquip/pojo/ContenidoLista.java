package com.izv.dam.newquip.pojo;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;

/**
 * Created by dam on 21/10/16.
 */

public class ContenidoLista implements Parcelable {

    private long id;
    private long idlista;
    private String nota;

    public ContenidoLista(long id, long idlista, String texto) {
        this.id = id;
        this.idlista = idlista;
        this.nota = nota;
    }

    public ContenidoLista(){
        this(0, 0, null);
    }

    protected ContenidoLista(Parcel in){
        id = in.readLong();
        idlista = in.readLong();
        nota = in.readString();
    }

    public static final Creator<ContenidoLista> CREATOR = new Creator<ContenidoLista>(){

        public ContenidoLista createFromParcel(Parcel in) {return new ContenidoLista(in);}

        public ContenidoLista[] newArray(int size) { return new ContenidoLista[size];}
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdlista() {
        return idlista;
    }

    public void setIdlista(long idlista) {
        this.idlista = idlista;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public ContentValues getContentValues() { return this.getContentValues(false);}

    public ContentValues getContentValues(boolean withId){
        ContentValues valores = new ContentValues();
        if(withId){
            valores.put(ContratoBaseDatos.TablaContenidoLista._ID, this.getId());
        }
        valores.put(ContratoBaseDatos.TablaContenidoLista.IDLISTA, this.getIdlista());
        valores.put(ContratoBaseDatos.TablaContenidoLista.NOTA, this.getNota());
        return valores;
    }

    public static ContenidoLista getContenidoLista(Cursor c){
        ContenidoLista objeto = new ContenidoLista();
        objeto.setId(c.getLong(c.getColumnIndex(ContratoBaseDatos.TablaContenidoLista._ID)));
        objeto.setIdlista(c.getLong(c.getColumnIndex(ContratoBaseDatos.TablaContenidoLista.IDLISTA)));
        objeto.setNota(c.getString(c.getColumnIndex(ContratoBaseDatos.TablaContenidoLista.NOTA)));
        return objeto;
    }

    @Override
    public String toString() {
        return "ContenidoLista{" +
                "id=" + id +
                ", idlista=" + idlista +
                ", nota='" + nota + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(idlista);
        dest.writeString(nota);
    }
}

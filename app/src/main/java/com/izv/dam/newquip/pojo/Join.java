package com.izv.dam.newquip.pojo;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.itextpdf.text.List;
import com.izv.dam.newquip.contrato.ContratoBaseDatos;

/**
 * Created by dam on 10/11/16.
 */

public class Join implements Parcelable {
    long id;
    String titulo, texto, img;
    int tipo;


    public Join(long id, String titulo, String texto, int tipo) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
        this.tipo = tipo;
    }

    public Join(long id, String titulo, String texto, int tipo, String img) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
        this.tipo = tipo;
        this.img = img;
    }

    public Join() {
        this(0, "", "", 0);
    }

    protected Join(Parcel in) {
        id = in.readLong();
        titulo = in.readString();
        texto = in.readString();
        tipo = in.readInt();
        if(img != null){
            img = in.readString();
        }
    }

    public static final Creator<Join> CREATOR = new Creator<Join>() {
        @Override
        public Join createFromParcel(Parcel in) {
            return new Join(in);
        }

        @Override
        public Join[] newArray(int size) {
            return new Join[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ContentValues getContentValues(){
        return this.getContentValues(false);
    }

    public ContentValues getContentValues(boolean withId){
        ContentValues valores = new ContentValues();
        if(withId){
            valores.put(ContratoBaseDatos.TablaNota._ID, this.getId());
        }
        valores.put(ContratoBaseDatos.TablaNota.TITULO, this.getTitulo());
        valores.put(ContratoBaseDatos.TablaNota.NOTA, this.getTexto());
        return valores;
    }

    public static Join getJoin(Cursor c){
        Join objeto = new Join();
        objeto.setId(c.getLong(0));
        objeto.setTitulo(c.getString(1));
        objeto.setTexto(c.getString(2));
        objeto.setTipo(c.getInt(3));
        return objeto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(titulo);
        dest.writeString(texto);
        dest.writeInt(tipo);
    }
}

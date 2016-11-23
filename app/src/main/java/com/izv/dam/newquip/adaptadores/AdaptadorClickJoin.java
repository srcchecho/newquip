package com.izv.dam.newquip.adaptadores;

/**
 * Created by dam on 17/11/2016.
 */

public interface AdaptadorClickJoin extends AdaptadorClickNota, AdaptadorClickLista{
    public void onItemClickListenerN(int pos);
    public void onItemLongClickListenerN(int pos);
    public void onItemClickListenerL(int pos);
    public void onItemLongClickListenerL(int pos);
}

package com.izv.dam.newquip.adaptadores;

/**
 * Created by dam on 17/11/2016.
 */

public interface AdaptadorClickJoin extends AdaptadorClickNota, AdaptadorClickLista{
    public void onItemClickListenerJ(int pos);
    public void onItemLongClickListenerJ(int pos);
}

package com.izv.dam.newquip.util;

/**
 * Created by dam on 28/10/2016.
 */

public class UtilCadenas {
    public String getCondicionesSql (String condicionesIni, String newCondicion){
        return getCondicionesSql(condicionesIni, newCondicion,"and");
    }

    public String getCondicionesSql (String condicionesIni, String newCondicion, String conector){
        if(condicionesIni.trim().length() == 0 || condicionesIni == null){
            return newCondicion;
        }
        return condicionesIni + " " + conector + " " + newCondicion;
    }
}

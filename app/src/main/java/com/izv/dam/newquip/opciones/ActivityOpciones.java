package com.izv.dam.newquip.opciones;

import android.os.Bundle;

import com.izv.dam.newquip.R;

/**
 * Created by dam on 22/11/16.
 */

public class ActivityOpciones extends AppCompatPreferenceActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.opciones);

    }

}

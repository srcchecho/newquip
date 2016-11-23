package com.izv.dam.newquip.vistas.listas;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.contrato.ContratoLista;
import com.izv.dam.newquip.pojo.ContenidoLista;
import com.izv.dam.newquip.pojo.Lista;

import java.util.ArrayList;

import static com.izv.dam.newquip.R.string.nota;

/**
 * Created by dam on 18/10/16.
 */

public class VistaLista extends AppCompatActivity implements ContratoLista.InterfaceVista {

    private LinearLayout ly;

    //Guardar elementos lista en array
    private ArrayList<EditText> listaEt;

    private PresentadorLista presentador;
    private EditText editTextTitulo;
    private Lista lista = new Lista();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        presentador = new PresentadorLista(this); //<-antes del init

        init();

        editTextTitulo = (EditText) findViewById(R.id.tituloLista);

        if (savedInstanceState != null){
            lista = savedInstanceState.getParcelable("lista");
        } else {
            Bundle b = getIntent().getExtras();
            if(b != null){
                lista = b.getParcelable("lista");
            }
        }
        mostrarLista(lista);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.guardar) {
            saveLista();
            Toast guardar =
                    Toast.makeText(getApplicationContext(),
                            "Guardado", Toast.LENGTH_SHORT);

            guardar.show();
            onBackPressed();
            return true;
        }

        if (id == R.id.borrar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onPause(){
        saveLista();
        presentador.onPause();
        super.onPause();
    }

    protected void onResume(){
        presentador.onResume();
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable("lista", lista);
    }

    private void saveLista() {

        lista.setTitulo(editTextTitulo.getText().toString());
        long r = presentador.onSaveLista(lista);
        if(r > 0 & lista.getId() == 0){
            lista.setId(r);
        }

        //Contenido lista
        final EditText etTitulo = (EditText) findViewById(R.id.etTitulo);
        Lista l = new Lista();
        l.setTitulo(etTitulo.getText().toString());

        for(EditText et:listaEt){
            ContenidoLista cl = new ContenidoLista();

            cl.setNota(et.getText().toString());
            cl.setIdlista(lista.getId());

            long rid = presentador.onSaveContenidoLista(cl);
            if(r > 0 & lista.getId() == 0){
                lista.setId(r);
            }

            //Guardar cada uno de los elementos
        }
    }

    @Override
    public void mostrarLista(Lista n) {
        editTextTitulo.setText(lista.getTitulo());
    }

    private void init() {
        final EditText etTitulo = (EditText) findViewById(R.id.etTitulo);
        ly = (LinearLayout) findViewById(R.id.lladdLista);
        FloatingActionButton b = (FloatingActionButton) findViewById(R.id.btnAddNuevo);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                EditText et = new EditText(VistaLista.this);
                et.setLayoutParams(lparams);

                ly.addView(et);
                listaEt.add(et);
            }

        });

    }
}


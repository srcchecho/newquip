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
import com.izv.dam.newquip.dialogo.DialogoBorrar;
import com.izv.dam.newquip.dialogo.OnBorrarDialogListener;
import com.izv.dam.newquip.pojo.Join;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;

import java.util.ArrayList;

import static com.izv.dam.newquip.R.string.nota;

/**
 * Created by dam on 18/10/16.
 */

public class VistaLista extends AppCompatActivity implements ContratoLista.InterfaceVista, OnBorrarDialogListener {

    private LinearLayout ly;

    //Guardar elementos lista en array
    private ArrayList<EditText> listaEt;

    private PresentadorLista presentador;
    private EditText editTextTitulo;
    private Lista lista = new Lista();
    private Join join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        presentador = new PresentadorLista(this); //<-antes del init

        init();

        editTextTitulo = (EditText) findViewById(R.id.tituloLista);

        if (savedInstanceState != null) {
            lista = savedInstanceState.getParcelable("lista");
        } else {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                lista = b.getParcelable("lista");
            }
        }
        mostrarLista(lista);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
            //mostrarConfirmarBorrarLista(lista);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onPause() {
        saveLista();
        presentador.onPause();
        super.onPause();
    }

    protected void onResume() {
        presentador.onResume();
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("lista", lista);
    }

    private void saveLista() {
        lista.setTitulo(editTextTitulo.getText().toString());
        long r = presentador.onSaveLista(lista);
        if (r > 0 & lista.getId() == 0) {
            lista.setId(r);
        }
    }

    @Override
    public void mostrarLista(Lista n) {
        editTextTitulo.setText(lista.getTitulo());
    }

    private void init() {
        final EditText etTitulo = (EditText) findViewById(R.id.etTitulo);
        FloatingActionButton b = (FloatingActionButton) findViewById(R.id.btnAddNuevo);
        listaEt = new ArrayList<>();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lista l = new Lista();
                l.setTitulo(etTitulo.getText().toString());

                //Recorrer ArrayLista
                String todo = "";

                for (EditText et : listaEt) {

                    // json -> todo += "\"" + et.getText().toString()+ "\"" + ",";
                }
                //todo = "[" + todo + "]";
                //Parte del array Lista
                //l.setLista(todo);
                //Toast.makeText(VistaLista.this, l.toString(), Toast.LENGTH_LONG).show();


                presentador.onSaveLista(null);
            }
        });

        //añadir edt -> EditText et = (EditText) findViewById(R.id.)

        ly = (LinearLayout) findViewById(R.id.lladdLista);
        b = (FloatingActionButton) findViewById(R.id.btnAddNuevo);
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

    @Override
    public void onBorrarPossitiveButtonClick(Nota n) {

    }

    @Override
    public void onBorrarPossitiveButtonClickL(Lista l) {
        presentador.onDeleteLista(l);
        Toast borrar =
                Toast.makeText(getApplicationContext(),
                        "Borrado", Toast.LENGTH_SHORT);

        borrar.show();
        onBackPressed();
    }

    @Override
    public void onBorrarNegativeButtonClick() {

    }

    @Override
    public void mostrarConfirmarBorrarLista(Lista l) {
        DialogoBorrar fragmentBorrar = DialogoBorrar.newInstance(l);
        fragmentBorrar.show(getSupportFragmentManager(), "Dialogo borrar");

    }
}


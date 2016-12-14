package com.izv.dam.newquip.vistas.listas;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorContenidoLista;
import com.izv.dam.newquip.contrato.ContratoLista;
import com.izv.dam.newquip.dialogo.DialogoBorrar;
import com.izv.dam.newquip.dialogo.DialogoLista;
import com.izv.dam.newquip.dialogo.OnBorrarDialogListener;
import com.izv.dam.newquip.dialogo.OnDialogoListaListener;
import com.izv.dam.newquip.pojo.ContenidoLista;
import com.izv.dam.newquip.pojo.Join;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;

import java.util.ArrayList;

import static com.izv.dam.newquip.R.string.nota;

/**
 * Created by dam on 18/10/16.
 */

public class VistaLista extends AppCompatActivity implements ContratoLista.InterfaceVista, OnBorrarDialogListener, OnDialogoListaListener {

    private LinearLayout ly;

    //Guardar elementos lista en array
    private ArrayList<EditText> listaEt;

    private PresentadorLista presentador;
    private EditText editTextTitulo;
    private Lista lista = new Lista();
    private Join join;
    private AdaptadorContenidoLista adaptadorCL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        presentador = new PresentadorLista(this); //<-antes del init

        init();

        editTextTitulo = (EditText) findViewById(R.id.tituloLista);

        adaptadorCL = new AdaptadorContenidoLista(this, null);

        //recyclerView
        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.listaCl);
        recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager linear = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linear);
        recyclerView.setAdapter(adaptadorCL);

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
            mostrarConfirmarBorrarLista(lista);
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
        presentador.setIdLis(lista.getId());
        System.out.println("ID LISTA: " + lista.getId());
    }

    private void init() {
        final EditText etTitulo = (EditText) findViewById(R.id.etTitulo);
        ly = (LinearLayout) findViewById(R.id.lladdLista);
        ImageButton b = (ImageButton) findViewById(R.id.btnAddNuevo);
        listaEt = new ArrayList<>();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoLista fragmentLista = new DialogoLista();
                fragmentLista.show(getSupportFragmentManager(), "Dialogo lista");
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

    @Override
    public void mostrarDatosCL(Cursor c) {
        System.out.println("Numero: " + c.getCount());
        adaptadorCL.changeCursor(c);
    }

    @Override
    public void onPossitiveButtonClick(DialogFragment dialog) {
        //insertar elemento

        lista.setTitulo(editTextTitulo.getText().toString());
        long r = presentador.onSaveLista(lista);
        if (r > 0 & lista.getId() == 0) {
            lista.setId(r);
        }
        Dialog dialogView = dialog.getDialog();
        EditText elemento = (EditText) dialogView.findViewById(R.id.etDialog);
        elemento.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(elemento, InputMethodManager.SHOW_IMPLICIT);
        ContenidoLista cl = new ContenidoLista();
        cl.setNota(elemento.getText().toString());
        cl.setIdlista(lista.getId());
        long rid = presentador.onSaveContenidoLista(cl);
        if(rid > 0 & cl.getId() == 0){
            lista.setId(rid);
        }
    }
}


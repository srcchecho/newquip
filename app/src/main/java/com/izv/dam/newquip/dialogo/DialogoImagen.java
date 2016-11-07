package com.izv.dam.newquip.dialogo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.pojo.Nota;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Pilar on 26/09/2016.
 */

public class DialogoImagen extends DialogFragment {
    private static ImageView imagenL;
    private Nota n;
    private String APP_DIRECTORY = "imagenes/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";
    Bitmap bmp;
    private static final int RESULT_LOAD_IMAGE = 200;
    private static final int PHOTO_CODE = 100;
    // Interfaz de comunicación
    OnImagenDialogListener listener;

    public DialogoImagen() {
    }

    public static DialogoImagen newInstance(Nota n, ImageView img) {
        imagenL = img;
        DialogoImagen fragment = new DialogoImagen();
        Bundle args = new Bundle();
        args.putParcelable("nota",n);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            n=getArguments().getParcelable("nota");
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogImagen();
    }
    public AlertDialog createDialogImagen() {
        final CharSequence[] items = { "Sacar Foto", "Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seleccione una opcion");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    opencamera();
                    imagenL.setVisibility(View.VISIBLE);
                } else if (item == 1) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                    imagenL.setVisibility(View.VISIBLE);
                }
            }
        });
        AlertDialog alert = builder.create();
        return alert;
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            Bundle ext = data.getExtras();
            bmp = (Bitmap)ext.get("data");
            imagenL.setImageBitmap(bmp);
        }
    }*/

    private void opencamera(){
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdirs();

        String path = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;

        File newFile = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        startActivityForResult(intent, PHOTO_CODE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnImagenDialogListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnBorrarDialogListener");

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PHOTO_CODE:
                if(resultCode == RESULT_OK){
                    String dir = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
                    decodeBitmap(dir);
                }
            break;

            case RESULT_LOAD_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri path = data.getData();
                    imagenL.setImageURI(path);
                }
            break;
        }
    }

    private void decodeBitmap(String dir) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);

        imagenL.setImageBitmap(bitmap);
    }
}


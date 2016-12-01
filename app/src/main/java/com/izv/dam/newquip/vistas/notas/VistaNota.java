package com.izv.dam.newquip.vistas.notas;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CFFFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.izv.dam.newquip.R;
import com.izv.dam.newquip.contrato.ContratoNota;
import com.izv.dam.newquip.dialogo.DialogoBorrar;
import com.izv.dam.newquip.dialogo.OnBorrarDialogListener;
import com.izv.dam.newquip.dialogo.OnImagenDialogListener;
import com.izv.dam.newquip.pojo.Join;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.SYSTEM_ALERT_WINDOW;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.R.attr.data;

public class VistaNota extends AppCompatActivity implements ContratoNota.InterfaceVista, OnBorrarDialogListener, OnImagenDialogListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    private EditText editTextTitulo, editTextNota;
    private Nota nota = new Nota();
    private Join join;
    private PresentadorNota presentador;

    //camara
    private static String APP_DIRECTORY = "Quip/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "imagenes";
    private static String MEDIA_DIRECTORY2 = APP_DIRECTORY + "PDF";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private String mPath;

    //imagen
    ImageView imagen, img;
    private LinearLayout mRlView;
    String result2;

    //nota
    String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);

        presentador = new PresentadorNota(this);

        editTextTitulo = (EditText) findViewById(R.id.etTitulo);
        editTextNota = (EditText) findViewById(R.id.etNota);
        img = (ImageView) findViewById(R.id.imagen);
        mRlView = (LinearLayout) findViewById(R.id.rl_view);


        if (savedInstanceState != null) {
            nota = savedInstanceState.getParcelable("nota");
        } else {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                nota = b.getParcelable("nota");
            }
        }
        mostrarNota(nota);

        //imagen = (ImageView) findViewById(R.id.imagen);
    }



    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(mRlView, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    //vista nota

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.pdf) {
            if(mayRequestStoragePermission()) {
                crearPDF();
            }
            return true;
        }

        if (id == R.id.foto) {
            if(mayRequestStoragePermission()) {
                mostrarDialogImagen(nota);
            }
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.guardar) {
            saveNota();
            Toast guardar =
                    Toast.makeText(getApplicationContext(),
                            "Guardado", Toast.LENGTH_SHORT);

            guardar.show();
            onBackPressed();
            return true;
        }

        if (id == R.id.borrar) {
            mostrarConfirmarBorrarNota(nota);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void crearPDF() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY2);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated) {
            isDirectoryCreated = file.mkdirs();
        }
        if(isDirectoryCreated) {
            Document doc = new Document();
            String outPath = null;
            System.out.println("DATOS:" + nota.getTitulo());
            if ( nota.getTitulo() == null || nota.getTitulo().isEmpty() ){
                Snackbar.make(getCurrentFocus(), "Debe guardar la nota para continuar", Snackbar.LENGTH_LONG).show();
            }else {
                int tituloLong = nota.getTitulo().length();
                if (tituloLong != 0) {
                    outPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY2
                            + File.separator + nota.getTitulo() + "_" + nota.getId() + ".pdf";
                } else {
                    Long timestamp = System.currentTimeMillis() / 1000;
                    outPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY2
                            + File.separator + timestamp.toString() + ".pdf";
                }
                System.out.println("PATH:" + outPath);
                try {
                    PdfWriter.getInstance(doc, new FileOutputStream(outPath));
                    doc.open();
                    if (tituloLong != 0) {
                        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
                        doc.add(new Paragraph("Titulo: " + editTextTitulo.getText().toString(), font1));
                    } else {
                        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
                        doc.add(new Paragraph("Titulo: Sin Titulo", font1));
                    }
                    if (editTextNota.getText() != null) {
                        doc.add(new Paragraph(editTextNota.getText().toString()));
                    }
                    if (nota.getImg() != null) {
                        mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                                + File.separator + nota.getImg();
                        Image image1 = Image.getInstance(mPath);
                        image1.setAbsolutePosition(100f, 100);
                        image1.scaleAbsolute(400, 400);
                        doc.add((Element) image1);
                    }
                    doc.close();
                    Snackbar.make(getCurrentFocus(), "PDF Generado con Exito!", Snackbar.LENGTH_LONG).show();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void deleteNota() {
        presentador.onDeleteNota(nota);
    }


    @Override
    protected void onPause() {
        saveNota();
        presentador.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        presentador.onResume();
        super.onResume();
    }

    @Override
    public void mostrarNota(Nota n) {
        editTextTitulo.setText(nota.getTitulo());
        editTextNota.setText(nota.getNota());
        String nomImg = nota.getImg();
        if(nomImg != null){
            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + nomImg;

            Bitmap bmp = BitmapFactory.decodeFile(mPath);
            img.setImageBitmap(bmp);
        }
    }

    private void saveNota() {
        nota.setTitulo(editTextTitulo.getText().toString());
        nota.setNota(editTextNota.getText().toString());
        nota.setTipo(1);
        long r = presentador.onSaveNota(nota);
        if (r > 0 & nota.getId() == 0) {
            nota.setId(r);

        }
    }

    @Override
    public void mostrarConfirmarBorrarNota(Nota n) {
        DialogoBorrar fragmentBorrar = DialogoBorrar.newInstance(n);
        fragmentBorrar.show(getSupportFragmentManager(), "Dialogo borrar");

    }

    @Override
    public void mostrarDialogImagen(Nota n) {
        final CharSequence[] items = {"Sacar Foto", "Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige una opcion");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    abrirCamara();
                } else if (item == 1) {
                    abrirGaleria();

                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBorrarPossitiveButtonClick(Nota n) {
        presentador.onDeleteNota(n);
        Toast borrar =
                Toast.makeText(getApplicationContext(),
                        "Borrado", Toast.LENGTH_SHORT);

        borrar.show();
        onBackPressed();
    }

    @Override
    public void onBorrarPossitiveButtonClickL(Lista l) {

    }

    @Override
    public void onBorrarNegativeButtonClick() {

    }


    /* * Metodos con los que se abre el selector de imagenes de la galeria */
    public void abrirGaleria() {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    public void abrirCamara(){
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            imageName = timestamp.toString() + ".jpg";
            nota.setImg(imageName);
            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("nota", nota);
        outState.putString("file_path", mPath);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE://galeria
                if (resultCode == Activity.RESULT_OK) {
                    /*Creo carpeta si no existe*/
                    File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
                    boolean isDirectoryCreated = file.exists();

                    if(!isDirectoryCreated)
                        isDirectoryCreated = file.mkdirs();
                    /*cuando la carpeta este creada se hace una copia de la imagen y se guarda en la carpeta creada*/
                    if(isDirectoryCreated) {
                        Uri datos = data.getData();
                        String x = getRealPath(datos);
                        Long timestamp = System.currentTimeMillis() / 1000;
                        imageName = timestamp.toString() + ".jpg";
                        nota.setImg(imageName);
                        mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                                + File.separator + imageName;
                        File src = new File(x);
                        File dst = new File(mPath);
                        try {
                            copy(src, dst);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Bitmap bmImg = BitmapFactory.decodeFile(x);
                        img.setImageBitmap(bmImg);
                    }



                }
            break;
            case PHOTO_CODE://camara
                MediaScannerConnection.scanFile(this,
                        new String[]{mPath}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("ExternalStorage", "Scanned " + path + ":");
                                Log.i("ExternalStorage", "-> Uri = " + uri);
                            }
                        });


                Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                img.setImageBitmap(bitmap);
                break;
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(VistaNota.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(VistaNota.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getRealPath(Uri datos){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(datos);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = VistaNota.this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    public void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

}


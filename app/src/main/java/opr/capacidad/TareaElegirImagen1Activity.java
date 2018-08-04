package opr.capacidad;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import opr.capacidad.Data.WebServerConection;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;



public class TareaElegirImagen1Activity extends AppCompatActivity {

    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp/";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private ImageView mSetImage;
    private ImageView mSetImage2;
    private ImageView mSetImage3;
    private ImageView imageViewAuxiliar;
    private Button mOptionButton;
    private Button mOptionButton2;
    private Button mOptionButton3;
    private EditText viewConsigna;
    private EditText viewFProgramada;

    private String imageString;
    private String imageString2;
    private String imageString3;

    private Button Volver;
    private Button Crear;

    private ConstraintLayout layout;

    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_tarea_elegir_imagen1);

        mSetImage = (ImageView) findViewById(R.id.imageView);
        mSetImage2 = (ImageView) findViewById(R.id.imageView2);
        mSetImage3 = (ImageView) findViewById(R.id.imageView3);
        mOptionButton = (Button) findViewById(R.id.btnCargarImagen);
        mOptionButton2 = (Button) findViewById(R.id.btnCargarImagen2);
        mOptionButton3 = (Button) findViewById(R.id.btnCargarImagen3);
        viewConsigna = findViewById(R.id.etConsigna);
        viewFProgramada = findViewById(R.id.etDate);

        Volver = (Button) findViewById(R.id.btnVolver);
        Crear = (Button) findViewById(R.id.btnCrear);

        layout = findViewById(R.id.content_tarea_elegir_imagen1);

        if (mayRequestStoragePermission()){
            mOptionButton.setEnabled(true);
            mOptionButton2.setEnabled(true);
            mOptionButton3.setEnabled(true);


        }else{
            mOptionButton.setEnabled(false);
            mOptionButton2.setEnabled(false);
            mOptionButton3.setEnabled(false);

        }
        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("INFO","btn1");
                imageViewAuxiliar = mSetImage;
                showOptions();
            }
        });
        mOptionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("INFO","btn2");
                imageViewAuxiliar = mSetImage2;
                showOptions();
            }
        });
        mOptionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("INFO","btn3");
                imageViewAuxiliar = mSetImage3;
                showOptions();
            }
        });


        Crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("CREAR BUTTON", "On click");

                WebServerConection conn = new WebServerConection(TareaElegirImagen1Activity.this);

                /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = ((BitmapDrawable) mSetImage.getDrawable()).getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                Bitmap bitmap2 = ((BitmapDrawable) mSetImage2.getDrawable()).getBitmap();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos2);
                byte[] imageBytes2 = baos2.toByteArray();
                imageString2 = Base64.encodeToString(imageBytes2, Base64.DEFAULT);

                ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
                Bitmap bitmap3 = ((BitmapDrawable) mSetImage3.getDrawable()).getBitmap();
                bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, baos3);
                byte[] imageBytes3 = baos3.toByteArray();
                imageString3 = Base64.encodeToString(imageBytes3, Base64.DEFAULT);*/

                Log.i("CREAR", "consigna: " + viewConsigna.getText().toString() + ", fecha programada: " + viewFProgramada.getText().toString());

                conn.sendTareaElegirImagen(viewConsigna.getText().toString(),
                        viewFProgramada.getText().toString(),"1","path1",
                        "path2", "path3", "2");

                Log.i("CONECTION" , "Error = " + String.valueOf(conn.isErrorState()));

                if (conn.isErrorState()) {
                    Log.e("Error", conn.getConectionResponse());
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Error al crear la tarea", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER,0,0);
                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Tarea creada exitosamente", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER,0,0);
                }
            }
        });

        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Volver = new Intent(TareaElegirImagen1Activity.this, ElegirTarea.class);
                startActivity(Volver);
            }
        });


      /*  Intent intent = new Intent(TareaElegirImagen1Activity.this, ResolverTareaActivity.class);
        intent.putExtra("variable_integer", idResultante);
        intent.putExtra("variable_integer2", idResultante2);
        intent.putExtra("variable_integer3", idResultante3);

        startActivity(intent);*/
    }





    private boolean mayRequestStoragePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }
        if ((checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)){
            return true;
        }
        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(layout,"Los permisos son necesarios para poder usar la aplicacion.",Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                     requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA},MY_PERMISSIONS);

                }
            }).show();
        }else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA},MY_PERMISSIONS);
        }
        return false;
    }

    private void showOptions() {
        final CharSequence[] option = {"Tomar foto","Elegir de galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(TareaElegirImagen1Activity.this);
        builder.setTitle("Elige una opcion");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (option[which] == "Tomar foto"){
                    Log.i("INFO","camara");
                    openCamara();
                }else if(option[which] == "Elegir de galeria") {
                    Log.i("INFO","galeria");
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent.createChooser(intent,"Selecciona app de imagen"),SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void openCamara() {
        File file = new File(Environment.getExternalStorageDirectory(),MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if (!isDirectoryCreated){
            isDirectoryCreated = file.mkdirs();
        }
        if (isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);


            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(newFile));
            startActivityForResult(intent,PHOTO_CODE);

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPath = savedInstanceState.getString("file_path");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case PHOTO_CODE:
                        MediaScannerConnection.scanFile(this,
                                new String[]{mPath}, null,
                                new MediaScannerConnection.OnScanCompletedListener() {
                                    @Override
                                    public void onScanCompleted(String path, Uri uri) {
                                        Log.i("ExternalStorage", "Scanned" + path + ":");
                                        Log.i("ExternalStorage", "-> Uri = " + uri);
                                    }
                                });

                        Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                        imageViewAuxiliar.setImageBitmap(bitmap);


                        break;
                    case SELECT_PICTURE:
                        Uri path = data.getData();
                        imageViewAuxiliar.setImageURI(path);


                        break;

                }
            }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MY_PERMISSIONS){
            if (grantResults.length==2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(TareaElegirImagen1Activity.this,"Permisos aceptados",Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
                mOptionButton2.setEnabled(true);
                mOptionButton3.setEnabled(true);

            }

        }else {
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TareaElegirImagen1Activity.this);
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
}







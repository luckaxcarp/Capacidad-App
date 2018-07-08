package opr.capacidad;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class TareaElegirImagen1Activity extends AppCompatActivity {
    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp/";
    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    private ImageView mSetImage;
    private String mPathImage;
    private ImageView mSetImage2;
    private String mPathImage2;
    private ImageView mSetImage3;
    private String mPathImage3;
    private ImageView mSetImage4;
    private Button mOptionButton;
    private Button mOptionButton2;
    private Button mOptionButton3;
    private Button btnCrear;

    private RelativeLayout mRlView;

    private String mPath;
    private int actualImage;
    private String encodedString;
    private Uri filePath;
    private Bitmap bitmap;

    public int idTerapia;
    private String consigna;
    private String fProgramada;
    private int imagenCorrecta;

    public int getIdTerapia() {
        return idTerapia;
    }

    public void setIdTerapia(int idTerapia) {
        this.idTerapia = idTerapia;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_tarea_elegir_imagen1);

        mRlView = (RelativeLayout) findViewById(R.id.rl_view);

        mSetImage = (ImageView) findViewById(R.id.imageView);
        mSetImage2 = (ImageView) findViewById(R.id.imageView2);
        mSetImage3 = (ImageView) findViewById(R.id.imageView3);
        mOptionButton = (Button) findViewById(R.id.btnCargarImagen);
        mOptionButton2 = (Button) findViewById(R.id.btnCargarImagen2);
        mOptionButton3 = findViewById(R.id.btnCargarImagen3);
        btnCrear = findViewById(R.id.btnCrear);

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
                mSetImage4 = mSetImage;
                actualImage = 1;
                showOptions();
            }
        });
        mOptionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSetImage4 = mSetImage2;
                actualImage = 2;
                showOptions();
            }
        });
        mOptionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSetImage4 = mSetImage3;
                actualImage = 3;
                showOptions();
            }
        });
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consigna = findViewById(R.id.text_consigna).toString();
                fProgramada = findViewById(R.id.text_fprogramada).toString();
                imagenCorrecta = 3;

                StringRequest stringRequest = new StringRequest("http://18.218.177.65/upload-image-str2.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String > params = new HashMap<>();
                        String imageData = imageToString(bitmap);
                        params.put("image",imageData);
                        Log.i(" --- ENVIAR IMAGEN --- ", imageData);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(TareaElegirImagen1Activity.this);
                requestQueue.add(stringRequest);


                Log.i("INFO SUBIR IMAGEN","Ruta: " + filePath.getPath());
            }
        });

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
            Snackbar.make(mRlView,"Los permisos son necesarios para poder usar la aplicacion.",Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
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
                    openCamara();
                }else if(option[which] == "Elegir de galeria") {
                    openGalery();
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

    private void openGalery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Selecciona app de imagen"),SELECT_PICTURE);
    }

    private void savePathToCorrectImage(int imageNumber, String imagePath) {
        switch (imageNumber) {
            case 1:
                mPathImage = imagePath;
                break;
            case 2:
                mPathImage2 = imagePath;
                break;
            case 3:
                mPathImage3 = imagePath;
                break;
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

            if (resultCode == RESULT_OK && data != null) {

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

                        bitmap = BitmapFactory.decodeFile(mPath);
                        mSetImage4.setImageBitmap(bitmap);
                        savePathToCorrectImage(actualImage, mPath);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        //bitmap.recycle();

                        byte[] array = stream.toByteArray();
                        encodedString = Base64.encodeToString(array, 0);

                        break;
                    case SELECT_PICTURE:
                        filePath = data.getData();

                        try {
                            InputStream inputStream = getContentResolver().openInputStream(filePath);
                            Bitmap bitmapG = BitmapFactory.decodeStream(inputStream);
                            mSetImage4.setImageBitmap(bitmapG);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        mSetImage4.setImageURI(filePath);
                        savePathToCorrectImage(actualImage, filePath.toString());

                        Log.i("SELECCINOR DE GALERIA","Ruta: " + filePath.toString());
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

    private  String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}







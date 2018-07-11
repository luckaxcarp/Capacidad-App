package opr.capacidad;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import opr.capacidad.R;
import opr.capacidad.Utilidades.Utilidades;
import opr.capacidad.model.Chronometer;
import opr.capacidad.model.Tarea;

public class ResolverTareaActivity extends AppCompatActivity {

    private int idTarea;
    private Tarea tarea = null;
    private TextView tvTittle;
    private TextView tvConsigna;
    private ImageView ivImg1;
    private ImageView ivImg2;
    private ImageView ivImg3;
    private RadioGroup rbGroup;
    private RadioButton rbImg1;
    private RadioButton rbImg2;
    private RadioButton rbImg3;
    private RadioButton rightChoice;
    private Button btnSubmit;
    private double resolutionTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolver_tarea);

        tvTittle = findViewById(R.id.tarea_tittle);
        tvConsigna = findViewById(R.id.tarea_consigna);
        ivImg1 = findViewById(R.id.iv_img1);
        ivImg2 = findViewById(R.id.iv_img2);
        ivImg3 = findViewById(R.id.iv_img3);
        rbGroup = findViewById(R.id.radioGroup);
        rbImg1 = findViewById(R.id.radio_img1);
        rbImg2 = findViewById(R.id.radio_img2);
        rbImg3 = findViewById(R.id.radio_img3);
        btnSubmit = findViewById(R.id.btnSubmit);

        // <Load data
        tarea = new Tarea();
        tarea.getDataById(1);



        tvTittle.setText(tarea.getTittle());
        tvConsigna.setText(tarea.getConsigna());
        ivImg1.setImageBitmap(getBitmap(Consultar("1"))); //tarea.getImage1()
        ivImg2.setImageBitmap(getBitmap(Consultar("2")));
        ivImg3.setImageBitmap(getBitmap(Consultar("3")));

        switch (tarea.getRightChoice()) {
            case 1:
                rightChoice = rbImg1;
                break;
            case 2:
                rightChoice = rbImg2;
                break;
            case 3:
                rightChoice = rbImg3;
                break;
        }
        // Load data>

        Log.i("DEBUG", "-- aqui se llama al servicio --");
        Chronometer.setUpdateListener(this);
        initializeChronometer();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("CRONOMETRO", String.format("%.2f", resolutionTime) + "s");

                stopChronometer();

                if (rbGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Por favor elija una imagen.",Toast.LENGTH_SHORT).show();
                } else {
                    int selectedId = rbGroup.getCheckedRadioButtonId();
                    RadioButton selectedRD = findViewById(selectedId);

                    // --- PENDIENTE --- Enviar consulta a base de datos aumentando en uno la cantidad de intentos de esta tarea.

                    if (selectedRD == rightChoice) {
                        Toast.makeText(getApplicationContext(), "¡Felicidades! Respondiste correctamente.", Toast.LENGTH_SHORT).show();

                        int i = (int) resolutionTime; //temp
                        tarea.setTareaCompletada(i);

                    } else {
                        Toast.makeText(getApplicationContext(), "¡Casi! Sigue intentándolo.", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    /*public void getImage (View view){
        Cursor c = db.rawQuery("select * from tb",null);
        if(c.moveToNext()){
            byte[]  image = c.getBlob(0);
            Bitmap bmp = BitmapFactory.decodeByteArray(image,0,image.length);
            mSetImage.setImageBitmap(bmp);
            byte[]  image2 = c.getBlob(1);
            Bitmap bmp2 = BitmapFactory.decodeByteArray(image2,0,image2.length);
            mSetImage2.setImageBitmap(bmp2);
            byte[]  image3 = c.getBlob(2);
            Bitmap bmp3 = BitmapFactory.decodeByteArray(image3,0,image3.length);
            mSetImage3.setImageBitmap(bmp3);
        }
    }*/

    protected void onDestroy() {
        stopChronometer();
        super.onDestroy();
    }

    private void initializeChronometer() {
        Intent service = new Intent(this, Chronometer.class);
        startService(service);
    }

    private void stopChronometer() {
        Intent service = new Intent(this, Chronometer.class);
        stopService(service);
    }

    public void updateChronometer(double time) {
        resolutionTime = time;
    }

    public void onRadioButtonClicked(View view) {
    }

    private String Consultar(String id) {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();

        String[] parametros={id};
        String[] campos = {Utilidades.CAMPO_NOMBRE, Utilidades.CAMPO_ROL};

        String image = "";

        try {
            Cursor cursor = db.query(Utilidades.TABLA_USUARIO,campos,Utilidades.CAMPO_ID+"=?", parametros, null, null,null);
            cursor.moveToFirst();
            //campoNombre.setText(cursor.getString(0));
            image = cursor.getString(1);
            cursor.close();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_SHORT).show();
        }

        return image;
    }

    private Bitmap getBitmap(String encoded) {
        byte[] dataDec = Base64.decode(encoded, Base64.DEFAULT);
        String base64String = "";
        try {

            base64String = new String(dataDec, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } finally {
            byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedByte;
        }
    }
}

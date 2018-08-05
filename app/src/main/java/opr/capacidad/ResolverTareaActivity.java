package opr.capacidad;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import opr.capacidad.Data.WebServerConection;
import opr.capacidad.Utilidades.Utilidades;
import opr.capacidad.model.Chronometer;
import opr.capacidad.model.Tarea;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class ResolverTareaActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener {

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

    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private JSONObject requestResponse;

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

        requestQueue = Volley.newRequestQueue(ResolverTareaActivity.this);
        loadData();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("CRONOMETRO", String.format("%.2f", resolutionTime) + "s");

                stopChronometer();

                if (rbGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Por favor elija una imagen.", Toast.LENGTH_SHORT).show();
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

    private Bitmap Consultar(String id) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_usuarios", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();



        String[] parametros = {id};
        String[] campos = {Utilidades.CAMPO_ID_IMAGEN, Utilidades.CAMPO_NOMBRE_IMAGEN};

        String image = "";

        try {
            Cursor cursor = db.query(Utilidades.TABLA_IMAGEN, campos, Utilidades.CAMPO_ID_IMAGEN + "=?", parametros, null, null, null);
            cursor.moveToFirst();

            image = cursor.getString(1);
            cursor.close();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "El documento no existe", Toast.LENGTH_SHORT).show();
            Log.i("SQLite", e.toString());
        } finally {
            db.close();
        }

        byte[] imagenconvertida = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imagenconvertida, 0, imagenconvertida.length);
        return decodedImage;
    }

    private void loadData() {
        tarea = new Tarea();
        WebServerConection webServerConection = new WebServerConection(this);
        String url = webServerConection.generateUrlResolverTarea("13");

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("RESPONSE", response.toString());

                        JSONArray json = null;
                        JSONObject jsonObject = null;
                        try {
                            json = response.getJSONArray("tarea");
                            jsonObject = json.getJSONObject(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //tvTittle.setText("Vacío");//tarea.getTittle()
                        tvConsigna.setText(jsonObject.optString("consigna"));
                        /*ivImg1.setImageBitmap(ultimoRegistro("2"));
                        ivImg2.setImageBitmap(ultimoRegistro("1"));
                        ivImg3.setImageBitmap(ultimoRegistro(""));

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
                        }*/
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        queue.add(jsonObjectRequest);


        if (true) {
            Log.i("DEBUG", "-- aqui se llama al servicio --");
            Chronometer.setUpdateListener(this);
            initializeChronometer();
        } else {
            Toast.makeText(this,"No se pudieron cargar los datos",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i("RESPONSE", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        requestResponse = response;
        Log.i("RESPONSE", response.toString());
    }
}
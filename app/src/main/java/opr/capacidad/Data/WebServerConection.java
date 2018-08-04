package opr.capacidad.Data;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class WebServerConection implements Response.Listener<JSONObject>, Response.ErrorListener {

    private final String AWS_SERVER = "http://18.218.177.65/";
    private final String LOCAL_SERVER = "http://192.168.1.43/capacidad/";
    private final String INTERFAZ_INSERT_TAREA_ELEGIR_IMAGEN = "ins-tarea-elegirimagen.php";
    private final String CAMPO_CONSIGNA = "consigna";
    private final String CAMPO_FECHA_PROGRAMADA = "f_programada";
    private final String CAMPO_TIPO = "tipo";
    private final String CAMPO_ID_TERAPIA = "id_terapia";
    private final String CAMPO_UBICACION1 = "ubicacion1";
    private final String CAMPO_UBICACION2 = "ubicacion2";
    private final String CAMPO_UBICACION3 = "ubicacion3";
    private final String CAMPO_IMAGENCORRECTA = "imagencorrecta";
    private final String VALUE_TIPO_ELEGIR_IMAGEN = "1";

    private String serverUrl = LOCAL_SERVER;

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private String conectionResponse;
    private boolean errorState;
    private ProgressDialog progress;


    public WebServerConection(Context context) {
        request = Volley.newRequestQueue(context);
        Log.i("WebServer", "constructor");
    }

    public void sendTareaElegirImagen(String consigna, String fechaProgramada, String idTerapia,
                                      String ubicacion1, String ubicacion2, String ubicacion3,
                                      String imagencorrecta) {
        Log.i("WebServer", "sentTareaElegirImagen");
        final String tipoTarea = VALUE_TIPO_ELEGIR_IMAGEN;

        String url = serverUrl + INTERFAZ_INSERT_TAREA_ELEGIR_IMAGEN + "?" + CAMPO_CONSIGNA + "=" +
                consigna + "&" + CAMPO_FECHA_PROGRAMADA + "=" + fechaProgramada + "&" +
                CAMPO_TIPO + "=" + tipoTarea + "&" + CAMPO_ID_TERAPIA + "=" + idTerapia +
                "&" + CAMPO_UBICACION1 + "=" + ubicacion1 + "&" + CAMPO_UBICACION2 + "=" + ubicacion2 +
                "&" + CAMPO_UBICACION3 + "=" + ubicacion3 + "&" + CAMPO_IMAGENCORRECTA + "=" + imagencorrecta;

        Log.i("WebServer", "url: " + url);

        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        errorState = true;
        conectionResponse = error.toString();

    }

    @Override
    public void onResponse(JSONObject response) {
        errorState = false;
        conectionResponse = response.toString();
    }

    public String getConectionResponse() {
        return conectionResponse;
    }

    public boolean isErrorState() {
        return errorState;
    }

}

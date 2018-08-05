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
    private final String INTERFAZ_SELECT_TAREA_ELEGIR_IMAGEN = "sel-tarea-elegirimagen.php";

    // Constants of insert Tarea
    private final String CAMPO_CONSIGNA = "consigna";
    private final String CAMPO_FECHA_PROGRAMADA = "f_programada";
    private final String CAMPO_TIPO = "tipo";
    private final String CAMPO_ID_TERAPIA = "id_terapia";
    private final String CAMPO_UBICACION1 = "ubicacion1";
    private final String CAMPO_UBICACION2 = "ubicacion2";
    private final String CAMPO_UBICACION3 = "ubicacion3";
    private final String CAMPO_IMAGENCORRECTA = "imagencorrecta";
    private final String VALUE_TIPO_ELEGIR_IMAGEN = "1";

    // Constants of select Tarea
    private final String CAMPO_ID_TAREA = "tarea";

    private String serverUrl = LOCAL_SERVER;

    private String url = "";
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private JSONObject connResponse;
    private VolleyError connError;
    private boolean errorState;

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
        url = url.replace(" ", "%20");
        Log.i("WebServer", "url: " + url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, this,this);
        request.add(jsonObjectRequest);
    }

    public String generateUrlResolverTarea(String idtarea) {
        return url = serverUrl + INTERFAZ_SELECT_TAREA_ELEGIR_IMAGEN + "?" + CAMPO_ID_TAREA + "=" +
                idtarea;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        errorState = true;
        connError = error;
        Log.i("TAREA", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        errorState = false;
        connResponse = response;
        Log.i("TAREA", connResponse.toString());
    }

    public JSONObject getConnResponse() {
        return connResponse;
    }

    public boolean isErrorState() {
        return errorState;
    }

    public VolleyError getConnError() {
        return connError;
    }
}

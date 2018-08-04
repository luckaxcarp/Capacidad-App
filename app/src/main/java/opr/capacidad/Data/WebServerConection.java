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
    private final String LOCAL_SERVER = "http://localhost/";

    private String serverUrl = LOCAL_SERVER;
    private final String TAREA_ELEGIR_IMAGEN_SCRIPT = "ins-tarea-elegirimagen.php";
    private final String CAMPO_CONSIGNA = "consigna";
    private final String CAMPO_FECHA_FINALIZACION = "f_finalizacion";

    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private String conectionResponse;
    private boolean errorState;
    private ProgressDialog progress;


    public WebServerConection(Context context) {
        request = Volley.newRequestQueue(context);
    }

    public void sendTareaElegirImagen(String consigna, String fechaFinalizacion) {
        String url = serverUrl + TAREA_ELEGIR_IMAGEN_SCRIPT + "?" + CAMPO_CONSIGNA + "=" +
                consigna + "&" + CAMPO_FECHA_FINALIZACION + "=" + fechaFinalizacion;

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

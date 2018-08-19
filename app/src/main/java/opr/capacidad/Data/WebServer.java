package opr.capacidad.Data;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

final public class WebServer {

    private static String AWS_SERVER = "http://18.218.177.65/";
    private static String LOCAL_SERVER = "http://192.168.1.43/capacidad/";
    private static String SERVER_URL = LOCAL_SERVER;

    private static String INTERFAZ_INSERT_TAREA_ELEGIR_IMAGEN = "ins-tarea-elegirimagen.php";
    private static String INTERFAZ_SELECT_TAREA_ELEGIR_IMAGEN = "sel-tarea-elegirimagen.php";
    private static String NUEVA_INTERFAZ_SELECT_TAREA_ELEGIR_IMAGEN = "resolver-tarea.php";
    private static String INTERFAZ_RECORD_ATTEMPT = "record-attempt.php";

    // Constants of insert Tarea
    private static String CAMPO_CONSIGNA = "consigna";
    private static String CAMPO_FECHA_PROGRAMADA = "f_programada";
    private static String CAMPO_TIPO = "tipo";
    private static String CAMPO_ID_TERAPIA = "id_terapia";
    private static String CAMPO_UBICACION1 = "ubicacion1";
    private static String CAMPO_UBICACION2 = "ubicacion2";
    private static String CAMPO_UBICACION3 = "ubicacion3";
    private static String CAMPO_IMAGENCORRECTA = "imagencorrecta";
    private static String VALUE_TIPO_ELEGIR_IMAGEN = "1";

    // Constants of ResolverTareaActivity
    private static String CAMPO_ID_TAREA = "tarea";
    private static String CAMPO_TIEMPO = "tiempo";

    public static String generateUrlCreateTarea() {
        String url = SERVER_URL + INTERFAZ_INSERT_TAREA_ELEGIR_IMAGEN;
        return url;
    }

    public static String generateUrlResolverTarea(String idtarea) {
        String url = SERVER_URL + NUEVA_INTERFAZ_SELECT_TAREA_ELEGIR_IMAGEN + "?" + CAMPO_ID_TAREA + "=" +
                idtarea;
        return url;
    }

    public static String genUrlRecordAttempt(String idTarea, String resolutionTime) {
        String url = SERVER_URL + INTERFAZ_RECORD_ATTEMPT + "?" + CAMPO_ID_TAREA + "=" + idTarea + "&" + CAMPO_TIEMPO + "=" + resolutionTime;
        return url;
    }
}

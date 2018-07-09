package opr.capacidad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class SendDataToServer {
    private static String SERVER_URL = "http://18.218.177.65/";

    public static Bitmap getBitmap(Context context, Uri uriFile) {
        Bitmap bitmap = null;

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uriFile);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            Log.i("BITMAP GALERY IMAGE", "Debug de error en proxima linea.");
            e.printStackTrace();
        }

        return bitmap;
    }

    public static String encodeBitmap(Bitmap bitmap) {
        String url = "";

        try {
            //Bitmap to Base64 to String
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            url = Base64.encodeToString(stream.toByteArray(), 0);

            // String to URL
            url = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.i("ENCODE URL", "Error: " + e);
        }

         return url;
    }

    public static void sendImage(Context context,String image) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String url = SERVER_URL + "upload-image-str2.php";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("pic", image);
            jsonBody.put("test", "setted");
            final String requestBody = jsonBody.toString();

            Log.i("VOLLEY", "request: " + requestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return super.parseNetworkResponse(response); //Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void uploadAvatar(String username,String sex, String accessToken, String image, Response.Listener<JSONObject> success, Response.ErrorListener error) {
        /**String url = "your server api url";
        ScoinJsonRequest request = new ScoinJsonRequest(Request.Method.POST, url, getuploadAvatarParams(user, sex, image), success, error);
        request.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);**/
    }

    private Map<String, String> getuploadAvatarParams(String username,String sex,String stringBase64)
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("gender", sex);
        params.put("ibase64", stringBase64);

        return params;
    }
}

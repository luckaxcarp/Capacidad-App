package opr.capacidad;

public class AgregarMultimedia {

    public static void postImage(String ImageLink){
        RequestParams params = new RequestParams();
        try {
            params.put("imagen", new File(ImageLink));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.0.103:8080/android/foto/subirfoto.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("statusCode "+statusCode);//statusCode 200
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


}

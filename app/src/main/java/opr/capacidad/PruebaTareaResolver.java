package opr.capacidad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.content.Intent;


public class PruebaTareaResolver extends AppCompatActivity {

    private ImageView mSetImage;
    private ImageView mSetImage2;
    private ImageView mSetImage3;

   ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_tarea_resolver);

        mSetImage = (ImageView) findViewById(R.id.imageView4);
        mSetImage2 = (ImageView) findViewById(R.id.imageView5);
        mSetImage3 = (ImageView) findViewById(R.id.imageView6);

        Intent intent = getIntent();
        Bitmap bitmap = intent.getParcelableExtra("bitMap");
        ImageView icono = (ImageView) findViewById(R.id.imageView);
        icono.setImageBitmap(bitmap);


    }
}

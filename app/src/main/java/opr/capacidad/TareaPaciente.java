package opr.capacidad;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TareaPaciente extends AppCompatActivity {
    SQLiteDatabase db;

    private ImageView mSetImage;
    private ImageView mSetImage2;
    private ImageView mSetImage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea_paciente);

        mSetImage = (ImageView) findViewById(R.id.imagensalida);
        mSetImage2 = (ImageView) findViewById(R.id.imagensalida2);
        mSetImage3 = (ImageView) findViewById(R.id.imagensalida3);
    }
    public void getImage (View view){
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
    }
}

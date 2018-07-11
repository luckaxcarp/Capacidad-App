package opr.capacidad.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import opr.capacidad.R;

public class ElegirTarea extends AppCompatActivity {

   private Button elegirImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_tarea);

        elegirImagen = (Button) findViewById(R.id.btnElegirImagen);

        elegirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent elegirImagen = new Intent(ElegirTarea.this, TareaElegirImagen1Activity.class);
                startActivity(elegirImagen);
            }
        });

    }
}

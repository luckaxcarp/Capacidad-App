package opr.capacidad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import opr.capacidad.Vistas.TareaElegirImagen1Activity;

public class ElegirTarea extends AppCompatActivity {

   private Button crearTarea;
    private Button Volver;
    private Button Unir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_tarea);

        crearTarea = (Button) findViewById(R.id.btnCrearTarea);

        crearTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent elegirImagen = new Intent(ElegirTarea.this, TareaElegirImagen1Activity.class);
                startActivity(elegirImagen);
            }
        });
        Volver = (Button) findViewById(R.id.btnVolver);

        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Volver = new Intent(ElegirTarea.this, PantallaPrincipal.class);
                startActivity(Volver);
            }
        });
        Unir = (Button) findViewById(R.id.btnResolverTarea);

        Unir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Unir = new Intent(ElegirTarea.this, Select.class);
                startActivity(Unir);
            }
        });

    }
}

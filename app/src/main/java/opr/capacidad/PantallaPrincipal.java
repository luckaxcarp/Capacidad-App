package opr.capacidad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PantallaPrincipal extends AppCompatActivity {
    private Button Tarea;
    private Button Consultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal2);

        Tarea = (Button) findViewById(R.id.btnTarea);

        Tarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Tarea = new Intent(PantallaPrincipal.this, ElegirTarea.class);
                startActivity(Tarea);
            }
        });
        Consultar = (Button) findViewById(R.id.btnConsultarUsuario);

        Consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Consultar = new Intent(PantallaPrincipal.this, ConsultarUsuario.class);
                startActivity(Consultar);
            }
        });
    }
}

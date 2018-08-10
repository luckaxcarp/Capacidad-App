package opr.capacidad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Select extends AppCompatActivity {

    private Button cargar;
    private EditText idTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        cargar = findViewById(R.id.btnCargarTarea);
        idTarea = findViewById(R.id.etIdTarea);

        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent elegirImagen = new Intent(Select.this, ResolverTareaActivity.class);
                elegirImagen.putExtra("ID_TAREA", idTarea.getText().toString());
                startActivity(elegirImagen);
            }
        });
    }
}

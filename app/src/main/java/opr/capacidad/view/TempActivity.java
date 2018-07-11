package opr.capacidad.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import opr.capacidad.R;

public class TempActivity extends AppCompatActivity {

    private Button btnTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        btnTarea = findViewById(R.id.button2);
        btnTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TempActivity.this, ElegirTarea.class);
                startActivity(intent);
            }
        });
    }





}

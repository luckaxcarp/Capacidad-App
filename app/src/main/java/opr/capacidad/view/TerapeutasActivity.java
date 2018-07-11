package opr.capacidad.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import opr.capacidad.R;

public class TerapeutasActivity extends AppCompatActivity {

    private Button btnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terapeutas);

        btnButton = findViewById(R.id.btn_terapia);
        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TerapeutasActivity.this, ResolverTareaActivity.class);
                startActivity(intent);
            }
        });
    }


}

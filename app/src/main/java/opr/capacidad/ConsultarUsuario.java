package opr.capacidad;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import opr.capacidad.Utilidades.Utilidades;

public class ConsultarUsuario extends AppCompatActivity {

    EditText campoId,campoNombre,campoRol;

    ConexionSQLiteHelper conn;

    private Button Volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuario);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        campoId = (EditText) findViewById(R.id.campoIdConsulta);
        campoNombre = (EditText) findViewById(R.id.campoNombreConsulta);
        campoRol = (EditText) findViewById(R.id.campoRolConsulta);

        Volver = (Button) findViewById(R.id.btnVolver);

        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Volver = new Intent(ConsultarUsuario.this, PantallaPrincipal.class);
                startActivity(Volver);
            }
        });

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnConsultar:
                Consultar();
                break;
            case R.id.btnActulizar:
                break;
            case R.id.btnEliminar:
                break;
        }

    }

    private void Consultar() {

        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={campoId.getText().toString()};
        String[] campos = {Utilidades.CAMPO_NOMBRE, Utilidades.CAMPO_ROL};

        try {
            Cursor cursor = db.query(Utilidades.TABLA_USUARIO,campos,Utilidades.CAMPO_ID+"=?", parametros, null, null,null);
            cursor.moveToFirst();
            campoNombre.setText(cursor.getString(0));
            campoRol.setText(cursor.getString(1));
            cursor.close();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_SHORT).show();
            Limpiar();
        }



    }

    private void Limpiar() {
        campoNombre.setText("");
        campoRol.setText("");
    }
}

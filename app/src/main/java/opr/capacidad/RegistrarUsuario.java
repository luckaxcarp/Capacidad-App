package opr.capacidad;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import opr.capacidad.Utilidades.Utilidades;

public class RegistrarUsuario extends AppCompatActivity {



    EditText campoId,campoNombre,campoRol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        campoId= (EditText) findViewById(R.id.campoId);
        campoNombre= (EditText) findViewById(R.id.campoNombre);
        campoRol= (EditText) findViewById(R.id.campoRol);

 Button Volver = findViewById(R.id.btnVolver);

        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Volver = new Intent(RegistrarUsuario.this, TodoUsuario.class);
                startActivity(Volver);

            }
        });

    }

    public void onClick(View view) {
        registrarUsuarios();
        //registrarUsuariosSql();
    }

    /*private void registrarUsuariosSql() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        //insert into usuario (id,nombre,telefono) values (123,'Cristian','85665223')

        String insert="INSERT INTO "+Utilidades.TABLA_USUARIO
                +" ( " +Utilidades.CAMPO_ID+","+Utilidades.CAMPO_NOMBRE+","+Utilidades.CAMPO_ROL+")" +
                " VALUES ("+campoId.getText().toString()+", '"+campoNombre.getText().toString()+"','"
                +campoRol.getText().toString()+"')";

        db.execSQL(insert);


        db.close();
    }*/


    private void registrarUsuarios() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_ID,campoId.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRE,campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_ROL,campoRol.getText().toString());

        Long idResultante=db.insert(Utilidades.TABLA_USUARIO,Utilidades.CAMPO_ID,values);

        Toast.makeText(getApplicationContext(),"Id Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        db.close();
    }

}

package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.utilidades.Utilidades;

public class ConsultarUsuariosActivity extends AppCompatActivity {

    EditText campoId, campoNombre, campoTelefono;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuarios);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_usuarios", null, 1);

        campoId = (EditText) findViewById(R.id.documentoId);
        campoNombre = (EditText) findViewById(R.id.campoNombreConsulta);
        campoTelefono = (EditText) findViewById(R.id.campoTelefonoConsulta);


    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnConsultar) {

        } else if (id == R.id.btnActualizar) {
            actualizarUsuario();
        } else if (id == R.id.btnEliminar) {
            eliminarUsuario();
        }
    }


    private void eliminarUsuario() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {campoId.getText().toString()};

        db.delete(Utilidades.TABLA_USUARIO, Utilidades.CAMPO_ID + "=?", parametros);
        Toast.makeText(getApplicationContext(), "Se eliminó el usuario", Toast.LENGTH_LONG).show();
        campoId.setText("");
        limpiar();
        db.close();
    }

    private void actualizarUsuario() {
        // Obtener una instancia de la base de datos en modo escritura
        SQLiteDatabase db = conn.getWritableDatabase();

        // Obtener el ID del usuario desde el campo de entrada
        String[] parametros = {campoId.getText().toString()};

        // Crear un objeto ContentValues para almacenar los valores a actualizar
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE, campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_TELEFONO, campoTelefono.getText().toString());

        try {
            // Actualizar el registro en la base de datos
            int filasAfectadas = db.update(Utilidades.TABLA_USUARIO, values, Utilidades.CAMPO_ID + "=?", parametros);

            if (filasAfectadas > 0) {
                // Si se actualizó al menos una fila, mostrar mensaje de éxito
                Toast.makeText(getApplicationContext(), "Se actualizó correctamente", Toast.LENGTH_LONG).show();
            } else {
                // Si no se actualizó ninguna fila, mostrar mensaje de error
                Toast.makeText(getApplicationContext(), "No se encontró el usuario para actualizar", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // Mostrar mensaje de error en caso de excepción
            Toast.makeText(getApplicationContext(), "Error al actualizar el usuario", Toast.LENGTH_LONG).show();
        } finally {
            // Cerrar la base de datos
            db.close();
        }
    }



    private void limpiar() {
        campoNombre.setText("");
        campoTelefono.setText("");
    }

}



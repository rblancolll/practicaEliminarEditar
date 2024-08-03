package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.utilidades.Utilidades;

public class RegistroUsuariosActivity extends AppCompatActivity {

    EditText campoId, campoNombre, campoTelefono;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        campoId = findViewById(R.id.campoId);
        campoNombre = findViewById(R.id.campoNombre);
        campoTelefono = findViewById(R.id.campoTelefono);

        // Inicializa la conexión a la base de datos
        conn = new ConexionSQLiteHelper(this, "bd_usuarios", null, 1);

        // Verificar el esquema de la base de datos
        verificarEsquema();
    }

    public void onClick(View view) {
        registrarUsuarios();
    }

    private void registrarUsuarios() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String id = campoId.getText().toString();

        // Verificar si el ID ya existe
        if (idExiste(id)) {
            Toast.makeText(getApplicationContext(), "Error: ID ya existente", Toast.LENGTH_LONG).show();
        } else {
            ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_ID, id);
            values.put(Utilidades.CAMPO_NOMBRE, campoNombre.getText().toString());
            values.put(Utilidades.CAMPO_TELEFONO, campoTelefono.getText().toString());

            try {
                long resultado = db.insertOrThrow(Utilidades.TABLA_USUARIO, null, values);
                if (resultado != -1) {
                    Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                }
            } catch (SQLiteConstraintException e) {
                Toast.makeText(getApplicationContext(), "Error al insertar el registro", Toast.LENGTH_LONG).show();
            } finally {
                db.close();
            }
        }
    }

    private boolean idExiste(String id) {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {id};
        String query = "SELECT " + Utilidades.CAMPO_ID + " FROM " + Utilidades.TABLA_USUARIO + " WHERE " + Utilidades.CAMPO_ID + "=?";
        Cursor cursor = db.rawQuery(query, parametros);

        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;
    }

    private void verificarEsquema() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + Utilidades.TABLA_USUARIO + ")", null);

        if (cursor.moveToFirst()) {
            do {
                int columnNameIndex = cursor.getColumnIndex("name");
                int columnTypeIndex = cursor.getColumnIndex("type");
                int primaryKeyIndex = cursor.getColumnIndex("pk");

                if (columnNameIndex != -1 && columnTypeIndex != -1 && primaryKeyIndex != -1) {
                    String columnName = cursor.getString(columnNameIndex);
                    String columnType = cursor.getString(columnTypeIndex);
                    String isPrimaryKey = cursor.getString(primaryKeyIndex);
                    Log.d("DB_SCHEMA", "Column: " + columnName + " Type: " + columnType + " PrimaryKey: " + isPrimaryKey);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}


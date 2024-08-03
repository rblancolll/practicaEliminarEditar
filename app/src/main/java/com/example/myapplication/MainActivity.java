package com.example.myapplication;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_usuarios", null, 1);

    }

    public void onClick(View view) {
        Intent intent = null;

        intent = new Intent(MainActivity.this, RegistroUsuariosActivity.class);


        if (intent != null) {
            startActivity(intent);
        }
    }

    public void onClick2(View view) {
        Intent intent = null;


        intent = new Intent(MainActivity.this, ConsultarUsuariosActivity.class);

        if (intent != null) {
            startActivity(intent);
        }
    }
}

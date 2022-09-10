package com.example.viajes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etCodviaje, etDestino, etCantidad, etValor;
    CheckBox cbActivo;
    ClsOpenHelper admin = new ClsOpenHelper(this, "ConcesionarioDB",null, 1 );
    String CodigoViaje, CiudadDestino, Cantidad, valor;
    int sw;
    long resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();
        etCodviaje = findViewById(R.id.etCodviaje);
        etDestino = findViewById(R.id.etDestino);
        etCantidad = findViewById(R.id.etCantidad);
        etValor = findViewById(R.id.etValor);
        cbActivo = findViewById(R.id.cbActivo);
        sw=0;
    }

    public void guardar(View view) {


        CodigoViaje = etCodviaje.getText().toString();
        CiudadDestino = etDestino.getText().toString();
        Cantidad = etCantidad.getText().toString();
        valor = etValor.getText().toString();
        if( CodigoViaje.isEmpty() || CiudadDestino.isEmpty()
                || CiudadDestino.isEmpty() || valor.isEmpty()) {

            Toast.makeText(this, "Todos los campos son requerido", Toast.LENGTH_SHORT).show();
            etCodviaje = findViewById(R.id.etCodviaje);
            etCodviaje.requestFocus();
        } else {

            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("codigo_viaje", CodigoViaje);
            registro.put("Ciudad_Destino", CiudadDestino);
            registro.put("Cantidad", Cantidad);
            registro.put("valor", Integer.parseInt(valor));


            if (sw==0) {
                resp = db.insert("tbl_viajes", null, registro);
            } else {
                resp = db.update("tbl_viajes", registro, "CodigoViaje='" + etCodviaje + "'", null);
                sw=0;
            }

            if (resp > 0) {
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "No se pudo guardar la informacion", Toast.LENGTH_SHORT).show();
            }

            db.close();

        }


    }

    private void limpiarCampos() {
        etCodviaje.setText("");
        etDestino.setText("");
        etCantidad.setText("");
        etValor.setText("");
        cbActivo.setChecked(false);
        etCodviaje.requestFocus();
        sw=0;
    }

    public void main(View view) {
        Intent intmain = new Intent(this, MainActivity.class);
        startActivity(intmain);
    }
    public void anular(View view) {
        if(sw==0) {
            Toast.makeText(this, "Debe consultar el codigo", Toast.LENGTH_SHORT).show();
            etCodviaje.requestFocus();
        } else {
            SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("activo", "no");
            long resp =  db.update("tbl_viajes", registro, "codigo_viaje='" + CodigoViaje +"'", null);
            if (resp > 0) {
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                sw=0;
            } else {
                Toast.makeText(this, "Error anulando registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }
    public void consultar(View view) {

        CodigoViaje = etCodviaje.getText().toString();
        if (CodigoViaje.isEmpty()) {
            Toast.makeText(this, "El codigo es requerido", Toast.LENGTH_SHORT).show();
            etCodviaje.requestFocus();
        } {
            SQLiteDatabase db = admin.getReadableDatabase();
            Cursor fila = db.rawQuery(
                    "SELECT * FROM tbl_viajes WHERE codigo_viaje = '" + CodigoViaje + "'", null
            );

            if (fila.moveToNext()) {
                sw = 1;
                etDestino.setText(fila.getString(1));
                etCantidad.setText(fila.getString(2));
                etValor.setText(fila.getString(3));

                if (fila.getString(4).equals("si")) {
                    cbActivo.setChecked(true);
                } else {
                    cbActivo.setChecked(false);
                }

            } else {
                Toast.makeText(this, "Viaje no registrado", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void cancelar(View view) {
        limpiarCampos();
    }
}

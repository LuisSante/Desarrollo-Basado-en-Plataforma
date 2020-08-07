package com.tutorials.basededatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewAnimator;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo, et_descripcion, et_precio; //objetos de tipo edittext

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_codigo = (EditText) findViewById(R.id.txt_codigo); //relacion entre la parte logica y grafica
        et_descripcion = (EditText) findViewById(R.id.txt_descripcion);
        et_precio = (EditText) findViewById(R.id.txt_precio);

    }
        //Metodo para dar de alta los productos
    public void Registrar(View view){
        Context context;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1); //usamos nuestro constructor
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase(); //metodo para poder abrir nuestra base de datos en modo lectura y escritura

        String codigo = et_codigo.getText().toString(); //para trabajar con los datos que se vaya a ingresar
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo",codigo); //guardar los datos que se han ingresado
            registro.put("descripcion",descripcion);
            registro.put("precio",precio);

            BaseDeDatos.insert("articulos",null, registro);
            BaseDeDatos.close();
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

        } else{
            Toast.makeText(this,"Debes de llenar todos los campos",Toast.LENGTH_SHORT).show();
            //en caso de que no haya llenado todos o no haya llenado ni uno
        }
    }

    //Metodo para buscar
    public void Buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();//vamos a abrir la base de datos en modo lectura y escritura
        String codigo = et_codigo.getText().toString(); //poder localizar

        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery
                    ("select descripcion, precio from articulos where codigo = "+codigo,null);

            //en caso de encontrar datos
            if(fila.moveToFirst()){//nos va a poder identificar
                et_descripcion.setText(fila.getString(0));//obten la cadena de caracteres
                et_precio.setText((fila.getString(1)));
                BaseDeDatos.close();
            }else{
                Toast.makeText(this, "No existe en articulo",Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
        }else{
            Toast.makeText(this, "Debes introducir el codigo del articulo",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo eliminar

    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper( this,"administracion",null,1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();////vamos a abrir la base de datos en modo lectura y escritura
        String codigo = et_codigo.getText().toString();//poder localizar

        if(!codigo.isEmpty()){
            int cantidad = BaseDeDatos.delete(//delete retorna la cantidad de registros borrados
                    "articulos", "codigo ="+codigo,null);//concatenar y poner la variable que contiene el codigo
                    BaseDeDatos.close();

                    et_codigo.setText(" ");
                    et_descripcion.setText(" ");
                    et_precio.setText(" ");

                    if(cantidad==1){
                        Toast.makeText(this,"Articulo elimando exitosamente",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"Articulo no existente",Toast.LENGTH_SHORT).show();
                    }

        }else{
            Toast.makeText(this,"Debes introducir el codigo del articulo",Toast.LENGTH_SHORT).show();
        }
;    }


}
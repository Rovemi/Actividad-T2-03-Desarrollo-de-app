package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    daoProducto dao;
    Adaptador adapter;
    ArrayList<Producto> lista;
    Producto c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao= new daoProducto(this);
        lista=dao.verTodos();
        adapter= new Adaptador(this, lista, dao);
        ListView list=(ListView)findViewById(R.id.lista);
        Button agregar=(Button)findViewById(R.id.AGREGAR);
        if(lista!=null&&lista.size()>0) {
            list.setAdapter(adapter);
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialogo=new Dialog(MainActivity.this);
                dialogo.setTitle("Nuevo Registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();
                final EditText clave=(EditText)dialogo.findViewById(R.id.clave);
                final EditText nombre=(EditText)dialogo.findViewById(R.id.nombre);
                final EditText precio=(EditText)dialogo.findViewById(R.id.precio);
                final EditText descripcion=(EditText)dialogo.findViewById(R.id.descripcion);
                final EditText cantidad=(EditText)dialogo.findViewById(R.id.cantidad);

                Button guardar=(Button)dialogo.findViewById(R.id.d_agregar);
                guardar.setText("Agregar");
                Button cancelar=(Button)dialogo.findViewById(R.id.d_cancelar);

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            c=new Producto(clave.getText().toString(), nombre.getText().toString(),
                            precio.getText().toString(),
                            descripcion.getText().toString(),
                            cantidad.getText().toString());

                            dao.insertar(c);
                            lista=dao.verTodos();
                            adapter.notifyDataSetChanged();
                            dialogo.dismiss();

                        }catch (Exception e){
                            Toast.makeText(getApplication(), "OCURRIO UN ERROR", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    dialogo.dismiss();
                    }
                });


            }
        });

    }
}
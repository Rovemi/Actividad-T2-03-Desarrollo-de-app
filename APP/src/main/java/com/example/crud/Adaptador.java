package com.example.crud;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    ArrayList<Producto> lista;
    daoProducto dao;
    Producto c;
    Activity a;
    int id=0;
    public Adaptador(Activity a, ArrayList<Producto> lista, daoProducto dao) {
        this.lista = lista;
        this.a = a;
        this.dao = dao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Producto getItem(int i) {
        c=lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        c=lista.get(i);
        return c.getClave();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View v= view;
        if (v==null){
            LayoutInflater li=(LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=li.inflate(R.layout.item, null );
        }
        c=lista.get(posicion);
        TextView clave=(TextView)v.findViewById(R.id.t_clave);
        TextView nombre=(TextView)v.findViewById(R.id.t_nombre);
        TextView precio=(TextView)v.findViewById(R.id.t_precio);
        TextView descripcion=(TextView)v.findViewById(R.id.t_descripcion);
        TextView cantidad=(TextView)v.findViewById(R.id.t_cantidad);

        Button editar=(Button)v.findViewById(R.id.editar);
        Button eliminar=(Button)v.findViewById(R.id.eliminar);

        clave.setText(""+c.getClave());
        nombre.setText(c.getNombre());
        precio.setText(""+c.getPrecio());
        descripcion.setText(c.getDescripcion());
        cantidad.setText(""+c.getCantidad());

        editar.setTag(posicion);
        eliminar.setTag(posicion);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=Integer.parseInt(view.getTag().toString());
                Dialog dialogo=new Dialog(a);
                dialogo.setTitle("Editar Registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();
                final EditText clave=(EditText)dialogo.findViewById(R.id.clave);
                final EditText nombre=(EditText)dialogo.findViewById(R.id.nombre);
                final EditText precio=(EditText)dialogo.findViewById(R.id.precio);
                final EditText descripcion=(EditText)dialogo.findViewById(R.id.descripcion);
                final EditText cantidad=(EditText)dialogo.findViewById(R.id.cantidad);

                Button guardar=(Button)dialogo.findViewById(R.id.d_agregar);
                guardar.setText("Guardar");
                Button cancelar=(Button)dialogo.findViewById(R.id.d_cancelar);

                c=lista.get(pos);
                setId(c.getClave());
                clave.setText(""+c.getClave());
                nombre.setText(c.getNombre());
                precio.setText(""+c.getPrecio());
                descripcion.setText(""+c.getDescripcion());
                cantidad.setText(""+c.getCantidad());

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            c=new Producto(clave.getText().toString(), nombre.getText().toString(),
                                    precio.getText().toString(),
                                    descripcion.getText().toString(),
                                    cantidad.getText().toString());

                            dao.editar(c);
                            lista=dao.verTodos();
                            notifyDataSetChanged();
                            dialogo.dismiss();

                        }catch (Exception e){
                            Toast.makeText(a, "OCURRIO UN ERROR", Toast.LENGTH_SHORT).show();
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
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=Integer.parseInt(view.getTag().toString());
                c=lista.get(pos);
                setId(c.getClave());
                AlertDialog.Builder del=new AlertDialog.Builder(a);
                del.setMessage("¿Estas seguro?");
                del.setCancelable(false);
                del.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.eliminar(getId());
                        lista=dao.verTodos();
                        notifyDataSetChanged();

                    }
                });
                del.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                del.show();
            }
        });
        return v;
    }
}

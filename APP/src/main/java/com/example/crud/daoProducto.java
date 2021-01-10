package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoProducto {
    SQLiteDatabase cx;
    ArrayList<Producto> lista=new ArrayList<Producto>();
    Producto c;
    Context ct;
    String nombreBD = "BDProductos";
    String tabla = "create table if not exists producto(clave integer primary key autoincrement, nombre text, precio integer, descripcion text, cantidad integer)";

    public daoProducto(Context c) {
        this.ct = c;
        cx = c.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
        cx.execSQL(tabla);
    }

    public boolean insertar(Producto c) {
        ContentValues contenedor= new ContentValues();
        contenedor.put("clave",c.getClave());
        contenedor.put("nombre",c.getNombre());
        contenedor.put("precio",c.getPrecio());
        contenedor.put("descripcion",c.getDescripcion());
        contenedor.put("cantidad",c.getCantidad());
        return (cx.insert("producto", null, contenedor))>0;

    }

    public boolean eliminar(int clave){


        return (cx.delete("producto", "id="+c.getClave(), null))>0;
    }

    public boolean editar(Producto c){
        ContentValues contenedor= new ContentValues();
        contenedor.put("clave",c.getClave());
        contenedor.put("nombre",c.getNombre());
        contenedor.put("precio",c.getPrecio());
        contenedor.put("descripcion",c.getDescripcion());
        contenedor.put("cantidad",c.getCantidad());
        return (cx.update("producto", contenedor, "id="+c.getClave(), null))>0;
    }

    public ArrayList<Producto> verTodos(){
        lista.clear();
    Cursor cursor=cx.rawQuery("select * from producto", null);
    if (cursor!=null && cursor.getCount()>0){
        cursor.moveToFirst();
        do {
            lista.add(new Producto(cursor.getInt(0),cursor.getString(1),
                    cursor.getInt(2), cursor.getString(3),
                    cursor.getInt(4)));

        }while (cursor.moveToNext());
    }

        return lista;
    }

    public Producto verUno(int posicion){
        Cursor cursor=cx.rawQuery("select * from producto", null);
        cursor.moveToPosition(posicion);
        c= new Producto(cursor.getInt(0),cursor.getString(1),
                cursor.getInt(2), cursor.getString(3),
                cursor.getInt(4));


        return c;
    }
}

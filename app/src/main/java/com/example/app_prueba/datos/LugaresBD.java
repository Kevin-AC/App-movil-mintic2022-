package com.example.app_prueba.datos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.app_prueba.modelo.GeoPunto;
import com.example.app_prueba.modelo.Lugar;
import com.example.app_prueba.modelo.TipoLugar;

public class LugaresBD extends SQLiteOpenHelper implements RepositorioLugares {

    Context contexto;
    public LugaresBD(Context contexto){
        super(contexto,"lugares",null,1);
        this.contexto = contexto;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE lugares ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nombre TEXT, " +
                "direccion TEXT, " +
                "longitud REAL, " +
                "latitud REAL, " +
                "tipo INTEGER, " +
                "foto TEXT, " +
                "telefono INTEGER, " +
                "url TEXT, " +
                "comentario TEXT, " +
                "fecha BIGINT, " +
                "valoracion REAL)");
        db.execSQL("INSERT INTO lugares VALUES (null, "+"'LA UIS', "+"'Cra 27#9, Bucaramanga, Santander', -73.121, 7.1377, "+
                TipoLugar.EDUCACION.ordinal() + ", '', 6344000, "+ "'https://www.uis.edu.co/', "+ "'Una de las mejores universidades de Colombia.', "+
                        System.currentTimeMillis() +", 5.0)");
        db.execSQL("INSERT INTO lugares VALUES (null, "+ "'Estadio Atanasio Girardot', "+ "'Cra. 74 # 48010', -75.59013,6.256864, "+
                TipoLugar.DEPORTE.ordinal() + ", '', 0, "+ "'http://comunasdemedellin.com/', "+ "'Estadio de la ciudad de medellín', "
                +System.currentTimeMillis() +", 4.5)");
        db.execSQL("INSERT INTO lugares VALUES (null, "+ "'Movistar Arena', "+ "'Diagonal. 61c #26-36, Bogotá, Cundinamarca',- 74.07695,4.64888,"+
        TipoLugar.ESPECTACULO.ordinal() + ", '', 5470183, "+ "'https://movistararena.co/', "+ "'Centro de eventos en Bogotá', "+
                System.currentTimeMillis() +", 4.0)");
        db.execSQL("INSERT INTO lugares VALUES (null, "+ "'Bancolombia', "+ "'Silos, Santander',-72.90982,7.2466845, "+
                TipoLugar.BANCO.ordinal() + ", '', 0, "+ "'', "+ "'Centro de eventos en Bogotá', "+
                System.currentTimeMillis() +", 4.0)");
        db.execSQL("INSERT INTO lugares VALUES (null, "+ "'Loma Mesa de Ruitoque', "+ "'Loma Mesa de Ruitoque, Floridablanca, Santander',0,0, "+
                TipoLugar.BAR.ordinal() + ", '', 0, "+ "'', "+ "'Centro de eventos en Bogotá', "+
                        System.currentTimeMillis() +", 4.0)");
    }

    public static Lugar extraeLugar(Cursor cursor) {
        Lugar lugar = new Lugar();
        lugar.setNombre(cursor.getString(1));
        lugar.setDireccion(cursor.getString(2));
        lugar.setPosicion(new GeoPunto(cursor.getDouble(3), cursor.getDouble(4)));
        lugar.setTipo(TipoLugar.values()[cursor.getInt(5)]);
        lugar.setFoto(cursor.getString(6));
        lugar.setTelefono(cursor.getInt(7));lugar.setUrl(cursor.getString(8));
        lugar.setComentario(cursor.getString(9));
        lugar.setFecha(cursor.getLong(10));
        lugar.setValoracion(cursor.getFloat(11));
        return lugar;
    }
    public Cursor extraeCursor() {
        String consulta = "SELECT * FROM lugares";
        SQLiteDatabase bd = getReadableDatabase();
        return bd.rawQuery(consulta, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    @Override
    public Lugar elemento(int id) {
        return null;
    }
    @Override
    public void agrega(Lugar lugar) {
    }
    @Override
    public int nuevo() {
        return 0;
    }
    @Override
    public void borrar(int id) {
    }
    @Override
    public int dimension() {
        return 0;
    }
    @Override
    public void actualizar(int id, Lugar lugar) {
    }

}

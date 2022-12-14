package com.example.app_prueba.datos;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.app_prueba.Aplicacion;
import com.example.app_prueba.modelo.GeoPunto;
import com.example.app_prueba.modelo.Lugar;
import com.example.app_prueba.modelo.TipoLugar;

public class LugaresBD extends SQLiteOpenHelper implements RepositorioLugares {

    Context contexto;
    public LugaresBD(Context contexto){
        super(contexto,"lugares.db",null,1);
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
                "valoracion REAL)"
        );
        //SENTENCIA SQL PARA INSERTAR UN DATO EN LA TABLA lugares revisar
        // el orden de los campos con el QUERY anterior
        db.execSQL("INSERT INTO lugares VALUES (null, "+"'LA UIS', "+"'Cra 27#9, Bucaramanga, Santander', -73.121, 7.1377, "+
                TipoLugar.EDUCACION.ordinal() + ", '', 6344000, "+ "'https://www.uis.edu.co/', "+ "'Una de las mejores universidades de Colombia.', "+
                        System.currentTimeMillis() +", 5.0)");
        db.execSQL("INSERT INTO lugares VALUES (null, "+ "'Estadio Atanasio Girardot', "+ "'Cra. 74 # 48010', -75.59013,6.256864, "+
                TipoLugar.DEPORTE.ordinal() + ", '', 0, "+ "'http://comunasdemedellin.com/', "+ "'Estadio de la ciudad de medell??n', "
                +System.currentTimeMillis() +", 4.5)");
        db.execSQL("INSERT INTO lugares VALUES (null, "+ "'Movistar Arena', "+ "'Diagonal. 61c #26-36, Bogot??, Cundinamarca',- 74.07695,4.64888,"+
        TipoLugar.ESPECTACULO.ordinal() + ", '', 5470183, "+ "'https://movistararena.co/', "+ "'Centro de eventos en Bogot??', "+
                System.currentTimeMillis() +", 4.0)");
        db.execSQL("INSERT INTO lugares VALUES (null, "+ "'Bancolombia Sucursal', "+ "'AV CL 26 N?? 68B-31 LOCAL 101 Bogota D.C, Cundinamarca',-74.1716087,4.6555073, "+
                TipoLugar.BANCO.ordinal() + ", '', 0180025, "+ "'www.bancolombia.com', "+ "'Centro Bogot??', "+
                System.currentTimeMillis() +", 5.0)");
        db.execSQL("INSERT INTO lugares VALUES (null, "+ "'Beer Corner', "+ "'Diagonal 100, Cra. 106c, Apartad??, Antioquia',-76.6416017,7.8870868, "+
                TipoLugar.BAR.ordinal() + ", '', 828566, "+ "'', "+ "'Centro de eventos', "+
                        System.currentTimeMillis() +", 4.0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    @Override
    public Lugar elemento(int id) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM lugares WHERE _id = "+id,null);
        try {
            if (cursor.moveToNext()){
                return extraeLugar(cursor);
            } else{
                throw new SQLException("Error al consultar en la base de datos con el ID "+id);
            }
        }catch (Exception e){
            throw  e;
        }finally {
            if (cursor!=null) {
                cursor.close();
            }

        }
    }
    @Override
    public void agrega(Lugar lugar) {
    }
    @Override
    public int nuevo() {
        int _id=-1;
        Lugar lugar = new Lugar();
        getWritableDatabase().execSQL("INSERT INTO lugares (nombre, " +
                "direccion, longitud, latitud, tipo, foto, telefono, url, " +
                "comentario, fecha, valoracion) VALUES ('', '', " +
                lugar.getPosicion().getLongitud()+","+
                lugar.getPosicion().getLatitud()+","+
                lugar.getTipo().ordinal()+
                ",'',0,'','',"+ lugar.getFecha()+",0)");
        Cursor cursor = getReadableDatabase()
                .rawQuery("SELECT _id FROM lugares WHERE fecha ="+ lugar.getFecha(),null);
        if (cursor.moveToNext()) _id=cursor.getInt(0);
        cursor.close();
        return _id;
    }
    @Override
    public void borrar(int id) {
        getWritableDatabase().execSQL("DELETE FROM  lugares WHERE _id ="+id);
    }
    @Override
    public int dimension() {
        return 0;
    }
    @Override
    public void actualizar(int id, Lugar lugar) {
        getWritableDatabase().execSQL("UPDATE lugares SET" +
       // String consulta = "UPDATE lugares SET " +
                " nombre = '"+lugar.getNombre()+
                "', direccion = '"+lugar.getDireccion()+
                "', longitud = "+lugar.getPosicion().getLongitud()+
                ", latitud = "+lugar.getPosicion().getLatitud()+
                ", tipo= "+lugar.getTipo().ordinal()+
                ", foto = '"+lugar.getFoto()+
                "', telefono = "+lugar.getTelefono()+
                ", url = '"+lugar.getUrl()+
                "', comentario = '"+lugar.getComentario()+
                "', fecha= "+lugar.getFecha()+
                ", valoracion = "+lugar.getValoracion()+
                " WHERE _id= "+id);
       // Log.d("tag"," consulta update "+ consulta);
        //getWritableDatabase().execSQL(consulta);
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
        String consulta,max ; // "SELECT * FROM lugares ORDER BY nombre DESC";
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(contexto);
        max = pref.getString("maximo","12");
        switch (pref.getString("orden","0")){
            case "0":
                consulta= "SELECT * FROM lugares";
                break;
            case "1":
                consulta = "SELECT * FROM lugares ORDER BY valoracion DESC";
                break;
            default:
                double lon = ((Aplicacion)contexto.getApplicationContext()).posicionActual.getLongitud();
                double lat = ((Aplicacion)contexto.getApplicationContext()).posicionActual.getLatitud();
                consulta = "SELECT * FROM lugares ORDER BY " +
                        "("+lon+"-longitud) * ("+lon+"-longitud) +" +
                        "("+lat+"-latitud) *("+lat+"-latitud)";
                break;
        }
        consulta += " LIMIT "+max;
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(consulta,null);
    }


}

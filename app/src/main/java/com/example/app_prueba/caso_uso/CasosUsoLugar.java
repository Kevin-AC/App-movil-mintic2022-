package com.example.app_prueba.caso_uso;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.example.app_prueba.datos.RepositorioLugares;
import com.example.app_prueba.modelo.GeoPunto;
import com.example.app_prueba.modelo.Lugar;
import com.example.app_prueba.presentacion.EdicionLugarActivity;
import com.example.app_prueba.presentacion.VistaLugarActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CasosUsoLugar {
    private Activity actividad;
    private RepositorioLugares lugares;

    ///contructor
    public CasosUsoLugar(Activity actividad, RepositorioLugares lugares) {
        this.actividad = actividad;
        this.lugares = lugares;
    }

    //opereciones o funciones de la app
    public void mostrar(int pos) {
        Intent mostrar = new Intent(actividad, VistaLugarActivity.class);
        mostrar.putExtra("pos", pos);
        actividad.startActivity(mostrar);
    }

    public void borrar(final int id) {
        new AlertDialog.Builder(actividad)
                .setTitle("Borrado de lugar")
                .setMessage("¿Seguro de eliminar este lugar?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        lugares.borrar(id);
                        actividad.finish();

                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    public void editar(int pos, int codigoSolicitud) {
        Intent intent_ed_lugar = new Intent(actividad, EdicionLugarActivity.class);
        intent_ed_lugar.putExtra("pos", pos);
        actividad.startActivityForResult(intent_ed_lugar, codigoSolicitud);
    }

    public void guardar(int id, Lugar nuevoLugar) {
        lugares.actualizar(id, nuevoLugar);
    }

    // INTENCIONES
    public void compartir(Lugar lugar) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, "Observa este lugar " + lugar.getNombre() + " - " + lugar.getUrl());
        actividad.startActivity(i);
    }

    public void llamarTelefono(Lugar lugar) {
        actividad.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + lugar.getTelefono())));
    }

    public void verPgWeb(Lugar lugar) {
        actividad.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(lugar.getUrl())));
    }

    public final void verMapa(Lugar lugar) {
        double lat = lugar.getPosicion().getLatitud();
        double lon = lugar.getPosicion().getLongitud();
        Uri uri = lugar.getPosicion() != GeoPunto.SIN_POSICION ? Uri.parse("geo:" + lat + ',' + lon + "?z=18&q=" + Uri.encode(lugar.getDireccion())) : Uri.parse("geo:0,0?q=" + Uri.encode(lugar.getDireccion()));
        Log.d("tag casos uso lugar", "vermapa " + uri + " " + Uri.encode(lugar.getDireccion()) + "\n" + lugar.getPosicion() + "geopto " + GeoPunto.SIN_POSICION);
        actividad.startActivity(new Intent("android.intent.action.VIEW", uri));
    }

    public void ponerDeGaleria(int codidoSolicitud) {
        String action;
        if (android.os.Build.VERSION.SDK_INT >= 19) { // API 19 - Kitkat
            action = Intent.ACTION_OPEN_DOCUMENT;
        } else {
            action = Intent.ACTION_PICK;
        }
        Intent i = new Intent(action, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        actividad.startActivityForResult(i, codidoSolicitud);
    }

    public void ponerFoto(int pos, String uri, ImageView imageView) {
        Lugar lugar = lugares.elemento(pos);
        lugar.setFoto(uri);
        visualizarFoto(lugar, imageView);
    }

    public void visualizarFoto(Lugar lugar, ImageView imageView) {
        if (lugar.getFoto() != null && !lugar.getFoto().isEmpty()) {
            imageView.setImageBitmap(reduceBitmap(actividad, lugar.getFoto(), 1024, 1024));
        } else {
            imageView.setImageBitmap(null);
        }
    }

    public Uri tomarFoto(int codidoSolicitud) {
        try {
            Uri uriUltimaFoto;
            File file = File.createTempFile("img_" + (System.currentTimeMillis() / 1000), ".jpg", actividad.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            if (Build.VERSION.SDK_INT >= 24) {
                uriUltimaFoto = FileProvider.getUriForFile(actividad, "misiontic.app_prueba.fileProvider", file);
            } else {
                uriUltimaFoto = Uri.fromFile(file);
            }
            Intent intento_tomarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intento_tomarFoto.putExtra(MediaStore.EXTRA_OUTPUT, uriUltimaFoto);
            actividad.startActivityForResult(intento_tomarFoto, codidoSolicitud);
            return uriUltimaFoto;
        } catch (IOException ex) {
            Toast.makeText(actividad, "Error al crear fichero de imagen", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private Bitmap reduceBitmap(Context contexto, String uri, int maxAncho, int maxAlto) {
        try {
            InputStream input = null;
            Uri u = Uri.parse(uri);
            if (u.getScheme().equals("http") || u.getScheme().equals("https")) {
                input = new URL(uri).openStream();
            } else {
                input = contexto.getContentResolver().openInputStream(u);
            }
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = (int) Math.max(
                    Math.ceil(options.outWidth / maxAncho),
                    Math.ceil(options.outHeight / maxAlto));
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(input, null, options);
        } catch (FileNotFoundException e) {
            Toast.makeText(contexto, "Fichero/recurso de imagen no encontrado", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Toast.makeText(contexto, "Error accediendo a imagen", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }

    }
}

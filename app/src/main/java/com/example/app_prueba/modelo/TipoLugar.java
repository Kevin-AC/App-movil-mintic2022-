package com.example.app_prueba.modelo;

import com.example.app_prueba.R;

public enum TipoLugar {
    OTROS ("Otros", R.drawable.otros),
    RESTAURANTE ("Restaurante", R.drawable.restaurante),
    BAR ("Bar", R.drawable.bar),
    COPAS ("Copas", R.drawable.copa),
    ESPECTACULO ("Espectáculo",R.drawable.teatro),
    HOTEL ("Hotel",R.drawable.hotel),
    COMPRAS ("Compras",R.drawable.shop),
    EDUCACION ("Educación", R.drawable.educacion),
    DEPORTE ("Deporte",R.drawable.deporte),
    BANCO ("Banco",R.drawable.banco ),
    GASOLINERA ("Gasolinera", R.drawable.gasolinera);

    private final String texto;
    private final int recurso; //referencia a archivo img del lugar

    TipoLugar(String texto, int recurso) {
        this.texto = texto;
        this.recurso = recurso;
    }

    public String getTexto() {
        return texto;
    }

    public int getRecurso() {
        return recurso;
    }

    public static String[] getNombres() {
        String[] resultado = new String[TipoLugar.values().length];
        for (TipoLugar tipo : TipoLugar.values()) {
            resultado[tipo.ordinal()] = tipo.texto;
        }
        return resultado;
    }
}

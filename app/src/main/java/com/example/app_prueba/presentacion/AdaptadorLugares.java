package com.example.app_prueba.presentacion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_prueba.Aplicacion;
import com.example.app_prueba.R;
import com.example.app_prueba.datos.RepositorioLugares;
import com.example.app_prueba.modelo.GeoPunto;
import com.example.app_prueba.modelo.Lugar;

public class AdaptadorLugares extends RecyclerView.Adapter<AdaptadorLugares.ViewHolder> {

    protected View.OnClickListener onClickListener;
    protected RepositorioLugares lugares; // Lista de lugares a mostrar
    //el constructor de la clase
    public AdaptadorLugares(RepositorioLugares lugares) {this.lugares = lugares;}
    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre, direccion,distancia;
        public ImageView foto;
        public RatingBar valoracion;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            direccion = itemView.findViewById(R.id.direccion);
            foto = itemView.findViewById(R.id.foto);
            valoracion= itemView.findViewById(R.id.valoracion);
            distancia = itemView.findViewById(R.id.distancia);
        }
        // Personalizamos un ViewHolder a partir de un lugar
        public void personaliza(Lugar lugar) {
            nombre.setText(lugar.getNombre());
            direccion.setText(lugar.getDireccion());
            int id = R.drawable.otros;
            switch(lugar.getTipo()) {
                case RESTAURANTE:id = R.drawable.cuchilleria; break;
                case BAR: id = R.drawable.bar; break;
                case COPAS: id = R.drawable.cerveza; break;
                case ESPECTACULO:id = R.drawable.spotlight; break;
                case HOTEL: id = R.drawable.hotel; break;
                case COMPRAS: id = R.drawable.tienda; break;
                case EDUCACION: id = R.drawable.colegio; break;
                case DEPORTE: id = R.drawable.estadio; break;
                case BANCO: id = R.drawable.bank; break;
                case GASOLINERA: id = R.drawable.bombagasolina; break;
            }
            foto.setImageResource(id);
            foto.setScaleType(ImageView.ScaleType.FIT_END);
            valoracion.setRating(lugar.getValoracion());

            GeoPunto pos = ((Aplicacion)itemView.getContext().getApplicationContext()).posicionActual;
            if(pos.equals(GeoPunto.SIN_POSICION) || lugar.getPosicion().equals(GeoPunto.SIN_POSICION)){
                distancia.setText("... Km");
            } else {
                int d = (int) pos.distancia(lugar.getPosicion());
                if (d<2000){
                    distancia.setText(d+" m");
                } else {
                    distancia.setText(d/1000 + " Km");
                }
            }
        }
    }


    //set de onclick
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    //estos m??todos son propios del ViewHolder y deben ser importados
// Creamos el ViewHolder con la vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista,parent,false);
        vista.setOnClickListener(onClickListener);
        return new ViewHolder(vista);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lugar lugar = lugares.elemento(position);
        holder.personaliza(lugar);
    }
    @Override public int getItemCount() {
        return lugares.dimension();
    }
}
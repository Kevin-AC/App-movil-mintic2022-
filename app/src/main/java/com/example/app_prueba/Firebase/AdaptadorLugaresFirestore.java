package com.example.app_prueba.Firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.app_prueba.Aplicacion;
import com.example.app_prueba.R;
import com.example.app_prueba.modelo.GeoPunto;
import com.example.app_prueba.modelo.Lugar;
import com.example.app_prueba.presentacion.AdaptadorLugares;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdaptadorLugaresFirestore extends FirestoreRecyclerAdapter<Lugar, AdaptadorLugares.ViewHolder> {
    protected View.OnClickListener onClickListener;
    protected Context context;
    public AdaptadorLugaresFirestore(FirestoreRecyclerOptions<Lugar> options, Context context) {
        super(options);
        this.context= context;
    }
    @Override
    public AdaptadorLugares.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista, parent,false);
        view.setOnClickListener(onClickListener);
        return new AdaptadorLugares.ViewHolder(view);
    }
    @Override
    protected void onBindViewHolder(AdaptadorLugares.ViewHolder holder, int position, Lugar lugar) {
        personalizaVista(holder,lugar);
        holder.itemView.setOnClickListener(onClickListener);
        holder.itemView.setTag(new Integer(position));
    }
    public void setOnClickListener(View.OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }
    public String getKey(int pos) {
        return super.getSnapshots().getSnapshot(pos).getId(); }
    public int getPos(String id) {
        int pos = 0;
        while (pos < getItemCount()){
            if (getKey(pos).equals(id)) return pos;
            pos++;
        }
        return -1; }
    // Personalizamos un ViewHolder a partir de un lugar
    public void personalizaVista(AdaptadorLugares.ViewHolder holder,Lugar lugar) {
        holder.nombre.setText(lugar.getNombre());
        holder.direccion.setText(lugar.getDireccion());
        int id = R.drawable.otros;
        switch(lugar.getTipo()) {
            case RESTAURANTE: id = R.drawable.cuchilleria; break;
            case BAR : id = R.drawable.bar; break;
            case COPAS:id = R.drawable.cerveza; break;
            case ESPECTACULO: id = R.drawable.spotlight; break;
            case HOTEL: id = R.drawable.hotel; break;
            case COMPRAS: id = R.drawable.tienda; break;
            case EDUCACION: id = R.drawable.colegio; break;
            case DEPORTE: id = R.drawable.estadio; break;
            case BANCO: id = R.drawable.bank; break;
            case GASOLINERA: id = R.drawable.bombagasolina; break;}
        holder.foto.setImageResource(id);
        holder.foto.setScaleType(ImageView.ScaleType.FIT_END);
        holder.valoracion.setRating(lugar.getValoracion());
        GeoPunto pos=((Aplicacion) context.getApplicationContext()).posicionActual;
        if (pos.equals(GeoPunto.SIN_POSICION) ||
                lugar.getPosicion().equals(GeoPunto.SIN_POSICION)) {
            holder.distancia.setText("... Km");} else {
            int d=(int) pos.distancia(lugar.getPosicion());
            if (d < 2000) holder.distancia.setText(d + " m");
            else holder.distancia.setText(d / 1000 + " Km");
        }
    }
}

package com.db.adapters;

import java.util.ArrayList;

import com.devsmind.bancodesuenos.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public abstract class Lista_adaptador extends BaseAdapter{
	
    private ArrayList<?> Data; 
    private int R_layout_IdView; 
    private Context contexto;
      
    public Lista_adaptador(Context contexto, int R_layout_IdView, ArrayList<?> entradas) {
        super();
        this.contexto = contexto;
        this.Data = entradas; 
        this.R_layout_IdView = R_layout_IdView; 
    }
      
    @Override
    public View getView(int posicion, View view, ViewGroup pariente) {
        if (view == null) {
			LayoutInflater vi = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
            view = vi.inflate(R_layout_IdView, null); 
        }
        onEntrada (Data.get(posicion), view);
        
        return view; 
    }

    
    
	@Override
	public int getCount() {
		return Data.size();
	}

	@Override
	public Object getItem(int posicion) {
		return Data.get(posicion);
	}

	@Override
	public long getItemId(int posicion) {
		return posicion;
		
	}
	
	/** Devuelve cada una de las entradas con cada una de las vistas a la que debe de ser asociada
	 * @param entrada La entrada que será la asociada a la view. La entrada es del tipo del paquete/handler
	 * @param view View particular que contendrá los datos del paquete/handler
	 */
	public abstract void onEntrada (Object entrada, View view);
    
}
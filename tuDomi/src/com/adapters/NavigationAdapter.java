package com.adapters;

import java.util.ArrayList;

import com.models.Carrito;
import com.tuDomicilio.tuDomicilio.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationAdapter extends BaseAdapter {
    private Activity activity;  
	private ArrayList<Carrito> arrayitms; 
	private Typeface coolvetica;

   public NavigationAdapter(Activity activity,ArrayList<Carrito>  listarry) {  
       super();  
       this.activity = activity;  
       this.arrayitms=listarry;
       this.coolvetica = Typeface.createFromAsset(activity.getAssets(),"fonts/coolvetica.ttf");
       TextView tvTitulo = (TextView) activity.findViewById(R.id.tvTituloR); 
       TextView tvCantidad = (TextView) activity.findViewById(R.id.tvCantidadR);
	   	TextView tvTotal = (TextView) activity.findViewById(R.id.lblPrecioR);
	   	int cantidad = 0, total=0;
	   	
	   	for (Carrito carrito : listarry) {
				cantidad += carrito.getCantidad();
				total += carrito.getPrecio()*carrito.getCantidad();
			}
	   	
	   	tvCantidad.setText("Cantidad: " + Integer.toString(cantidad));
	   	tvTotal.setText(Integer.toString(total));
	   	tvCantidad.setTypeface(coolvetica);
	   	tvTotal.setTypeface(coolvetica);
	   	tvTitulo.setTypeface(coolvetica);
	   	
       }
   
   public void ResetViews(){
	    
       TextView tvCantidad = (TextView) activity.findViewById(R.id.tvCantidadR);
       TextView tvTotal = (TextView) activity.findViewById(R.id.lblPrecioR);
       tvCantidad.setText("Cantidad: 0");
       tvTotal.setText("0");
   }
   //Retorna objeto Item_objct del array list
   @Override
   public Object getItem(int position) {       
       return arrayitms.get(position);
   }   
    public int getCount() {  
      // TODO Auto-generated method stub  
        return arrayitms.size();  
    }    
    @Override
    public long getItemId(int position) {
        return position;
    }   
    
    //Declaramos clase estatica la cual representa a la fila
    public static class Fila  
    {  
    		TextView titulo_itm;
    		TextView precio;
    		TextView descripcion;
    }  
   public View getView(int position, View convertView, ViewGroup parent) {  
      // TODO Auto-generated method stub  
	   Fila view;  
       LayoutInflater inflator = activity.getLayoutInflater();  
      if(convertView==null)  
       {  
    	  
           view = new Fila();
           //Creo objeto item y lo obtengo del array
           Carrito itm=arrayitms.get(position);
           convertView = inflator.inflate(R.layout.carrito, null);
           
           //Titulo
           view.titulo_itm = (TextView) convertView.findViewById(R.id.lblListItem);
           view.precio = (TextView) convertView.findViewById(R.id.lblPrecio);
           view.descripcion = (TextView) convertView.findViewById(R.id.lblSubtitle);
           
           //Seteo en el campo titulo el nombre correspondiente obtenido del objeto
           view.titulo_itm.setText(itm.getNombre());
           view.precio.setText(Integer.toString(itm.getPrecio()));
           view.descripcion.setText("Cantidad: " + Integer.toString(itm.getCantidad()));
           
           view.titulo_itm.setTypeface(coolvetica);
           view.precio.setTypeface(coolvetica);
           view.descripcion.setTypeface(coolvetica);
           //Icono
           
           //view.icono = (ImageView) convertView.findViewById(R.id.icon);
           //Seteo el icono
           //view.icono.setImageResource(itm.getIcono());           
           convertView.setTag(view);  
        }  
        else  
        {
        	view = (Fila) convertView.getTag();
        	
        } 
      return convertView;
      
    }
	public void setArrayitms(ArrayList<Carrito> arrayitms) {
		this.arrayitms = arrayitms;
	}
      
	
}

package com.fragment.details.sub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adapters.MyListAdapter;
import com.config.Constants;

import com.dataParser.JSONParser;
import com.db.Queries;

import com.fragment.imageviewer.ImageViewerFragment;


import com.models.Carrito;
import com.models.Photo;
import com.models.Producto;

import com.models.Restaurante;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.tuDomicilio.tuDomicilio.IManagerFragmento;
import com.tuDomicilio.tuDomicilio.IMenu;
import com.tuDomicilio.tuDomicilio.MainActivity;
import com.tuDomicilio.tuDomicilio.R;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;


import android.view.LayoutInflater;
import android.view.View;


import android.widget.AdapterView;

import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class SubDetailGalleryView extends DialogFragment implements OnItemClickListener{

	private View viewInflate = null;
	DisplayImageOptions options;
	private Restaurante restaurant;
	ArrayList<Photo> photoList;
	private FragmentActivity act;
	private MainActivity main;
	private Constants cs = new Constants();
	private String url = cs.URL_PATH + "consultarMenuRestaurante";
	IManagerFragmento mf;
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	public List<String> listDataHeader= new ArrayList<String>();
	public HashMap<String, List<Producto>> listDataChild = new HashMap<String, List<Producto>>();
    
	Producto pro;
	Dialog customDialog = null;
	
	
	public SubDetailGalleryView(FragmentActivity fragmentActivity, MainActivity main, IManagerFragmento mf) {
		// TODO Auto-generated method stub
		this.act = fragmentActivity;
		this.main = main;
		LayoutInflater inflater = fragmentActivity.getLayoutInflater();
		viewInflate = inflater.inflate(R.layout.sub_detail_gallery, null, false);
		this.mf = mf;
	}
	

	public View showGalleries(Restaurante res) {
		
		this.restaurant = res;
		
		expListView = (ExpandableListView) act.findViewById(R.id.lvExp);
		 
        // preparing list data
        //prepareListData();
 
		new JSONParse().execute();
		
        return viewInflate;
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View v, int pos, long resid) {
		// TODO Auto-generated method stub
		
		Bundle b = new Bundle();
		b.putSerializable("photoList", photoList);
		b.putInt("index", pos);
		
		ImageViewerFragment frag = new ImageViewerFragment();
		frag.setArguments(b);
		
		MainActivity main = (MainActivity)act;
        main.changeFragment(
        		Constants.FRAGMENT_TAB_1_IMAGE_VIEWER, 
        		R.id.frameMainContainer, 
        		frag, 
        		true, false, false);
	}
	
	public View getView() {
		return viewInflate;
	}
	
	public void showpp(){
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		
		LayoutInflater inflater = act.getLayoutInflater();
		View view= inflater.inflate(R.layout.popup, null);
		builder.setView(view);
	
		Queries q = main.getQueries();
		Carrito c = q.getCarritoById(pro.getIdProducto());
		
		builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		if(c==null){

			builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					EditText etCantidad = (EditText) customDialog.findViewById(R.id.etCantidad);
					EditText etIndicaciones = (EditText) customDialog.findViewById(R.id.etInidicaciones);
					String cant  = etCantidad.getText().toString();
					int cantidad = Integer.parseInt(cant);
					
					if (cantidad < 1 || cantidad >200){
						Toast.makeText(act,
				                "No se Agregó el producto, digite una cantidad Válida",
				                Toast.LENGTH_LONG).show();
					}else{
						Queries q = main.getQueries();
						Carrito entry = new Carrito();
						entry.setIdProducto(pro.getIdProducto());
						entry.setNombre(pro.getNombre());
						entry.setDescripcion(pro.getDescripcion());
						entry.setPrecio(pro.getPrecio());
						entry.setCantidad(cantidad);
						entry.setIndicaciones(etIndicaciones.getText().toString());
						entry.setIdRestaurante(restaurant.getIdRestaurante());
						q.insertCarrito(entry);
						Toast.makeText(act,
				                "Producto Agregado Al Carrito Correctamente",
				                Toast.LENGTH_SHORT).show();
					}
					main.actualizarMenuCarrito();
				}
			});

		}else{
			
			
			builder.setNeutralButton("Borrar", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Queries q = main.getQueries();
					q.deleteProductoCarrito(pro.getIdProducto());
					Toast.makeText(act,
			                "Producto Eliminado Del Carrito Correctamente",
			                Toast.LENGTH_SHORT).show();
					main.actualizarMenuCarrito();
				}
			});
			
			builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					EditText etCantidad = (EditText) customDialog.findViewById(R.id.etCantidad);
					EditText etIndicaciones = (EditText) customDialog.findViewById(R.id.etInidicaciones);
					String cant  = etCantidad.getText().toString();
					int cantidad = Integer.parseInt(cant);
					
					if (cantidad < 1 || cantidad >200){
						Toast.makeText(act,
				                "No se Modificó el producto, digite una cantidad Válida",
				                Toast.LENGTH_LONG).show();
					}else{
						Queries q = main.getQueries();
						Carrito entry = new Carrito();
						entry.setIdProducto(pro.getIdProducto());
						entry.setNombre(pro.getNombre());
						entry.setDescripcion(pro.getDescripcion());
						entry.setPrecio(pro.getPrecio());
						entry.setCantidad(cantidad);
						entry.setIndicaciones(etIndicaciones.getText().toString());
						entry.setIdRestaurante(restaurant.getIdRestaurante());
						q.updateCarrito(entry);
						Toast.makeText(act,
				                "Producto Modificado Correctamente",
				                Toast.LENGTH_SHORT).show();
					}
					main.actualizarMenuCarrito();
				}
			});
		}
		
		
		Dialog dialog = builder.create();
		this.customDialog = dialog;
		dialog.show();
		
		TextView tvPopTitle =  (TextView) dialog.findViewById(R.id.tvPopTitle);			
		TextView tvPopSubtitle =  (TextView) dialog.findViewById(R.id.tvPopSubtitle);
		TextView lblPopPrecio =  (TextView) dialog.findViewById(R.id.lblPopPrecio);
		EditText etCantidad = (EditText) dialog.findViewById(R.id.etCantidad);
		EditText etIndicaciones = (EditText) dialog.findViewById(R.id.etInidicaciones);
		
		
		if(c == null){
			
			tvPopTitle.setText(pro.getNombre());
			tvPopSubtitle.setText(pro.getDescripcion());
			lblPopPrecio.setText(Integer.toString(pro.getPrecio()));
			
			etCantidad.setText("1");
			etCantidad.requestFocus();
		}else{
			tvPopTitle.setText(c.getNombre());
			tvPopSubtitle.setText(c.getDescripcion());
			lblPopPrecio.setText(Integer.toString(c.getPrecio()));
			
			etCantidad.setText(Integer.toString(c.getCantidad()));
			etCantidad.requestFocus();
			
			etIndicaciones.setText(c.getIndicaciones());
		}
		
		
		
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}


	public void showList(JSONArray json) throws JSONException{
		
		for(int i=0; i < json.length(); i++){
			JSONObject c = json.getJSONObject(i);
			Iterator<?> tipo  = c.keys();
			String nombreCategoria = "";
			
			while(tipo.hasNext()){
				ArrayList<Producto> lp = new ArrayList<Producto>();
		        String key2 = (String)tipo.next();
		        nombreCategoria= key2;
		        JSONArray ja = c.getJSONArray(key2);
		        for(int j=0; j < ja.length(); j++){
		        	JSONObject pro = ja.getJSONObject(j);
    		        Producto p = new Producto();
    		        p.mapearProducto(pro, p);
    		        lp.add(p);
		        }
		        this.listDataHeader.add(nombreCategoria);
				this.listDataChild.put(nombreCategoria, lp);
		    }
			
		}
		
		expListView = (ExpandableListView) act.findViewById(R.id.lvExp);
		//showList(ls);
		listAdapter = new MyListAdapter(act, this.listDataHeader, this.listDataChild);
		 
		expListView.setAdapter(listAdapter);
		 
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
 
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });
 
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
 
            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(act.getApplicationContext(),
                		listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();*/
            }
        });
 
        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
 
            @Override
            public void onGroupCollapse(int groupPosition) {
            	/*    Toast.makeText(act.getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();*/
 
            }
        });
 
        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {
 
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
            	Producto p  = new Producto();
            	p = (Producto) listAdapter.getChild(groupPosition, childPosition);
            	p.setIdRestaurante(restaurant.getIdRestaurante());
            	//pro = p;
            	mf.showpp(p);
                return false;
            }
        });

	}
	
	private class JSONParse extends AsyncTask<String, String, JSONArray > {
	       private ProgressDialog pDialog;
	      @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            
	            pDialog = new ProgressDialog(act);
	            pDialog.setMessage("Cargando Menú");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
	      }
	      @Override
	        protected JSONArray  doInBackground(String... args) {
	        JSONParser jParser = new JSONParser();
	        // Getting JSON from URL
	        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	        pairs.add(new BasicNameValuePair("idRestaurante", Integer.toString(restaurant.getIdRestaurante())));
	        
	        
	        JSONArray  json = jParser.getJSONArrayFromUrl(url, pairs);
	        return json;
	      }
	       @Override
	         protected void onPostExecute(JSONArray json) {
	    	   try {
    		    
	    		showList(json);				
				pDialog.dismiss();
		    	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	   
	        }
       }
}

package com.fragment.index;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.config.Config;
import com.config.Constants;
import com.config.UIConfig;
import com.dataParser.JSONParser;
import com.db.Queries;

import com.fragment.grid.GridFragment;
import com.google.android.gms.analytics.ecommerce.Product;

import com.models.Category;
import com.models.Producto;
import com.models.Restaurante;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.slider.MGSlider;
import com.slider.MGSliderAdapter;
import com.slider.MGSlider.OnMGSliderListener;
import com.slider.MGSliderAdapter.OnMGSliderAdapterListener;
import com.tuDomicilio.tuDomicilio.IManagerFragmento;
import com.tuDomicilio.tuDomicilio.MainActivity;
import com.tuDomicilio.tuDomicilio.R;
import com.utilities.MGUtilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;

public class IndexFragment extends Fragment implements OnItemClickListener, OnClickListener{
	
	private View mRoot;

	DisplayImageOptions options;
	IManagerFragmento mf;
	JSONObject array = null;
	private Constants cn = new Constants();
	private String url = cn.URL_PATH + "upload";

	
	public IndexFragment(IManagerFragmento mf){
		this.mf=mf;	
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mRoot = inflater.inflate(R.layout.index, null);
		return mRoot;
		
	}
	
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto|-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		setRetainInstance(true);
		MainActivity main = (MainActivity) getActivity();
		final ImageView buttonBack = (ImageView) main.findViewById(R.id.ibBack);
   	 	buttonBack.setVisibility(View.INVISIBLE);
		
		options = new DisplayImageOptions.Builder()
			.showImageOnLoading(UIConfig.SLIDER_PLACEHOLDER)
			.showImageForEmptyUri(UIConfig.SLIDER_PLACEHOLDER)
			.showImageOnFail(UIConfig.SLIDER_PLACEHOLDER)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
		
		createSlider();
        	
        GridFragment gridFragment = new GridFragment(mf);
		mf.changeFragment(
    			Constants.FRAGMENT_GRIDS, 
        		R.id.frameMainContainer, 
        		gridFragment, 
        		false, false, false
    			);
		
		if(MGUtilities.hasConnection(main) && 
				Config.WILL_DOWNLOAD_DATA) {
			
			if(main.getCargo()==false)
				descargar();
			
		}
		
		else if(main.getQueries().getRestaurants().size() == 0) {
        	Toast.makeText(
        			getActivity(), 
        			"Comrueba tu conexión a Internet. No se pueden Cargar Datos !!!", 
        			Toast.LENGTH_LONG).show();
        }
		
	}
	
	public void descargar(){
		new JSONParse().execute();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View v, int pos, long resId) {
		// TODO Auto-generated method stub
		
		MGSlider slider = (MGSlider) mRoot.findViewById(R.id.slider);
		slider.stopSliderAnimation();
		
		TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		tvTitle.setTextColor(getResources().getColor(UIConfig.THEME_COLOR));
		
		ImageView imgArrow = (ImageView) v.findViewById(R.id.imgArrow);
		imgArrow.setImageResource(UIConfig.LIST_ARROW_SELECTED);
		
		
	}
	// Create Slider
	private void createSlider() {
		
		//MainActivity main = (MainActivity) getActivity();
		
		final ArrayList<Restaurante> restaurantList = new ArrayList<Restaurante>();
    	Restaurante entry = new Restaurante();
		entry.setDescripcion("Carrera 32 # 40 - 32");
		entry.setNombre("Monta Carga");
		entry.setClave("");
		entry.setDireccion("");
		entry.setEmail("");
		entry.setEstado("");
		entry.setIdRestaurante(1);
		entry.setIdTipoRestaurante(2);
		entry.setLat("");
		entry.setLng("");
		entry.setPuntaje(0);
		entry.setRegId("");
		entry.setTelefono("");
		restaurantList.add(entry);
		
		MGSlider slider = (MGSlider) mRoot.findViewById(R.id.slider);
		slider.setMaxSliderThumb(restaurantList.size());
    	MGSliderAdapter adapter = new MGSliderAdapter(
    			R.layout.slider_entry, restaurantList.size(), restaurantList.size());
    	
    	adapter.setOnMGSliderAdapterListener(new OnMGSliderAdapterListener() {
			
			@Override
			public void onOnMGSliderAdapterCreated(MGSliderAdapter adapter, View v,
					int position) {
				// TODO Auto-generated method stub
				
				Restaurante res = restaurantList.get(position);
				
				ImageView imageViewSlider = (ImageView) v.findViewById(R.id.imageViewSlider);
				
				/*if(p != null) {
					final MainActivity main = (MainActivity) getActivity();
					main.getImageLoader().displayImage(p.photo_url, imageViewSlider, options);
				}*/
				
				imageViewSlider.setTag(position);
				//imageViewSlider.setOnClickListener(CategoryFragment.this);
				
				Spanned name = Html.fromHtml(res.getNombre());
				Spanned address = Html.fromHtml(res.getDescripcion());
				
				TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
				tvTitle.setText(name);
				
				TextView tvSubtitle = (TextView) v.findViewById(R.id.tvSubtitle);
				tvSubtitle.setText(address);
				
			}
			
		});
    	
    	slider.setOnMGSliderListener(new OnMGSliderListener() {
			
			@Override
			public void onItemThumbSelected(MGSlider slider, ImageView[] buttonPoint,
					ImageView imgView, int pos) { }
			
			@Override
			public void onItemThumbCreated(MGSlider slider, ImageView imgView, int pos) { }
			
			@Override
			public void onAllItemThumbCreated(MGSlider slider, LinearLayout linearLayout) { }
			
			@Override
			public void onItemPageScrolled(MGSlider slider, ImageView[] buttonPoint, int pos) { }
			
			@Override
			public void onItemMGSliderToView(MGSlider slider, int pos) { }
			
			@Override
			public void onItemMGSliderViewClick(AdapterView<?> adapterView, View v,
					int pos, long resid) {
				// TODO Auto-generated method stub
				
				
			}
			
		});
    	
    	slider.setOffscreenPageLimit(restaurantList.size() - 1);
    	slider.setAdapter(adapter);
    	slider.setActivity(this.getActivity());
    	slider.setSliderAnimation(5000);
    	slider.resumeSliderAnimation();
 
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	private class JSONParse extends AsyncTask<String, String, JSONObject > {
	       private ProgressDialog pDialog;
	      @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	       
	            MainActivity main = (MainActivity)getActivity();
	            pDialog = new ProgressDialog(main);
	            pDialog.setMessage("Actualizando Datos...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
	      }
	      @Override
	        protected JSONObject  doInBackground(String... args) {
	        JSONParser jParser = new JSONParser();
	        // Getting JSON from URL
	        
	        MainActivity main = (MainActivity) getActivity();
	        Queries q = main.getQueries();
	        String fecha = q.getFecha();
	        if(fecha == null){
	        	fecha = "";
	        }
	        
	        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	        pairs.add(new BasicNameValuePair("fecha", fecha ));
	        JSONObject  json = jParser.getJSONFromUrl(url, pairs);
	        
	        return json;
	      }
	       @Override
	         protected void onPostExecute(JSONObject json) {
	    	   try {
	    		   String f = json.getString("fecha");
	    		   MainActivity main = (MainActivity) getActivity();
	    		   if(f == "" || f == null || f=="null"){
	    			   
	    		   }else{   
	    			   
	    			   Queries q = main.getQueries();
	    			   q.deleteTable("act");
	    			   q.deleteTable("restaurante");
	    			   q.insertFecha(f);
	    			   
	    			   JSONArray jrest = json.getJSONArray("restaurantes");
	    			   //JSONArray jcat = json.getJSONArray("categorias");
	    			   //JSONArray jpro = json.getJSONArray("productos");
	    			   
	   	    		   for(int i=0; i < jrest.length(); i++){
		   	    			JSONObject c = jrest.getJSONObject(i);
		   	    			Restaurante r = new Restaurante();
		   	    			r.mapearRestaurante(c, r);
		   	    			q.insertRestaurant(r);
	   	    		   }
	   	    		   
		   	    		/*for(int i=0; i < jcat.length(); i++){
		   	    			JSONObject c = jcat.getJSONObject(i);
		   	    			Category r = new Category();
		   	    			r.mapearCategoria(c, r);
		   	    			main.getQueries().insertCategory(r);
	   	    		   }
		   	    		
		   	    		for(int i=0; i < jpro.length(); i++){
		   	    			JSONObject c = jpro.getJSONObject(i);
		   	    			Producto r = new Producto();
		   	    			r.mapearProducto(c, r);
		   	    			main.getQueries().insertProducto(r);
	   	    		   }
	   	    		   */
	    		   }
	    		main.setCargo(true);
				pDialog.dismiss();
		    	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	   
	        }
       }

}

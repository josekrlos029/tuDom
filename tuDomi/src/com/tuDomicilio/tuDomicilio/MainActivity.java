package com.tuDomicilio.tuDomicilio;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.adapters.NavigationAdapter;
import com.config.Constants;
import com.db.DbHelper;
import com.db.Queries;

import com.fragment.index.IndexFragment;

import com.fragment.pedido.PedidoFragment;
import com.fragment.restaurant.RestaurantFragment;
import com.fragment.splash.SplashFragment;
import android.os.Bundle;

import com.models.Carrito;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tuDomicilio.tuDomicilio.R;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements IManagerFragmento, DrawerListener {
	
	private IndexFragment indexFragment;
	
	Dialog customDialog = null;
	private ArrayList<Carrito> NavItms;
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ListView mDrawerListR;
    private ActionBarDrawerToggle mDrawerToggle;
	
    NavigationAdapter NavAdapter;
	private ActionBarDrawerToggle toggle;
	private static final String[] opciones = {"Opci�n 1", "Opci�n 2", "Opci�n 3"};
	
	private int state = 0;
	private int stateCarrito = 0;
	
    private DbHelper dbHelper;
	private SQLiteDatabase db;
	private Queries q;
    private boolean cargo = false;
	
	protected ImageLoader imageLoader;
	
	private String back;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        
        dbHelper = new DbHelper(this);
        q = new Queries(db, dbHelper);
        
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getBaseContext()));
        
        initDrawer();
        showEstadoCarrito();
        changeFragment(
        		Constants.FRAGMENT_SPLASH, 
        		R.id.frameRootContainer, 
        		new SplashFragment(), 
        		false, false, false);
        
    }
    
	public Queries getQueries() {
		return q;
	}
	
	/*
	 * Metodo Que Inicializa el Men� de la derecha
	 */
    public void initDrawer(){
    	
    	
    	 ActionBar actionBar = getSupportActionBar();
    	 actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    	 actionBar.setCustomView(R.layout.actionbar_top2);
    	 
    	 final ImageView buttonBack = (ImageView) findViewById(R.id.ibBack);
    	 buttonBack.setVisibility(View.INVISIBLE);
    	 
    	 buttonBack.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
            	
            	 if(back == "home"){
            		 showIndex();
            	 }else if(back == "listaRestaurantes"){
            		 showListaRestaurantes();
            	 }
            	 
             }
    	 });
    	 
    	 final ImageView button = (ImageView) findViewById(R.id.ibMenu);
         button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
             	showDrawer();
             }
         });
         
         final ImageView buttonCarrito = (ImageView) findViewById(R.id.ibCarrito);
         buttonCarrito.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
             	showDrawerCarrito();
             }
         });
        
    	 mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
         mDrawerList = (ListView) findViewById(R.id.left_drawer);

         // set a custom shadow that overlays the main content when the drawer opens
         mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.END);
         // set up the drawer's list view with items and click listener
         mDrawerList.setAdapter(new ArrayAdapter<String>(this,
        		 android.R.layout.simple_list_item_1, android.R.id.text1, opciones));
         mDrawerList.setOnItemClickListener(new OnItemClickListener() {
 			
 			@Override
 			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
 				Toast.makeText(MainActivity.this, "Pulsado: " + opciones[arg2], Toast.LENGTH_SHORT).show();
 				
 			}
 		});
         mDrawerListR = (ListView) findViewById(R.id.drawer2);
         View header = getLayoutInflater().inflate(R.layout.header, null);
         
         mDrawerListR.addHeaderView(header);
         
         NavItms = q.getCarrito();
         
         NavAdapter= new NavigationAdapter(this,NavItms);
         
         mDrawerListR.setAdapter(NavAdapter);
         // set up the drawer's list view with items and click listener
         //mDrawerListR.setAdapter(new ArrayAdapter<String>(this,
         //		 android.R.layout.simple_list_item_1, android.R.id.text1,opciones));
         
         mDrawerListR.setOnItemClickListener(new OnItemClickListener() {
 			
 			@Override
 			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
 				showpp((Carrito) NavAdapter.getItem(arg2 - 1));
 			}
 		});
         // enable ActionBar app icon to behave as action to toggle nav drawer
         //getActionBar().setDisplayHomeAsUpEnabled(true);
         //getActionBar().setHomeButtonEnabled(true);

         // ActionBarDrawerToggle ties together the the proper interactions
         // between the sliding drawer and the action bar app icon
         mDrawerToggle = new ActionBarDrawerToggle(
                 this,                  /* host Activity */
                 mDrawerLayout,         /* DrawerLayout object */
                 R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                 R.string.drawer_open,  /* "open drawer" description for accessibility */
                 R.string.drawer_close  /* "close drawer" description for accessibility */
                 ) {
             public void onDrawerClosed(View view) {
                 //getActionBar().setTitle(mTitle);
                 invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
             }

             public void onDrawerOpened(View drawerView) {
                 
             }
         };
         mDrawerLayout.setDrawerListener(mDrawerToggle);
         
        final PedidoFragment fragmentPedido = new PedidoFragment(this);
        Button btnHacerPedido = (Button) findViewById(R.id.btnHacerPedido);
        Button btnBorrarPedido = (Button) findViewById(R.id.btnBorrarPedido);
        
        Typeface coolvetica = Typeface.createFromAsset(getAssets(),"fonts/coolvetica.ttf");
        btnHacerPedido.setTypeface(coolvetica);
        btnBorrarPedido.setTypeface(coolvetica);
        
 	   	btnHacerPedido.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				mDrawerLayout.closeDrawers();
 				changeFragment(Constants.FRAGMENT_PEDIDO, 
 		        		R.id.frameRootContainer, 
 		        		fragmentPedido, 
 		        		false, true, false);
 			}
 		});
 	   	
 	   	btnBorrarPedido.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				q.deleteTable("lista");
				NavAdapter.setArrayitms(q.getCarrito());
				NavAdapter.ResetViews();
				showEstadoCarrito();
				mDrawerLayout.closeDrawers();
				showToast("Carrito De Compras Borrado Correctamente");
			}
		});
    }
    	

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (toggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    
    
    public void actualizarMenuCarrito(){
    	
    	ArrayList<Carrito> ls = q.getCarrito();
    	this.NavAdapter.setArrayitms(ls);
    
    	
    	TextView tvCantidad = (TextView) findViewById(R.id.tvCantidadR);
    	TextView tvTotal = (TextView) findViewById(R.id.lblPrecioR);
    	int cantidad = 0, total=0;
    	
    	for (Carrito carrito : ls) {
			cantidad += carrito.getCantidad();
			total += carrito.getPrecio()*carrito.getCantidad();
		}
    	
    	tvCantidad.setText("Cantidad: " + Integer.toString(cantidad));
    	tvTotal.setText(Integer.toString(total));
    	showEstadoCarrito();
    }
    
    
    /*
     * Quita el splash del inicio
     */
    public void removeSplash() {
		final Timer t = new Timer();
        t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				t.cancel();
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						ActionBar actionBar = getSupportActionBar();
						actionBar.show();
						showIndex();
						
					}
				});
			}
		}, 3000);
	}
    
    /*
     * Inicializa el Fragmento Seleccionado
     */
	@Override
	public void changeFragment(String fragmentTag, int layoutId, Fragment frag,
			boolean addToBackStack, boolean animate, boolean back) {
		// TODO Auto-generated method stub
		FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
    	
        if(animate == true){
        	
        	if(back == true){
        		transaction.setCustomAnimations(R.anim.zoom_back_in, R.anim.zoom_back_out);
        	}else{
        		transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out);
        	}
        	
        }
        
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.replace(layoutId, frag, fragmentTag);
		
		if(addToBackStack)
			transaction.addToBackStack(null);
		
		transaction.commit();
		
	}
	
    
    /*
     * Abrir o Cerrar Drawe (men� Derecha)
     */
    public void showDrawer(){
    	
    	if(getState()==0){
    		mDrawerLayout.openDrawer(mDrawerList);
    		setState(1);
    	}else{
    		mDrawerLayout.closeDrawers();
    		setState(0);
    	}
    	
    }
    
    public void showDrawerCarrito(){
    	
    	if(getStateCarrito()==0){
    		mDrawerLayout.openDrawer(mDrawerListR);
    		setStateCarrito(1);
    	}else{
    		mDrawerLayout.closeDrawers();
    		setStateCarrito(0);
    	}
    	
    }
    
    
    /*
     * Visualiza el fragmento del index
     */
    private void showIndex() {
		
		indexFragment = new IndexFragment(this);
		
		changeFragment(
        		Constants.FRAGMENT_INDEX, 
        		R.id.frameRootContainer, 
        		indexFragment, 
        		false, false, false);
		
	}
    
private void showListaRestaurantes() {
		
		RestaurantFragment restaurantFragment = new RestaurantFragment(this);
		
		changeFragment(
        		Constants.FRAGMENT_RESTAURANTES, 
        		R.id.frameRootContainer, 
        		restaurantFragment, 
        		false, true, false);
		
	}
    	
	public ImageLoader getImageLoader() {
		return imageLoader;
	}
	
	
	public void showToast(String msg){
		Toast.makeText(MainActivity.this,  msg, Toast.LENGTH_SHORT).show();
	}

	public void popBackStack() {
		if( this.getSupportFragmentManager().getBackStackEntryCount() != 0 ){
            this.getSupportFragmentManager().popBackStack();
        }
	}
	
    @Override
    public void onDrawerClosed(View arg0) {
    	// TODO Auto-generated method stub
    	setState(0);
    	setStateCarrito(0);
    }
    
    @Override
    public void onDrawerOpened(View arg0) {
    	// TODO Auto-generated method stub
    	setState(1);
    	setStateCarrito(1);
    }
	
	@Override
	public void onDrawerSlide(View arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDrawerStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Metodo que muestra u oculta el botón del Carrito
	 * Si hay elementos en el carrito de compras lo muestra
	 * sino lo oculta
	 */
	public void showEstadoCarrito(){
		final ImageView buttonCarrito = (ImageView) findViewById(R.id.ibCarrito);
		Button btnHacerPedido = (Button) findViewById(R.id.btnHacerPedido);
        Button btnBorrarPedido = (Button) findViewById(R.id.btnBorrarPedido);
		if(q.getCarrito().size() > 0){
			buttonCarrito.setVisibility(View.VISIBLE);
			btnHacerPedido.setVisibility(View.VISIBLE);
			btnBorrarPedido.setVisibility(View.VISIBLE);
		}else{
			buttonCarrito.setVisibility(View.INVISIBLE);
			btnHacerPedido.setVisibility(View.INVISIBLE);
			btnBorrarPedido.setVisibility(View.INVISIBLE);
		}
	}
	
	public void showpp(final IMenu obj){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		LayoutInflater inflater = getLayoutInflater();
		View view= inflater.inflate(R.layout.popup, null);
		builder.setView(view);
	
		Queries q = getQueries();
		Carrito c = q.getCarritoById(obj.getIdProducto());
		
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
						showToast("No se Agregó el producto, digite una cantidad Válida");
						
					}else{
						Queries q = getQueries();
						Carrito entry = new Carrito();
						entry.setIdProducto(obj.getIdProducto());
						entry.setNombre(obj.getNombre());
						entry.setDescripcion(obj.getDescripcion());
						entry.setPrecio(obj.getPrecio());
						entry.setCantidad(cantidad);
						entry.setIndicaciones(etIndicaciones.getText().toString());
						entry.setIdRestaurante(obj.getIdRestaurante());
						q.insertCarrito(entry);
						showToast("Producto Agregado Al Carrito Correctamente");
						
					}
					actualizarMenuCarrito();
				}
			});

		}else{
			
			
			builder.setNeutralButton("Borrar", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Queries q = getQueries();
					q.deleteProductoCarrito(obj.getIdProducto());
					showToast("Producto Eliminado Del Carrito Correctamente");
					actualizarMenuCarrito();
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
						showToast("No se Modificó el producto, digite una cantidad Válida");
						
					}else{
						Queries q = getQueries();
						Carrito entry = new Carrito();
						entry.setIdProducto(obj.getIdProducto());
						entry.setNombre(obj.getNombre());
						entry.setDescripcion(obj.getDescripcion());
						entry.setPrecio(obj.getPrecio());
						entry.setCantidad(cantidad);
						entry.setIndicaciones(etIndicaciones.getText().toString());
						entry.setIdRestaurante(obj.getIdRestaurante());
						q.updateCarrito(entry);
						showToast( "Producto Modificado Correctamente");
					}
					actualizarMenuCarrito();
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
			
			tvPopTitle.setText(obj.getNombre());
			tvPopSubtitle.setText(obj.getDescripcion());
			lblPopPrecio.setText(Integer.toString(obj.getPrecio()));
			
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

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}
	
	public boolean getCargo() {
		return cargo;
	}

	public void setCargo(boolean cargo) {
		this.cargo = cargo;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getStateCarrito() {
		return stateCarrito;
	}

	public void setStateCarrito(int stateCarrito) {
		this.stateCarrito = stateCarrito;
	}
	

}
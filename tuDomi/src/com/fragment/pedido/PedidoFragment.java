package com.fragment.pedido;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.config.Config;
import com.config.UIConfig;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.wearable.NodeApi.GetConnectedNodesResult;
import com.models.Restaurante;
import com.tuDomicilio.tuDomicilio.IManagerFragmento;
import com.tuDomicilio.tuDomicilio.MainActivity;
import com.tuDomicilio.tuDomicilio.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class PedidoFragment extends Fragment{
	private View viewInflate;
	IManagerFragmento mf;
	private SupportMapFragment mapFragment;
	private GoogleMap googleMap;
	private LocationClient mLocationClient;
	
	public PedidoFragment(IManagerFragmento mf) {
		// TODO Auto-generated constructor stub
		this.mf  = mf;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		viewInflate = inflater.inflate(R.layout.hacer_pedido, container, false);		
        return viewInflate;
        
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		MainActivity main = (MainActivity) getActivity();
		EditText etDireccion = (EditText) main.findViewById(R.id.etDireccion);
		EditText etReferencia = (EditText) main.findViewById(R.id.etReferencias);
		EditText etTelefono = (EditText) main.findViewById(R.id.etTelefono);
		EditText etFPago = (EditText) main.findViewById(R.id.etFormaPago);
		TextView tvValorCompra = (TextView) main.findViewById(R.id.tvValorCompra);
		TextView tvValorDomicilio = (TextView) main.findViewById(R.id.tvValorDomicilio);
		TextView tvDireccion = (TextView) main.findViewById(R.id.tvDireccion);
		TextView tvTelefono = (TextView) main.findViewById(R.id.tvTelefono);
		TextView tvReferencias = (TextView) main.findViewById(R.id.tvReferencias);
		TextView tvFormaPago = (TextView) main.findViewById(R.id.tvFormaPago);
		
		Typeface coolvetica = Typeface.createFromAsset(main.getAssets(),"fonts/coolvetica.ttf");
		
		etDireccion.setTypeface(coolvetica);
		etReferencia.setTypeface(coolvetica);
		etTelefono.setTypeface(coolvetica);
		etFPago.setTypeface(coolvetica);
		
		tvValorCompra.setTypeface(coolvetica);
		tvValorDomicilio.setTypeface(coolvetica);
		tvTelefono.setTypeface(coolvetica);
		tvDireccion.setTypeface(coolvetica);
		tvReferencias.setTypeface(coolvetica);
		tvFormaPago.setTypeface(coolvetica);
		/*
		tvTelefono.setTextColor(main.getResources().getColor(UIConfig.THEME_COLOR));
		tvDireccion.setTextColor(main.getResources().getColor(UIConfig.THEME_COLOR));
		tvReferencias.setTextColor(main.getResources().getColor(UIConfig.THEME_COLOR));
		tvFormaPago.setTextColor(main.getResources().getColor(UIConfig.THEME_COLOR));
		*/
		etDireccion.setTextColor(main.getResources().getColor(UIConfig.THEME_COLOR));
		etReferencia.setTextColor(main.getResources().getColor(UIConfig.THEME_COLOR));
		etTelefono.setTextColor(main.getResources().getColor(UIConfig.THEME_COLOR));
		etFPago.setTextColor(main.getResources().getColor(UIConfig.THEME_COLOR));
		
		setMap();
	}
	
	private void setMap() {
		
		mapFragment = new SupportMapFragment();
		FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_map_pedido, mapFragment);
        fragmentTransaction.commit();
        
        setMapData();
       
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				t.cancel();
				
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						setMapData();
					}
				});
			}
		}, 500);
       
	}
	
	public View setMapData() {
		
		googleMap = mapFragment.getMap();
		
		if(googleMap == null)
			return viewInflate;
		
		googleMap.setMyLocationEnabled(true);
		
		/*
		LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		LocationListener listener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				MarkerOptions markerOptions = new MarkerOptions();
				markerOptions.title("Su Ubicación !");
				
				markerOptions.position(
						new LatLng(lat,lng));
				 
				markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
				Marker mark = googleMap.addMarker(markerOptions);
				mark.showInfoWindow();
				
				CameraUpdate zoom = CameraUpdateFactory.zoomTo(Config.MAP_ZOOM_LEVEL);
		    	googleMap.moveCamera(zoom);
		    	
				CameraUpdate center = CameraUpdateFactory.newLatLng(markerOptions.getPosition());
				
				googleMap.animateCamera(center);
				
				Geocoder geocoder = new Geocoder(getActivity());
				List<Address> list = null ;
				try {
					list = geocoder.getFromLocation(lat, lng, 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Address add = list.get(0);
				EditText etDireccion = (EditText) getActivity().findViewById(R.id.etDireccion);
				etDireccion.setText(add.getThoroughfare()+ " # "+add.getFeatureName());
				
			}
			
		};*/
		
		//manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 0, listener);
		
		return viewInflate;
		
	}
	
}

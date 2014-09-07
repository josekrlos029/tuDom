package com.fragment.grid;

import java.util.ArrayList;

import com.config.Constants;
import com.config.UIConfig;

import com.fragment.restaurant.RestaurantFragment;
import com.models.Category;
import com.models.Photo;

import com.models.Restaurante;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.slider.MGSlider;
import com.slider.MGSliderAdapter;
import com.slider.MGSlider.OnMGSliderListener;
import com.slider.MGSliderAdapter.OnMGSliderAdapterListener;
import com.tuDomicilio.tuDomicilio.IManagerFragmento;
import com.tuDomicilio.tuDomicilio.MainActivity;
import com.tuDomicilio.tuDomicilio.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class GridFragment extends Fragment{
	
	private View mRoot;
	
	private View viewInflate = null;
	private ArrayList<Restaurante> restaurantList;
	private ImageView imgFave;
	DisplayImageOptions options;
	IManagerFragmento mf;
	private Typeface coolvetica;
	
	public GridFragment(IManagerFragmento mf) {
		// TODO Auto-generated constructor stub
		this.mf = mf;
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
		
		mRoot = inflater.inflate(R.layout.grid_icons, null);
		return mRoot;
		
	}
	
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto|-generated method stub
		super.onViewCreated(view, savedInstanceState);
		final MainActivity main = (MainActivity) getActivity();
		coolvetica = Typeface.createFromAsset(main.getAssets(),"fonts/coolvetica.ttf");
		
		TextView tvLicores = (TextView) main.findViewById(R.id.tvLicores);
		tvLicores.setTypeface(coolvetica);
		
		TextView tvLugares = (TextView) main.findViewById(R.id.tvLugares);
		tvLugares.setTypeface(coolvetica);
		
		TextView tvRestaurantes = (TextView) main.findViewById(R.id.tvRestaurantes);
		tvRestaurantes.setTypeface(coolvetica);
		
		TextView tvservicios = (TextView) main.findViewById(R.id.tvservicios);
		tvservicios.setTypeface(coolvetica);
		
		TextView tvDatos = (TextView) main.findViewById(R.id.tvDatos);
		tvDatos.setTypeface(coolvetica);
		
		final Button b =(Button) main.findViewById(R.id.btnRestaurantes);
		 b.setOnClickListener(new OnClickListener() 
	        {
	            @Override
	            public void onClick(View v) 
	            {
	            	main.setBack("home");
	            	mf.changeFragment(Constants.FRAGMENT_RESTAURANTES, 
	        		R.id.frameRootContainer, 
	        		new RestaurantFragment(mf), 
	        		false, true, false);
	            }
	        });
	}
	
}

package com.fragment.details;

import java.util.Timer;
import java.util.TimerTask;

import com.config.Constants;
import com.config.UIConfig;

import com.fragment.details.sub.SubDetailAboutView;
import com.fragment.details.sub.SubDetailGalleryView;
import com.fragment.details.sub.SubDetailMapView;

import com.models.Restaurante;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.tuDomicilio.tuDomicilio.IManagerFragmento;
import com.tuDomicilio.tuDomicilio.MainActivity;
import com.tuDomicilio.tuDomicilio.R;
import com.segment.control.MGSegmentControl;
import com.segment.control.MGSegmentControl.OnMGSegmentSelectedListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class DetailsFragment extends Fragment implements OnMGSegmentSelectedListener, OnClickListener{

	DisplayImageOptions options;
	private Restaurante restaurant;
	private View viewInflate;
	private ImageView imgBack, imgFave;
	
	private Constants cs = new Constants();
	
	SubDetailAboutView aboutView = null;
	SubDetailMapView mapView = null;
	SubDetailGalleryView galleryView = null;
	IManagerFragmento mf;
	
	public DetailsFragment(IManagerFragmento mf) {
		// TODO Auto-generated constructor stub
		this.mf = mf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		// save the reference of the inflated view
		viewInflate = inflater.inflate(R.layout.fragment_details, container, false);		
        return viewInflate;
	}
	
	@Override
    public void onDestroyView()  {
        super.onDestroyView();
        if (viewInflate != null) {
            ViewGroup parentViewGroup = (ViewGroup) viewInflate.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }
        }
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// this is really important in order to save the state across screen
		// configuration changes for example
		setRetainInstance(true);
	}
	
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		options = new DisplayImageOptions.Builder()
			.showImageOnLoading(UIConfig.SLIDER_PLACEHOLDER)
			.showImageForEmptyUri(UIConfig.SLIDER_PLACEHOLDER)
			.showImageOnFail(UIConfig.SLIDER_PLACEHOLDER)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
		
        // SET THIS BACK TO NULL WHEN TRANSFERED TO ANOTHER FRAGMENT
        aboutView = null;
    	mapView = null;
    	galleryView = null;
    	
        Bundle b = getArguments();
        this.restaurant = (Restaurante) b.getSerializable("restaurant");
        setDetails(this.restaurant);
        
        preDrawCalculation();
        //checkFavoriteState();
	}

	public Restaurante getRestaurant() {
		return restaurant;
	}
	
	private void preDrawCalculation() {
		
		// TODO Auto-generated method stub		
		ImageView imgViewPic = (ImageView) viewInflate.findViewById(R.id.imgViewPic);
		
		MainActivity main = (MainActivity)getActivity();
		main.getImageLoader().displayImage(cs.URL_PATH_PHOTO + restaurant.getIdRestaurante() + ".png", imgViewPic, options);
		
	}
	
	
	public void setDetails(Restaurante res) {
		 MGSegmentControl segmentControl = (MGSegmentControl) 
					viewInflate.findViewById(R.id.segmentControl);

			segmentControl.setOnMGSegmentSelectedListener(this);
       		
		try {
			final MainActivity main = (MainActivity) getActivity();
			Typeface coolvetica = Typeface.createFromAsset(main.getAssets(),"fonts/coolvetica.ttf");
			segmentControl.setSegmentCreation(UIConfig.SELECTED_INNER_TAB_BG, 
					UIConfig.UNSELECTED_INNER_TAB_BG, UIConfig.INNER_TAB_TITLE, coolvetica);
			segmentControl.setSelectedSegment(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	private void showDetailFragment(int pos) {
		
		FrameLayout frameDetails = (FrameLayout) viewInflate.findViewById(R.id.containerDetails);
		frameDetails.removeAllViews();
		
		if(pos == 0) {
			
			if(aboutView == null) {
				aboutView = new SubDetailAboutView(getActivity());
				aboutView.setDetail(restaurant);
			}
			
			frameDetails.addView(aboutView.getView());
		}
		
		else if(pos == 2) {
			
			 if(mapView == null) {
				mapView = new SubDetailMapView(getActivity());
				mapView.setMapData(restaurant);
				
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
								mapView.setMapData(restaurant);
							}
						});
					}
				}, 500);
			}
			
			frameDetails.addView(mapView.getView());
		}
		
		else if(pos == 1) {
			
			if(galleryView == null) {
				galleryView = new SubDetailGalleryView(getActivity(), (MainActivity) getActivity(), mf);
				galleryView.showGalleries(restaurant);
			}
			
			frameDetails.addView(galleryView.getView());
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
	}
	
	
	private void checkFavoriteState() {
		
		/*MainActivity main = (MainActivity)getActivity();
		Restaurant resFave = main.getQueries().getFavoriteRestaurantsByRestaurantId(restaurant.restaurant_id);
		
		if(resFave == null) {
			imgFave.setImageResource(UIConfig.FAVE_ADD);
			MainActivity.favoriteState = FAVORITES.FAVORITES_ADD;
		}
		else {
			imgFave.setImageResource(UIConfig.FAVE_REMOVE);
			MainActivity.favoriteState = FAVORITES.FAVORITES_REMOVE;
		}
		*/
	}
	
	private void addToFavorites() {
		/*
		MainActivity main = (MainActivity)getActivity();
		main.getQueries().insertFavorite(restaurant.restaurant_id);
		
		checkFavoriteState();
		*/
	}
	
	private void deleteFavorite() {
		
		/*
		MainActivity main = (MainActivity)getActivity();
		
		Favorite fave = main.getQueries().getFavoriteByRestaurantId(restaurant.restaurant_id);
		main.getQueries().deleteFavorite(fave.favorite_id);
		
		checkFavoriteState();
		*/
	}

	@Override
	public void OnMGSegmentControlSelected(MGSegmentControl control,
			Button button, int position) {
		// TODO Auto-generated method stub
		showDetailFragment(position);
		button.setTextColor(Color.WHITE);
		button.setTypeface(button.getTypeface(), Typeface.BOLD);
	}

	@Override
	public void OnMGSegmentControlCreated(MGSegmentControl control,
			Button button, int position) {
		// TODO Auto-generated method stub
		//showDetailFragment(position);
	}
}

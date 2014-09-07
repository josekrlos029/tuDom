package com.fragment.restaurant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.adapters.MGListAdapter;
import com.adapters.MGListAdapter.OnMGListAdapterAdapterListener;
import com.config.Constants;
import com.config.UIConfig;
import com.dataParser.JSONParser;
import com.db.Queries;
import com.fragment.details.DetailsFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.imageview.RoundedImageView;

import com.models.Restaurante;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.tuDomicilio.tuDomicilio.IManagerFragmento;
import com.tuDomicilio.tuDomicilio.MainActivity;
import com.tuDomicilio.tuDomicilio.R;
import com.utilities.MGUtilities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class RestaurantFragment extends Fragment implements OnItemClickListener, OnClickListener{

	private View viewInflate = null;
	private ArrayList<Restaurante> restaurantList;
	public int categoryId = 0;
	private ImageView imgBack, imgFave;
	DisplayImageOptions options;
	private Constants cn = new Constants();
	private String url = cn.URL_PATH + "consultarRestaurantesPorTipo";
	private String idTipo = "restaurantes";
	private Typeface coolvetica;
	IManagerFragmento mf;
	
	public RestaurantFragment(IManagerFragmento mf) {
		// TODO Auto-generated constructor stub
		this.mf = mf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		// save the reference of the inflated view
		if(viewInflate == null)
			viewInflate = inflater.inflate(R.layout.fragment_restaurants, container, false);

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
	
	@SuppressWarnings("unchecked")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		final MainActivity main = (MainActivity) getActivity();
		final ImageView buttonBack = (ImageView) main.findViewById(R.id.ibBack);
   	 	buttonBack.setVisibility(View.VISIBLE);
		
        options = new DisplayImageOptions.Builder()
			.showImageOnLoading(UIConfig.RESTAURANT_LIST_PLACEHOLDER)
			.showImageForEmptyUri(UIConfig.RESTAURANT_LIST_PLACEHOLDER)
			.showImageOnFail(UIConfig.RESTAURANT_LIST_PLACEHOLDER)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
        
        //getRestaurants();
        Queries q = main.getQueries();
        ArrayList<Restaurante> l = new ArrayList<Restaurante>();
        l = q.getRestaurants();
        Collections.shuffle(l);
        showList(l);
        
	}

	public void showList(final ArrayList<Restaurante> restaurantList) {
		
		this.restaurantList = restaurantList;
		if(viewInflate == null)
			return;
		
		final MainActivity main = (MainActivity) getActivity();
		
		        
		ListView listView = (ListView) viewInflate.findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
		
		View v = MGUtilities.getNoResultView(getActivity());
		
		((ViewGroup)viewInflate.getParent()).addView(
				v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		listView.setEmptyView(v);
        
		MGListAdapter adapter = new MGListAdapter(
				getActivity(), restaurantList.size(), R.layout.restaurant_entry);
		
		adapter.setOnMGListAdapterAdapterListener(new OnMGListAdapterAdapterListener() {
			
			@Override
			public void OnMGListAdapterAdapterCreated(MGListAdapter adapter, View v,
					int position, ViewGroup viewGroup) {
				// TODO Auto-generated method stub
				
				Restaurante res = restaurantList.get(position);
				
				RoundedImageView imgViewThumb = (RoundedImageView) v.findViewById(R.id.imgViewThumb);
				imgViewThumb.setCornerRadius(UIConfig.BORDER_RADIUS);
				imgViewThumb.setBorderWidth(UIConfig.BORDER_WIDTH);
				imgViewThumb.setBorderColor(getResources().getColor(UIConfig.THEME_COLOR));
				
				main.getImageLoader().displayImage(cn.URL_PATH_PHOTO_RESTAURANTE + res.getIdRestaurante() + ".png", imgViewThumb, options);
				
				ImageView imgListFave = (ImageView) v.findViewById(R.id.imgListFave);
				ImageView imgListFeatured = (ImageView) v.findViewById(R.id.imgListFeatured);
				
				imgListFave.setVisibility(View.INVISIBLE);
				imgListFeatured.setVisibility(View.INVISIBLE);
				
				String var = "2";
				
				if(var.contains("1"))
					imgListFeatured.setVisibility(View.VISIBLE);
				
				Spanned name = Html.fromHtml(res.getNombre());
				
				coolvetica = Typeface.createFromAsset(main.getAssets(),"fonts/coolvetica.ttf");
				TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
				tvTitle.setText(name);
				tvTitle.setTypeface(coolvetica);
				
				Spanned address = Html.fromHtml(res.getDescripcion());
				TextView tvSubtitle = (TextView) v.findViewById(R.id.tvSubtitle);
				tvSubtitle.setText(address);
				tvSubtitle.setTypeface(coolvetica);
			}
		});
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}


	@Override
	public void onItemClick(AdapterView<?> adapterView, View v, int pos, long resId) {
		// TODO Auto-generated method stub
		TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		tvTitle.setTextColor(getResources().getColor(UIConfig.THEME_COLOR));
		
		ImageView imgArrow = (ImageView) v.findViewById(R.id.imgArrow);
		imgArrow.setImageResource(UIConfig.LIST_ARROW_SELECTED);
		
		Restaurante res = restaurantList.get(pos);
		MainActivity main = (MainActivity)getActivity();
		main.setBack("listaRestaurantes");
		
		Bundle b = new Bundle();
		b.putSerializable("restaurant", res);
		
		DetailsFragment frag = new DetailsFragment(mf);
		frag.setArguments(b);
		
        mf.changeFragment(
        		Constants.FRAGMENT_TAB_1_DETAILS, 
        		R.id.frameRootContainer, 
        		frag, 
        		true, true, false);
       
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

    
    
}

package com.fragment.imageviewer	;

import java.util.ArrayList;
/*
import ru.truba.touchgallery.GalleryWidget.BaseUrlPagerAdapter;
import ru.truba.touchgallery.GalleryWidget.GalleryViewPager;
import ru.truba.touchgallery.GalleryWidget.BaseUrlPagerAdapter.OnItemChangeListener;
*/
import com.config.UIConfig;
//import com.image.cache.util.ImageFetcherWrapper;
import com.models.Photo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
//import com.project.restaurantlocator.MainActivity;
//import com.project.restaurantlocator.R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ImageViewerFragment extends Fragment implements OnClickListener{

	private View viewInflate = null;
	//public ImageFetcherWrapper imageFetcher;
	private ImageView imgBack;
	DisplayImageOptions options;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		// save the reference of the inflated view
		//if(viewInflate == null)
			//viewInflate = inflater.inflate(R.layout.fragment_imageviewer, container, false);

        return viewInflate;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// this is really important in order to save the state across screen
		// configuration changes for example
		setRetainInstance(true);
		
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		/*
		imageFetcher = new ImageFetcherWrapper(
				getActivity(), 0, 0, UIConfig.SLIDER_PLACEHOLDER);
		
		imgBack = (ImageView) viewInflate.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        
        
        options = new DisplayImageOptions.Builder()
			.showImageOnLoading(UIConfig.SLIDER_PLACEHOLDER)
			.showImageForEmptyUri(UIConfig.SLIDER_PLACEHOLDER)
			.showImageOnFail(UIConfig.SLIDER_PLACEHOLDER)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
        
        Bundle b = getArguments();
        ArrayList<Photo> photoList = (ArrayList<Photo>) b.getSerializable("photoList");
        int index = b.getInt("index");
        setPhotos(photoList, index);
        */
	}
	
	public void setPhotos(ArrayList<Photo> photoList, int position) {
		/*
		String[] urls = new String[photoList.size()];
		
		for(int x = 0; x < photoList.size(); x++) {
			Photo p = photoList.get(x);
			
			String strUrl = p.photo_url;
			if(!strUrl.contains("http")) {
				strUrl = "http://" + strUrl;
			}
			
			urls[x] = strUrl;
		}
			
		
		BaseUrlPagerAdapter adapter = new BaseUrlPagerAdapter(
				getActivity(), urls, R.layout.imageviewer_entry, UIConfig.SLIDER_PLACEHOLDER); 
		
		adapter.setOnItemChangeListener(new OnItemChangeListener() {
			
			@Override
			public void onItemChange(int currentPosition) { }
			
			@Override
			public void onGalleryAdapterCreated(BaseUrlPagerAdapter adapter, View v,
					int currentPosition) { }

			@Override
			public void onItemImageView(String imageUrl, ImageView imgView, final ProgressBar mProgressBar) {
				// TODO Auto-generated method stub
				MainActivity main = (MainActivity)getActivity();
				
				main.getImageLoader().displayImage(imageUrl, imgView, options, 
						
					new SimpleImageLoadingListener() {
					 
					@Override
					 public void onLoadingStarted(String imageUri, View view) {
						mProgressBar.setProgress(0);
						mProgressBar.setVisibility(View.VISIBLE);
					 }

					 @Override
					 public void onLoadingFailed(String imageUri, View view,
							 FailReason failReason) {
						 mProgressBar.setVisibility(View.GONE);
					 }

					 @Override
					 public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						 mProgressBar.setVisibility(View.GONE);
					 }
				 }, new ImageLoadingProgressListener() {
					 @Override
					 public void onProgressUpdate(String imageUri, View view, int current,
							 int total) {
						 mProgressBar.setProgress(Math.round(100.0f * current / total));
					 }
				 }
);
			}
		});
		
		GalleryViewPager galleryPager = (GalleryViewPager) viewInflate.findViewById(R.id.imageViewer);
        galleryPager.setOffscreenPageLimit(2);
        galleryPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        
        if(position < photoList.size())
        	galleryPager.setCurrentItem(position);
		*/
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		/*
		switch (v.getId()) {
			case R.id.imgBack:
				MainActivity main = (MainActivity) getActivity();
				main.popBackStack();
				break;
		}*/
	}
	
}

package com.config;

import com.tuDomicilio.tuDomicilio.R;

public class UIConfig {

	
	public static int[] UNSELECTED_TAB_ICONS = {
			R.drawable.ic_restaurant_unselected,
			R.drawable.ic_featured_unselected,
			R.drawable.ic_search_unselected,
			R.drawable.ic_map_unselected,
			R.drawable.ic_gallery_unselected
	};
	
	public static int[] SELECTED_TAB_ICONS = {
			R.drawable.ic_restaurant,
			R.drawable.ic_featured,
			R.drawable.ic_search,
			R.drawable.ic_map,
			R.drawable.ic_gallery
	};
	
	public static int[] TAB_TITLE = {
		R.string.tab_restaurants,
		R.string.tab_featured,
		R.string.tab_search,
		R.string.tab_map,
		R.string.tab_gallery
	};
	
	public static int[] INNER_TAB_TITLE = {
		R.string.sub_tab_details,
		R.string.sub_tab_gallery,
		R.string.sub_tab_map,
		
	};
	
	public static int[] SELECTED_INNER_TAB_BG = {
		R.drawable.inner_tab_left_selected,
		R.drawable.inner_tab_left_selected,
		R.drawable.inner_tab_left_selected
	};
	
	public static int[] UNSELECTED_INNER_TAB_BG = {
		R.drawable.inner_tab_left,
		R.drawable.inner_tab_left,
		R.drawable.inner_tab_left
	};
	
	
	
	public static int TAB_UNSELECTED_TEXT_COLOR = R.color.unselected_text_color;
	public static int TAB_SELECTED_TEXT_COLOR = R.color.theme_color;
	
	
	public static int BORDER_THICKNESS = 0;
	
	
	
	
	
	
	
	//public static int FAVE_ADD = R.drawable.ic_fave_add;
	
	//public static int FAVE_REMOVE = R.drawable.ic_fave_remove;
	
	//public static int FAVE_LIST = R.drawable.ic_fave;
	
	public static int RESTAURANT_LIST_PLACEHOLDER = R.drawable.restaurant_placeholder;
	
	public static float BORDER_RADIUS = 10;
	
	public static float BORDER_WIDTH = 5;
	
	public static int THEME_COLOR = R.color.theme_color;
	
	public static int LIST_ARROW_NORMAL = R.drawable.list_arrow;
	
	public static int LIST_ARROW_SELECTED = R.drawable.list_arrow_selected;
	
	public static int SLIDER_PLACEHOLDER = R.drawable.placeholder;
}

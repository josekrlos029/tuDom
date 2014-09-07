package com.tabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;

public class TabsListener <T extends Fragment> implements TabListener{
	private Fragment fragment;
    private final String tag;
   
    public TabsListener(Activity activity, String tag, Class<T> cls) {
        this.tag = tag;
        fragment = Fragment.instantiate(activity, cls.getName());
    }
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        ft.replace(android.R.id.content, fragment, tag);
    }
    @Override 
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        ft.remove(fragment);
    }
    @Override 
    public void onTabReselected(Tab tab, FragmentTransaction ft) {}
}

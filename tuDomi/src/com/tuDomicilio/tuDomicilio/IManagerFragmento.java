package com.tuDomicilio.tuDomicilio;

import android.support.v4.app.Fragment;

public interface IManagerFragmento {
	public void changeFragment(String fragmentTag, int layoutId, Fragment frag, boolean addToBackStack, boolean animate, boolean back);
	public void showpp(IMenu obj);
}

package com.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.models.Producto;
import com.tuDomicilio.tuDomicilio.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
 
public class MyListAdapter extends BaseExpandableListAdapter {
 
	private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Producto>> _listDataChild;
 
    public MyListAdapter(Context context, List<String> listDataHeader,
            HashMap<String, List<Producto>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
    	
    	Producto p = (Producto) getChild(groupPosition, childPosition);
        final String childText = p.getNombre();
        
        
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }
        Typeface coolvetica = Typeface.createFromAsset(_context.getAssets(),"fonts/coolvetica.ttf");
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        txtListChild.setTypeface(coolvetica);

        TextView txtListSubtitle = (TextView) convertView
                .findViewById(R.id.lblSubtitle);
        
        txtListSubtitle.setText(p.getDescripcion());
        txtListSubtitle.setTypeface(coolvetica);
        
        TextView lblPrecio = (TextView) convertView
                .findViewById(R.id.lblPrecio);
        
        lblPrecio.setText(Integer.toString(p.getPrecio()));
        lblPrecio.setTypeface(coolvetica);
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
        Typeface coolvetica = Typeface.createFromAsset(_context.getAssets(),"fonts/coolvetica.ttf");
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(coolvetica);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
 
}
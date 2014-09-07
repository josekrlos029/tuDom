package com.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Category {

	private String nombre;
	private int idCategoria;
	private int idRestaurante;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public int getIdRestaurante() {
		return idRestaurante;
	}
	public void setIdRestaurante(int idRestaurante) {
		this.idRestaurante = idRestaurante;
	}
	
public void mapearCategoria(JSONObject json, Category r){
		
		try {
			
			int id = (json.getString("id") != null)? Integer.parseInt(json.getString("id")):0;	
			r.setIdCategoria(id);
			
			String nombre = (json.getString("nombre") != null)? json.getString("nombre"): "";
			r.setNombre(nombre);
			
			int idRestaurante = (json.getString("idRestaurante") != null)? Integer.parseInt(json.getString("idRestaurante")):0;	
			r.setIdRestaurante(idRestaurante);
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}

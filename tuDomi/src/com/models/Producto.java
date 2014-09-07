package com.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.tuDomicilio.tuDomicilio.IMenu;

public class Producto implements IMenu{
	private int idProducto;
	private String nombre;
	private String descripcion;
	private int precio;
	private String estado;
	private int idCategoria;
	private int idRestaurante;
	
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
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
	public void mapearProducto(JSONObject json, Producto r){
		
		try {
			
			int id = (json.getString("idProducto") != null)? Integer.parseInt(json.getString("idProducto")):0;	
			r.setIdProducto(id);
			
			String nombre = (json.getString("nombre") != null)? json.getString("nombre"): "";
			r.setNombre(nombre);
			
			String descripcion = (json.getString("descripcion") != null)? json.getString("descripcion"): "";
			r.setDescripcion(descripcion);
			
			String estado = (json.getString("estado") != null)? json.getString("estado"): "";
			r.setEstado(estado);

			int precio  = (json.getString("precio") != null)? Integer.parseInt(json.getString("precio")):0;
		    r.setPrecio(precio);
		    
		    
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	
}

package com.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Restaurante implements Serializable{

	
	private String direccion;
	private String regId;
	private String token;
	private String descripcion;
	private String email;
	private String clave;
	private double votos;
	private String lat;
	private String lng;
	private String nombre;
	private String telefono;
	private double puntaje;
	private int idRestaurante;
	private int idTipoRestaurante;
	private String estado;
	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public double getVotos() {
		return votos;
	}
	public void setVotos(double votos) {
		this.votos = votos;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public double getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(double puntaje) {
		this.puntaje = puntaje;
	}
	public int getIdRestaurante() {
		return idRestaurante;
	}
	public void setIdRestaurante(int idRestaurante) {
		this.idRestaurante = idRestaurante;
	}
	public int getIdTipoRestaurante() {
		return idTipoRestaurante;
	}
	public void setIdTipoRestaurante(int idTipoRestaurante) {
		this.idTipoRestaurante = idTipoRestaurante;
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public void mapearRestaurante(JSONObject json, Restaurante r){
		
		try {
			
			int id = (json.getString("id") != null)? Integer.parseInt(json.getString("id")):0;	
			r.setIdRestaurante(id);
			
			String nombre = (json.getString("nombre") != null)? json.getString("nombre"): "";
			r.setNombre(nombre);
			
			String descripcion = (json.getString("descripcion") != null)? json.getString("descripcion"): "";
			r.setDescripcion(descripcion);
			
			String telefono = (json.getString("telefono") != null)? json.getString("telefono"): "";
			r.setTelefono(telefono);
			
			String direccion = (json.getString("direccion") != null)? json.getString("direccion"): "";
			r.setDireccion(direccion);
			
			String email = (json.getString("email") != null)? json.getString("email"): "";
			r.setEmail(email);
			
			String lat = (json.getString("lat") != null)? json.getString("lat"): "";
			r.setLat(lat);
			
			String lng = (json.getString("lng") != null)? json.getString("lng"): "";
			r.setLng(lng);
			
			int puntaje = (json.getString("puntaje") != null)? Integer.parseInt(json.getString("puntaje")):0;
		    r.setPuntaje(puntaje);
		    
		    int idTipo = (json.getString("idTipo") != null)? Integer.parseInt(json.getString("idTipo")):0;
		    r.setIdTipoRestaurante(idTipo);
		    
		    String estado = (json.getString("estado") != null)? json.getString("estado"): "";
		    r.setEstado(estado);
		    
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}

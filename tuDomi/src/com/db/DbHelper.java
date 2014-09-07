package com.db;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {
	
	static final String TAG = "DbHelper";
	static final String DB_NAME = "tuDomicilio";
	static final int DB_VERSION = 1;
	static Activity activity;
	
	public DbHelper(Activity act) {
		super(act.getApplicationContext(), DB_NAME, null, DB_VERSION);
		activity = act;
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
			
		
	    db.execSQL("CREATE TABLE IF NOT EXISTS lista("
	    		+ "id INTEGER UNIQUE,"
	    		+ "nombre TEXT,"
	    		+ "descripcion TEXT,"
	    		+ "precio INTEGER,"
	    		+ "cantidad INTEGER,"
	    		+ "indicaciones TEXT,"
	    		+ "idRestaurante INTEGER"
	    		+ ");");
	    
	    db.execSQL("CREATE TABLE IF NOT EXISTS categoriaproducto("
	    		+ "idCategoria INTEGER PRIMARY KEY,"
	    		+ "nombre TEXT,"
	    		+ "idRestaurante INTEGER"
	    		+ ");");
	    
	    
	    db.execSQL("CREATE TABLE IF NOT EXISTS act("
	    		+ "fecha TEXT"
	    		+ ");" );
	    
	    db.execSQL("CREATE TABLE IF NOT EXISTS favorites("
	    		+ "favorite_id INTEGER PRIMARY KEY AUTOINCREMENT,"
	    		+ "restaurant_id INTEGER"
	    		+ ");");
	    
	    
	    db.execSQL("CREATE TABLE IF NOT EXISTS horario("
	    		+ "idRestaurante INTEGER,"
	    		+ "lunes TEXT,"
	    		+ "martes TEXT,"
	    		+ "miercoles TEXT,"
	    		+ "jueves TEXT,"
	    		+ "viernes TEXT,"
	    		+ "sabado TEXT,"
	    		+ "domingo TEXT"
	    		+ ");");
	    
	    
	    db.execSQL("CREATE TABLE IF NOT EXISTS productorestaurante("
	    		+ "idProducto INTEGER PRIMARY KEY,"
	    		+ "nombre TEXT,"
	    		+ "descripcion TEXT,"
	    		+ "idCategoria TEXT,"
	    		+ "precio INTEGER,"
	    		+ "estado TEXT"
	    		+ ");");
	    
	    db.execSQL("CREATE TABLE IF NOT EXISTS restaurante("
	    		+ "idRestaurante INTEGER PRIMARY KEY," //AUTOINCREMENT
	    		+ "nombre TEXT,"
	    		+ "descripcion TEXT,"
	    		+ "telefono TEXT,"
	    		+ "direccion TEXT,"
	    		+ "email TEXT,"
	    		+ "lat TEXT,"
	    		+ "lng TEXT,"
	    		+ "puntaje FLOAT,"
	    		+ "votos FLOAT,"
	    		+ "estado TEXT"
	    		+ ");");
	    
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
		db.execSQL("DROP TABLE IF EXISTS categoriaproducto");
		db.execSQL("DROP TABLE IF EXISTS fecha");
		db.execSQL("DROP TABLE IF EXISTS productorestaurante");
		db.execSQL("DROP TABLE IF EXISTS favorites");
		db.execSQL("DROP TABLE IF EXISTS restaurante");
		db.execSQL("DROP TABLE IF EXISTS horario");
	}
}
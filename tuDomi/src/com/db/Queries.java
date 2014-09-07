package com.db;
import java.util.ArrayList;

import com.models.Category;
import com.models.Favorite;
import com.models.Photo;
import com.models.Producto;
import com.models.Restaurante;
import com.models.Carrito;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Queries {
	
	private SQLiteDatabase db;
	private DbHelper dbHelper;
	
	
	public Queries(SQLiteDatabase db, DbHelper dbHelper) {
		this.db = db;
		this.dbHelper = dbHelper;
	}
	
	public void deleteTable(String tableName) {
		
		db = dbHelper.getWritableDatabase();
		try{
			db.delete(tableName, null, null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		db.close();
	}
	
	
	public void insertRestaurant(Restaurante entry) {
		
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("idRestaurante", entry.getIdRestaurante());
		values.put("nombre", entry.getNombre());
		values.put("descripcion", entry.getDescripcion());
		values.put("telefono", entry.getTelefono());
		values.put("email", entry.getEmail());
		values.put("direccion", entry.getDireccion());
		values.put("lat", entry.getLat());
		values.put("lng", entry.getLng());
		values.put("puntaje", entry.getPuntaje());
		values.put("votos", entry.getVotos());
		db.insert("restaurante", null, values);
		db.close();
	}
	
	public void insertFavorite(int restaurantId) {
		
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("restaurant_id", restaurantId);
		db.insert("favorites", null, values);
		db.close();
	}
	
	public void insertFecha(String fecha) {
		
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("fecha", fecha);
		db.insert("act", null, values);
		db.close();
	}
	
	public void deleteFavorite(int favoriteId) {
		
		db = dbHelper.getWritableDatabase();
		try{
			db.delete("favorites", "favorite_id = " + favoriteId, null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		db.close();
	}
	
	
	public void insertPhoto(Photo entry) {
		
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("created_at", entry.created_at);
		values.put("photo_url", entry.photo_url);
		values.put("thumb_url", entry.thumb_url);
		values.put("photo_id", entry.photo_id);
		values.put("restaurant_id", entry.restaurant_id);
		db.insert("photos", null, values);
		db.close();
	}
	
	public void insertCategory(Category entry) {
		
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("idCategoria", entry.getIdCategoria());
		values.put("nombre", entry.getNombre());
		values.put("idRestaurante", entry.getIdRestaurante());
		db.insert("categoriaproducto", null, values);
		db.close();
	}
	
	public void insertProducto(Producto entry) {
		
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("idCategoria", entry.getIdCategoria());
		values.put("nombre", entry.getNombre());
		values.put("precio", entry.getPrecio());
		values.put("estado", entry.getEstado());
		values.put("idProducto", entry.getIdProducto());
		values.put("descripcion", entry.getDescripcion());
		db.insert("productorestaurante", null, values);
		db.close();
	}
	
	public void insertCarrito(Carrito entry) { 
		
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", entry.getIdProducto());
		values.put("nombre", entry.getNombre());
		values.put("descripcion", entry.getDescripcion());
		values.put("precio", entry.getPrecio());
		values.put("cantidad", entry.getCantidad());
		values.put("indicaciones", entry.getIndicaciones());
		values.put("idRestaurante", entry.getIdRestaurante());
		db.insert("lista", null, values);
		db.close();
	}
	
	public void updateCarrito(Carrito entry) { 
		
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("nombre", entry.getNombre());
		values.put("descripcion", entry.getDescripcion());
		values.put("precio", entry.getPrecio());
		values.put("cantidad", entry.getCantidad());
		values.put("indicaciones", entry.getIndicaciones());
		values.put("idRestaurante", entry.getIdRestaurante());
		db.update("lista", values, "id="+entry.getIdProducto(), null);
		db.close();
	}
	
	public void deleteProductoCarrito(int idProducto){
		db = dbHelper.getWritableDatabase();
		db.delete("lista", "id = " + idProducto, null);
		db.close();
	}
	
	public Category getCategoryByCategoryId(int categoryId) {
		
		Category entry = null;
		String sql = String.format("SELECT * FROM categoriaproducto WHERE idCategoria = %d", categoryId);
		ArrayList<Category> list = new ArrayList<Category>();
		db = dbHelper.getReadableDatabase();
		Cursor mCursor = db.rawQuery(sql, null); 
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) {
			do {
				entry = new Category();
				entry.setNombre(mCursor.getString( mCursor.getColumnIndex("nombre") ));
				entry.setIdCategoria(mCursor.getInt( mCursor.getColumnIndex("idCategoria") ));
				entry.setIdRestaurante( mCursor.getInt( mCursor.getColumnIndex("idRestaurante") ));
				list.add(entry);
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		dbHelper.close();
		return entry;
	}
	
	public ArrayList<Category> getCategories(int idRestaurante) {
		
		ArrayList<Category> list = new ArrayList<Category>();
		db = dbHelper.getReadableDatabase();
		String sql = String.format("SELECT * FROM categoriaproducto WHERE idRestaurante = %d", idRestaurante);
		Cursor mCursor = db.rawQuery(sql, null);
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) {
			do {
				Category entry = new Category();
				entry.setNombre(mCursor.getString( mCursor.getColumnIndex("nombre") ));
				entry.setIdCategoria(mCursor.getInt( mCursor.getColumnIndex("idCategoria") ));
				entry.setIdRestaurante( mCursor.getInt( mCursor.getColumnIndex("idRestaurante") ));
				list.add(entry);
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		dbHelper.close();
		return list;
	
	}
	
public String getFecha() {
		
		String fecha = null;
		db = dbHelper.getReadableDatabase();
		String sql = "SELECT * FROM act";
		Cursor mCursor = db.rawQuery(sql, null); 
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) {
			do {
				fecha = mCursor.getString( mCursor.getColumnIndex("fecha"));
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		dbHelper.close();
		return fecha;
	}
	
	public ArrayList<Producto> getProductos(int idCategoria) {
		
		ArrayList<Producto> list = new ArrayList<Producto>();
		db = dbHelper.getReadableDatabase();
		String sql = String.format("SELECT * FROM productorestaurante WHERE idCategoria = %d", idCategoria);
		Cursor mCursor = db.rawQuery(sql, null);
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) {
			do {
				Producto entry = new Producto();
				entry.setNombre(mCursor.getString( mCursor.getColumnIndex("nombre") ));
				entry.setDescripcion(mCursor.getString( mCursor.getColumnIndex("descripcion") ));
				entry.setEstado(mCursor.getString( mCursor.getColumnIndex("estado") ));
				entry.setIdProducto(mCursor.getInt( mCursor.getColumnIndex("idProducto") ));
				entry.setIdCategoria(mCursor.getInt( mCursor.getColumnIndex("idCategoria") ));
				entry.setPrecio( mCursor.getInt( mCursor.getColumnIndex("precio") ));
				list.add(entry);
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		dbHelper.close();
		return list;
	}
	
	private ArrayList<Restaurante> getRestaurantsUsingSQL(String sql) {
		
		ArrayList<Restaurante> list = new ArrayList<Restaurante>();
		db = dbHelper.getReadableDatabase();
		Cursor mCursor = db.rawQuery(sql, null); 
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) {
			do {
				Restaurante entry = new Restaurante();
				entry.setIdRestaurante(mCursor.getInt( mCursor.getColumnIndex("idRestaurante") ));
				entry.setNombre(mCursor.getString( mCursor.getColumnIndex("nombre") ));
				entry.setDescripcion(mCursor.getString( mCursor.getColumnIndex("descripcion") ));
				entry.setTelefono( mCursor.getString( mCursor.getColumnIndex("telefono") ));
				entry.setDireccion(mCursor.getString( mCursor.getColumnIndex("direccion") ));
				entry.setEmail(mCursor.getString( mCursor.getColumnIndex("email") ));
				entry.setLat(mCursor.getString( mCursor.getColumnIndex("lat") ));
				entry.setLng(mCursor.getString( mCursor.getColumnIndex("lng") ));
				entry.setPuntaje(mCursor.getFloat( mCursor.getColumnIndex("puntaje") ));
				entry.setVotos(mCursor.getFloat( mCursor.getColumnIndex("votos") ));
				entry.setEstado(mCursor.getString( mCursor.getColumnIndex("estado") ));
				list.add(entry);
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		dbHelper.close();
		return list;
	}
	/*
	public ArrayList<Restaurant> getFeaturedRestaurants() {
		
		String sql = String.format("SELECT * FROM restaurants WHERE featured = 1 ORDER BY name ASC");
		ArrayList<Restaurant> list = getRestaurantsUsingSQL(sql);
		return list;
	}
*/
	
	public ArrayList<Restaurante> getRestaurants() {
		
		ArrayList<Restaurante> list = getRestaurantsUsingSQL("SELECT * FROM restaurante");
		return list;
	}
	
	public ArrayList<Restaurante> getRestaurantsByCategoryId(int categoryId) {
		
		String sql = String.format("SELECT * FROM restaurants WHERE category_id = %d ORDER BY name ASC", categoryId);
		ArrayList<Restaurante> list = getRestaurantsUsingSQL(sql);
		return list;
	}
	
	public Restaurante getRestaurantByRestaurantId(int restaurantId) {
		
		String sql = String.format("SELECT * FROM restaurante WHERE idRestaurante = %d ORDER BY name ASC", restaurantId);
		ArrayList<Restaurante> list = getRestaurantsUsingSQL(sql);
		
		return list.size() == 0 ? null : list.get(0);
	}
	
	
	private ArrayList<Photo> getPhotosBySQL(String sql) {
		
		ArrayList<Photo> list = new ArrayList<Photo>();
		db = dbHelper.getReadableDatabase();
		Cursor mCursor = db.rawQuery(sql, null); 
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) {
			do {
				Photo entry = new Photo();
				entry.created_at = mCursor.getString( mCursor.getColumnIndex("created_at") );
				entry.photo_id = mCursor.getInt( mCursor.getColumnIndex("photo_id") );
				entry.photo_url = mCursor.getString( mCursor.getColumnIndex("photo_url") );
				entry.restaurant_id = mCursor.getInt( mCursor.getColumnIndex("restaurant_id") );
				entry.thumb_url = mCursor.getString( mCursor.getColumnIndex("thumb_url") );
				list.add(entry);
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		dbHelper.close();
		return list;
	}

	public ArrayList<Photo> getPhotos() {
		
		return getPhotosBySQL("SELECT * FROM photos");
	}
	
	public ArrayList<Photo> getPhotosByRestaurantId(int restaurantId) {
		
		String sql = String.format("SELECT * FROM photos WHERE restaurant_id = %d", restaurantId);
		return getPhotosBySQL(sql);
	}
	
	
	public Photo getPhotoByPhotoId(int photoId) {
		
		String sql = String.format("SELECT * FROM photos WHERE photo_id = %d", photoId);
		ArrayList<Photo> photos = getPhotosBySQL(sql);
		return photos.size() == 0 ? null : photos.get(0);
	}
	
	public Photo getPhotoByRestaurantId(int restaurantId) {
		
		Photo entry = null;
		String sql = String.format("SELECT * FROM photos WHERE restaurant_id = %d", restaurantId);
		db = dbHelper.getReadableDatabase();
		Cursor mCursor = db.rawQuery(sql , null); 
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) {
			do {
				entry = new Photo();
				entry.created_at = mCursor.getString( mCursor.getColumnIndex("created_at") );
				entry.photo_id = mCursor.getInt( mCursor.getColumnIndex("photo_id") );
				entry.photo_url = mCursor.getString( mCursor.getColumnIndex("photo_url") );
				entry.restaurant_id = mCursor.getInt( mCursor.getColumnIndex("restaurant_id") );
				entry.thumb_url = mCursor.getString( mCursor.getColumnIndex("thumb_url") );
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		dbHelper.close();
		return entry;
	}
	
	public ArrayList<Favorite> getFavorites() {
		
		ArrayList<Favorite> list = new ArrayList<Favorite>();
		db = dbHelper.getReadableDatabase();
		Cursor mCursor = db.rawQuery("SELECT * FROM favorites", null); 
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) {
			do {
				Favorite entry = new Favorite();
				entry.favorite_id = mCursor.getInt( mCursor.getColumnIndex("favorite_id") );
				entry.restaurant_id = mCursor.getInt( mCursor.getColumnIndex("restaurant_id") );
				list.add(entry);
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		dbHelper.close();
		return list;
	}
	
	/*
	public ArrayList<Restaurant> getFavoriteRestaurants() {
		
		String sql = String.format("SELECT * FROM restaurants INNER JOIN favorites ON restaurants.restaurant_id = favorites.restaurant_id ORDER BY name ASC");
		ArrayList<Restaurant> list = getRestaurantsUsingSQL(sql);
		
		return list;
	}
	
	public Restaurant getFavoriteRestaurantsByRestaurantId(int restaurantId) {
		
		String sql = String.format(
				"SELECT * FROM restaurants INNER JOIN favorites ON " 
						+ "restaurants.restaurant_id = favorites.restaurant_id " 
						+ "WHERE restaurants.restaurant_id = %d", restaurantId);
		
		ArrayList<Restaurant> list = getRestaurantsUsingSQL(sql);
		
		return list.size() == 0 ? null : list.get(0);
	}
	
	*/
	public Favorite getFavoriteByRestaurantId(int restaurantId) {
		
		Favorite entry = null;
		String sql = String.format("SELECT * FROM favorites WHERE restaurant_id = %d", restaurantId);
		db = dbHelper.getReadableDatabase();
		Cursor mCursor = db.rawQuery(sql , null); 
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) {
			do {
				entry = new Favorite();
				entry.favorite_id = mCursor.getInt( mCursor.getColumnIndex("favorite_id") );
				entry.restaurant_id = mCursor.getInt( mCursor.getColumnIndex("restaurant_id") );
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		dbHelper.close();
		return entry;
	}
	
	
	public ArrayList<String> getCategoryNames() {
		
		ArrayList<String> list = new ArrayList<String>();
		db = dbHelper.getReadableDatabase();
		Cursor mCursor = db.rawQuery("SELECT * FROM categories", null); 
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) {
			do {
				String cat = mCursor.getString( mCursor.getColumnIndex("category") );
				
				list.add(cat);
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		dbHelper.close();
		
		return list;
	}
	
	public Carrito getCarritoById(int id){
		
		Carrito c = null;
		db = dbHelper.getReadableDatabase();
		String sql = String.format("SELECT * FROM lista WHERE id = %d", id);
		Cursor mCursor = db.rawQuery(sql , null);
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) {
			do {
				c = new Carrito();
				c.setIdProducto(mCursor.getInt( mCursor.getColumnIndex("id") ));
				c.setNombre(mCursor.getString( mCursor.getColumnIndex("nombre") ));
				c.setDescripcion(mCursor.getString( mCursor.getColumnIndex("descripcion") ));
				c.setIndicaciones(mCursor.getString( mCursor.getColumnIndex("indicaciones") ));
				c.setCantidad(mCursor.getInt( mCursor.getColumnIndex("cantidad") ));
				c.setPrecio(mCursor.getInt( mCursor.getColumnIndex("precio") ));
				c.setIdRestaurante(mCursor.getInt( mCursor.getColumnIndex("idRestaurante") ));
				
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		dbHelper.close();
		return c;
		
	}
	
public ArrayList<Carrito> getCarrito(){
		
		ArrayList<Carrito> list = new ArrayList<Carrito>();
		db = dbHelper.getReadableDatabase();
		String sql = "SELECT * FROM lista";
		Cursor mCursor = db.rawQuery(sql , null);
		mCursor.moveToFirst();
		
		if (!mCursor.isAfterLast()) {
			do {
				
				Carrito c = new Carrito();
				c.setIdProducto(mCursor.getInt( mCursor.getColumnIndex("id") ));
				c.setNombre(mCursor.getString( mCursor.getColumnIndex("nombre") ));
				c.setDescripcion(mCursor.getString( mCursor.getColumnIndex("descripcion") ));
				c.setIndicaciones(mCursor.getString( mCursor.getColumnIndex("indicaciones") ));
				c.setCantidad(mCursor.getInt( mCursor.getColumnIndex("cantidad") ));
				c.setPrecio(mCursor.getInt( mCursor.getColumnIndex("precio") ));
				c.setIdRestaurante(mCursor.getInt( mCursor.getColumnIndex("idRestaurante") ));
				list.add(c);
				
			} while (mCursor.moveToNext());
		}
		mCursor.close();
		dbHelper.close();
		return list;
		
	}
	
}

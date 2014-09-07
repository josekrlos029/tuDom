package com.config;

public class Config {

	
	// Change this to YES or No to change format data using JSON or XML
	// NO means XML data format
	// YES means JSON data format
	public static boolean WILL_USE_JSON_FORMAT = false;
	
	
	// URL of the json file
	public static String DATA_JSON_URL = "http://mangasaurgames.com/apps/restau-v1.2/rest/data_json.php";

	
	// URL of the xml file
	public static String DATA_XML_URL = "http://mangasaurgames.com/apps/restau-v1.2/rest/data_xml.php";
	
	
	// Map zoom level
	public static int MAP_ZOOM_LEVEL = 14;
	
	
	// Search Category for all selection
	public final static String CATEGORY_ALL = "All";
	
	
	// Debug state, set this always to true to get always an update of data.
	public final static boolean WILL_DOWNLOAD_DATA = true;
}

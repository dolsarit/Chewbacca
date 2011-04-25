package cmuHCI.WalkyScotty;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import cmuHCI.WalkyScotty.entities.*;
import cmuHCI.WalkyScotty.util.*;

public class DBAdapter extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/cmuHCI.WalkyScotty/databases/";
    private static String DB_NAME = "chewbacca";
    private SQLiteDatabase myDataBase; 
    private final Context myContext;
    
    /* Image array..array index = loc_id */
    private static int[] images = 
	{ 
    	R.drawable.bakerhall,
		R.drawable.bakerhall,
		R.drawable.ghc,
		R.drawable.si_senor1,
		R.drawable.bakerhall,
		R.drawable.ph,
		R.drawable.cfa,
		R.drawable.cic,
		R.drawable.dh,
		R.drawable.uc,
		R.drawable.hbh,
		R.drawable.hh,
		R.drawable.hunt_lib_1
	};
    
    public static int getImage(int locID){
    	return images[locID % images.length];
    }
	
	public DBAdapter(Context context) {
		//super(context, name, factory, version);
		super(context, DB_NAME, null, 1);
        this.myContext = context;
	}
	
	/**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 dbExist = false;
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
    			System.out.println("copying db");
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
    
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;

    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
    	
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        //myDataBase = this.getReadableDatabase();
        
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
	
	//Data retrieval
	
	public ArrayList<Building> getBuildings(){
		ArrayList<Building> buildings = new ArrayList<Building>();
		
		Cursor c = myDataBase.query(true, "buildings JOIN locations ON (buildings.location_id = locations._id)", new String[]{"locations._id", "locations.name","locations.description","locations.image"}, null, null, null, null, "locations.name", null);

		c.moveToFirst();
		
		int idCol = c.getColumnIndex("_id");
		int nameCol = c.getColumnIndex("name");
		int descCol = c.getColumnIndex("description");
		int imgCol = c.getColumnIndex("image");
		
		if(c!=null){
			if(c.isFirst()){
				do{
					int id = c.getInt(idCol);
					String name = c.getString(nameCol);
					String desc = c.getString(descCol);
					String img = c.getString(imgCol);
					
					buildings.add(new Building(id, name, desc, img));
				}
				while(c.moveToNext());
			}
		}
		
		return buildings;
	}
	
	public ArrayList<Room> getRooms(){
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		Cursor c = myDataBase.query(true, "rooms JOIN locations ON (rooms.location_id = locations._id)", new String[]{"locations._id", "locations.name","locations.description","locations.image"}, null, null, null, null, "locations.name", null);

		c.moveToFirst();
		
		int idCol = c.getColumnIndex("_id");
		int nameCol = c.getColumnIndex("name");
		int descCol = c.getColumnIndex("description");
		int imgCol = c.getColumnIndex("image");
		
		if(c!=null){
			if(c.isFirst()){
				do{
					int id = c.getInt(idCol);
					String name = c.getString(nameCol);
					String desc = c.getString(descCol);
					String img = c.getString(imgCol);
					
					rooms.add(new Room(id, name, desc, img));
				}
				while(c.moveToNext());
			}
		}
		
		return rooms;
	}
	
	public ArrayList<Room> getRooms(Building building){
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		Cursor c = myDataBase.query(true, "rooms JOIN locations ON (rooms.location_id = locations._id)", 
				new String[]{"locations._id", "locations.name","locations.description","locations.image"}, "rooms.building_id = " + building.getId(), null, null, null, "locations.name", null);

		c.moveToFirst();
		
		int idCol = c.getColumnIndex("_id");
		int nameCol = c.getColumnIndex("name");
		int descCol = c.getColumnIndex("description");
		int imgCol = c.getColumnIndex("image");
		
		if(c!=null){
			if(c.isFirst()){
				do{
					int id = c.getInt(idCol);
					String name = c.getString(nameCol);
					String desc = c.getString(descCol);
					String img = c.getString(imgCol);
					
					rooms.add(new Room(id, name, desc, img));
				}
				while(c.moveToNext());
			}
		}
		
		return rooms;
	}
	
	public Building getBuilding(int locationID){
		Cursor c = myDataBase.query(true, "buildings JOIN locations ON (buildings.location_id = locations._id)", 
				new String[]{"locations._id", "locations.name","locations.description","locations.image"}, "locations._id = " + locationID, null, null, null, "locations.name", null);


		c.moveToFirst();
		
		int idCol = c.getColumnIndex("_id");
		int nameCol = c.getColumnIndex("name");
		int descCol = c.getColumnIndex("description");
		int imgCol = c.getColumnIndex("image");
		
		if(c!=null){
			if(c.isFirst()){
				int id = c.getInt(idCol);
				String name = c.getString(nameCol);
				String desc = c.getString(descCol);
				String img = c.getString(imgCol);
				
				return new Building(id, name, desc, img);
			}
		}
		
		return null;
	}
	
	public Building getBuildingForRoom(){
		return null;
	}
	
	public Restaurant getRestaurant(int locationID){
		Cursor c = myDataBase.query(true, "restaurants JOIN locations ON (restaurants.location_id = locations._id)", 
				new String[]{"locations._id", "locations.name","locations.description","locations.image, restaurants.hours, restaurants.menu"}, "locations._id = " + locationID, null, null, null, "locations.name", null);


		c.moveToFirst();
		
		int idCol = c.getColumnIndex("_id");
		int nameCol = c.getColumnIndex("name");
		int descCol = c.getColumnIndex("description");
		int imgCol = c.getColumnIndex("image");
		int hourCol = c.getColumnIndex("hours");
		int menuCol = c.getColumnIndex("menu"); 
		
		if(c!=null){
			if(c.isFirst()){
				int id = c.getInt(idCol);
				String name = c.getString(nameCol);
				String desc = c.getString(descCol);
				String img = c.getString(imgCol);
				String hours = c.getString(hourCol);
				String menu = c.getString(menuCol);
				
				return new Restaurant(id, name, desc, img, hours, menu);
			}
		}
		
		return null;
	}
	
	public ArrayList<Restaurant> getRestaurants(){
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		Cursor c = myDataBase.query(true, "restaurants JOIN locations ON (restaurants.location_id = locations._id)", 
				new String[]{"locations._id", "locations.name","locations.description","locations.image",
				"restaurants.hours", "restaurants.menu"}, null, null, null, null, "locations.name", null);

		c.moveToFirst();
		
		int idCol = c.getColumnIndex("_id");
		int nameCol = c.getColumnIndex("name");
		int descCol = c.getColumnIndex("description");
		int imgCol = c.getColumnIndex("image");
		int hourCol = c.getColumnIndex("hours");
		int menuCol = c.getColumnIndex("menu");
		
		if(c!=null){
			if(c.isFirst()){
				do{
					int id = c.getInt(idCol);
					String name = c.getString(nameCol);
					String desc = c.getString(descCol);
					String img = c.getString(imgCol);
					String hours = c.getString(hourCol);
					String menu = c.getString(menuCol);
					
					restaurants.add(new Restaurant(id, name, desc, img, hours, menu));
				}
				while(c.moveToNext());
			}
		}
		
		return restaurants;
	}
	
	public ArrayList<Service> getServices(){
		ArrayList<Service> services = new ArrayList<Service>();
		
		Cursor c = myDataBase.query(true, "services JOIN locations ON (services.location_id = locations._id)", 
				new String[]{"locations._id", "locations.name","locations.description","locations.image"}, null, null, null, null, "locations.name", null);

		c.moveToFirst();
		
		int idCol = c.getColumnIndex("_id");
		int nameCol = c.getColumnIndex("name");
		int descCol = c.getColumnIndex("description");
		int imgCol = c.getColumnIndex("image");
		
		if(c!=null){
			if(c.isFirst()){
				do{
					int id = c.getInt(idCol);
					String name = c.getString(nameCol);
					String desc = c.getString(descCol);
					String img = c.getString(imgCol);
					
					services.add(new Service(id, name, desc, img));
				}
				while(c.moveToNext());
			}
		}
		
		return services;
	}
	
	public ArrayList<Shuttle> getShuttles(){
		ArrayList<Shuttle> shuttles = new ArrayList<Shuttle>();
		
		Cursor c = myDataBase.query(true, "shuttles JOIN locations ON (shuttles.location_id = locations._id)", 
				new String[]{"locations._id", "locations.name","locations.description","locations.image",
				"shuttles.hours", "shuttles.stops"}, null, null, null, null, "locations.name", null);

		c.moveToFirst();
		
		int idCol = c.getColumnIndex("_id");
		int nameCol = c.getColumnIndex("name");
		int descCol = c.getColumnIndex("description");
		int imgCol = c.getColumnIndex("image");
		int hourCol = c.getColumnIndex("hours");
		int stopCol = c.getColumnIndex("stops");
		
		if(c!=null){
			if(c.isFirst()){
				do{
					int id = c.getInt(idCol);
					String name = c.getString(nameCol);
					String desc = c.getString(descCol);
					String img = c.getString(imgCol);
					String stops = c.getString(stopCol);
					String hours = c.getString(hourCol);
					
					shuttles.add(new Shuttle(id, name, desc, img, stops, hours));
				}
				while(c.moveToNext());
			}
		}
		
		return shuttles;
	}
	
	public ArrayList<Escort> getEscorts(){
		ArrayList<Escort> escorts = new ArrayList<Escort>();
		
		Cursor c = myDataBase.query(true, "escorts JOIN locations ON (escorts.location_id = locations._id)", 
				new String[]{"locations._id", "locations.name","locations.description","locations.image",
				"escorts.hours", "escorts.stops"}, null, null, null, null, "locations.name", null);

		c.moveToFirst();
		
		int idCol = c.getColumnIndex("_id");
		int nameCol = c.getColumnIndex("name");
		int descCol = c.getColumnIndex("description");
		int imgCol = c.getColumnIndex("image");
		int hourCol = c.getColumnIndex("hours");
		int stopCol = c.getColumnIndex("stops");
		
		if(c!=null){
			if(c.isFirst()){
				do{
					int id = c.getInt(idCol);
					String name = c.getString(nameCol);
					String desc = c.getString(descCol);
					String img = c.getString(imgCol);
					String stops = c.getString(stopCol);
					String hours = c.getString(hourCol);
					
					escorts.add(new Escort(id, name, desc, img, stops, hours));
				}
				while(c.moveToNext());
			}
		}
		
		return escorts;
	}
	
	public ArrayList<Location> getOther(){
		ArrayList<Location> locations = new ArrayList<Location>();
		
		locations.addAll(getShuttles());
		locations.addAll(getEscorts());
		
		Collections.sort(locations, new AlphaComparator());
		
		return locations;
	}
	
	public Location getLocation(int locID){
		
		Cursor c = myDataBase.query(true, "locations", 
				new String[]{"locations._id", "locations.name","locations.description","locations.image", "locations.loc_type"}, "" +
						"locations._id = " + locID, null, null, null, "locations.name", null);

		c.moveToFirst();
		
		int idCol = c.getColumnIndex("_id");
		int nameCol = c.getColumnIndex("name");
		int descCol = c.getColumnIndex("description");
		int imgCol = c.getColumnIndex("image");
		int typeCol = c.getColumnIndex("loc_type");
		
		if(c!=null){
			if(c.isFirst()){
				int id = c.getInt(idCol);
				String name = c.getString(nameCol);
				String desc = c.getString(descCol);
				String img = c.getString(imgCol);
				String locType = c.getString(typeCol);
				
				Location l = new Location(id, name, desc, img);
				l.setlType(locType);
				
				return l;
			}
		}
		
		return null;
	}
	
	public ArrayList<Location> getAllLocations(){
		ArrayList<Location> locations = new ArrayList<Location>();
		
		Cursor c = myDataBase.query(true, "locations", 
				new String[]{"locations._id", "locations.name","locations.description","locations.image", "locations.loc_type"}, null, null, null, null, "locations.name", null);

		c.moveToFirst();
		
		int idCol = c.getColumnIndex("_id");
		int nameCol = c.getColumnIndex("name");
		int descCol = c.getColumnIndex("description");
		int imgCol = c.getColumnIndex("image");
		int typeCol = c.getColumnIndex("loc_type");
		
		if(c!=null){
			if(c.isFirst()){
				do{
					int id = c.getInt(idCol);
					String name = c.getString(nameCol);
					String desc = c.getString(descCol);
					String img = c.getString(imgCol);
					String locType = c.getString(typeCol);
					
					Location l = new Location(id, name, desc, img);
					l.setlType(locType);
					
					locations.add(l);
				}
				while(c.moveToNext());
			}
		}
		
		return locations;
	}
	
	public Location getLocation(String loc){
		
		Cursor c = myDataBase.query(true, "locations", 
				new String[]{"locations._id", "locations.name","locations.description","locations.image", "locations.loc_type"}, "" +
						"locations.name = '" + loc + "'", null, null, null, "locations.name", null);

		c.moveToFirst();
		
		int idCol = c.getColumnIndex("_id");
		int nameCol = c.getColumnIndex("name");
		int descCol = c.getColumnIndex("description");
		int imgCol = c.getColumnIndex("image");
		int typeCol = c.getColumnIndex("loc_type");
		
		if(c!=null){
			if(c.isFirst()){
				int id = c.getInt(idCol);
				String name = c.getString(nameCol);
				String desc = c.getString(descCol);
				String img = c.getString(imgCol);
				String locType = c.getString(typeCol);
				
				Location l = new Location(id, name, desc, img);
				l.setlType(locType);
				return l;
			}
		}
		
		return null;
	}
	
	public DirectionList getDirections(Location from, Location to){
		DirectionList directions;
		
		return null;
	
	}
	
	public MyDirectedGraph<Location, WeightedEdge<Location>> getGraph(){
		ArrayList<WeightedEdge<Location>> edges = new ArrayList<WeightedEdge<Location>>();
		HashSet<Location> locs = new HashSet<Location>();
		MyDirectedGraph<Location, WeightedEdge<Location>> graph = new MyDirectedGraph<Location, WeightedEdge<Location>>();
		
		Cursor c = myDataBase.query(true, "nearby", 
				new String[]{"nearby._id", "nearby.loc_1","nearby.loc_2","nearby.dist"}, null, null, null, null, null, null);

		c.moveToFirst();
		
		int idCol = c.getColumnIndex("_id");
		int locCol = c.getColumnIndex("loc_1");
		int loc2Col = c.getColumnIndex("loc_2");
		int distCol = c.getColumnIndex("dist");
		
		if(c!=null){
			if(c.isFirst()){
				do{
					int id = c.getInt(idCol);
					int loc_1 = c.getInt(locCol);
					int loc_2 = c.getInt(loc2Col);
					int dist = c.getInt(distCol);
					
					Location l1 = new Location(loc_1, null, null, null);
					Location l2 = new Location(loc_2, null, null, null);
					
					graph.addVertex(l1);
					graph.addVertex(l2);

					graph.addEdge(new WeightedEdge<Location>(l1, l2, dist));
					graph.addEdge(new WeightedEdge<Location>(l2, l1, dist));
				}
				while(c.moveToNext());
			}
		}	
		return graph;
	}
	
}

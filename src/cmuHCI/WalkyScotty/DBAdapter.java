package cmuHCI.WalkyScotty;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import cmuHCI.WalkyScotty.entities.Building;
import cmuHCI.WalkyScotty.entities.DirectionList;
import cmuHCI.WalkyScotty.entities.Escort;
import cmuHCI.WalkyScotty.entities.Location;
import cmuHCI.WalkyScotty.entities.Restaurant;
import cmuHCI.WalkyScotty.entities.Room;
import cmuHCI.WalkyScotty.entities.Service;
import cmuHCI.WalkyScotty.entities.Shuttle;

public class DBAdapter extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/cmuHCI.WalkyScotty/databases/";
    private static String DB_NAME = "chewbacca";
    private SQLiteDatabase myDataBase; 
    private final Context myContext;
	
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
		
		Cursor c = myDataBase.query(true, "buildings JOIN locations ON (buildings.location_id = locations._id)", new String[]{"buildings._id", "locations.name","locations.description","locations.image"}, null, null, null, null, "locations.name", null);

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
		
		Cursor c = myDataBase.query(true, "rooms JOIN locations ON (rooms.location_id = locations._id)", new String[]{"rooms._id", "locations.name","locations.description","locations.image"}, null, null, null, null, "locations.name", null);

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
				new String[]{"rooms._id", "locations.name","locations.description","locations.image"}, "rooms.building_id = " + building.getId(), null, null, null, "locations.name", null);

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
	
	public Building getBuildingForRoom(){
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
		int stopCol = c.getColumnIndex("stop");
		
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
		int stopCol = c.getColumnIndex("stop");
		
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
	
	public Location getLocation(int locID){
		
		Cursor c = myDataBase.query(true, "locations", 
				new String[]{"locations._id", "locations.name","locations.description","locations.image"}, "" +
						"locations._id = " + locID, null, null, null, "locations.name", null);

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
				
				return new Location(id, name, desc, img);
			}
		}
		
		return null;
	}
	
	public ArrayList<Location> getAllLocations(){
		ArrayList<Location> locations = new ArrayList<Location>();
		
		Cursor c = myDataBase.query(true, "locations", 
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
					
					locations.add(new Location(id, name, desc, img));
				}
				while(c.moveToNext());
			}
		}
		
		return locations;
	}
	
	public Location getLocation(String loc){
		
		Cursor c = myDataBase.query(true, "locations", 
				new String[]{"locations._id", "locations.name","locations.description","locations.image"}, "" +
						"locations.name = '" + loc + "'", null, null, null, "locations.name", null);

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
				
				return new Location(id, name, desc, img);
			}
		}
		
		return null;
	}
	
	public DirectionList getDirections(Location from, Location to){
		DirectionList directions;
		
		return null;
	
	}
}

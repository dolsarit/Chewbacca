package cmuHCI.WalkyScotty;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import cmuHCI.WalkyScotty.entities.*;

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
		
		Cursor c = myDataBase.query(true, "rooms JOIN locations ON (rooms.location_id = rooms._id)", new String[]{"rooms._id", "rooms.name","rooms.description","rooms.image"}, null, null, null, null, "rooms.name", null);

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
		
		Cursor c = myDataBase.query(true, "rooms JOIN locations ON (rooms.location_id = rooms._id)", 
				new String[]{"rooms._id", "rooms.name","rooms.description","rooms.image"}, "rooms.building_id = " + building.getId(), null, null, null, "rooms.name", null);

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
	
}

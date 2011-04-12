package cmuHCI.WalkyScotty;

import java.io.IOException;
import java.util.ArrayList;

import cmuHCI.WalkyScotty.entities.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends Activity {

	// TODO: Create a custom cursor to make this less painful
	private final String[] PLACES = {"Buildings", "Food Places", "Rooms", "Services", "Other"};
	private final String[][] SUBPLACES = {
			{ "Baker", "GHC", "Tepper"},
			{"Si Senor"},
			{"Baker Hall Clusters", "Giant Eagle Auditorium"},
			{"Escor Services"},
			{"Merson Courtyard"}
	};
	
	// List of items to give to the autocomplete text box
	
	private final String[] AC_PLACES = {"Baker Hall"};
	
	
	// Search text box
	AutoCompleteTextView searchBox;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search);
		
		// Set up browse menus
		
		//buildSubCategories();
		
		ListView blv = (ListView) findViewById(R.id.browse_list);
		blv.setAdapter(new NestedListAdapter(this, PLACES, SUBPLACES));
		blv.setTextFilterEnabled(true);
		
		// Set up autocomplete for search box
		
		searchBox = (AutoCompleteTextView) findViewById(R.id.search_box);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.autocomplete_list_item, AC_PLACES);
		searchBox.setAdapter(adapter);
		
		searchBox.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				doSearch(searchBox.getText().toString());
			}
			
		});
		
		// Set up form actions (search)
		
		Button searchButton = (Button) findViewById(R.id.search_form_enter);
		
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doSearch(searchBox.getText().toString());
			}
			
		});
		
		// TODO: set up form actions (browse)
		
	}
	
	private void doSearch(String key) {
		if (key.equals("Baker Hall")) {
			this.startActivity(new Intent(this, BakerInfo.class));
		} else {
			Toast.makeText(this, "Can't find anything corresponding to " + key, Toast.LENGTH_LONG).show();
		}
	}
	
	private void buildSubCategories(){
		ArrayList<String> buildings = new ArrayList<String>();
		DBAdapter adp = new DBAdapter(this);
		try {
			adp.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adp.openDataBase();
		
		int i=0;
		for(Building b:adp.getBuildings()){ //Spits out an arraylist of buildings
			//buildings.add(b.getName());
			SUBPLACES[0][i] = b.getName();
			i++;
		}
		//SUBPLACES[0] = (String[]) buildings.toArray();
		
		adp.close();
	}
}

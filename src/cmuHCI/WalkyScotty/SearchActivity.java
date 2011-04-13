package cmuHCI.WalkyScotty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;
import cmuHCI.WalkyScotty.entities.*;

public class SearchActivity extends Activity {
	private final String[] PLACES = { "Buildings", "Food Places", "Rooms",
			"Services", "Other" };
	
	private String[][] SUBPLACES = new String[5][10];
	private Location[][] LOCATIONS = new Location[5][10];
	
	// List of items to give to the autocomplete text box

	private final String[] AC_PLACES = { "Baker Hall" };
	
	private List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    private List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
    private final String NAME = "NAME";

	// Search text box
	AutoCompleteTextView searchBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search);
		
		// Set up browse menus
		ExpandableListView blv = (ExpandableListView) findViewById(R.id.browse_list);
		
		buildSubCategories();
        buildList();
        
        // Set up our adapter
        ExpandableListAdapter mAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { NAME },
                new int[] { android.R.id.text1},
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { NAME },
                new int[] { android.R.id.text1}
                );
        blv.setAdapter(mAdapter);
        
        blv.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
					navigateDetailsPage(LOCATIONS[groupPosition][childPosition].getId());
				return true;
			}
        	
        });
		
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
		int id = getLocID(key);
		if (id > 0) {
			navigateDetailsPage(id);
		} else {
			Toast.makeText(this, "Can't find anything corresponding to " + key,
					Toast.LENGTH_LONG).show();
		}
	}
	
	private int getLocID(String loc){
		DBAdapter adp = new DBAdapter(this);
		try {
			adp.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adp.openDataBase();
		
		Location l = adp.getLocation(loc);
		adp.close();
		if(l == null)
			return -1;
		else
			return l.getId();
	}
	
	private void navigateDetailsPage(int locID){
		if(locID <= 0)
			throw new RuntimeException("Bad Location ID");
		
		Intent i = new Intent(this, BakerInfo.class);
		i.putExtra("lID", locID);
		this.startActivity(i);
	}
	
	private void buildList(){
		for (int i = 0; i < PLACES.length; i++) {
        	if(PLACES[i] == null || PLACES[i].equals(""))
        		continue;
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(NAME, PLACES[i]);

            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < SUBPLACES[i].length; j++) {
            	if(SUBPLACES[i][j] == null || SUBPLACES[i][j].equals(""))
            		continue;
                Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);
                curChildMap.put(NAME, SUBPLACES[i][j]);
            }
            childData.add(children);
        }
		
	}
	
	private void buildSubCategories(){
		DBAdapter adp = new DBAdapter(this);
		try {
			adp.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adp.openDataBase();
		
		int i=0;
		for(Building b:adp.getBuildings()){
			//buildings.add(b.getName());
			SUBPLACES[0][i] = b.getName();
			LOCATIONS[0][i] = b;
			i++;
		}
		i=0;
		for(Restaurant r:adp.getRestaurants()){
			SUBPLACES[1][i] = r.getName();
			LOCATIONS[1][i] = r;
		}
		//bSUBPLACES[0] = (String[]) buildings.toArray();
		
		adp.close();
	}
}

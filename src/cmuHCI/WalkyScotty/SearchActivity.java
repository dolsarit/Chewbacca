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
import cmuHCI.WalkyScotty.entities.Building;

public class SearchActivity extends Activity {
	
	private final String[] PLACES = { "Buildings", "Food Places", "Rooms",
			"Services", "Other" };
	private final String[][] SUBPLACES = { { "Baker", "GHC", "Tepper" },
			{ "Si Senor" },
			{ "Baker Hall Clusters", "Giant Eagle Auditorium" },
			{ "Escort Services" }, { "Merson Courtyard" } };
	
	// List of items to give to the autocomplete text box

	private final String[] AC_PLACES = { "Baker Hall" };

	// Search text box
	AutoCompleteTextView searchBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search);
		
		// Set up browse menus
		
		ExpandableListView blv = (ExpandableListView) findViewById(R.id.browse_list);
		
		final String NAME = "NAME";
		
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        for (int i = 0; i < PLACES.length; i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            curGroupMap.put(NAME, PLACES[i]);

            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < SUBPLACES[i].length; j++) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);
                curChildMap.put(NAME, SUBPLACES[i][j]);
            }
            childData.add(children);
        }

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
				Toast.makeText(SearchActivity.this, SUBPLACES[groupPosition][childPosition] + " was clicked, but this functionality is unimplemented.", Toast.LENGTH_LONG).show();
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
		if (key.equals("Baker Hall")) {
			this.startActivity(new Intent(this, BakerInfo.class));
		} else {
			Toast.makeText(this, "Can't find anything corresponding to " + key,
					Toast.LENGTH_LONG).show();
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

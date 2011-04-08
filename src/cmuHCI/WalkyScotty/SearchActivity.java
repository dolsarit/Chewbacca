package cmuHCI.WalkyScotty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchActivity extends Activity {

	// TODO: Create a custom adapter to make this less painful
	private final String[] PLACES = {"Buildings", "Food Places", "Rooms", "Services", "Other"};
	private final String[][] SUBPLACES = {
			{ "Baker", "GHC", "Tepper"},
			{"Si Senor"},
			{"Baker Hall Clusters", "Giant Eagle Auditorium"},
			{"Escor Services"},
			{"Merson Courtyard"}
	};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search);
		
		ListView blv = (ListView) findViewById(R.id.browse_list);
		blv.setAdapter(new NestedListAdapter(this, PLACES, SUBPLACES));
		blv.setTextFilterEnabled(true);
		
		blv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//((ViewGroup) view).addView(listItemViews.get(position));
				// Pass this through back to the view?
			}
		});
		
		
	}
}

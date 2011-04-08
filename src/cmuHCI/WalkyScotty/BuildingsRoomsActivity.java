package cmuHCI.WalkyScotty;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BuildingsRoomsActivity extends Activity{

	private final String[] ROOMS = {"Adamson Wing", "Giant Eagle Auditorium"};
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildingrooms);
        ListView brlv = (ListView) findViewById(R.id.buildingrooms_list);
		brlv.setTextFilterEnabled(true);
		brlv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, ROOMS));
    }
}

package cmuHCI.WalkyScotty;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class BuildingsRoomsActivity extends Activity{

	private final String[] ROOMS = {"Adamson Wing", "Giant Eagle Auditorium"};
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buildingrooms);
        ListView brlv = (ListView) findViewById(R.id.buildingrooms_list);
		brlv.setTextFilterEnabled(true);
		brlv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, ROOMS));
		
		TextView bc = (TextView) findViewById(R.id.building_rooms_breadcrumb);
        bc.setClickable(true);
        
        bc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				BuildingsRoomsActivity.this.finish();
			}
        });

    }
}

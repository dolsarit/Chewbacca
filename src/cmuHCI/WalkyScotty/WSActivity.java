package cmuHCI.WalkyScotty;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public abstract class WSActivity extends Activity {
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater mi = getMenuInflater();
    	mi.inflate(R.menu.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.menu_directions:
    		startActivity(new Intent(this, DirectionsMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    		return true;
    	case R.id.menu_help:
    		startActivity(new Intent(this, HelpActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    		return true;
    	case R.id.menu_map:
    		startActivity(new Intent(this, MapMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    		return true;
    	case R.id.menu_search:
    		startActivity(new Intent(this, SearchActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    		return true;
    	default:
        	return super.onOptionsItemSelected(item);
    	}
    }
}

package cmuHCI.WalkyScotty;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BakerInfo extends Activity{
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bakerinfo);
        
        // Set title of the page
        TextView title = (TextView) findViewById(R.id.BakerHallText);
        title.setText(R.string.BakerHall);
        
        // Add photo to screen
        Drawable myImage = (this.getResources()).getDrawable(R.drawable.bakerhall);
        ImageView photo = (ImageView) findViewById(R.id.BakerHallPhoto);
        photo.setImageDrawable(myImage);
        
        // Set title of the description
        TextView desc = (TextView) findViewById(R.id.BakerHallDes);
        desc.setText(R.string.BakerDes);
        
    }

}
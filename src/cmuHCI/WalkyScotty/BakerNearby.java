package cmuHCI.WalkyScotty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class BakerNearby extends Activity{
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bakernearby);
        
        // Set title of the page
        TextView title = (TextView) findViewById(R.id.NearbyText);
        title.setText(R.string.WhatNearby);
        
        // Set up GridView
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(BakerNearby.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        
        
        Button directionsButton = (Button) findViewById(R.id.BakerNearby_Directions_Button);
        
        directionsButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View view) {
        		BakerNearby.this.startActivity(new Intent(BakerNearby.this, DirectionsMainActivity.class));
        	}
        });
        
        Button searchButton = (Button) findViewById(R.id.BakerNearby_Search_Button);
        
        searchButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View view) {
        		BakerNearby.this.startActivity(new Intent(BakerNearby.this, SearchActivity.class));
        	}
        });
        
        Button mapButton = (Button) findViewById(R.id.BakerNearby_Map_Button);
        
        mapButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View view) {
        		BakerNearby.this.startActivity(new Intent(BakerNearby.this, MapMainActivity.class));
        	}
        });

    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }
        
        // To do: return actual object
        public Object getItem(int position) {
            return null;
        }

        // To do: return row id of the item
        public long getItemId(int position) {
            return 0;
        }

        // create a new Layout which contains ImageView and it Text for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
        	View v;
        	if(convertView==null){
        		LayoutInflater li = getLayoutInflater();
        		v = li.inflate(R.layout.gridbox, null);
        		TextView text = (TextView) v.findViewById(R.id.icon_text);
                text.setText(mThumbText[position]);
                
                ImageView image = (ImageView)v.findViewById(R.id.icon_image);
                image.setImageResource(mThumbIds[position]);
                //image.setLayoutParams(new GridView.LayoutParams(85, 85));
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                image.setPadding(8, 8, 8, 8);
            } 
        	else {
                v = convertView;
            }
        	return v;
        }

        private String[] mThumbText = {
        		"pic_0", "pic_1",
        		"pic_2", "pic_3",
        		"pic_4", "pic_5",
        		"pic_6", "pic_7"
        };
        
        // references to our images
        private Integer[] mThumbIds = {
        		R.drawable.sample_0, R.drawable.sample_1,
        		R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7
        };
    }
    
}
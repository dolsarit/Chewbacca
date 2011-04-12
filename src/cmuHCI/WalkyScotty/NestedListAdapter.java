package cmuHCI.WalkyScotty;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NestedListAdapter extends BaseAdapter {

	private Context context;
	private String[] topLevel;
	private String[][] subLevel;
	private int active;
	private int numItems;
	private Set<LinearLayout> topLevelItems;
	
	public NestedListAdapter(Context context, String[] topLevelItems, String[][] subLevelItems) {
		if (topLevelItems.length != subLevelItems.length)
			throw new RuntimeException("Invalid arguments to NestedListAdapter constructor.");
		
		// Setup
		this.context = context;
		this.topLevel = topLevelItems;
		this.subLevel = subLevelItems;
		
		// Set the flag for the active list menu to none
		active = -1;
		
		numItems = topLevel.length;
		
		this.topLevelItems = new HashSet<LinearLayout>();
	}
	
	@Override
	public int getCount() {
		return numItems;
	}

	@Override
	public Object getItem(int position) {
		return topLevel[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//LinearLayout v = (LinearLayout) li.inflate(layoutId, null);
		
		LinearLayout l = new LinearLayout(context);
		l.setOrientation(LinearLayout.VERTICAL);
		TextView v = new TextView(context);
		v.setText(topLevel[position]);
		l.setTag((Integer)position);
		l.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (((Integer) v.getTag()).equals(active))
					return;
				
				for (LinearLayout ll : topLevelItems) {
					
					for (int i = 1; i < ll.getChildCount(); i++) {
						ll.removeViewAt(i);
					}
				}
				for (int i=0; i < subLevel[(Integer)v.getTag()].length; i++) {
					TextView tv = new TextView(context);
					tv.setText(subLevel[(Integer)v.getTag()][i]);
					((LinearLayout)v).addView(tv);
				}
				active = (Integer) v.getTag();
			}
		});
		topLevelItems.add(l);
		l.addView(v);
		
		return l;
	}

}

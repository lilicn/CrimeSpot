package edu.vanderbilt.cs278.safespot;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArrayAdapterItem extends ArrayAdapter<LocationReview> {

	Context mContext;
	int layoutResourceId;
	LocationReview data[] = null;
	public ArrayAdapterItem(Context context, int resource,
			LocationReview[] objects) {
		super(context, resource, objects);
		this.layoutResourceId = resource;
		this.mContext = context;
		this.data = objects;
	}
	
    @Override
    public View getView(int position, View contentView, ViewGroup parent) {

    	LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();

    	    contentView = inflater.inflate(layoutResourceId, parent, false);

    	    ImageView imageView = (ImageView) contentView.findViewById(R.id.imageView1);
    	    TextView text_lat = (TextView) contentView.findViewById(R.id.txt_lat);
    	    TextView text_lon = (TextView) contentView.findViewById(R.id.txt_lon);
    	    TextView text_score = (TextView) contentView.findViewById(R.id.list_score);
    	    
    	    text_lat.setText("Latitude: "+data[position].lat.toString());
    	    text_lon.setText("Longitude: "+data[position].lon.toString());
    	    text_score.setText(data[position].score.toString());

    	    return contentView;

    }

}

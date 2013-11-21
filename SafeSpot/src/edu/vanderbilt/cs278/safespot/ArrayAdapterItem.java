package edu.vanderbilt.cs278.safespot;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Di & Li
 *
 */
public class ArrayAdapterItem extends ArrayAdapter<String> {

	Context mContext;
	int layoutResourceId;
	List<String> data = null;

	public ArrayAdapterItem(Context context, int resource,
			List<String> objects) {
		super(context, resource, objects);
		this.layoutResourceId = resource;
		this.mContext = context;
		this.data = objects;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
		contentView = inflater.inflate(layoutResourceId, parent, false);
		ImageView imageView = (ImageView) contentView
				.findViewById(R.id.imageView1);
		TextView text_score = (TextView) contentView
				.findViewById(R.id.list_score);
		text_score.setText(data.get(position));
		return contentView;

	}

}

package com.example.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodu.R;

public class CustomListAdapter extends BaseAdapter {
	private List<ItemModel> items;
	private Activity activity;
    private LayoutInflater inflater;
	Context context;

	public CustomListAdapter(Activity activity, List<ItemModel> movieItems) {
        this.activity = activity;
        this.items = movieItems;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);
        
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView description2 = (TextView) convertView.findViewById(R.id.description2);
        TextView extra = (TextView) convertView.findViewById(R.id.extra);
        ImageView icon = (ImageView) convertView.findViewById(R.id.thumbnail);
        
     // getting movie data for the row
        ItemModel m = items.get(position);
         
        // title
        title.setText(m.getTitle());
        if(m.getIcon() != null){
        Bitmap bitmap = BitmapFactory.decodeByteArray(m.getIcon(), 0, m.getIcon().length);
        icon.setImageBitmap(bitmap);
        }
        //icon.setImageResource(m.getIcon());
         
        // description
        description.setText(String.valueOf(m.getDescription()));
         
        // sub description
        String genreStr = "";
        /*for (String str : m.getCuisine()) {
            genreStr += str + ", ";
        }*/
        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;
        description2.setText(m.getSubDescription());
         
        // release year
        extra.setText(m.getExtra());
 
        return convertView;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}


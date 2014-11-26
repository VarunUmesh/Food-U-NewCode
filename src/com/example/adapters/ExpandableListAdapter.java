package com.example.adapters;

import java.util.HashMap;
import java.util.List;

import com.example.foodu.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context _context;
    private List<ItemModel> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<ItemModel, List<String>> _listDataChild;
 
    public ExpandableListAdapter(Context context, List<ItemModel> listDataHeader,
            HashMap<ItemModel, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

	@Override
	public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final String childText = (String) getChild(groupPosition, childPosition);
        final ItemModel item = (ItemModel) getGroup(groupPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
            ImageButton review = (ImageButton) convertView.findViewById(R.id.rating);
            review.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Toast.makeText(_context, "it is " + item.getTitle(), Toast.LENGTH_SHORT).show();
					
				}
            	
            });
        }
 
        //TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
 
       // txtListChild.setText(childText);
        return convertView;
    }

	@Override
	public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

	@Override
	public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
        return groupPosition;
    }

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        ItemModel item = (ItemModel) getGroup(groupPosition);
        String headerTitle  = item.getTitle();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_row, null);
        }
 
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.title);
        TextView lbldesc = (TextView) convertView.findViewById(R.id.description);
        ImageView icon = (ImageView) convertView.findViewById(R.id.thumbnail);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        lbldesc.setText(item.getDescription());
        Bitmap bitmap = BitmapFactory.decodeByteArray(item.getIcon(), 0, item.getIcon().length);
        icon.setImageBitmap(bitmap);
        //icon.setImageResource(item.getIcon());
        icon.setPadding(50, 0, 0, 0);
        return convertView;
    }

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
 
  
 
   
 
    
 
   
}

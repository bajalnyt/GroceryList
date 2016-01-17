package com.mohbajal.grocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by 908752 on 1/17/16.
 */
public class ItemAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;

    public ItemAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // check if the view already exists
        // if so, no need to inflate and findViewById again!
        if (convertView == null) {

            // Inflate the custom row layout from your XML.
            convertView = mInflater.inflate(R.layout.row_store , null);

            // create a new "Holder" with subviews
            holder = new ViewHolder();
            holder.itemText = (TextView) convertView.findViewById(R.id.row_text);
            holder.itemSubText = (TextView) convertView.findViewById(R.id.row_subtext);

            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {

            // skip all the expensive inflation/findViewById
            // and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }



       // holder.itemText.setText();

        return convertView;
    }

    private static class ViewHolder {
        public TextView itemText;
        public TextView itemSubText;
        //public TextView authorTextView;
    }
}

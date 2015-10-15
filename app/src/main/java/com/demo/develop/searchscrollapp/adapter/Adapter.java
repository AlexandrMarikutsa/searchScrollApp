package com.demo.develop.searchscrollapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.develop.searchscrollapp.R;

import java.util.List;

public class Adapter extends BaseAdapter {
    private List<String> strings;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(Context context, List<String> strings) {
        this.strings = strings;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        TextView stringName;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public String getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.mylist,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.stringName = (TextView) convertView.findViewById(R.id.name);
             convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String string =  getItem(position);

        viewHolder.stringName.setText(string);
         return convertView;
    }

}

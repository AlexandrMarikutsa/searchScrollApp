package com.demo.develop.searchscrollapp.adapter;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import com.demo.develop.searchscrollapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class Adapter extends ArrayAdapter<String> implements SectionIndexer{
    private String myLog = "---------------------";
    private String[] items;
    private Context context;
    private HashMap<String, Integer> alphaIndexer;
    private String[] sections;

    public Adapter(Context context, LinkedList<String> items) {
        super(context, R.layout.list_item, items);
        this.context = context;
        alphaIndexer = new HashMap<String, Integer>();
        int size = items.size();

        for (int x = 0; x < size; x++){
            String s = items.get(x);
            String ch = s.substring(0, 1);
            ch = ch.toUpperCase();
            if(!alphaIndexer.containsKey(ch)){
                alphaIndexer.put(ch, x);
            }
        }
        Set<String> sectionLetters = alphaIndexer.keySet();
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
        Collections.sort(sectionList);
        sections = new String[sectionList.size()];
        sectionList.toArray(sections);
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int section) {
        return alphaIndexer.get(sections[section]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

//    @Override
//    public int getCount() {
//        return items.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return items[position];
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Log.e(myLog, "" + position);
//
//        LinearLayout ll = new LinearLayout(context);
//        ll.setOrientation(LinearLayout.VERTICAL);
//        ll.setBackgroundColor(Color.RED);
//
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        TextView tv = new TextView(context);
//        tv.setText(items[position]);
//
//        Log.e(myLog, items[position]);
//
//        ll.addView(tv,layoutParams);
//        return ll;
//    }

}

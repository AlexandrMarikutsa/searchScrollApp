package com.demo.develop.searchscrollapp.adapter;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.demo.develop.searchscrollapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Adapter extends ArrayAdapter<String> implements SectionIndexer{
    private String myLog = "---------------------";
    private List<String> items;
    private Context context;
    private HashMap<String, Integer> alphaIndexer;
    private String[] sections;

    public Adapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        alphaIndexer = new HashMap<String, Integer>();
        this.items = objects;
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
        return (int) (getCount() * ((float)section/(float)getSections().length));
//        return alphaIndexer.get(sections[section]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}

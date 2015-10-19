package com.demo.develop.searchscrollapp.customViews;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.develop.searchscrollapp.constants.ArrayOfStrings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class MySectionIndexer extends LinearLayout {
    private Context context;
    private Set<String> sectionLetters;
    private HashMap<String, Integer> alphaIndexer;
    private String[] sections;

    public MySectionIndexer(Context context) {
        super(context);
        this.context = context;
        showSectionLetters();
    }

    public MySectionIndexer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        showSectionLetters();
    }

    public MySectionIndexer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        showSectionLetters();
    }

    private void showSectionLetters(){
        LinkedList<String> items = new LinkedList<>();
        alphaIndexer = new HashMap<String, Integer>();
        for(String s: ArrayOfStrings.STRINGS) {
            items.add(s);
        }
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
        for(String s: sectionList){
            TextView textView = new TextView(context);
            textView.setText(s);
            addView(textView);
        }
    }

//    public MySectionIndexer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            super(context, attrs, defStyleAttr, defStyleRes);
//        }
//    }
}

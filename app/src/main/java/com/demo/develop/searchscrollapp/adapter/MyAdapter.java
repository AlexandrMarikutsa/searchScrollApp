package com.demo.develop.searchscrollapp.adapter;


import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MyAdapter extends ArrayAdapter<String> implements SectionIndexer {
    private List<String> strings;
    private HashMap<String, Integer> alphaIndexer;
    private String[] sections;

    public MyAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        this.strings = objects;
        alphaIndexer = new HashMap<String, Integer>();
        getSectionLetters();
    }

    private void getSectionLetters() {
        int size = strings.size();

        for (int x = 0; x < size; x++) {
            String s = strings.get(x);
            String ch = s.substring(0, 1);
            ch = ch.toUpperCase();
            if (!alphaIndexer.containsKey(ch)) {
                alphaIndexer.put(ch, x);
            }
        }
        Set<String> sectionLetters = alphaIndexer.keySet();
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
        Collections.sort(sectionList);
        sections = new String[sectionList.size()];
        sectionList.toArray(sections);
    }

    public Object[] getSections() {
//        String[] chars = new String[SideSelector.ALPHABET.length];
//        for (int i = 0; i < SideSelector.ALPHABET.length; i++) {
//            chars[i] = String.valueOf(SideSelector.ALPHABET[i]);
//        }
//
        return sections;
    }

    public int getPositionForSection(int i) {
        return (int) (getCount() * ((float)i/(float)getSections().length));
    }

    public int getSectionForPosition(int i) {
        return 0;
    }
}

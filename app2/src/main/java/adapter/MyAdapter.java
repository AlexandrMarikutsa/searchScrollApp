package adapter;


import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import constants.ArrayOfStrings;
import service.Alphavit;

public class MyAdapter extends ArrayAdapter<String> implements SectionIndexer {

    private String[] strings = ArrayOfStrings.STRINGS;
    private List<String> items ;
    private HashMap<String, Integer> alphaIndexer;
    private String[]sections;

    public MyAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        arrayToList();
        int size = items.size();
        alphaIndexer = new HashMap<>();

        for (int x = 0; x < size; x++) {
            String s = items.get(x);
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
        return sections;
    }

    public int getPositionForSection(int i) {

        Log.d("-------", "getPositionForSection " + i);
        return (int) (getCount() * ((float)i/(float)getSections().length));
    }

    public int getSectionForPosition(int i) {
        return 0;
    }

    private void arrayToList(){
        items = new ArrayList<>();
        for (String ch : strings) {
            items.add(ch);
        }
    }
}

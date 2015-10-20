package service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Alphavit {
    public final static String[] getAlphaLetters(String[] items) {
        int size = items.length;
        HashMap<String, Integer> alphaIndexer = new HashMap<>();

        for (int x = 0; x < size; x++) {
            String s = items[x];
            String ch = s.substring(0, 1);
            ch = ch.toUpperCase();
            if (!alphaIndexer.containsKey(ch)) {
                alphaIndexer.put(ch, x);
            }
        }
        Set<String> sectionLetters = alphaIndexer.keySet();
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
        Collections.sort(sectionList);
        String[]sections = new String[sectionList.size()];
        sectionList.toArray(sections);
        return sections;
    }
}

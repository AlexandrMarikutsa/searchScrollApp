package service;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Alphavit {
    public final static Map<Character, Integer> getAlphaLetters(String[] items) {
        int size = items.length;
        HashMap<Character, Integer> alphaIndexer = new HashMap<>();
        Arrays.sort(items);

        for (int x = 0; x < size; x++) {
            String s = items[x];
            Character ch = s.charAt(0);
            ch.toUpperCase(ch);
            if (!alphaIndexer.containsKey(ch)) {
                alphaIndexer.put(ch, x);
            }
        }
        return alphaIndexer;
    }
}

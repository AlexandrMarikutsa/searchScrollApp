package adapter;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import java.util.List;

import constants.ArrayOfStrings;

public class MyAdapter extends ArrayAdapter<String> implements SectionIndexer {

    private Character[] sections;

    public MyAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
    }

    public Object[] getSections() {
        return sections;
    }

    public int getPositionForSection(int i) {
        return i;
    }

    public int getSectionForPosition(int i) {
        return 0;
    }
}

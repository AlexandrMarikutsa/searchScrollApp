package com.demo.develop.app2;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.List;

import constants.ArrayOfStrings;
import service.Alphavit;

public class Main extends Activity {

    List<String> items;
    public static final String TAG = Main.class.getCanonicalName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view);

        items = new ArrayList<String>();

        for (String ch : ArrayOfStrings.STRINGS) {
                items.add(ch);
        }

        listView.setAdapter(new IndexingArrayAdapter(this, android.R.layout.simple_list_item_1, items));

        SideSelector sideSelector = (SideSelector) findViewById(R.id.side_selector);
        sideSelector.setListView(listView);

    }

    public class IndexingArrayAdapter extends ArrayAdapter<String> implements SectionIndexer {
        public IndexingArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
        }

        public Object[] getSections() {
            return Alphavit.getAlphaLetters(items);
        }

        public int getPositionForSection(int i) {

            Log.d(TAG, "getPositionForSection " + i);
            return (int) (getCount() * ((float)i/(float)getSections().length));
        }

        public int getSectionForPosition(int i) {
            return 0;
        }
    }

}

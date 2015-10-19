package com.demo.develop.searchscrollapp;

import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.develop.searchscrollapp.adapter.Adapter;
import com.demo.develop.searchscrollapp.constants.ArrayOfStrings;
import com.demo.develop.searchscrollapp.customViews.MySectionIndexer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        try {
//            Thread.sleep(8000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        setContentView(R.layout.activity_main);


        LinkedList<String> items = new LinkedList<>();
        for(String s: ArrayOfStrings.STRINGS) {
            items.add(s);
        }
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new Adapter(MainActivity.this,items));
    }
}

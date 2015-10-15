package com.demo.develop.searchscrollapp;

import android.app.Activity;
import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.demo.develop.searchscrollapp.adapter.Adapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] values = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
                            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
        List<String> strings = new ArrayList<>();
        for(String s: values){
            strings.add(s);
        }

        listView = (ListView) findViewById(R.id.listView);
        Adapter adapter = new Adapter(getApplication(), strings);
        listView.setAdapter(adapter);
    }
}

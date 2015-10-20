package com.demo.develop.app2;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.List;

import adapter.MyAdapter;
import constants.ArrayOfStrings;
import service.Alphavit;

public class Main extends Activity {
    private List<String> items;
    public static final String TAG = Main.class.getCanonicalName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try {
//            Thread.sleep(8000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        setContentView(R.layout.activity_main);

//        initImageViewSize();
        ListView listView = (ListView) findViewById(R.id.list_view);
        items = new ArrayList<String>();

        for (String ch : ArrayOfStrings.STRINGS) {
                items.add(ch);
        }

        listView.setAdapter(new MyAdapter(this, android.R.layout.simple_list_item_1, items));

        SideSelector sideSelector = (SideSelector) findViewById(R.id.side_selector);
//        sideSelector.setListView(Alphavit.getAlphaLetters(ArrayOfStrings.STRINGS));
        sideSelector.setListView(listView);
    }

//    private void initImageViewSize() {
//        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.main_relative_layout);
//        relativeLayout.getViewTreeObserver()
//                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//                    @Override
//                    public void onGlobalLayout() {
//                        int heidht = relativeLayout.getHeight();
//                        if (relativeLayout.getViewTreeObserver() != null) {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                                relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                            }
//                            else{
//                                relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                            }
//                        }
////                        convertPixelsToDp(heidht, getApplicationContext());
//                    }
//                });
//    }

//    private float convertPixelsToDp(int px, Context context){
//        float dp = px;
//        imageView.getLayoutParams().height = (int) dp/PART_OF_SCREEN;
//        imageView.getLayoutParams().width = (int) dp/PART_OF_SCREEN;
//        return dp;
//    }
}

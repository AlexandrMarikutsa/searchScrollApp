package com.demo.develop.searchscrollapp.customViews;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.demo.develop.searchscrollapp.constants.ArrayOfStrings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class MySectionIndexer extends View {
    private Context context;
    private Set<String> sectionLetters;
    private HashMap<String, Integer> alphaIndexer;
    private String[] sections;
    private SectionIndexer selectionIndexer = null;
    private Paint paint;
    private ListView list;
    public static final int BOTTOM_PADDING = 10;

    public MySectionIndexer(Context context) {
        super(context);
        this.context = context;
        showSectionLetters();
        init();
    }

    public MySectionIndexer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        showSectionLetters();
        init();
    }

    public MySectionIndexer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        showSectionLetters();
        init();
    }

    private void init() {
        setBackgroundColor(0x44FFFFFF);
        paint = new Paint();
        paint.setColor(0xFFA6A9AA);
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public void setListView(ListView _list) {
        list = _list;
        selectionIndexer = (SectionIndexer) _list.getAdapter();

        Object[] sectionsArr = selectionIndexer.getSections();
        sections = new String[sectionsArr.length];
        for (int i = 0; i < sectionsArr.length; i++) {
            sections[i] = sectionsArr[i].toString();
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int y = (int) event.getY();
        float selectedIndex = ((float) y / (float) getPaddedHeight()) * sectionLetters.size();

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (selectionIndexer == null) {
                selectionIndexer = (SectionIndexer) list.getAdapter();
            }
            int position = selectionIndexer.getPositionForSection((int) selectedIndex);
            if (position == -1) {
                return true;
            }
            list.setSelection(position);
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {

        int viewHeight = getPaddedHeight();
        float charHeight = ((float) viewHeight) / (float) sections.length;

        float widthCenter = getMeasuredWidth() / 2;
        for (int i = 0; i < sections.length; i++) {
            canvas.drawText(String.valueOf(sections[i]), widthCenter, charHeight + (i * charHeight), paint);
        }
        super.onDraw(canvas);
    }

    private int getPaddedHeight() {
        return getHeight() - BOTTOM_PADDING;
    }

    private void showSectionLetters() {
        LinkedList<String> items = new LinkedList<>();
        alphaIndexer = new HashMap<String, Integer>();
        for (String s : ArrayOfStrings.STRINGS) {
            items.add(s);
        }
        int size = items.size();

        for (int x = 0; x < size; x++) {
            String s = items.get(x);
            String ch = s.substring(0, 1);
            ch = ch.toUpperCase();
            if (!alphaIndexer.containsKey(ch)) {
                alphaIndexer.put(ch, x);
            }
        }
        sectionLetters = alphaIndexer.keySet();
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
        Collections.sort(sectionList);
        sections = new String[sectionList.size()];
        sectionList.toArray(sections);
//        for(String s: sectionList){
//            TextView textView = new TextView(context);
//            textView.setHeight(60);
//            textView.setText(s);
//            addView(textView);
//        }
//    }

//    public MySectionIndexer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            super(context, attrs, defStyleAttr, defStyleRes);
//        }
//    }
    }
}
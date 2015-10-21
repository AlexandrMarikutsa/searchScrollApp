package com.demo.develop.app2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.List;

import constants.ArrayOfStrings;
import service.Alphavit;

public class SideSelector extends View {

//    public static String[] alphabet;
    public static final int BOTTOM_PADDING = 10;

    private List<Section> sectionsOnSelector;
    private Section section;
    private float charHeight;
//    private SectionIndexer selectionIndexer = null;
//    private ListView list;
    private Paint paint;
    private String[] sections;

    public SideSelector(Context context) {
        super(context);
        init();
    }

    public SideSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SideSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setBackgroundColor(0x44FFFFFF);
        paint = new Paint();
        paint.setColor(0xFFA6A9AA);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
    }

//    public void setListView(ListView _list) {
//        list = _list;
//        selectionIndexer = (SectionIndexer) _list.getAdapter();
//
//        Object[] sectionsArr = selectionIndexer.getSections();
//        sections = new String[sectionsArr.length];
//        for (int i = 0; i < sectionsArr.length; i++) {
//            sections[i] = sectionsArr[i].toString();
//        }
//    }
    public void setListLetters(String[] sections) {
        this.sections = sections;
    }


    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int y = (int) event.getY();

        Log.e("-----", " " + y);

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
//            if (selectionIndexer == null) {
//                selectionIndexer = (SectionIndexer) list.getAdapter();
//            }
            for (Section s: sectionsOnSelector) {
                if (y >= s.y && y <= (s.y+charHeight)) {
                    Log.e("-----POSITION-----", "" + s.position);
                    Log.e("-----LETTER-----", "" + s.name);
//                    int position = selectionIndexer.getPositionForSection(s.position);
//                    if (position == -1) {
//                        return true;
//                    }
//                    list.setSelection(position);
                }
            }
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        sectionsOnSelector = new ArrayList<>();
        int viewHeight = getPaddedHeight();
        float widthCenter = getMeasuredWidth() / 2;
        charHeight = 30;
        if(viewHeight <= charHeight*sections.length) {
            int numSections = (int) ((viewHeight/2 - charHeight)/charHeight);

            for (int i = 0; i < numSections; i++) {
                canvas.drawText(String.valueOf(sections[i]), widthCenter, i * charHeight + charHeight, paint);
                section = new Section(sections[i].charAt(0),i, (int) (i * charHeight));
                sectionsOnSelector.add(section);
            }
            canvas.drawText(String.valueOf("*"), widthCenter, viewHeight / 2, paint);
            canvas.drawText(String.valueOf("*"), widthCenter, viewHeight / 2 + charHeight, paint);

            for (int i = 0; i < numSections; i++) {
                canvas.drawText(String.valueOf(sections[(sections.length - 1) - i]), widthCenter, (viewHeight - (i * charHeight)), paint);
                section = new Section(sections[(sections.length - 1) - i].charAt(0),(sections.length - 1) - i, (int) (viewHeight - ((i + 1) * charHeight)));
                sectionsOnSelector.add(section);
            }
        }else {
            for (int i = 0; i < sections.length; i++) {
               canvas.drawText(String.valueOf(sections[i]), widthCenter, charHeight + (i * charHeight), paint);
                section = new Section(sections[i].charAt(0),i, (int) (i * charHeight));
                sectionsOnSelector.add(section);
            }
        }
        super.onDraw(canvas);
    }

    private int getPaddedHeight() {
        return getHeight() - BOTTOM_PADDING;
    }

    public class Section {
        private char name;
        private int position;
        private int y;

        public Section(char name, int position, int y) {
            this.name = name;
            this.position = position;
            this.y = y;
        }
    }
}
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
    private static String TAG = SideSelector.class.getCanonicalName();

    public static String[] alphabet;
    public static final int BOTTOM_PADDING = 10;

    private List<Section> sectionsOnSelector;
    private Section section;
    private float charHeight;
    private SectionIndexer selectionIndexer = null;
    private ListView list;
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

        Log.e("-----", " " + y);

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (selectionIndexer == null) {
                selectionIndexer = (SectionIndexer) list.getAdapter();
            }
            for (Section s: sectionsOnSelector) {
                if (y >= s.y && y <= (s.y+charHeight)) {
                    Log.e("-----POSITION-----", "" + s.position);
                    int position = selectionIndexer.getPositionForSection(s.position);
                    if (position == -1) {
                        return true;
                    }
                    list.setSelection(position);
                }
            }
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        sectionsOnSelector = new ArrayList<>();
        int viewHeight = getPaddedHeight();
        charHeight = ((float) viewHeight) / 30;
        float widthCenter = getMeasuredWidth() / 2;

        for(int i = 0; i < 4; i++) {
            canvas.drawText(String.valueOf(sections[i]), widthCenter, (i+1)*charHeight + charHeight, paint);
            section = new Section(i, (int) ((i+1)*charHeight));
            sectionsOnSelector.add(section);
        }
        canvas.drawText(String.valueOf("*"), widthCenter, 5*charHeight + (charHeight), paint);
        canvas.drawText(String.valueOf("*"), widthCenter, charHeight + (6 * charHeight), paint);

        for(int i = 0; i < 4; i++) {
            canvas.drawText(String.valueOf(sections[(sections.length - 1) - i]), widthCenter, ((sections.length - 1) - i)*charHeight +charHeight, paint);
            section = new Section((sections.length - 1) - i, (int)(((sections.length - 1) - i)*charHeight));
            sectionsOnSelector.add(section);
        }
//        canvas.drawText(String.valueOf(sections[sections.length - 1]), widthCenter, charHeight + (3 * charHeight), paint);
        super.onDraw(canvas);
    }

    private int getPaddedHeight() {
        return getHeight() - BOTTOM_PADDING;
    }

    public class Section {
        public int position;
        public int y;

        public Section(int position, int y) {
            this.position = position;
            this.y = y;
        }
    }
}
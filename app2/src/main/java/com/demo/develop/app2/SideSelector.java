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

import constants.ArrayOfStrings;
import service.Alphavit;

public class SideSelector extends View {
    private static String TAG = SideSelector.class.getCanonicalName();

    public static String[] alphabet;
    public static final int BOTTOM_PADDING = 10;

    private Section section;
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
//        alphabet = ArrayOfStrings.STRINGS;
//        alphabet = Alphavit.getAlphaLetters(alphabet);
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
            if (y >= 0 && y <= section.y) {
                Log.e("----------", "" + section.position);
                int position = selectionIndexer.getPositionForSection(section.position);
                if (position == -1) {
                    return true;
                }
                list.setSelection(position);
            }
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {

        int viewHeight = getPaddedHeight();
        float charHeight = ((float) viewHeight) / 30;
        float widthCenter = getMeasuredWidth() / 2;

        canvas.drawText(String.valueOf(sections[5]), widthCenter, charHeight, paint);
        section = new Section();
        section.position =5;
        section.y = (int) charHeight;
        canvas.drawText(String.valueOf("*"), widthCenter, charHeight + (charHeight), paint);
        canvas.drawText(String.valueOf("*"), widthCenter, charHeight + (2 * charHeight), paint);
        canvas.drawText(String.valueOf(sections[sections.length - 1]), widthCenter, charHeight + (3 * charHeight), paint);
        super.onDraw(canvas);
    }

    private int getPaddedHeight() {
        return getHeight() - BOTTOM_PADDING;
    }

    public class Section {
        public int position;
        public int y;
    }
}
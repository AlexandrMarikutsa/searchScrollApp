package com.demo.develop.app2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import listeners.OnCustomEventListener;

public class SideSelector extends View {

    public static final int BOTTOM_PADDING = 10;

    private OnCustomEventListener listener;
    private List<Section> sectionsOnSelector;
    private Section section;
    private float charHeight;
    private Paint paint;
    private Character[] sections;
    private int upLetter = 0;
    private int downLetter = 0;

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
    public void setListLetters(Set<Character> sectionsSet) {

        Character[]sections = new Character[sectionsSet.size()];
        sectionsSet.toArray(sections);
        Arrays.sort(sections);
        this.sections = sections;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            for (Section s: sectionsOnSelector) {
                if (y >= s.y && y <= (s.y+charHeight)) {
                    if(listener !=null){
                        listener.getChar(s.name);
                    }
                    changeSelectorView(s.position);
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
//        int maxSections = (int) (viewHeight/charHeight);

        if(viewHeight <= charHeight*sections.length) {
            int numSections = (int) ((viewHeight/2 - charHeight)/charHeight);

            if((numSections+upLetter) < (sections.length - numSections)) {
                for (int i = 0 + upLetter; i < numSections + upLetter; i++) {
                    canvas.drawText(String.valueOf(sections[i]), widthCenter, (i - upLetter) * charHeight + charHeight, paint);
                    section = new Section(sections[i], i, (int) ((i - upLetter) * charHeight));
                    sectionsOnSelector.add(section);
                }
            }
            canvas.drawText(String.valueOf("*"), widthCenter, viewHeight / 2, paint);
            canvas.drawText(String.valueOf("*"), widthCenter, viewHeight / 2 + charHeight, paint);

            for (int i = 0; i < numSections; i++) {
                canvas.drawText(String.valueOf(sections[(sections.length - 1) - i]), widthCenter, (viewHeight - (i * charHeight)), paint);
                section = new Section(sections[(sections.length - 1) - i],(sections.length - 1) - i, (int) (viewHeight - ((i + 1) * charHeight)));
                sectionsOnSelector.add(section);
            }
        }else {
            for (int i = 0; i < sections.length; i++) {
               canvas.drawText(String.valueOf(sections[i]), widthCenter, charHeight + (i * charHeight), paint);
                section = new Section(sections[i],i, (int) (i * charHeight));
                sectionsOnSelector.add(section);
            }
        }
        super.onDraw(canvas);
    }

    private void changeSelectorView(int position){
        upLetter = position-1;
        this.invalidate();
    }

    private int getPaddedHeight() {
        return getHeight() - BOTTOM_PADDING;
    }

    public class Section {
        private Character name;
        private int position;
        private int y;

        public Section(Character name, int position, int y) {
            this.name = name;
            this.position = position;
            this.y = y;
        }
    }

    public void setCustomEventListener(OnCustomEventListener eventListener) {
        listener = eventListener;
    }
}
package com.demo.develop.app2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import constants.PaintParams;
import listeners.OnCustomEventListener;

public class SideSelector extends View {

    private static int BOTTOM = 4;
    private OnCustomEventListener listener;
    private List<Section> sectionsOnSelector;
    private Section section;
    private Section currentSection;
    private int pressedSection = 0;
    private float charHeight;
    private Paint paint;
    private Paint paintPoint;
    private Paint paintCurrentLetter;
    private Character[] sections;
    private float widthCenter;
    private int radius = 3;
    private int textSize = PaintParams.MIN_TEXT_SIZE;
    private int upPoint;
    private int downPoint;
    private int downSections;
    private int upSections;

    public SideSelector(Context context) {
        super(context);
        setListLetters()
        countNumberOfElements();
    }

    public SideSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        countNumberOfElements();
    }

    public SideSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        countNumberOfElements();
    }

    private void init() {
        setBackgroundColor(Color.BLACK);
        paint = new Paint();
        paint.setColor(0xFFA6A9AA);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);

        paintCurrentLetter = new Paint();
        paintCurrentLetter.setColor(Color.parseColor("#E6E6E6"));
        paintCurrentLetter.setTextSize((float) (textSize * 1.1));
        paintCurrentLetter.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paintCurrentLetter.setTextAlign(Paint.Align.CENTER);

        paintPoint = new Paint();
        paintPoint.setColor(0xFFA6A9AA);
        paintPoint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paintPoint.setTextAlign(Paint.Align.CENTER);
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
                if (y >= (s.y - charHeight) && y <= s.y) {
                    if(listener !=null){
                        listener.getChar(s.name);
                        pressedSection = s.position;
                        currentSection = s;

                    }
                    changeSelectorView(s.position);
                }
            }
        }
        return true;
    }

    private int countNumberOfElements(){
        if (getWidth()/2 > textSize){
            textSize = getHeight()/7;
        }
        return getHeight()/textSize;
    }

    protected void onDraw(Canvas canvas) {
        init();
        super.onDraw(canvas);
    }

    private List<Section> getSectionList(){
        List<Section> sections = new ArrayList<>();
        for(int i = 1; i <= countNumberOfElements(); i++){
            Section section = new Section(sections[i], i, )
        }
    }

    private void changeSelectorView(int position){
        this.invalidate();
    }

    public class Section {
        private Character name;
        private int position;
        private int y;
        private boolean visible;

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
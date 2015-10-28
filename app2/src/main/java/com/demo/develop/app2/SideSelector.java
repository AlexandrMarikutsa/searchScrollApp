package com.demo.develop.app2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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
    private List<Section> sectionsAll;
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
    }

    public SideSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
            for (Section s: sectionsAll) {
                if (y >= (s.y - charHeight) && y <= s.y) {
                    if(listener !=null){
                        listener.getChar(s.name);
                        pressedSection = s.position;
                        currentSection = s;

                    }
                    invalidate();
                }
            }
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        initTextSize();
        getAllSections();
        drawSections(canvas);
        super.onDraw(canvas);
    }

    public void initTextSize(){
        int height  = getHeight();
        int width = getWidth();
        int widthPart = width/2;
        if (widthPart > textSize){
            if(height/widthPart >= 7){
                textSize = height/(height/widthPart);
            }else {
                textSize = height/7;
            }
        }
        init();
        widthCenter = getMeasuredWidth() / 2;
        charHeight = textSize;
    }

    private int countNumberOfElements(){
        return getHeight()/textSize;
    }

    public class Section {
        private Character name;
        private int position;
        private int y;
        private boolean visible;

        public Section(Character name, int position) {
            this.name = name;
            this.position = position;
        }

        public Section(Character name, int position, int y) {
            this.name = name;
            this.position = position;
            this.y = y;
        }
    }

    private void drawNotAllSections(Canvas canvas){
        int numOfUpAndDownSec = (int) ((getHeight() - BOTTOM + textSize) / textSize);
        if((numOfUpAndDownSec % 2) > 0){
            upSections = numOfUpAndDownSec/2;
            downSections = upSections;
            for(int i = 0; i < upSections; i++){
                Section section = sectionsAll.get(i);
                section.y = i * textSize + textSize;
                drawSection(canvas, section);
            }
            upPoint = ((upSections - 1) * textSize + textSize + 2 * radius);
            downPoint = (upPoint + 4 * radius);
            drawPoint(canvas, upPoint);
            drawPoint(canvas, downPoint);

            for(int i = 1; i < downSections + 1; i++){
                Section section = sectionsAll.get(i + sectionsAll.size() - downSections - 1);
                section.y = i * textSize + downPoint;
                drawSection(canvas, section);
            }
        }else {

        }
    }

    public void drawSections(Canvas canvas){
        /*if all letters are on the screen*/
        if(countNumberOfElements() >= sectionsAll.size()){
            int i = 0;
            for(Section section: sectionsAll){
                i++;
                int thisCharHeight = (getHeight() - BOTTOM)/ sections.length;
                section.y = i * thisCharHeight;
                drawSection(canvas, section);
            }
        }else {
            drawNotAllSections(canvas);
        }
    }

    private void getAllSections(){
        sectionsAll = new ArrayList<>();
        for(int i = 0; i < sections.length; i++) {
            sectionsAll.add(new Section(sections[i], i));
        }
    }

    public void setCustomEventListener(OnCustomEventListener eventListener) {
        listener = eventListener;
    }

    public void drawSection(Canvas canvas, Section section){
        if (section.position != pressedSection) {
            canvas.drawText(String.valueOf(section.name), widthCenter, section.y, paint);
        } else {
            canvas.drawText(String.valueOf(section.name), widthCenter, section.y, paintCurrentLetter);
        }
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
//        paintPoint.setTextSize(textSize/2);
    }

    public void drawPoint(Canvas canvas, int point){
        radius = textSize/8;
        canvas.drawCircle(widthCenter, point, radius, paintPoint);
    }

    private void getSections(){
        if(getHeight() <= charHeight*sections.length) {

        }
    }
}
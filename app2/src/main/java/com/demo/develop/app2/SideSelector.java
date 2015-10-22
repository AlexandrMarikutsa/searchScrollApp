package com.demo.develop.app2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import constants.PaintParams;
import listeners.OnCustomEventListener;

public class SideSelector extends View {

    public static final int BOTTOM_PADDING = 10;

    private OnCustomEventListener listener;
    private List<Section> sectionsOnSelector;
    private Section section;
    private int pressedSection = 0;
    private float charHeight;
    private Paint paint;
    private Paint paintPoint;
    private Paint paintCurrentLetter;
    private Character[] sections;
    private int upLetter = 0;
    private int downLetter = 0;
    private float widthCenter;
    private int radius = 3;
    private int textSize = PaintParams.MIN_TEXT_SIZE;

    public SideSelector(Context context) {
        super(context);
//        init();
    }

    public SideSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init();
    }

    public SideSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        init();
    }

    private void init() {
        setBackgroundColor(Color.BLACK);
        paint = new Paint();
        paint.setColor(0xFFA6A9AA);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);

        paintCurrentLetter = new Paint();
        paintCurrentLetter.setColor(Color.parseColor("#E6E6E6"));
        paintCurrentLetter.setTextSize((float) (textSize*1.1));
        paintCurrentLetter.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paintCurrentLetter.setTextAlign(Paint.Align.CENTER);

        paintPoint = new Paint();
        paintPoint.setColor(0xFFA6A9AA);
        paintPoint.setTextSize(20);
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
                if (y >= s.y && y <= (s.y+charHeight)) {
                    if(listener !=null){
                        listener.getChar(s.name);
                        pressedSection = s.position;
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
        widthCenter = getMeasuredWidth() / 2;
        if (getWidth()/2 > textSize){
                if(viewHeight/7 >= PaintParams.MAX_TEXT_SIZE){
                    textSize = PaintParams.MAX_TEXT_SIZE;
                }else {
                    textSize = viewHeight/(getWidth()/2);
                }
        }
        init();
        charHeight = paint.getTextSize()+ 2;

        if(viewHeight <= charHeight*sections.length) {
            int numSections = (int) ((viewHeight/2 - radius*2)/charHeight);

//            if (((viewHeight/2 - radius*2)- (numSections+upLetter)*charHeight >= charHeight){
//                numSections = numSections +1;
//            }
//            if((numSections+upLetter) < (sections.length - numSections)) {
                for (int i = 0 + upLetter; i < numSections + upLetter; i++) {
                    section = new Section(sections[i], i, (int) ((i - upLetter) * charHeight));
                    if(section.position != pressedSection) {
                        canvas.drawText(String.valueOf(sections[i]), widthCenter, (i - upLetter) * charHeight + charHeight, paint);
                    }else {
                        canvas.drawText(String.valueOf(sections[i]), widthCenter, (i - upLetter) * charHeight + charHeight, paintCurrentLetter);
                    }
                    sectionsOnSelector.add(section);
                }
//                if((numSections+upLetter) < (21)) {
//                canvas.drawCircle(widthCenter, viewHeight / 2 - 3*radius, radius, paintPoint);
//                    canvas.drawText(String.valueOf("---"), widthCenter, height / 2, paint);
//                    canvas.drawText(String.valueOf("-+-+-"), widthCenter, viewHeight / 2, paint);
                canvas.drawCircle(widthCenter, getHeight() / 2 - 2*radius, radius, paintPoint);
                canvas.drawCircle(widthCenter, getHeight() / 2 + 2*radius, radius, paintPoint);
//                    canvas.drawCircle(widthCenter, viewHeight / 2 + 2*charHeight/3, 3, paintPoint);
//                }else {
//                    canvas.drawText(String.valueOf("9"), widthCenter, viewHeight / 2, paint);
//                    canvas.drawText(String.valueOf("9"), widthCenter, viewHeight / 2 + charHeight, paint);
//            }
            for (int i = 0; i < numSections; i++) {
                section = new Section(sections[(sections.length - 1) - i],(sections.length - 1) - i, (int) (viewHeight - ((i + 1) * charHeight)));
                if(section.position != pressedSection) {
                    canvas.drawText(String.valueOf(sections[(sections.length - 1) - i]), widthCenter, (viewHeight - (i * charHeight)), paint);
                }else {
                    canvas.drawText(String.valueOf(sections[(sections.length - 1) - i]), widthCenter, (viewHeight - (i * charHeight)), paintCurrentLetter);
                }
                sectionsOnSelector.add(section);
            }
        }else {
            for (int i = 0; i < sections.length; i++) {
                section = new Section(sections[i], i, (int) (i * charHeight));
                charHeight = viewHeight/sections.length;
                if(section.position != pressedSection) {
                    canvas.drawText(String.valueOf(sections[i]), widthCenter, charHeight + (i * charHeight), paint);
                }else {
                    canvas.drawText(String.valueOf(sections[i]), widthCenter, charHeight + (i * charHeight), paintCurrentLetter);
                }
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
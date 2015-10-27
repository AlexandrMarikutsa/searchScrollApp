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
    private int upPoint;
    private int downPoint;

    public SideSelector(Context context) {
        super(context);
    }

    public SideSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
                    }
                    changeSelectorView(s.position);
                }
            }
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        sectionsOnSelector = new ArrayList<>();
        int viewHeight = getHeight();
        int middleHeight;
        widthCenter = getMeasuredWidth() / 2;
        if (getWidth()/2 > textSize){
            textSize = viewHeight/7;
//                if(viewHeight/7 >= PaintParams.MAX_TEXT_SIZE){
//                    textSize = PaintParams.MAX_TEXT_SIZE;
//                }else {
//                    textSize = viewHeight/(getWidth()/2);
//                }
        }
        init();
        charHeight = paint.getTextSize();

        if(viewHeight <= charHeight*sections.length) {/* Якщо НЕ поміщаються всі букви */
            /* Створюєм першу та останню букву */
            Section sectionFirst = new Section(sections[0], 0, (int) charHeight);
            drawSection(canvas, sectionFirst);
            Section sectionLast = new Section(sections[sections.length-1], sections.length-1, viewHeight - BOTTOM);
            drawSection(canvas, sectionLast);
            sectionsOnSelector.add(sectionFirst);
            sectionsOnSelector.add(sectionLast);

            /*Знаходимо відстань між першою та останньою буквами*/
            middleHeight = (int) ((sectionLast.y - charHeight) - sectionFirst.y);

            /*встановлюємо координати для крапок*/
            upPoint = middleHeight / 2 + sectionFirst.y - 2 * radius;
            downPoint = middleHeight / 2 + sectionFirst.y + 2 * radius;

            /*вираховуємо кількість секцій від першої літери до крапки*/
            int numSections = (int) ((upPoint - radius - sectionFirst.y)/charHeight);
            int charHeightForThisSections = (upPoint - radius - sectionFirst.y)/numSections;

            /*якщо натиснуто на останню букву перед крапкою, то змінюємо вигляд і
            * розміщення букв та крапок*/
            if((pressedSection >= (numSections - 1)) && (pressedSection <= sections.length - numSections)){
                /*встановлюємо координати для крапок*/
                upPoint = sectionFirst.y + 2 * radius;
                downPoint = (int) (sectionLast.y - charHeight);
                /*малюємо крапки*/
                drawPoint(canvas, upPoint);
                drawPoint(canvas, downPoint);

                /*Знаходимо відстань між верхньою та нижньою крапками*/
                middleHeight = downPoint - 2 * radius - upPoint;

                /*вираховуємо кількість секцій від верхньої крапки до нижньої*/
                int numSectionsCentral = (int) (middleHeight/charHeight);
                charHeightForThisSections = middleHeight/numSectionsCentral;

                /*малюєм букви від верхньої крапки до нижньої крапки*/
                /*Якщо натиснено на букву над верхньою крапкою*/
                int letterNearPoint = 0;
                if(pressedSection == (numSections - 1)){
                    letterNearPoint = numSections-2;
                }
                    /*Якщо натиснено на букву над нижньою крапкою*/
                if (pressedSection == (sections.length - numSections)) {
                    letterNearPoint = sections.length - 2 - numSectionsCentral;
                }
                /*for down buttons*/
                if(pressedSection < (sections.length - numSections) && pressedSection > (numSections - 1)){
                    letterNearPoint = sections.length - 2 - numSectionsCentral;
                }

                if(pressedSection <= sections.length - numSections - numSectionsCentral + 3  && pressedSection > numSections -1){
                    letterNearPoint = sections.length - 3 - numSectionsCentral;
                }
                for (int i = 0; i < numSectionsCentral; i++) {
                    section = new Section(sections[i + letterNearPoint], i+letterNearPoint, (upPoint + charHeightForThisSections + i * charHeightForThisSections));
                    drawSection(canvas, section);
                    sectionsOnSelector.add(section);
                }
            }else {
                /*малюємо крапки*/
                drawPoint(canvas, upPoint);
                drawPoint(canvas, downPoint);

                /*малюєм букви від другої букви до крапки*/
                for (int i = 1; i < numSections; i++) {
                    section = new Section(sections[i], i, (sectionFirst.y + i * charHeightForThisSections));
                    drawSection(canvas, section);
                    sectionsOnSelector.add(section);
                }
                /*малюєм букви від другої крапки до передостанньої букви*/
                int j = 1;
                for (int i = sections.length - numSections; i < sections.length - 1; i++) {
                    j++;
                    section = new Section(sections[i], i, (downPoint + radius + j * charHeightForThisSections));
                    drawSection(canvas, section);
                    sectionsOnSelector.add(section);
                }
            }
            super.onDraw(canvas);
        }else {
            /* Використовується, якщо поміщаються всі букви */
            for (int i = 0; i < sections.length; i++) {
                section = new Section(sections[i], i, (int) (i * charHeight));
                charHeight = (viewHeight - BOTTOM)/ sections.length;
                if (section.position != pressedSection) {
                    canvas.drawText(String.valueOf(sections[i]), widthCenter, charHeight + (i * charHeight), paint);
                } else {
                    canvas.drawText(String.valueOf(sections[i]), widthCenter, charHeight + (i * charHeight), paintCurrentLetter);
                }
                sectionsOnSelector.add(section);
            }
            super.onDraw(canvas);
        }
    }

    private void changeSelectorView(int position){
        upLetter = position-1;
        this.invalidate();
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

    public void drawSection(Canvas canvas, Section section){
        if (section.position != pressedSection) {
            canvas.drawText(String.valueOf(section.name), widthCenter, section.y, paint);
        } else {
            canvas.drawText(String.valueOf(section.name), widthCenter, section.y, paintCurrentLetter);
        }
    }

    public void drawPoint(Canvas canvas, int point){
        canvas.drawCircle(widthCenter, point, radius, paintPoint);
    }
}
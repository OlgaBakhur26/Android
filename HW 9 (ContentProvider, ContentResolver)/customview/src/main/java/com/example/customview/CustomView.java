package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomView extends View {

    interface TouchActionListener {
        void onTouchDown(int x, int y);
    }

    private int radiusInnerCircle;
    private int radiusOuterCircle;
    private int colorRightBottomSector;
    private int colorLeftBottomSector;
    private int colorLeftTopSector;
    private int colorRightTopSector;
    private int colorInnerCircle;

    private int screenCenterX;
    private int screenCenterY;

    private Paint paintInnerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintRightBottomSector = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintLeftBottomSector = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintLeftTopSector = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintRightTopSector = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RectF sector = new RectF();
    private TouchActionListener touchActionListener;

    private List<String> colorList = Arrays.asList("#FF6720", "#ECB7C9", "#00BCD4", "#D11A88",
            "#009688", "#DAEA43", "#A7DDF6", "#EF1505", "#086A57", "#5B086A", "#8A9BCD", "#FFF6A7",
            "#D17095", "#236E0E");

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
    }

    private void getAttrs(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView);
            try {
                radiusInnerCircle = typedArray.getDimensionPixelSize(R.styleable.CustomView_radiusInnerCircle, 0);
                radiusOuterCircle = typedArray.getDimensionPixelSize(R.styleable.CustomView_radiusOuterCircle, 0);
                colorRightBottomSector = typedArray.getColor(R.styleable.CustomView_colorRightBottomSector, 0);
                colorLeftBottomSector = typedArray.getColor(R.styleable.CustomView_colorLeftBottomSector, 0);
                colorLeftTopSector = typedArray.getColor(R.styleable.CustomView_colorLeftTopSector, 0);
                colorRightTopSector = typedArray.getColor(R.styleable.CustomView_colorRightTopSector, 0);
                colorInnerCircle = typedArray.getColor(R.styleable.CustomView_colorInnerCircle, 0);
            } finally {
                typedArray.recycle();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenCenterX = w / 2;
        screenCenterY = h / 2;
    }

//    private void adjustPaint(){
//        paintRightBottomSector.setColor(colorRightBottomSector);
//        paintRightBottomSector.setStyle(Paint.Style.FILL);
//
//        paintLeftBottomSector.setColor(colorLeftBottomSector);
//        paintLeftBottomSector.setStyle(Paint.Style.FILL);
//
//        paintLeftTopSector.setColor(colorLeftTopSector);
//        paintLeftTopSector.setStyle(Paint.Style.FILL);
//
//        paintRightTopSector.setColor(colorRightTopSector);
//        paintRightTopSector.setStyle(Paint.Style.FILL);
//
//        paintInnerCircle.setColor(colorInnerCircle);
//        paintInnerCircle.setStyle(Paint.Style.FILL);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        adjustPaint();
        sector.set(screenCenterX - radiusOuterCircle, screenCenterY - radiusOuterCircle,
                screenCenterX + radiusOuterCircle, screenCenterY + radiusOuterCircle);
        canvas.drawArc(sector, 0, 90, true, paintRightBottomSector);

        sector.set(screenCenterX - radiusOuterCircle, screenCenterY - radiusOuterCircle,
                screenCenterX + radiusOuterCircle, screenCenterY + radiusOuterCircle);
        canvas.drawArc(sector, 90, 90, true, paintLeftBottomSector);

        sector.set(screenCenterX - radiusOuterCircle, screenCenterY - radiusOuterCircle,
                screenCenterX + radiusOuterCircle, screenCenterY + radiusOuterCircle);
        canvas.drawArc(sector, 180, 90, true, paintLeftTopSector);

        sector.set(screenCenterX - radiusOuterCircle, screenCenterY - radiusOuterCircle,
                screenCenterX + radiusOuterCircle, screenCenterY + radiusOuterCircle);
        canvas.drawArc(sector, 270, 90, true, paintRightTopSector);

        sector.set(screenCenterX - radiusInnerCircle, screenCenterY - radiusInnerCircle,
                screenCenterX + radiusInnerCircle, screenCenterY + radiusInnerCircle);
        canvas.drawOval(sector, paintInnerCircle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int pointX = (int) event.getX();
            int pointY = (int) event.getY();

            if (touchActionListener != null) {
                touchActionListener.onTouchDown(pointX, pointY);
            }

            if (Math.pow(radiusInnerCircle, 2) >= Math.pow((pointX - screenCenterX), 2) + Math.pow((pointY - screenCenterY), 2)) {
                colorInnerCircle = changeColor();
                colorRightBottomSector = changeColor();
                colorLeftBottomSector = changeColor();
                colorLeftTopSector = changeColor();
                colorRightTopSector = changeColor();
            }

            if (Math.pow(radiusInnerCircle, 2) <= Math.pow((pointX - screenCenterX), 2) + Math.pow((pointY - screenCenterY), 2)
                    && Math.pow(radiusOuterCircle, 2) >= Math.pow((pointX - screenCenterX), 2) + Math.pow((pointY - screenCenterY), 2)) {
                if (pointX > screenCenterX && pointY > screenCenterY) {
                    colorRightBottomSector = changeColor();
                }
                if (pointX < screenCenterX && pointY > screenCenterY) {
                    colorLeftBottomSector = changeColor();
                }
                if (pointX < screenCenterX && pointY < screenCenterY) {
                    colorLeftTopSector = changeColor();
                }
                if (pointX > screenCenterX && pointY < screenCenterY) {
                    colorRightTopSector = changeColor();
                }
            }
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    private int changeColor() {
        return Color.parseColor(colorList.get((int) (Math.random() * colorList.size())));
    }

    public void setTouchActionListener(TouchActionListener touchActionListener) {
        this.touchActionListener = touchActionListener;
    }
}

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

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF sector = new RectF();
    private TouchActionListener touchActionListener;

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
    public boolean isAttachedToWindow() {
        return super.isAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenCenterX = w / 2;
        screenCenterY = h / 2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(colorRightBottomSector);
        paint.setStyle(Paint.Style.FILL);
        sector.set(screenCenterX - radiusOuterCircle, screenCenterY - radiusOuterCircle,
                screenCenterX + radiusOuterCircle, screenCenterY + radiusOuterCircle);
        canvas.drawArc(sector, 0, 90, true, paint);


        paint.setColor(colorLeftBottomSector);
        paint.setStyle(Paint.Style.FILL);
        sector.set(screenCenterX - radiusOuterCircle, screenCenterY - radiusOuterCircle,
                screenCenterX + radiusOuterCircle, screenCenterY + radiusOuterCircle);
        canvas.drawArc(sector, 90, 90, true, paint);

        paint.setColor(colorLeftTopSector);
        paint.setStyle(Paint.Style.FILL);
        sector.set(screenCenterX - radiusOuterCircle, screenCenterY - radiusOuterCircle,
                screenCenterX + radiusOuterCircle, screenCenterY + radiusOuterCircle);
        canvas.drawArc(sector, 180, 90, true, paint);

        paint.setColor(colorRightTopSector);
        paint.setStyle(Paint.Style.FILL);
        sector.set(screenCenterX - radiusOuterCircle, screenCenterY - radiusOuterCircle,
                screenCenterX + radiusOuterCircle, screenCenterY + radiusOuterCircle);
        canvas.drawArc(sector, 270, 90, true, paint);

        paint.setColor(colorInnerCircle);
        paint.setStyle(Paint.Style.FILL);
        sector.set(screenCenterX - radiusInnerCircle, screenCenterY - radiusInnerCircle,
                screenCenterX + radiusInnerCircle, screenCenterY + radiusInnerCircle);
        canvas.drawOval(sector, paint);
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

    private List<String> colorList = new ArrayList<String>() {{
        add("#FF6720");
        add("#ECB7C9");
        add("#00BCD4");
        add("#D11A88");
        add("#009688");
        add("#DAEA43");
        add("#A7DDF6");
        add("#EF1505");
        add("#086A57");
        add("#5B086A");
        add("#8A9BCD");
        add("#FFF6A7");
        add("#D17095");
        add("#236E0E");
    }};

    private int changeColor() {
        return Color.parseColor(colorList.get((int) (Math.random() * colorList.size())));
    }

    public void setTouchActionListener(TouchActionListener touchActionListener) {
        this.touchActionListener = touchActionListener;
    }
}

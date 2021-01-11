package com.example.shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class Scene extends View {
    ArrayList<Figure> figures = new ArrayList<Figure>();
    ArrayList<Figure> reservedFigures = new ArrayList<Figure>();
    Paint paint = new Paint();

    Point[] points = new Point[3];

    float density;
    int gridSize = 30;
    int gridWidth;
    int gridHeight;
    int countPoints;

    public static final String RECT = "rect";
    public static final String TRIANGLE = "triangle";
    public static final String CIRCLE = "circle";
    String mode = "draw";
    String typeShape = "rect";
    String color = "#000000";

    public Scene(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        density = getResources().getDisplayMetrics().density;
        gridSize *= density;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gridWidth = getWidth() / gridSize;
        gridHeight = getHeight() / gridSize;

        drawGrid(canvas);
        for (Figure figure: figures){
            figure.draw(canvas, paint);
        }
        drawPoints(canvas);
    }

    private void drawGrid(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        DashPathEffect effects = new DashPathEffect(new float[] { 3, 9}, 0);
        paint.setPathEffect(effects);

        for (int i = 0; i < gridWidth ; i++) {
            int x = i * gridSize;
            canvas.drawLine(x, 0, x, getHeight(), paint);
        }

        for (int i = 0; i < gridHeight ; i++) {
            int y = i * gridSize;
            int endX = getWidth();
            canvas.drawLine(0, y, endX, y, paint);
        }

    }

    private void drawPoints(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        for (int i = 0; i < countPoints ; i++) {
            Point point = points[i];
            canvas.drawCircle(point.x, point.y, 10, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            onDownTouch((int)event.getX(), (int)event.getY());
        }
        return true;
    }

    private void onDownTouch(int x, int y) {
        if (this.mode.equals("draw")) {
            points[countPoints] = new Point(x, y);
            countPoints += 1;

            switch (this.typeShape) {
                case RECT: checkRectForCreating(); break;
                case CIRCLE: checkCircleForCreating(); break;
                case TRIANGLE: checkTriangleForCreating(); break;
            }

            invalidate();
        }
    }

    private void checkRectForCreating() {
        if (countPoints >= 2) {
            int width = points[1].x - points[0].x;
            int height = points[1].y - points[0].y;
            figures.add(new Rect(points[0], color, width, height));
            countPoints = 0;
        }
    }

    private void checkCircleForCreating() {
        if (countPoints >= 2) {
            int a = points[1].x - points[0].x;
            int b = points[1].y - points[0].y;
            float radius = (float)Math.sqrt( Math.pow(a, 2) + Math.pow(b, 2) );
            figures.add(new Circle(points[0], color, radius));
            countPoints = 0;
        }
    }

    private void checkTriangleForCreating() {
        if (countPoints >= 3) {
            figures.add(new Triangle(color, points[0], points[1], points[2]));
            countPoints = 0;
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setTypeShape(String type) {
        this.typeShape = type;
        countPoints = 0;
        invalidate();
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public void addToFigures(Figure figure) {
        this.figures.add(figure);
    }
}

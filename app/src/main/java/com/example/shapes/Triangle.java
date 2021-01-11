package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import org.json.JSONException;
import org.json.JSONObject;

public class Triangle extends Figure {
    public Point a;
    public Point b;
    public Point c;

    Triangle(String color, Point a, Point b, Point c){
        super(color);
        this.type = "TRIANGLE";
        this.a = a;
        this.b = b;
        this.c = c;
    }

    void draw(Canvas canvas, Paint paint){
        if (a != null) {
            paint.setColor(Color.parseColor(this.color));

            Path path = new Path();
            path.moveTo(a.x, a.y);
            path.lineTo(b.x, b.y);
            path.lineTo(c.x, c.y);
            path.lineTo(a.x, a.y);

            canvas.drawPath(path, paint);
        }
    }

    @Override
    JSONObject convertToJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("TYPE", type);
        jsonObject.put("COLOR", color);

        jsonObject.put("POINT_AX", a.x);
        jsonObject.put("POINT_AY", a.y);

        jsonObject.put("POINT_BX", b.x);
        jsonObject.put("POINT_BY", b.y);

        jsonObject.put("POINT_CX", c.x);
        jsonObject.put("POINT_CY", c.y);

        return jsonObject;
    }
}

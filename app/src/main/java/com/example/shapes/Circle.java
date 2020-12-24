package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import org.json.JSONException;
import org.json.JSONObject;

public class Circle extends Figure{
    public Point point;
    public float radius;

    Circle(Point point, String color, float radius){
        super(color);
        this.point = point;
        this.radius = radius;
    }

    void draw(Canvas canvas, Paint paint){
        if (point != null) {
            paint.setColor(Color.parseColor(this.color));
            canvas.drawCircle(point.x, point.y, radius, paint);
        }
    }

    @Override
    JSONObject convertToJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("COLOR", color);
        jsonObject.put("POINT", point);
        jsonObject.put("RADIUS", radius);

        return jsonObject;
    }
}

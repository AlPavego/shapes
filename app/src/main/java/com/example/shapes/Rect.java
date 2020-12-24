package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import org.json.JSONException;
import org.json.JSONObject;

public class Rect extends Figure{
    public Point point;
    public int width;
    public int height;

    Rect(Point point, String color, int width, int height){
        super(color);
        this.point = point;
        this.width = width;
        this.height = height;
    }

    void draw(Canvas canvas, Paint paint){
        if (point != null) {
            paint.setColor(Color.parseColor(this.color));
            canvas.drawRect(point.x, point.y, point.x + width, point.y + height, paint);
        }
    }

    @Override
    JSONObject convertToJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("COLOR", color);
        jsonObject.put("POINT", point);
        jsonObject.put("WIDTH", width);
        jsonObject.put("HEIGHT", height);

        return jsonObject;
    }
}

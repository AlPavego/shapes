package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Figure {
    String color;

    Figure(String color){
        this.color = color;
    }

    void draw(Canvas canvas, Paint paint) {
    }

    JSONObject convertToJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("COLOR", color);
        return jsonObject;
    }
}

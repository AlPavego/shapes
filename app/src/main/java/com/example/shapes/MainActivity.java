package com.example.shapes;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final String FILENAME = "figuresJSON";
    final String TAG_NAME = "my_tag";

    RadioGroup shapes;
    Spinner colors;
    Scene scene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scene = findViewById(R.id.scene);
        shapes = findViewById(R.id.shapeGroup);
        colors = findViewById(R.id.colors);

        shapes.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rect: scene.setTypeShape(Scene.RECT); break;
                    case R.id.circle: scene.setTypeShape(Scene.CIRCLE); break;
                    case R.id.triangle: scene.setTypeShape(Scene.TRIANGLE); break;
                }
            }
        });

        colors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] colorArr = getResources().getStringArray(R.array.colors);
                scene.setColor("#" + colorArr[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("lifecycle", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle", "onResume()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        JSONArray jsonArray = new JSONArray();
        String str = "";
        for (Figure figure: scene.getFigures()) {
            try {
                jsonArray.put(figure.convertToJSON());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        str = jsonArray.toString();
        outState.putString(FILENAME, str);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String figuresJSONText = savedInstanceState.getString(FILENAME);
        ArrayList<String> figuresJSONArray = new ArrayList<String>();

        int len = 0;
        for (int i = 0; i < figuresJSONText.length(); i++) {
            if ("{".equals(String.valueOf(figuresJSONText.charAt(i)))) {
                while (!("}".equals(String.valueOf(figuresJSONText.charAt(i + len))))) {
                    len++;
                }
                figuresJSONArray.add(figuresJSONText.substring(i, i + len + 1));
                i += len;
                len = 0;
            }
        }
        for (String str: figuresJSONArray) {
            try {
                JSONObject jsonObject = new JSONObject(str);

                String type = jsonObject.getString("TYPE");
                String color = jsonObject.getString("COLOR");
                if (type.equals("RECT")) {
                    int x = jsonObject.getInt("POINTX");
                    int y = jsonObject.getInt("POINTY");

                    int width = jsonObject.getInt("WIDTH");
                    int height = jsonObject.getInt("HEIGHT");

                    scene.addToFigures(new Rect(new Point(x, y), color, width, height));
                } else if (type.equals("CIRCLE")) {
                    int x = jsonObject.getInt("POINTX");
                    int y = jsonObject.getInt("POINTY");

                    float radius = (float) jsonObject.getDouble("RADIUS");

                    scene.addToFigures(new Circle(new Point(x, y), color, radius));
                } else if (type.equals("TRIANGLE")) {
                    int a_x = jsonObject.getInt("POINT_AX");
                    int a_y = jsonObject.getInt("POINT_AY");

                    int b_y = jsonObject.getInt("POINT_BX");
                    int b_x = jsonObject.getInt("POINT_BY");

                    int c_y = jsonObject.getInt("POINT_CX");
                    int c_x = jsonObject.getInt("POINT_CY");

                    scene.addToFigures(new Triangle(color,
                            new Point(a_x, a_y), new Point(b_x, b_y), new Point(c_x, c_y)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
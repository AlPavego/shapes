package com.example.shapes;

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

public class MainActivity extends AppCompatActivity {
    final String FILENAME = "figuresJSON";

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
        for (Figure figure: scene.getFigures()) {
            try {
                jsonArray.put(figure.convertToJSON());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            outState.putString(FILENAME, jsonArray.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String figuresJSONText = savedInstanceState.getString(FILENAME);

    }
}
package com.example.a10616.testdemo.jackson;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.a10616.testdemo.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonActivity extends AppCompatActivity {
    public static final String TAG = JacksonActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackson);
    }


    public void input(View view) {
        String inputJson = " {\n" +
                "        \"type\": \"input\",\n" +
                "        \"label\": \"标题\",\n" +
                "        \"uiType\": \"input\",\n" +
                "        \"input\" : \"lvsheng\"\n" +
                "        \n" +
                "      }";
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputPage page = ((InputPage) mapper.readValue(inputJson, Page.class));
            Log.e(TAG, "number: " + page.getInput() + "  " + page.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void number(View view) {
        ObjectMapper mapper = new ObjectMapper();

        String numberJson = " {\n" +
                "        \"type\": \"number\",\n" +
                "        \"label\": \"价格\",\n" +
                "        \"uiType\": \"input\",\n" +
                "        \"number\" : 110\n" +
                "        \n" +
                "      }";
        try {
            NumberPage page = ((NumberPage) mapper.readValue(numberJson, Page.class));
            Log.e(TAG, "input: " + page.getNumber() + "  " + page.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

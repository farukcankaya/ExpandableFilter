package com.farukcankaya.expandablefilterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.farukcankaya.expandablefilter.ExpandableFilter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ExpandableFilter expandableFilter = (ExpandableFilter) findViewById(R.id.expandableLayout);
        List<String> items = new ArrayList<>(0);
        items.add("\ue902");
        items.add("\ue900");
        items.add("\ue901");
        expandableFilter.setEmoji("\ue902");
        expandableFilter.setLabel("Price");
        expandableFilter.setItems(items);

        expandableFilter.setItemSelectListener(new ExpandableFilter.OnItemSelectListener() {
            @Override
            public void onSelected(int position, String text) {
                Log.i("Select", "position:" + position + " text:" + text);
            }

            @Override
            public void onDelected(int position, String text) {
                Log.i("Delect", "position:" + position + " text:" + text);
            }
        });
    }
}

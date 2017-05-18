package com.farukcankaya.expandablefilterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        final LinearLayout ll = (LinearLayout) expandableFilter.getChildAt(0);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableFilter.toggle();
            }
        });
    }
}

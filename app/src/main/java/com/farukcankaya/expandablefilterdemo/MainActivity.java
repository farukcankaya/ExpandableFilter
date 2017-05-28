package com.farukcankaya.expandablefilterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.farukcankaya.expandablefilter.ExpandableFilter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExpandableFilter.OnItemSelectListener {
    ExpandableFilter priceFilter, distanceFilter, openingFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        priceFilter = (ExpandableFilter) findViewById(R.id.priceFilter);
        distanceFilter = (ExpandableFilter) findViewById(R.id.distanceFilter);
        openingFilter = (ExpandableFilter) findViewById(R.id.openingFilter);

        priceFilter.setItemSelectListener(this);
        distanceFilter.setItemSelectListener(this);
        openingFilter.setItemSelectListener(this);
    }

    @Override
    public void onSelected(int position, String text) {
        Toast.makeText(getApplicationContext(), "Selected: " + text + " Position:" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeselected(int position, String text) {
        Toast.makeText(getApplicationContext(), "Deselected: " + text + " Position:" + position, Toast.LENGTH_SHORT).show();
    }
}

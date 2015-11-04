package com.liiang.nlc.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.liiang.nlc.R;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        //listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1), getDate());
        //setContentView(R.layout.activity_book_list);
        setContentView(listView);
    }

    private List<String> getDate(){
        List<String> data = new ArrayList<String>();
        data.add("test1");
        data.add("test2");
        data.add("test3");
        data.add("test4");
        return data;
    }
}

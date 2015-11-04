package com.liiang.nlc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.liiang.nlc.R;
import com.liiang.nlc.model.BookTypePair;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initspinner();
        initSearchView();
    }

    private void  initspinner(){
        List<Map<String, String>> types = new ArrayList<Map<String, String>>();
        BookTypePair[] array = new BookTypePair[2];//设入adapter的数组
        BookTypePair p = new BookTypePair("NLC01", "中文文献");
        BookTypePair p1 = new BookTypePair("NLC09", "English");
        array[0] = p;
        array[1] = p1;
        ArrayAdapter<BookTypePair> adapter=new ArrayAdapter<BookTypePair>(this,android.R.layout.simple_spinner_item, array);
        Spinner spinner = (Spinner)findViewById(R.id.booktypespinner);
        spinner.setAdapter(adapter);
    }

    private void initSearchView(){
        ListView list = (ListView) findViewById(R.id.bookListView);
        //生成动态数组，并且转载数据
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<30;i++)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("bookid", ""+i);
            map.put("bookname", "用户体验"+i);
            map.put("author", "罗浩");
            map.put("publish", "中信出版社");
            map.put("publishyear", "2015");
            mylist.add(map);
        }
        //生成适配器，数组===》ListItem
        SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释
                mylist,//数据来源
                R.layout.activity_book_list,//ListItem的XML实现
                //动态数组与ListItem对应的子项
                new String[] {"bookid", "bookname", "author", "publish", "publishyear"},
                //ListItem的XML文件里面的两个TextView ID
                new int[] {R.id.bookid,R.id.bookname, R.id.author, R.id.publish, R.id.publishyear});
        //添加并且显示
        list.setAdapter(mSchedule);
    }
}

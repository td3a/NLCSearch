package com.liiang.nlc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.liiang.nlc.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initSearchView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            openSearchActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSearchActivity(){
        Intent intent = new Intent();
        //intent.putExtra("name","LeiPei");
            /* 指定intent要启动的类 */
        intent.setClass(MainActivity.this, SearchActivity.class);
            /* 启动一个新的Activity */
        startActivity(intent);
            /* 关闭当前的Activity */
        // finish();
    }

    private void initSearchView(){
        ListView list = (ListView) findViewById(R.id.collectedlistview);
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

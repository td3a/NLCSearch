package com.liiang.nlc.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.liiang.nlc.R;
import com.liiang.nlc.model.BookDetail;
import com.liiang.nlc.model.BookTypePair;
import com.liiang.nlc.utils.Post;

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
        addEventListener();
    }

    private void addEventListener(){
        Button searchBtn = (Button) findViewById( R.id.searchBtn );
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Spinner spinner = (Spinner) findViewById(R.id.booktypespinner);
                    BookTypePair bookType = (BookTypePair) spinner.getSelectedItem();
                    String type = bookType.key;
                    EditText keyEditText = (EditText) findViewById(R.id.searchKeyEditText);
                    String keyStr = keyEditText.getText().toString();
                    showTypeAndKeyStr(SearchActivity.this, type, keyStr);
            }
        });
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

    private void initSearchView(List<BookDetail> bookDetailList){
        //生成动态数组，并且转载数据
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        for(BookDetail bookDetail : bookDetailList)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("bookid", bookDetail.getBookid());
            map.put("bookname", bookDetail.getBookname());
            map.put("author",  bookDetail.getAuthor());
            map.put("publish", bookDetail.getPublish());
            map.put("publishyear", bookDetail.getPublishyear());
            mylist.add(map);
        }
        fillListView(mylist);

    }
    private void fillListView(ArrayList<HashMap<String, String>> bookDetailList){
        ListView list = (ListView) findViewById(R.id.bookListView);
        //生成适配器，数组===》ListItem
        SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释
                bookDetailList,//数据来源
                R.layout.activity_book_list,//ListItem的XML实现
                //动态数组与ListItem对应的子项
                new String[] {"bookid", "bookname", "author", "publish", "publishyear"},
                //ListItem的XML文件里面的两个TextView ID
                new int[] {R.id.bookid,R.id.bookname, R.id.author, R.id.publish, R.id.publishyear});
        //添加并且显示
        list.setAdapter(mSchedule);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView bookidTextView = (TextView)view.findViewById(R.id.bookid);
                String bookid = bookidTextView.getText().toString();
                TextView booknameTextView = (TextView)view.findViewById(R.id.bookname);
                String bookname = booknameTextView.getText().toString();
                openDetailActivity(bookid, bookname);
            }
        });
    }


    private void showTypeAndKeyStr(Context context, String type , String key){
        final String typeStr = type;
        final String keyStr = key;
        Toast.makeText(getApplicationContext(), "Loading",  Toast.LENGTH_SHORT).show();
        new Thread(){
            public void run() {
                //Toast.makeText(getApplicationContext(), "Loading",  Toast.LENGTH_SHORT).show();
                final List<BookDetail> bookDetailList = Post.doSearch(typeStr, keyStr);
                if(bookDetailList != null && bookDetailList.size() > 0 ){
                    SearchActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initSearchView(bookDetailList);
                        }
                    });
                }
            }
        }.start();
    }


    /**
     * @param bookid--> "NLC01039383737"
     */
    private void openDetailActivity(String bookid, String bookname){
        Intent intent = new Intent();
        intent.putExtra("bookid",bookid);
        intent.putExtra("bookname", bookname);
            /* 指定intent要启动的类 */
        intent.setClass(SearchActivity.this, StatusActivity.class);
            /* 启动一个新的Activity */
        startActivity(intent);
            /* 关闭当前的Activity */
        // finish();
    }
}

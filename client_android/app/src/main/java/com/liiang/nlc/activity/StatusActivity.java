package com.liiang.nlc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.liiang.nlc.R;
import com.liiang.nlc.model.BookDetail;
import com.liiang.nlc.model.BookStatus;
import com.liiang.nlc.utils.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();
        String bookid = intent.getStringExtra("bookid");
        String bookname = intent.getStringExtra("bookname");
        TextView nameTextView = (TextView) findViewById(R.id.statusbookname);
        nameTextView.setText(bookname);
        initStatusList(bookid);
    }


    private void initStatusList( final String bookid ){
        final String bookidStr = bookid;
        Toast.makeText(getApplicationContext(), "Loading",  Toast.LENGTH_SHORT).show();
        new Thread(){
            public void run() {
                //Toast.makeText(getApplicationContext(), "Loading",  Toast.LENGTH_SHORT).show();
                final List<BookStatus> statusList = Post.doGetStatus(bookidStr);
                if(statusList != null && statusList.size() > 0 ){
                    StatusActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initStatusList(statusList);
                        }
                    });
                }
            }
        }.start();
    }

    private  void initStatusList(List<BookStatus> statusList ){
        //生成动态数组，并且转载数据
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        for(BookStatus status : statusList)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("loanstatus", status.getLoanstatus());
            map.put("collectionno", status.getCollectionno());
            map.put("duedate",  status.getDuedate());
            map.put("duehour", status.getDuehour());
            map.put("sublibrary", status.getSublibrary());
            map.put("location", status.getLocation());
            map.put("requestno", status.getRequestno());
            map.put("barcode", status.getBarcode());
            mylist.add(map);
        }
        fillListView(mylist);
    }

    private  void fillListView(List<HashMap<String, String>> statusList){
        ListView list = (ListView) findViewById(R.id.statusListView);
        //生成适配器，数组===》ListItem
        SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释
                statusList,//数据来源
                R.layout.activity_status_list,//ListItem的XML实现
                //动态数组与ListItem对应的子项
                new String[] {"loanstatus", "collectionno", "duedate", "duehour", "sublibrary", "location", "requestno", "barcode"},
                //ListItem的XML文件里面的两个TextView ID
                new int[] {R.id.loanstatus,R.id.collectionno, R.id.duedate, R.id.duehour, R.id.sublibrary, R.id.location, R.id.requestno, R.id.barcode});
        //添加并且显示
        list.setAdapter(mSchedule);
    }
}

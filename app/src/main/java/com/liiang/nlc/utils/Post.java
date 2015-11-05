package com.liiang.nlc.utils;

import android.util.JsonToken;

import com.liiang.nlc.model.BookDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LYN on 2015-11-05.
 */
public class Post {

    public static String SERVER_DOMAIN = "http://www.liiang.com/NLC/";

    public static List<BookDetail> doSearch(String type, String keyStr){

        List<BookDetail> resultList = new ArrayList<BookDetail>();
        String urlStr = SERVER_DOMAIN + "NLCSearch.php";
        try {
            String paramStr ="keystr=" + URLEncoder.encode(keyStr, "UTF-8");
                    paramStr += "&local_base=" + URLEncoder.encode(type, "UTF-8");

            URL url = new URL(urlStr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("contentType", "UTF-8");
            httpConn.connect();
            DataOutputStream out = new DataOutputStream(httpConn.getOutputStream());

            out.writeBytes(paramStr);
            out.flush();
            out.close();
            if( httpConn.getResponseCode() == 200){
                InputStream in = httpConn.getInputStream();
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                int len =0;
                byte buffer[] = new byte[1024];
                while ( (len = in.read(buffer)) != -1){
                    bout.write(buffer, 0, len);
                }
                in.close();
                bout.close();
                String resultStr = new String(bout.toByteArray());
                JSONArray jsonArray = new  JSONArray(resultStr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i); // 得到每个对象
                    BookDetail book = new BookDetail();
                    book.setBookid(item.getString("bookid"));
                    book.setBookname(item.getString("bookname"));
                    book.setAuthor( item.getString("author"));
                    book.setPublish(item.getString("publish"));
                    book.setPublishyear(item.getString("year"));
                    resultList.add(book);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}

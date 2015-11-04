package com.liiang.nlc.model;

/**
 * Created by LYN on 2015-11-04.
 */
public class BookDetail {
    private String bookid;
    private String bookname;
    private String author;
    private String publish;
    private String publishyear;

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getPublishyear() {
        return publishyear;
    }

    public void setPublishyear(String publishyear) {
        this.publishyear = publishyear;
    }

    @Override
    public String toString(){
        return bookname;
    }
}

package com.liiang.nlc.model;

/**
 * Created by LYN on 2015-11-04.
 */
public class BookTypePair {
    public String key;
    public String value;

    public BookTypePair(String key, String value){
        this.key = key;
        this.value = value;
    }
    public String toString(){
        return value;
    }
}

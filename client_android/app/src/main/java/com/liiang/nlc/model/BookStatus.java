package com.liiang.nlc.model;

/**
 * Created by LYN on 2015-11-05.
 */
public class BookStatus {
    private String loanstatus;
    private String collectionno;
    private String duedate;
    private String duehour;
    private String sublibrary;
    private String location;
    private String requestno;
    private String barcode;

    public String getLoanstatus() {
        return loanstatus;
    }

    public void setLoanstatus(String loanstatus) {
        this.loanstatus = loanstatus;
    }

    public String getCollectionno() {
        return collectionno;
    }

    public void setCollectionno(String collectionno) {
        this.collectionno = collectionno;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getDuehour() {
        return duehour;
    }

    public void setDuehour(String duehour) {
        this.duehour = duehour;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequestno() {
        return requestno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSublibrary() {
        return sublibrary;
    }

    public void setSublibrary(String sublibrary) {
        this.sublibrary = sublibrary;
    }
}

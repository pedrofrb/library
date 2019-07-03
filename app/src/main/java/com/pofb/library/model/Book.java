package com.pofb.library.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Book {
    private int id;
    private String title;
    private String originLibrary;
    private Date devolution;
    private String circulationCode;

    public Book(int id, String title, String originLibrary, Date devolution, String circulationCode) {
        this.id = id;
        this.title = title;
        this.originLibrary = originLibrary;
        this.devolution = devolution;
        this.circulationCode = circulationCode;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginLibrary() {
        return originLibrary;
    }

    public void setOriginLibrary(String originLibrary) {
        this.originLibrary = originLibrary;
    }

    public Date getDevolution() {
        return devolution;
    }

    public void setDevolution(Date devolution) {
        this.devolution = devolution;
    }

    public String getCirculationCode() {
        return circulationCode;
    }

    public void setCirculationCode(String circulationCode) {
        this.circulationCode = circulationCode;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "name:"+title+" id: "+id+" origin: "+originLibrary+" circulation: "+circulationCode+" devolution: "+sdf.format(devolution);
    }
}

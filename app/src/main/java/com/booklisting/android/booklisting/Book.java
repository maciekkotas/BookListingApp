package com.booklisting.android.booklisting;

/**
 * Created by macie on 20.06.2017.
 */

public class Book {

    private String mTitle;
    private StringBuilder mAuthor;
    private String mUrl;
    private String mInfoLink;

    public Book(String title, StringBuilder author, String url, String infoLink) {
        mTitle = title;
        mAuthor = author;
        mUrl = url;
        mInfoLink = infoLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public StringBuilder getAuthor() {
        return mAuthor;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getInfoLink() {
        return mInfoLink;
    }
}

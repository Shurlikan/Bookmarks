package models;

import java.util.ArrayList;

/**
 * Created by lorenzoantenucci on 21/06/2017.
 */

public class Books {
    private String title;
    private String chapter;
    private String page;

    public Books(String t, String c, String p) {
        title = t;
        chapter = c;
        page = p;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    private static int lastBookId = 0;

    public static ArrayList<Books> createBooksList(int n) {
        ArrayList<Books> books = new ArrayList<Books>();

        for(int i = 0; i < n; i++) {
            books.add( new Books( "Titolo" + i, Integer.toString(i), Integer.toString(i * 100) ) );
        }

        return books;
    }
}

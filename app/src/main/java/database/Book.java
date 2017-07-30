package database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lorenzoantenucci on 21/06/2017.
 */

@Entity(indexes =  {
        @Index(value = "title", unique = true)
})
public class Book {
    @Id
    private Long id;

    private String chapter;

    @NotNull
    private String title;
    private String page;

@Generated(hash = 770356533)
public Book(Long id, String chapter, @NotNull String title, String page) {
    this.id = id;
    this.chapter = chapter;
    this.title = title;
    this.page = page;
}

@Generated(hash = 1839243756)
public Book() {
}

public Book(Long id) {
    this.id = id;
}

public String getTitle() {
    return this.title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getChapter() {
    return this.chapter;
}

public void setChapter(String chapter) {
    this.chapter = chapter;
}

public String getPage() {
    return this.page;
}

public void setPage(String page) {
    this.page = page;
}

public void setId(Long id) {
    this.id = id;
}

public Long getId() {
    return this.id;
}
}

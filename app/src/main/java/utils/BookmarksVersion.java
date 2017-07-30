package utils;

/**
 * Created by lorenzoantenucci on 14/07/2017.
 */

public class BookmarksVersion {
    private int first = 1;
    private int second = 0;
    private int third = 0;

    public BookmarksVersion() {}

    @Override
    public String toString() {
        return first + "." + second + "." + third;
    }
}

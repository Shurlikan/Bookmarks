package shruikan.bookmarks.com.bookmarks.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import adapters.BooksAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import database.Book;
import database.BookDao;
import database.DaoHelper;
import database.DaoSession;
import keys.BookInterface;
import shruikan.bookmarks.com.bookmarks.R;

public class AddBookmarkActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Query<Book> booksQuery;
    private BooksAdapter booksAdapter;
//    private BookDao bookDao;
    private List<Book> books;

    @BindView(R.id.add_bkm_title)
    EditText addBkmTitle;
    @BindView(R.id.add_bkm_chapter)
    EditText addBkmChapter;
    @BindView(R.id.add_bkm_page)
    EditText addBkmPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bookmark);
        ButterKnife.bind(this);
        Intent i = getIntent();

        String titleActivity = getResources().getString(R.string.add_bookmark_activity_title);
        setTitle(titleActivity);

//        sharedPreferences = getSharedPreferences("bookmarks", Context.MODE_PRIVATE);

        // Get the bookDao
        DaoSession daoSession = DaoHelper.getInstance(getApplicationContext()).getDaoSession();
        BookDao bookDao = daoSession.getBookDao();

        booksQuery = bookDao.queryBuilder().orderAsc(BookDao.Properties.Title).build();
    }

    @OnClick(R.id.add_bkm_save)
    public void onViewClicked() {
        String newTitle = addBkmTitle.getText().toString();
        String newChapter = addBkmChapter.getText().toString();
        String newPage = addBkmPage.getText().toString();

        // TODO Translations
        String msg = getResources().getString(R.string.add_bookmark_empty_field_msg);

        if (newTitle == "" || newPage == "") {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            setResult(ListBookmarksActivity.BACK_OPTION);
            finish();
        } else {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//            editor.putString(PreferencesInterface.title, newTitle);
//            editor.putString(PreferencesInterface.chapter, newChapter);
//            editor.putString(PreferencesInterface.page, newPage);
//
//            editor.commit();
            /**/
            // Insert new book

            Intent i = getIntent();

            i.putExtra(BookInterface.title, newTitle);
            i.putExtra(BookInterface.chapter, newChapter);
            i.putExtra(BookInterface.page, newPage);

            setResult(ListBookmarksActivity.ADD_BOOKMARK, i);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = getIntent();
        setResult(ListBookmarksActivity.BACK_OPTION, i);
        finish();
    }

}

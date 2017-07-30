package shruikan.bookmarks.com.bookmarks.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import database.Book;
import database.BookDao;
import database.DaoHelper;
import database.DaoSession;
import keys.BookInterface;
import shruikan.bookmarks.com.bookmarks.R;

public class EditBookmarkActivity extends AppCompatActivity {

    private Query<Book> booksQuery;
    private BookDao bookDao;
    private List<Book> books;
    private Long id;

    @BindView(R.id.add_bkm_title)
    EditText addBkmTitle;
    @BindView(R.id.add_bkm_chapter)
    EditText addBkmChapter;
    @BindView(R.id.add_bkm_page)
    EditText addBkmPage;
    @BindView(R.id.edit_bkm_edit)
    Button editBkmEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bookmark);
        ButterKnife.bind(this);

        setTitle(getResources().getString(R.string.edit_bookmark_title));

        DaoSession daoSession = DaoHelper.getInstance(getApplicationContext())
                .getDaoSession();
        bookDao = daoSession.getBookDao();

        Intent i = getIntent();
        id = i.getLongExtra(BookInterface.id, -1);

        booksQuery = bookDao.queryBuilder()
                .where(BookDao.Properties.Id.eq(id))
                .build();

        books = booksQuery.list();

        if( books.size() == 1 ) {
            addBkmTitle.setText( books.get(0).getTitle() );
            addBkmChapter.setText( books.get(0).getChapter() );
            addBkmPage.setText( books.get(0).getPage() );
        }
    }

    @OnClick(R.id.edit_bkm_edit)
    public void onViewClicked() {
        String title = addBkmTitle.getText().toString();
        String chapter = addBkmChapter.getText().toString();
        String page = addBkmPage.getText().toString();

        // TODO Translations
        String msg = getResources().getString(R.string.add_bookmark_empty_field_msg);

        if (title == "" || page == "") {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
        else {
            Intent i = getIntent();
            i.putExtra(BookInterface.id, id);
            i.putExtra(BookInterface.title, title);
            i.putExtra(BookInterface.chapter, chapter);
            i.putExtra(BookInterface.page, page);

            setResult(ListBookmarksActivity.EDIT_BOOKMARK, i);
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

package shruikan.bookmarks.com.bookmarks.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.greendao.query.Query;

import java.util.List;
import adapters.BooksAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import database.Book;
import database.BookDao;
import database.DaoHelper;
import database.DaoSession;
import keys.BookInterface;
import preferences.PreferencesActivity;
import preferences.PreferencesInterface;
import shruikan.bookmarks.com.bookmarks.R;

public class ListBookmarksActivity extends AppCompatActivity {
    public static final int BACK_OPTION = 0;
    public static final int ADD_BOOKMARK = 1;
    public static final int EDIT_BOOKMARK = 2;
    public static final int SETTINGS = 3;

    private BookDao bookDao;
    private Context context;
    private Query<Book> booksQuery;
    private BooksAdapter booksAdapter;

    private String[] drawerItems;
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    private CharSequence title;

    @BindView(R.id.bookmarks_list)
    RecyclerView bookmarksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bookmarks);
        ButterKnife.bind(this);

        setTitle(getResources().getString(R.string.list_bookmarks_toolbar_title));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListBookmarksActivity.this, AddBookmarkActivity.class);
                startActivityForResult(i, ADD_BOOKMARK);
            }
        });

        context = getApplicationContext();
        setUpViews();

        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // get the bookDao
        DaoSession daoSession = DaoHelper.getInstance(context).getDaoSession();
        bookDao = daoSession.getBookDao();

        // query all books, sorted a-z by their title
        booksQuery = bookDao.queryBuilder()
            .orderAsc(BookDao.Properties.Title)
            .build();
        updateBooks();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Book book;
        switch(resultCode) {
            case ListBookmarksActivity.BACK_OPTION:
                Log.d("BACK", "Back button pressed.");
                break;
            case ListBookmarksActivity.ADD_BOOKMARK:
                book = new Book();

                book.setTitle(data.getStringExtra(BookInterface.title));
                book.setChapter(data.getStringExtra(BookInterface.chapter));
                book.setPage(data.getStringExtra(BookInterface.page));

                bookDao.insert(book);
                Log.d("DaoExample", "Inserted new note, ID: " + book.getId());

                updateBooks();
                break;
            case ListBookmarksActivity.EDIT_BOOKMARK:
                book = new Book();

                Long id = data.getLongExtra(BookInterface.id, -1);

                book.setId(id);
                book.setTitle(data.getStringExtra(BookInterface.title));
                book.setChapter(data.getStringExtra(BookInterface.chapter));
                book.setPage(data.getStringExtra(BookInterface.page));

                bookDao.update(book);
                Log.d("DaoExample", "Update note with ID: " + book.getId());

                updateBooks();
                break;
            case ListBookmarksActivity.SETTINGS:
                booksQuery = bookDao.queryBuilder()
                        .orderAsc(BookDao.Properties.Title)
                        .build();

                setUpViews();

                updateBooks();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bookmark, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent i;
        switch (item.getItemId()) {
            case R.id.action_about:
                i = new Intent(ListBookmarksActivity.this, AboutActivity.class);
                startActivity(i);
                return true;
            case R.id.action_settings:
                i = new Intent(ListBookmarksActivity.this, PreferencesActivity.class);
                startActivityForResult(i, SETTINGS);
                return true;
            case R.id.action_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
/*
        if(resultCode == ListBookmarksActivity.BACK_OPTION) {
        }
        else if(resultCode == ListBookmarksActivity.ADD_BOOKMARK) {
            Book book = new Book();
            book.setTitle(data.getStringExtra(BookInterface.title));
            book.setChapter(data.getStringExtra(BookInterface.chapter));
            book.setPage(data.getStringExtra(BookInterface.page));

            bookDao.insert(book);
            Log.d("DaoExample", "Inserted new note, ID: " + book.getId());

            updateBooks();

        }
        else if()
*/
    private Book updateBook(Intent data) {
        Book book = new Book();

        Long id = data.getLongExtra(BookInterface.id, -1);

        book.setId(id);
        book.setTitle(data.getStringExtra(BookInterface.title));
        book.setChapter(data.getStringExtra(BookInterface.chapter));
        book.setPage(data.getStringExtra(BookInterface.page));

        return book;
    }

    private Book createBook(Intent data) {
        Book book = new Book();

        book.setTitle(data.getStringExtra(BookInterface.title));
        book.setChapter(data.getStringExtra(BookInterface.chapter));
        book.setPage(data.getStringExtra(BookInterface.page));

        return book;
    }

    private void setUpViews() {
        // Navigation drawer
        drawerItems = getResources().getStringArray(R.array.drawer_options);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.drawer_left);

        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawerItems));

        // Bookmarks list
        bookmarksList.setHasFixedSize(true);
        bookmarksList.setLayoutManager( new LinearLayoutManager(this) );

        booksAdapter = new BooksAdapter(ListBookmarksActivity.this, bookClickListener);
        bookmarksList.setAdapter(booksAdapter);
    }

    private void updateBooks() {
        List<Book> books = booksQuery.list();
        booksAdapter.setBooks(books);
    }

    BooksAdapter.BookClickListener bookClickListener = new BooksAdapter.BookClickListener() {
        @Override
        public void onBookClick(int position) {
            Intent i = new Intent(ListBookmarksActivity.this, EditBookmarkActivity.class);

            Book book = booksAdapter.getBook(position);

            Long id = book.getId();
            i.putExtra(BookInterface.id, id);
            startActivityForResult(i, EDIT_BOOKMARK);
        }
    };

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Log.d("SelectItem", "Invoked SelectItem method");
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        getActionBar().setTitle(this.title);
    }
}

package shruikan.bookmarks.com.bookmarks.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import preferences.PreferencesInterface;
import shruikan.bookmarks.com.bookmarks.R;

public class BookmarkActivity extends AppCompatActivity {
    @BindView(R.id.bkm_title)
    TextView bkmTitle;
    @BindView(R.id.bkm_chapter)
    TextView bkmChapter;
    @BindView(R.id.bkm_page)
    TextView bkmPage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.bkm_title_label)
    TextView bkmTitleLabel;
    @BindView(R.id.bkm_chapter_label)
    TextView bkmChapterLabel;
    @BindView(R.id.bkm_add)
    Button bkmAdd;

    private SharedPreferences sharedPreferences;

    public static int READ_NEW_DATA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*if (initializePreferences() == false) {
            readFromPreferences();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bookmark, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    /**
     * @return true if the sharedpreferences is initializated, false otherwise.
     */
/*
    private boolean initializePreferences() {
        sharedPreferences = getSharedPreferences("bookmarks", Context.MODE_PRIVATE);

        if (sharedPreferences.contains(PreferencesInterface.initialize) == false) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(PreferencesInterface.initialize, true);
            editor.commit();
            return true;
        } else
            return false;
    }
*/

    @OnClick(R.id.bkm_add)
    public void onViewClicked() {
        Intent i = new Intent(BookmarkActivity.this, AddBookmarkActivity.class);
        startActivityForResult(i, READ_NEW_DATA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( resultCode == READ_NEW_DATA ) {
        }
    }
}

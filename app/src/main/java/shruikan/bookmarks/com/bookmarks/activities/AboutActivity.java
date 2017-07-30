package shruikan.bookmarks.com.bookmarks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import shruikan.bookmarks.com.bookmarks.R;
import utils.BookmarksVersion;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.abt_version)
    TextView abtVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        getVersion();
    }

    // Get the version of the app and show it in the proper field.
    private void getVersion() {
        BookmarksVersion version = new BookmarksVersion();
        abtVersion.setText(version.toString());
    }
}

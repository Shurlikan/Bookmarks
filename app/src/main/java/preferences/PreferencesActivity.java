package preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import shruikan.bookmarks.com.bookmarks.R;
import shruikan.bookmarks.com.bookmarks.activities.ListBookmarksActivity;

/**
 * Created by lorenzoantenucci on 10/07/2017.
 */

public class PreferencesActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace( android.R.id.content, new BookmarksPreferenceFragment() )
                .commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        SharedPreferences.Editor editor = prefs.edit();
    }

    public static class BookmarksPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(ListBookmarksActivity.SETTINGS);
        finish();
    }

}

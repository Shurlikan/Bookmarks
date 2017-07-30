package adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import database.Book;
import database.BookDao;
import database.DaoHelper;
import database.DaoSession;
import dialogs.DeleteDialog;
import preferences.PreferencesInterface;
import shruikan.bookmarks.com.bookmarks.R;
import shruikan.bookmarks.com.bookmarks.activities.ListBookmarksActivity;

/**
 * Created by lorenzoantenucci on 21/06/2017.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private List<Book> dataset;
    private BookClickListener clickListener;
    private CallbackInterface callback;
    private BookDao bookDao;
    private int titleColor;
    private int chapterColor;
    private int pageColor;


    public interface BookClickListener {
        void onBookClick(int position);
    }

    public interface CallbackInterface {
        void onDeleteSelection(int position);
    }

    public BooksAdapter(Context context, BookClickListener clickListener) {
        this.clickListener = clickListener;
        this.dataset = new ArrayList<Book>();

        try {
            callback = (CallbackInterface) context;
        }
        catch(ClassCastException ex) {
            Log.e("BooksAdapter", "Must implement the callback interface in Activity");
        }

        // I use the SharedPreferences here.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        titleColor = preferences.getInt(PreferencesInterface.titleColor, -1);
        chapterColor = preferences.getInt(PreferencesInterface.chapterColor, -1);
        pageColor = preferences.getInt(PreferencesInterface.pageColor, -1);
    }

    public Book getBook(int position) {
        return dataset.get(position);
    }

    public void setBooks(@NonNull List<Book> books) {
        dataset = books;
        notifyDataSetChanged();
    }

    // To inflate the item layout and create the holder
    @Override
    public BooksAdapter.BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() )
                .inflate(R.layout.item_bookmarks, parent, false);

        return new BookViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(BooksAdapter.BookViewHolder holder, final int position) {
        Book book = dataset.get(position);
        holder.title.setText( book.getTitle() );
        holder.title.setTextColor(titleColor);
        holder.chapter.setText( book.getChapter() );
        holder.chapter.setTextColor(chapterColor);
        holder.page.setText( book.getPage() );
        holder.page.setTextColor(pageColor);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Create the delete dialog, bind the two buttons,
                set the listeners and show the dialog.*/
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_delete);
                dialog.setTitle(v.getResources().getString(R.string.dlg_del_title));

                final Button yesButton = (Button) dialog.findViewById(R.id.dlg_btn_yes);
                yesButton.setBackgroundResource(R.color.colorDialogYes);
                final Button noButton = (Button) dialog.findViewById(R.id.dlg_btn_no);
                noButton.setBackgroundResource(R.color.colorDialogNo);

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yesButton.setBackgroundResource(R.color.colorDialogYesClicked);
                        Book book = dataset.get(position);
                        DaoSession daoSession = DaoHelper.getInstance( v.getContext() ).getDaoSession();
                        bookDao = daoSession.getBookDao();
                        bookDao.delete(book);
                        Query<Book> booksQuery = bookDao.queryBuilder()
                                .orderAsc(BookDao.Properties.Title)
                                .build();
                        List<Book> books = booksQuery.list();
                        setBooks(books);

                        dialog.dismiss();
                    }
                });

                noButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noButton.setBackgroundResource(R.color.colorDialogNo);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private BooksAdapter parent;
        public TextView title;
        public TextView chapter;
        public TextView page;
        public ImageButton delete;

        public BookViewHolder(View itemView, final BookClickListener clickListener) {
            super(itemView);

            // Bind between holder and Item View
            title = (TextView) itemView.findViewById(R.id.item_bkm_title);
            chapter = (TextView) itemView.findViewById(R.id.item_bkm_chapter);
            page = (TextView) itemView.findViewById(R.id.item_bkm_page);
            delete = (ImageButton) itemView.findViewById(R.id.item_bkm_del);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener != null)
                        clickListener.onBookClick(getAdapterPosition());
                }
            });
        }
    }
}
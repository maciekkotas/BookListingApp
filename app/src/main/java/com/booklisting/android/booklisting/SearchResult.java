package com.booklisting.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SearchResult extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int BOOKS_ID = 1;
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String BOOK_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";
    private BookAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private ProgressBar progressBar;
    private String newUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        String bookSearch = getIntent().getStringExtra("search");

        Log.e("Title: ", bookSearch);

        newUrl = BOOK_REQUEST_URL + bookSearch.replace(" ", "").toLowerCase() + "&maxResults=30";
        Log.e("New URL: ", newUrl);

        final ListView bookList = (ListView) findViewById(R.id.book_list_view);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookList.setEmptyView(mEmptyStateTextView);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookList.setAdapter(mAdapter);

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = mAdapter.getItem(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(book.getInfoLink()));
                startActivity(i);

            }

        });

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            android.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOKS_ID, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_connection);
            Log.e(LOG_TAG, "no connection");
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, newUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        progressBar.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_data);
        mAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        } else {
            Log.e(LOG_TAG, "No data found");

        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}

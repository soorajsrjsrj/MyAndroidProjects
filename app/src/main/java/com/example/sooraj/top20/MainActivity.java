package com.example.sooraj.top20;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>, SwipeRefreshLayout.OnRefreshListener {
    public static final String LOG_TAG = MainActivity.class.getName();

    private TextView mEmptyStateTextView;
    private static final int NEWS_LOADER_ID = 1;
    NewsAdapter adapter;
    SwipeRefreshLayout swipe;

    private static final String USGS_REQUEST_URL =
            "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=d6b34536cdba4c9c9415a159edbdfa6c";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create a fake list of earthquake locations.
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorAccent));



        // Find a reference to the {@link ListView} in the layout
        final ListView newslistView = (ListView) findViewById(R.id.list);
        //URL Getnews = QueryUtils.buildURL(USGS_REQUEST_URL);


       // List<News> newsasync = QueryUtils.fetchNews(USGS_REQUEST_URL);



        adapter = new NewsAdapter(MainActivity.this);

        newslistView.setAdapter(adapter);
        newslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent contentBrowser = new Intent("android.intent.action.VIEW", Uri.parse(adapter.getItem(position).getmUrl()));
                startActivity(contentBrowser);
            }
        });

        if (Utility.isNetworkAvailable(MainActivity.this)) {
            getSupportLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        if (Utility.isNetworkAvailable(MainActivity.this)) {
            getSupportLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            swipe.setRefreshing(false);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new QueryUtils(this);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        swipe.setRefreshing(false);
        if (null != data) {
            adapter.clear();
            adapter.addFeeds(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }
}
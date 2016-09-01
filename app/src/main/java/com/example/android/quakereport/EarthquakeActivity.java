/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String mUsgsUrl = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private static EarthquakeAdapter mAdapter;
    private static TextView mEmptyTextView;
    private static ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        /** Find a reference to the {@link ListView} in the layout*/
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        /** Create a new {@link EarthquakeAdapter} EarthquakeAdapter object to set on
         * ListView {@link ListView
         */
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        /** Set the adapter on the {@link ListView}
         * so the list can be populated in the user interface
         */
        if (earthquakeListView != null) {
            earthquakeListView.setAdapter(mAdapter);
        } else {
            Log.e(LOG_TAG, "earthquakeListView null");
        }

        // set a listener to the list view to listen for click events
        if (earthquakeListView != null) {
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    // uri for the url stored in current earthquake object
                    Earthquake currentEarthquake = mAdapter.getItem(position);

                    Uri urlUri = Uri.parse(currentEarthquake.getUrl());

                    // build an implicit intent to open the url in a web browser on the device
                    Intent implicitIntent = new Intent(Intent.ACTION_VIEW, urlUri);
                    if (implicitIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(implicitIntent);
                    } else {
                        Log.e(LOG_TAG, getString(R.string.no_browser));
                        Toast.makeText(EarthquakeActivity.this, getString(R.string.no_browser),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Log.e(LOG_TAG, "ListView instance null");
        }

        // find progress bar
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // set empty view on list view
        mEmptyTextView = (TextView) findViewById(R.id.empty_view);
        if (earthquakeListView != null) {
            earthquakeListView.setEmptyView(mEmptyTextView);
        }

        // initialize a loader to request mUsgsUrl only if there is network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(0, null, this);
        }
        // else display no internet connection message
        else {
            mEmptyTextView.setText(R.string.no_iternet_message);
            mProgressBar.setVisibility(View.GONE);
        }

    }

    /**
     * Function which updates the UI with earthquake data
     *
     * @param earthquakes earhtquake {@link Earthquake} data
     */
    private void updateUI(List<Earthquake> earthquakes) {
        // clear previous data in adapter
        mAdapter.clear();

        // add new earthquake data
        mAdapter.addAll(earthquakes);
    }

    /**
     * Loader callback onCreateLoader to create and return a EarthquakeLoader
     * {@link EarthquakeLoader}
     *
     * @param i id of loader
     * @param bundle bundle passed in initLoader of LoaderManager
     * @return EarthquakeLoader {@link EarthquakeLoader}
     */
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        return new EarthquakeLoader(EarthquakeActivity.this, mUsgsUrl);
    }

    /**
     * Loader callback onLoadFinished called with loader has finished loading
     *
     * @param loader      {@link Loader<List<Earthquake>>}
     * @param earthquakes {@link List<Earthquake>}
     */
    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {

        // hide progress bar
        mProgressBar.setVisibility(View.GONE);


        // set text for empty textView
        mEmptyTextView.setText(R.string.empty_view_string);

        if (earthquakes == null || earthquakes.isEmpty())
            return;

        // update the ui with earthquakes data
        updateUI(earthquakes);
    }

    /**
     * Loader callback onLoaderReset called with the data source is no longer valid and the
     * loader resets
     *
     * @param loader {@link Loader<List<Earthquake>>}
     */
    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        mAdapter.clear();
    }

}

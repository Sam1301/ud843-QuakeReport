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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String usgsUrl = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private static EarthquakeAdapter mAdapter;

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

        // fire off an async task to request usgsUrl
        new EarthquakeAsyncTask().execute(usgsUrl);

    }

    /**
     * Function which updates the UI with earthquake data
     *
     * @param earthquakes earhtquake {@link Earthquake} data
     */
    private void updateUI(final ArrayList<Earthquake> earthquakes) {
        // clear previous data in adapter
        mAdapter.clear();

        // add new earthquake data
        mAdapter.addAll(earthquakes);
    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {

        @Override
        protected ArrayList<Earthquake> doInBackground(String... urls) {
            // check if input urls is not null or empty string
            if (urls.length < 1 || urls[0] == null)
                return null;

            ArrayList<Earthquake> earthquakes = QueryUtils.fetchEarthquakesData(urls[0]);
            return earthquakes;
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
            if (earthquakes == null || earthquakes.isEmpty())
                return;

            // update the ui with earthquakes data
            updateUI(earthquakes);
        }
    }
}

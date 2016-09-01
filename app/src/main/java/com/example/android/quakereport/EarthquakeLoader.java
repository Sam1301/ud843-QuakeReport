package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Implementation of AsyncTaskLoader {@link AsyncTaskLoader} which queries the USQS api for latest
 * 10 earthquakes above a magnitude of 6
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<Earthquake> loadInBackground() {
        // check if url is not null or empty string
        if (mUrl == null || mUrl.isEmpty())
            return null;

        // fetch and return earthquake data
        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakesData(mUrl);
        return earthquakes;

    }

    @Override
    protected void onStartLoading() {
        // start loading process
        forceLoad();

        super.onStartLoading();
    }
}

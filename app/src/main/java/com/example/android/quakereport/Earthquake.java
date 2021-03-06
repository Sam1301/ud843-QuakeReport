package com.example.android.quakereport;

/**
 * {@link Earthquake} clas to store data for each earthquake like
 * location {@link Earthquake#mPlace}, date {@link Earthquake#mDateInMillis} and
 * magnitude {@link Earthquake#mMagnitude}
 */
public class Earthquake {
    // to store place
    private String mPlace;

    // to store date
    private long mDateInMillis;

    // to store magnitude
    private double mMagnitude;

    // to store url of usgs website for the earthquake
    private String mUrl;

    /**
     * Constructor to initialize fields
     * @param magnitude magnitude {@link Earthquake#mMagnitude}
     * @param place place {@link Earthquake#mPlace}
     * @param dateInMillis date {@link Earthquake#mDateInMillis}
     */
    public Earthquake(double magnitude, String place, long dateInMillis, String url) {
        mMagnitude = magnitude;
        mPlace = place;
        mDateInMillis = dateInMillis;
        mUrl = url;
    }

    /**
     * Returns the magnitude of earthquake
     * @return magnitude {@link Earthquake#mMagnitude}
     */
    public double getMagnitude() {
        return mMagnitude;
    }


    /**
     * Returns the date of occurrence of earthquake
     * @return date {@link Earthquake#mDateInMillis}
     */
    public long getDateInMillis() {
        return mDateInMillis;
    }

    /**
     * Returns the location of earthquake
     * @return location {@link Earthquake#mPlace}
     */
    public String getPlace() {
        return mPlace;
    }

    /**
     * Returns the url of earthquake on usgs site
     *
     * @return url {@link Earthquake#mUrl}
     */
    public String getUrl() {
        return mUrl;
    }
}

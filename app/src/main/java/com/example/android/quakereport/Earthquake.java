package com.example.android.quakereport;

/**
 * {@link Earthquake} clas to store data for each earthquake like
 * location {@link Earthquake#mPlace}, date {@link Earthquake#mDate} and
 * magnitude {@link Earthquake#mMagnitude}
 */
public class Earthquake {
    // to store place
    private String mPlace;

    // to store date
    private String mDate;

    // to store magnitude
    private float mMagnitude;

    /**
     * Constructor to initialize fields
     * @param magnitude magnitude {@link Earthquake#mMagnitude}
     * @param place place {@link Earthquake#mPlace}
     * @param date date {@link Earthquake#mDate}
     */
    public Earthquake(float magnitude, String place, String date) {
        mMagnitude = magnitude;
        mPlace = place;
        mDate = date;
    }

    /**
     * Returns the magnitude of earthquake
     * @return magnitude {@link Earthquake#mMagnitude}
     */
    public float getMagnitude() {
        return mMagnitude;
    }


    /**
     * Returns the date of occurrence of earthquake
     * @return date {@link Earthquake#mDate}
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Returns the location of earthquake
     * @return location {@link Earthquake#mPlace}
     */
    public String getPlace() {
        return mPlace;
    }
}

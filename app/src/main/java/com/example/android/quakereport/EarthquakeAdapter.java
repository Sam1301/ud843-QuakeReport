package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Custom adapter to list earthquake data in list view {@link android.widget.ListView}
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    /**
     * Context is used to inflate the layout file and the list is data we want to populate.
     * @param context {@link Context}
     * @param earthquakes array list of earthquake {@link Earthquake} objects
     */
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    /**
     * Provides a view for the ListView {@link android.widget.ListView}
     * @param position position of data in the list of data that should be displayed
     * @param convertView recycled view to populate with data
     * @param parent parent ViewGroup used for inflation
     * @return the view for the given position in the ListView {@link android.widget.ListView}
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // check if existing view is being used, otherwise inflate new view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // get earthquake object at current position
        Earthquake currentEarthquake = getItem(position);

        // set value for View with id magnitude in list_item.xml for the convertView
        TextView magnitudeTextView = (TextView) convertView.findViewById(R.id.magnitude);
        // format the double value to display magnitude only up to 1 decimal place
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        magnitudeTextView.setText(decimalFormat.format(currentEarthquake.getMagnitude()));

        // set color of magnitude TextView background depending on its value
        GradientDrawable gradientDrawable = (GradientDrawable) magnitudeTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        gradientDrawable.setColor(magnitudeColor);

        // logic for splitting primary and secondary text
        String place = currentEarthquake.getPlace();
        String primary = place;
        String secondary = "Near the";
        if (Character.isDigit(place.charAt(0))) {
            int index = place.indexOf(" of ");
            secondary = place.substring(0, index) + " of";
            primary = place.substring(index + 4);
        }

        // set value for primary place
        TextView primaryPlace = (TextView) convertView.findViewById(R.id.primary_location);
        primaryPlace.setText(primary);

        // set value for secondary place
        TextView secondaryPlace = (TextView) convertView.findViewById(R.id.location_offset);
        secondaryPlace.setText(secondary);

        // set value for View with id date in list_item.xml for the convertView
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date);
        Date date = new Date(currentEarthquake.getDateInMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        dateTextView.setText(dateFormat.format(date));

        // set value for View with id time in list_item.xml for the convertView
        TextView timeTextView = (TextView) convertView.findViewById(R.id.time);
        dateFormat.applyPattern("h:mm a");
        timeTextView.setText(dateFormat.format(date));

        // return the updated view for the current position
        return convertView;
    }

    private int getMagnitudeColor(double magnitude) {
        // convert the decimal values to nearest integer values and return color according to the
        // integer
        switch ((int) Math.floor(magnitude)) {
            case 0:
            case 1:
                return ContextCompat.getColor(getContext(), R.color.magnitude1);
            case 2:
                return ContextCompat.getColor(getContext(), R.color.magnitude2);
            case 3:
                return ContextCompat.getColor(getContext(), R.color.magnitude3);
            case 4:
                return ContextCompat.getColor(getContext(), R.color.magnitude4);
            case 5:
                return ContextCompat.getColor(getContext(), R.color.magnitude5);
            case 6:
                return ContextCompat.getColor(getContext(), R.color.magnitude6);
            case 7:
                return ContextCompat.getColor(getContext(), R.color.magnitude7);
            case 8:
                return ContextCompat.getColor(getContext(), R.color.magnitude8);
            case 9:
                return ContextCompat.getColor(getContext(), R.color.magnitude9);
            default:
                return ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }
    }
}

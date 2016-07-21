package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.Date;
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
        magnitudeTextView.setText(String.format(
                getContext().getString(R.string.magnitude_string_format),
                currentEarthquake.getMagnitude()));

        // logic for splitting primary and secondary text
        String place = currentEarthquake.getPlace();
        String primary = place;
        String secondary = "Near to";
        if (Character.isDigit(place.charAt(0))) {
            int index = place.indexOf(" of ");
            secondary = place.substring(0, index);
            primary = place.substring(index + 4);
        }

        // set value for primary place
        TextView primaryPlace = (TextView) convertView.findViewById(R.id.primary_place);
        primaryPlace.setText(primary);

        // set value for secondary place
        TextView secondaryPlace = (TextView) convertView.findViewById(R.id.secondary_place);
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
}

package com.spitfireathlete.nidhi.nytsearch;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by nidhikulkarni on 2/9/16.
 */
public class SettingsFragment extends DialogFragment {

    public static class Filters {
        public String newsDesk;
        public boolean sortNewestFirst;
        public Calendar beginDate;

        public Filters() {
            // defaults
            sortNewestFirst = true;
            newsDesk = "";

            // default begin date is one year ago
            beginDate = new GregorianCalendar();
            Calendar now = Calendar.getInstance();
            beginDate.set(now.get(Calendar.YEAR) - 1, now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        }
    }


    private AutoCompleteTextView newsDeskTextView;

    private TextView newsDeskSelections;

    private RadioGroup sortOrder;

    private DatePicker beginDatePicker;

    private Filters currentFilters;

    public SettingsFragment () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getCurrentFilters(); // loads persisted filters, if any

        newsDeskSelections  = (TextView) view.findViewById(R.id.tvNewsDeskSelections);
        newsDeskSelections.setText(currentFilters.newsDesk);


        newsDeskTextView = (AutoCompleteTextView) view.findViewById(R.id.atvNewsDesk);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.newsdesk_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        newsDeskTextView.setAdapter(adapter);

        newsDeskTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CharSequence value = (CharSequence) parent.getItemAtPosition(position);
                String newValue = newsDeskSelections.getText().toString() + "\"" + value.toString() + "\" " ;
                newsDeskSelections.setText(newValue);
                newsDeskTextView.setText("");
            }

        });

        sortOrder = (RadioGroup) view.findViewById(R.id.rgSortOrder);
        if (currentFilters.sortNewestFirst) {
            sortOrder.check(R.id.rbNewest);
        } else {
            sortOrder.check(R.id.rbOldest);
        }


        beginDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
        beginDatePicker.updateDate(
                currentFilters.beginDate.get(Calendar.YEAR),
                currentFilters.beginDate.get(Calendar.MONTH),
                currentFilters.beginDate.get(Calendar.DAY_OF_MONTH));

        getDialog().setTitle("Search Filters");

    }

    public Filters getCurrentFilters() {

        if (currentFilters == null) {
            currentFilters = new Filters();
            loadFilters();
        }

        return currentFilters;
    }

    public void applyFilters() {

        getCurrentFilters();

        currentFilters.newsDesk = newsDeskSelections.getText().toString();
        currentFilters.sortNewestFirst = sortOrder.getCheckedRadioButtonId() == R.id.rbNewest;
        currentFilters.beginDate = new GregorianCalendar();
        currentFilters.beginDate.set(beginDatePicker.getYear(), beginDatePicker.getMonth(), beginDatePicker.getDayOfMonth());

        persistFilters();
    }

    public void clearFilters() {
        currentFilters = new Filters();
        persistFilters();
    }

    private void persistFilters() {
        SharedPreferences defaults = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = defaults.edit();

        getCurrentFilters();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        editor.putString("newsDesk", currentFilters.newsDesk);
        editor.putBoolean("sortNewestFirst", currentFilters.sortNewestFirst);

        if (currentFilters.beginDate != null) {
            editor.putLong("beginDate", currentFilters.beginDate.getTimeInMillis());
        }

        editor.commit();

    }

    private void loadFilters() {
        SharedPreferences defaults = PreferenceManager.getDefaultSharedPreferences(getContext());

        currentFilters.newsDesk = defaults.getString("newsDesk", "");
        currentFilters.sortNewestFirst = defaults.getBoolean("sortNewestFirst", true);
        long time = defaults.getLong("beginDate", -1);
        if (time > 0) {
            Calendar c = new GregorianCalendar();
            c.setTimeInMillis(time);
            currentFilters.beginDate = c;
        }
    }


}

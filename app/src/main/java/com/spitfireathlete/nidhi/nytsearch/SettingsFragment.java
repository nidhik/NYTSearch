package com.spitfireathlete.nidhi.nytsearch;

import android.os.Bundle;
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
            beginDate = null;
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

        newsDeskSelections  = (TextView) view.findViewById(R.id.tvNewsDeskSelections);
        newsDeskSelections.setText("");


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
        sortOrder.check(R.id.rbNewest);

        beginDatePicker = (DatePicker) view.findViewById(R.id.datePicker);


        getDialog().setTitle("Search Filters");

    }

    public Filters getFilters() {
        return currentFilters;
    }

    public void applyFilters() {
        if (currentFilters == null) {
            currentFilters = new Filters();
        }

        currentFilters.newsDesk = newsDeskSelections.getText().toString();
        currentFilters.sortNewestFirst = sortOrder.getCheckedRadioButtonId() == R.id.rbNewest;
        currentFilters.beginDate = new GregorianCalendar();
        currentFilters.beginDate.set(beginDatePicker.getYear(), beginDatePicker.getMonth(), beginDatePicker.getDayOfMonth());
    }

    public void clearFilters() {
        currentFilters = new Filters();
    }



}

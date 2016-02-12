package com.spitfireathlete.nidhi.nytsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by nidhikulkarni on 2/9/16.
 */
public class SettingsFragment extends DialogFragment {

    public static class Filters {
        public CharSequence newsDesk;
        public boolean sortNewestFirst;
        public Calendar beginDate;
    }

    private Spinner newsDeskSpinner;

    private RadioGroup sortOrder;

    private DatePicker beginDatePicker;


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
        newsDeskSpinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.newsdesk_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newsDeskSpinner.setAdapter(adapter);

        sortOrder = (RadioGroup) view.findViewById(R.id.rgSortOrder);
        sortOrder.check(R.id.rbNewest);

        beginDatePicker = (DatePicker) view.findViewById(R.id.datePicker);

        getDialog().setTitle("Search Filters");

    }

    public Filters getFilters() {
        Filters f = new Filters();
        f.newsDesk = (CharSequence) newsDeskSpinner.getSelectedItem();
        f.sortNewestFirst = sortOrder.getCheckedRadioButtonId() == R.id.rbNewest;
        f.beginDate = new GregorianCalendar();
        f.beginDate.set(beginDatePicker.getYear(), beginDatePicker.getMonth(), beginDatePicker.getDayOfMonth());
        return f;
    }




}

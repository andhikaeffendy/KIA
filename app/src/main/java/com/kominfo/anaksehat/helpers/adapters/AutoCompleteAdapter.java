package com.kominfo.anaksehat.helpers.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.models.Mother;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AutoCompleteAdapter extends ArrayAdapter {
    private Context context;
    private int resourceId;
    private List<Mother> items, tempItems, suggestions;

    public AutoCompleteAdapter(@NonNull Context context, int resourceId, List<Mother> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            Mother mother = getItem(position);
            TextView name = (TextView) view.findViewById(R.id.autocompletemotherName);
            String date = "";
            if (mother.getBirth_date() != null) {
                date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).format(mother.getBirth_date());
            } else {
                date = "";
            }
            name.setText(mother.getName() + "("+date+")");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Nullable
    @Override
    public Mother getItem(int position) {
        return items.get(position);
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return fruitFilter;
    }
    private Filter fruitFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Mother mother = (Mother) resultValue;
            return mother.getName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (Mother mother: tempItems) {
                    if (mother.getName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(mother);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<Mother> tempValues = (ArrayList<Mother>) filterResults.values;
            if (filterResults.count > 0) {
                clear();
                for (Mother motherObj : tempValues) {
                    add(motherObj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}

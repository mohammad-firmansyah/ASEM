package ptpn12.amanat.asem.utils;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    private List<String> originalList; // The original unfiltered list of strings
    private List<String> filteredList; // The filtered list of strings
    private Filter filter;

    public CustomAdapter(Context context, List<String> list) {
        super(context, 0, list);
        originalList = list;
        filteredList = new ArrayList<>(list);
        filter = new CustomFilter();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<String> filteredValues = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                // Return the original unfiltered list
                filteredValues.addAll(originalList);
            } else {
                // Filter the list based on the constraint
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String value : originalList) {
                    if (value.toLowerCase().contains(filterPattern)) {
                        filteredValues.add(value);
                    }
                }
            }

            results.values = filteredValues;
            results.count = filteredValues.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Update the filtered list and notify the adapter of the changes
            filteredList.clear();
            filteredList.addAll((List<String>) results.values);
            notifyDataSetChanged();
        }
    }
}
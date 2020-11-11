package com.kominfo.anaksehat.helpers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.models.ChildHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emrekabir on 25/07/18.
 */

public class ChildHistoriesAdapter extends RecyclerView.Adapter<ChildHistoriesAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<ChildHistory> dataList;
    private List<ChildHistory> dataListFiltered;
    private AdapterListener<ChildHistory> listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView left_icon, right_icon;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            left_icon = view.findViewById(R.id.data_icon_left);
            right_icon = view.findViewById(R.id.data_icon_right);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected product in callback
                    if(listener!=null)
                        listener.onItemSelected(dataListFiltered.get(getAdapterPosition()));
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // send selected product in callback
                    if(listener!=null)
                        listener.onItemLongSelected(dataListFiltered.get(getAdapterPosition()));
                    return true;
                }
            });
        }
    }


    public ChildHistoriesAdapter(Context context, List<ChildHistory> dataList, AdapterListener<ChildHistory> listener) {
        this.context = context;
        this.listener = listener;
        this.dataList = dataList;
        this.dataListFiltered = dataList;
    }

    public void setData(List<ChildHistory> dataList){
        this.dataList = dataList;
        this.dataListFiltered = dataList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ChildHistory data = dataListFiltered.get(position);
        holder.name.setText(DateHelper.getDateWithNameDay(data.getHistory_date()));
        if((position+1)%2==1){
            holder.left_icon.setVisibility(View.VISIBLE);
            holder.left_icon.setImageResource(R.drawable.item_history);
        } else {
            holder.right_icon.setVisibility(View.VISIBLE);
            holder.right_icon.setImageResource(R.drawable.item_history);
        }
    }

    @Override
    public int getItemCount() {
        return dataListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataListFiltered = dataList;
                } else {
                    List<ChildHistory> filteredList = new ArrayList<>();
                    for (ChildHistory row : dataList) {

                        if (row.getHistory_date().toString().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    dataListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataListFiltered = (ArrayList<ChildHistory>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

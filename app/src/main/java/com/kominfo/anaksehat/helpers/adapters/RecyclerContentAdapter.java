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

import com.squareup.picasso.Picasso;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.helpers.apihelper.UtilsApi;
import com.kominfo.anaksehat.models.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emrekabir on 25/07/18.
 */

public class RecyclerContentAdapter extends RecyclerView.Adapter<RecyclerContentAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<Content> dataList;
    private List<Content> dataListFiltered;
    private AdapterListener<Content> listener;
    private Picasso picasso;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, summary;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.picture);
            title = view.findViewById(R.id.title);
            summary = view.findViewById(R.id.summary);

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


    public RecyclerContentAdapter(Context context, List<Content> dataList, AdapterListener<Content> listener) {
        this.context = context;
        this.listener = listener;
        this.dataList = dataList;
        this.dataListFiltered = dataList;
        picasso = Picasso.get();
    }

    public void setData(List<Content> dataList){
        this.dataList = dataList;
        this.dataListFiltered = dataList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_data_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Content data = dataListFiltered.get(position);
        picasso.load(UtilsApi.BASE_URL+data.getImage_url())
//                .fit()
//                .transform(new CropSquareTransformation())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(holder.imageView);
        holder.summary.setText(data.getSummary());
        holder.title.setText(data.getTitle());
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
                    List<Content> filteredList = new ArrayList<>();
                    for (Content row : dataList) {

                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
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
                dataListFiltered = (ArrayList<Content>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

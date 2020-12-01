package com.kominfo.anaksehat.helpers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.models.Child;
import com.kominfo.anaksehat.models.Mother;
import com.kominfo.anaksehat.models.PemeriksaanBayi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BabyAdapter extends RecyclerView.Adapter<BabyAdapter.MyViewHolder> {
    private Context context;
    private List<PemeriksaanBayi> dataList;
    private AdapterListener<PemeriksaanBayi> listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        private ImageView left_icon, right_icon;

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
                        listener.onItemSelected(dataList.get(getAdapterPosition()));
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // send selected product in callback
                    if(listener!=null)
                        listener.onItemLongSelected(dataList.get(getAdapterPosition()));
                    return true;
                }
            });
        }
    }

    public BabyAdapter(Context context, List<PemeriksaanBayi> dataList, AdapterListener<PemeriksaanBayi> listener) {
        this.context = context;
        this.listener = listener;
        this.dataList = dataList;
    }

    public void setData(List<PemeriksaanBayi> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PemeriksaanBayi data = dataList.get(position);
        String date = "";
        if (data.getHistory_date() != null) {
            date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).format(data.getHistory_date());
        } else {
            date = "";
        }
        holder.name.setText(date);
        if((position+1)%2==1){
            holder.left_icon.setVisibility(View.VISIBLE);
            holder.left_icon.setImageResource(R.drawable.item_pregnancy);
        } else {
            holder.right_icon.setVisibility(View.VISIBLE);
            holder.right_icon.setImageResource(R.drawable.item_pregnancy);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}

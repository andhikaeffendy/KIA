package com.kominfo.anaksehat.helpers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.models.Checkbox;

import java.util.ArrayList;
import java.util.List;

public class BabyCheckboxAdapter extends RecyclerView.Adapter<BabyCheckboxAdapter.MyViewHolder> {
    private List<Checkbox> _isChecked = new ArrayList<Checkbox>();

    private Context context;
    private List<Checkbox> dataList;
    private OnItemCheckListener<Checkbox, String> onItemCheckListener;
    private String type;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CheckBox name;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cb_nb_baby);
        }
    }

    public BabyCheckboxAdapter(Context context, List<Checkbox> dataList, OnItemCheckListener<Checkbox, String> onItemCheckListener, String type) {
        this.context = context;
        this.dataList = dataList;
        this.onItemCheckListener = onItemCheckListener;
        this.type = type;
    }

    public void setData(List<Checkbox> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BabyCheckboxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_rv_checkbox, parent, false);

        return new BabyCheckboxAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BabyCheckboxAdapter.MyViewHolder holder, final int position) {
        final Checkbox data = dataList.get(position);
        holder.name.setText(data.getName());
        if (_isChecked.contains(position)){
            holder.name.setChecked(true);
        } else {
            holder.name.setChecked(false);
        }

        holder.name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    // KONDISI PADA SAAT CEKLIS
                    onItemCheckListener.onItemCheck(dataList.get(position), type);
                } else {
                    // KONDISI PADA SAAT CEKLIS DIHILANGKAN
                    onItemCheckListener.onItemUncheck(dataList.get(position), type);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

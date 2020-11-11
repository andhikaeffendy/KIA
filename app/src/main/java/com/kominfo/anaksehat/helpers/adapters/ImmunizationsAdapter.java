package com.kominfo.anaksehat.helpers.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.helpers.DateHelper;
import com.kominfo.anaksehat.models.Immunization;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emrekabir on 25/07/18.
 */

public class ImmunizationsAdapter extends RecyclerView.Adapter<ImmunizationsAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<Immunization> dataList;
    private List<Immunization> dataListFiltered;
    private AdapterListener<Immunization> listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView range_date;
        public ImageView icon;
        public RelativeLayout layer, backtext, newicon;
        public View line;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            range_date = view.findViewById(R.id.range_date);
            icon = view.findViewById(R.id.immunization_icon);
            layer = view.findViewById(R.id.layer);
            backtext = view.findViewById(R.id.backtext);
            newicon = view.findViewById(R.id.newicon);
            line = view.findViewById(R.id.line);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected product in callback
                    Immunization data = dataListFiltered.get(getAdapterPosition());
                    if(data.getColor().compareToIgnoreCase("green")==0) {
                        showPopup(view, data);
                    } else
                        if(listener!=null)
                            listener.onItemSelected(data);
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


    public ImmunizationsAdapter(Context context, List<Immunization> dataList, AdapterListener<Immunization> listener) {
        this.context = context;
        this.listener = listener;
        this.dataList = dataList;
        this.dataListFiltered = dataList;
    }

    public void setData(List<Immunization> dataList){
        this.dataList = dataList;
        this.dataListFiltered = dataList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.immunization_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Immunization data = dataListFiltered.get(position);
        holder.name.setText(data.getVaccine_name().toUpperCase());
        holder.range_date.setText(DateHelper.getRangeDate(data.getStart_date(), data.getEnd_date()));
        holder.icon.setVisibility(View.VISIBLE);
        holder.newicon.setVisibility(View.GONE);
        holder.line.setVisibility(View.GONE);
        switch (data.getColor()){
            case "red":
                holder.layer.setBackgroundResource(R.color.transparent);
                holder.backtext.setBackgroundResource(R.drawable.rounded_red);
                holder.icon.setImageResource(R.drawable.newalert);
                holder.name.setTextColor(Color.parseColor("#ffffff"));
                holder.range_date.setTextColor(Color.parseColor("#ffffff"));
                break;
            case "grey":
                holder.layer.setBackgroundResource(R.color.grey);
                holder.backtext.setBackgroundResource(R.color.grey);
//                holder.icon.setImageResource(R.drawable.newgrey);
                holder.icon.setVisibility(View.GONE);
                holder.newicon.setVisibility(View.VISIBLE);
                holder.name.setTextColor(Color.parseColor("#737373"));
                holder.range_date.setTextColor(Color.parseColor("#737373"));
                if(position+1!=getItemCount())
                    holder.line.setVisibility(View.VISIBLE);
                break;
            case "netral":
                holder.layer.setBackgroundResource(R.color.transparent);
                holder.backtext.setBackgroundResource(R.drawable.rounded_white);
                holder.icon.setImageResource(R.drawable.newimmun);
                holder.name.setTextColor(Color.parseColor("#24164d"));
                holder.range_date.setTextColor(Color.parseColor("#24164d"));
                break;
            case "green":
                holder.layer.setBackgroundResource(R.color.transparent);
                holder.backtext.setBackgroundResource(R.drawable.rounded_green);
                holder.icon.setImageResource(R.drawable.newcheck);
                holder.name.setTextColor(Color.parseColor("#ffffff"));
                holder.range_date.setTextColor(Color.parseColor("#ffffff"));
                break;
            default:
                break;
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
                    List<Immunization> filteredList = new ArrayList<>();
                    for (Immunization row : dataList) {

                        if (row.getVaccine_name().toLowerCase().contains(charString.toLowerCase())) {
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
                dataListFiltered = (ArrayList<Immunization>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private void showPopup(View parent, Immunization data){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.textview, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView textView = popupWindow.getContentView().findViewById(R.id.text);
        textView.setText("Tanggal Imunisasi :"+DateHelper.getDateWithNameDay(data.getImmunization_date()));
        popupWindow.showAsDropDown(parent, 120,-60);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}

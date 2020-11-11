package com.kominfo.anaksehat.helpers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.helpers.apihelper.UtilsApi;
import com.kominfo.anaksehat.models.Infographic;

import java.util.List;

public class InfographicsAdapter extends BaseAdapter {
    private Context mContext;
    private List<Infographic> infographics;
    private Picasso picasso;

    public InfographicsAdapter(Context c, List<Infographic> infographics, Picasso picasso) {
        mContext = c;
        this.infographics = infographics;
        this.picasso = picasso;
    }

    public int getCount() {
        return infographics.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        TextView title;
        Infographic infographic = infographics.get(position);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.infografis_item, null);
        }

        imageView = convertView.findViewById(R.id.picture);
        title = convertView.findViewById(R.id.title);

        picasso.load(UtilsApi.BASE_URL+infographic.getImage_url())
//                .fit()
//                .transform(new CropSquareTransformation())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(imageView);
        title.setText(infographic.getTitle());

        return convertView;
    }

}

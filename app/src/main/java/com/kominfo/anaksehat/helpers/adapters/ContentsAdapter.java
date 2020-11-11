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
import com.kominfo.anaksehat.models.Content;

import java.util.List;

public class ContentsAdapter extends BaseAdapter {
    private Context mContext;
    private List<Content> contents;
    private Picasso picasso;

    public ContentsAdapter(Context c, List<Content> contents, Picasso picasso) {
        mContext = c;
        this.contents = contents;
        this.picasso = picasso;
    }

    public int getCount() {
        return contents.size();
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
        TextView summary;
        TextView title;
        Content content = contents.get(position);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.grid_data_item, null);
        }

        imageView = convertView.findViewById(R.id.picture);
        title = convertView.findViewById(R.id.title);
        summary = convertView.findViewById(R.id.summary);

        picasso.load(UtilsApi.BASE_URL+content.getImage_url())
//                .fit()
//                .transform(new CropSquareTransformation())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(imageView);
        summary.setText(content.getSummary());
        title.setText(content.getTitle());

        return convertView;
    }

}

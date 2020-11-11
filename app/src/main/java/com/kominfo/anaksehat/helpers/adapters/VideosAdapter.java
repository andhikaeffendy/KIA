package com.kominfo.anaksehat.helpers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kominfo.anaksehat.helpers.apihelper.UtilsApi;
import com.squareup.picasso.Picasso;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.models.Video;

import java.util.List;

public class VideosAdapter extends BaseAdapter {
    private Context mContext;
    private List<Video> videos;
    private Picasso picasso;

    public VideosAdapter(Context c, List<Video> videos, Picasso picasso) {
        mContext = c;
        this.videos = videos;
        this.picasso = picasso;
    }

    public int getCount() {
        return videos.size();
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
        Video infographic = videos.get(position);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.video_item, null);
        }

        imageView = convertView.findViewById(R.id.picture);
        title = convertView.findViewById(R.id.title);

        picasso.load(UtilsApi.BASE_YOUTUBE_IMAGE_START+infographic.getYoutube_id()+UtilsApi.YOUTUBE_IMAGE_STANDARD)
//                .fit()
                .placeholder(R.drawable.background_video_detail)
                .error(R.drawable.background_video_detail)
                .into(imageView);
        title.setText(infographic.getTitle());

        return convertView;
    }

}

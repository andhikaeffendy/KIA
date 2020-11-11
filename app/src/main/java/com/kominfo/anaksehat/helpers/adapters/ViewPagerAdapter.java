package com.kominfo.anaksehat.helpers.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.kominfo.anaksehat.R;
import com.kominfo.anaksehat.helpers.CropSquareTransformation;
import com.kominfo.anaksehat.helpers.apihelper.UtilsApi;
import com.kominfo.anaksehat.models.Content;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Content> contents;
    private Picasso picasso;
    private AdapterListener<Content> listener;

    public ViewPagerAdapter(Context context, List<Content> contents, Picasso picasso,
                            AdapterListener<Content> listener) {
        this.context = context;
        this.contents = contents;
        this.picasso = picasso;
        this.listener = listener;
        if(contents.size()>5){
            this.contents = contents.subList(0,5);
        }
    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final Content content = contents.get(position);

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout_pos, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewPos);
        picasso.load(UtilsApi.BASE_URL+content.getImage_url())
//                .fit()
//                .transform(new CropSquareTransformation())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onItemSelected(content);

            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
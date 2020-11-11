package com.kominfo.anaksehat.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class BitmapDrawablePlaceHolder extends BitmapDrawable implements Target {

    protected Drawable drawable;
    protected Context context;
    protected TextView textView;

    public BitmapDrawablePlaceHolder(Context context, TextView textView){
        this.context = context;
        this.textView = textView;
    }

    @Override
    public void draw(final Canvas canvas) {
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, width, height);
        setBounds(0, 0, width, height);
        if (textView != null) {
            textView.setText(textView.getText());
        }
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        setDrawable(new BitmapDrawable(context.getResources(), bitmap));
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }

    public Drawable getDrawable() {
        return drawable;
    }
}

package com.kraftski.testproject;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    private ArrayList<Uri> mImageList;
    private Context mContext;

    public ImageAdapter(Context context) {
        mContext = context;
    }

    public void setImageList(ArrayList<Uri> imageList) {
        mImageList = imageList;
        notifyDataSetChanged();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.recycler_imageview, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(imageView );
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int position) {
        Uri uri = mImageList.get(position);
        Glide.with(mContext)
                .load(uri)
                .fitCenter()
                .into(imageViewHolder.mImageView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            imageViewHolder.mImageView.setColorFilter(Color.argb(position/(float)getItemCount(),
                    0, 0, 255));
        }
    }

    @Override
    public int getItemCount() {
        if (mImageList == null)
            return 0;
        return mImageList.size();
    }

    protected class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        public ImageViewHolder(ImageView imageView) {
            super(imageView);
            mImageView = imageView;
        }
    }
}
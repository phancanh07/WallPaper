package com.example.wallpaper.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.bumptech.glide.Glide;
import com.example.wallpaper.R;
import com.example.wallpaper.model.Photo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Photo> photos;


    public ViewPagerAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }


    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_pager, null);
        ImageView dowload = (ImageView) view.findViewById(R.id.image_item);
        Glide.with(context).load(photos.get(position).getUrlL()).into(dowload);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(@NonNull @NotNull Object object) {
        return super.getItemPosition(object);
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

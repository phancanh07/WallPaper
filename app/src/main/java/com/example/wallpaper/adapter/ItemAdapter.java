package com.example.wallpaper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wallpaper.databinding.ItemImageBinding;
import com.example.wallpaper.model.Photo;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemAdapterHolder> {
    private List<Photo> list;
    private Context context;

    public ItemAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Photo> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemImageBinding binding = ItemImageBinding.inflate(layoutInflater, parent, false);
        return new ItemAdapterHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapterHolder holder, int position) {
        final Photo photo = list.get(position);
        holder.itemView.viewDowload.setText(photo.getViews());
//        Glide.with(context).load(list.get(position).getUrlZ()).into(holder.itemView.imageView);
        if (photo.getUrlZ() != null) {
            Glide.with(context).load(list.get(position).getUrlZ()).into(holder.itemView.imageView);
        }
        holder.itemView.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ItemAdapterHolder extends RecyclerView.ViewHolder {

        ItemImageBinding itemView;

        public ItemAdapterHolder(ItemImageBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}




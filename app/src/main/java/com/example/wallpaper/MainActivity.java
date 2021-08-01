package com.example.wallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.wallpaper.adapter.ItemAdapter;
import com.example.wallpaper.databinding.ActivityMainBinding;
import com.example.wallpaper.model.Photo;
import com.example.wallpaper.modelview.EndlessRecyclerViewScrollListener;
import com.example.wallpaper.modelview.PhotoModel;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private PhotoModel model;
    private int PAGE = 1;
    private ItemAdapter adapter;
    private StaggeredGridLayoutManager manager;
    public EndlessRecyclerViewScrollListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.pro.setVisibility(View.INVISIBLE);
        initView();
        loadData();
        listener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Toast.makeText(MainActivity.this, "OKIHEHEHE", Toast.LENGTH_SHORT).show();
            }
        };
        binding.rv.addOnScrollListener(listener);
    }

    private void initView() {
        manager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        binding.progressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this).build());
        model = ViewModelProviders.of(this).get(PhotoModel.class);
        model.getPhoto(PAGE, binding.progressBar);
        adapter = new ItemAdapter(this);
        binding.rv.setHasFixedSize(false);
        binding.rv.setLayoutManager(manager);
        binding.rv.setAdapter(adapter);

    }

    private void loadData() {
        model.getLiveData().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                adapter.setList(photos);
                binding.progressBar.setVisibility(View.GONE);
            }
        });

    }


}
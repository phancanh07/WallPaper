package com.example.wallpaper.modelview;

import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wallpaper.model.Photo;

import java.util.List;

public class PhotoModel extends ViewModel {
    private PhotoResponse response;
    private LiveData<List<Photo>> liveData;


    public PhotoModel() {
        response = new PhotoResponse();
        liveData = response.getLiveData();

    }

    public void getPhoto(int page, ProgressBar progressBar) {
        response.getAllPhoto(page, progressBar);
    }

    public LiveData<List<Photo>> getLiveData() {
        return liveData;
    }
}

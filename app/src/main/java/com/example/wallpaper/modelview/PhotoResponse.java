package com.example.wallpaper.modelview;

import android.view.View;
import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;

import com.example.wallpaper.model.Photo;
import com.example.wallpaper.model.PhotoBg;
import com.example.wallpaper.service.ApiService;
import com.example.wallpaper.service.RemoteApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoResponse {
    private RemoteApi remoteApi;
    private MutableLiveData<List<Photo>> liveData;


    public PhotoResponse() {
        liveData = new MutableLiveData<>();
        remoteApi = ApiService.getClient().create(RemoteApi.class);
    }

    public void getAllPhoto(int page, ProgressBar progressBar) {
        remoteApi.getPhoto(page).enqueue(new Callback<PhotoBg>() {
            @Override
            public void onResponse(Call<PhotoBg> call, Response<PhotoBg> response) {
                if (response != null) {
                    liveData.postValue(response.body().getPhotos().getPhoto());
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<PhotoBg> call, Throwable t) {
                liveData.postValue(null);
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
    }


    public MutableLiveData<List<Photo>> getLiveData() {
        return liveData;
    }
}

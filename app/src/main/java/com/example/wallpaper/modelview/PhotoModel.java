package com.example.wallpaper.modelview;

import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wallpaper.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoModel extends ViewModel {
    private MutableLiveData<List<Photo>> mutableLiveData;
    private List<Photo> list;

    public PhotoModel() {
        mutableLiveData = new MutableLiveData<>();
        list = new ArrayList<>();
        mutableLiveData.postValue(list);
    }

    public MutableLiveData<List<Photo>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setList(List<Photo> list1) {
        list.addAll(list1);
        mutableLiveData.postValue(list);
    }
}

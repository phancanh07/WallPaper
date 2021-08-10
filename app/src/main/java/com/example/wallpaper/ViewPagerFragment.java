package com.example.wallpaper;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wallpaper.adapter.ViewPagerAdapter;
import com.example.wallpaper.databinding.FragmentViewPagerBinding;
import com.example.wallpaper.model.Photo;
import com.example.wallpaper.modelview.PhotoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "KEY";
    ViewPagerAdapter adapter;
    private PhotoModel model;

    // TODO: Rename and change types of parameters
    private int mParam;
    private static int CODE = 123;

    List<Photo> list = new ArrayList<>();
    FragmentViewPagerBinding view;

    public ViewPagerFragment() {
        // Required empty public constructor
    }


    public static ViewPagerFragment newInstance(int param) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = DataBindingUtil.inflate(inflater, R.layout.fragment_view_pager, container, false);

        model = ViewModelProviders.of(requireActivity()).get(PhotoModel.class);
        if (getArguments() != null) {
            mParam = getArguments().getInt(ARG_PARAM);
            Toast.makeText(getContext(), "" + mParam, Toast.LENGTH_SHORT).show();

            model.getMutableLiveData().observe(requireActivity(), new Observer<List<Photo>>() {
                @Override
                public void onChanged(List<Photo> photos) {
                    adapter = new ViewPagerAdapter(getActivity(), photos);
                    list.addAll(photos);
                    view.viewPager.setAdapter(adapter);
                    view.viewPager.setCurrentItem(mParam);

                }
            });
        }
        view.dowloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });


        return view.getRoot();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permistion = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permistion, CODE);
            } else {
                startDowloadImage();
            }
        } else {
            startDowloadImage();
        }
    }

    private void startDowloadImage() {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(list.get(view.viewPager.getCurrentItem()).getUrlL()));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Dowload ");
        request.setDescription("Dowload File....");
        request.setDestinationInExternalPublicDir(String.valueOf(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)), "wallpaper" + System.currentTimeMillis() + ".png");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
        }
    }
}
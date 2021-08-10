package com.example.wallpaper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.wallpaper.adapter.ItemAdapter;
import com.example.wallpaper.databinding.FragmentListBinding;
import com.example.wallpaper.model.Photo;
import com.example.wallpaper.model.PhotoBg;
import com.example.wallpaper.modelview.OnClickItem;
import com.example.wallpaper.modelview.PhotoModel;
import com.example.wallpaper.service.ApiService;
import com.example.wallpaper.service.RemoteApi;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String KEY = "123";


    private PhotoModel model;
    private int PAGE = 1;
    private int MaxPage;
    private ItemAdapter adapter;
    private StaggeredGridLayoutManager manager;
    private RemoteApi remoteApi;
    ViewPagerFragment fragment;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentListBinding binding;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        View view = binding.getRoot();
        initView();
        initData();
        loadData(PAGE);
        binding.nest.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (PAGE > MaxPage) {
                        Toast.makeText(getContext(), "End Page", Toast.LENGTH_SHORT).show();
                    } else {
                        binding.pro.setVisibility(View.VISIBLE);
                        PAGE++;
                        Toast.makeText(getContext(), "PAGE" + PAGE, Toast.LENGTH_SHORT).show();
                        loadData(PAGE);

                    }
                }
            }
        });
        click();

        return view;
    }

    private void initData() {
        model.getMutableLiveData().observe(requireActivity(), new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                adapter.setList(photos);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        binding.pro.setVisibility(View.INVISIBLE);
        model = ViewModelProviders.of(requireActivity()).get(PhotoModel.class);
        manager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        binding.progressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(getContext()).build());
        remoteApi = ApiService.getClient().create(RemoteApi.class);
        adapter = new ItemAdapter(getContext());
        ItemDecorationAlbumColumns columns = new ItemDecorationAlbumColumns(10, 2);
        binding.rv.addItemDecoration(columns);
        binding.rv.setHasFixedSize(false);
        binding.rv.setLayoutManager(manager);
        binding.rv.setAdapter(adapter);


    }

    private void loadData(int page) {
        remoteApi.getPhoto(page).enqueue(new Callback<PhotoBg>() {
            @Override
            public void onResponse(Call<PhotoBg> call, Response<PhotoBg> response) {
                if (response != null && response.isSuccessful()) {
                    model.setList(response.body().getPhotos().getPhoto());
                    MaxPage = response.body().getPhotos().getPages();
                    binding.progressBar.setVisibility(View.GONE);
                    binding.pro.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<PhotoBg> call, Throwable t) {

            }
        });

    }

    private void click() {
        adapter.setItem(new OnClickItem() {
            @Override
            public void ItemClick(int postions) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_frame, fragment.newInstance(postions)).addToBackStack(null).commit();
            }
        });
    }

}
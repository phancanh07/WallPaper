package com.example.wallpaper.service;

import com.example.wallpaper.model.PhotoBg;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemoteApi {
    @GET("services/rest/?method=flickr.favorites.getList&api_key=6de1341a0fd354639b854026b2f751e9&user_id=191840861%40N07&extras=description%2C+license%2C+date_upload%2C+date_taken%2C+owner_name%2C+icon_server%2C+original_format%2C+last_update%2C+geo%2C+tags%2C+machine_tags%2C+o_dims%2C+views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2Curl_o&per_page=12&format=json&nojsoncallback=1")
    Call<PhotoBg> getPhoto(@Query("page") int page);
}

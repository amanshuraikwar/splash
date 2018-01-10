package com.android.sonu.ummsplash.data.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by amanshuraikwar on 23/12/17.
 */

public interface RetrofitInterface {

    @GET
    @Streaming
    Call<ResponseBody> downloadFile(@Url String url);
}

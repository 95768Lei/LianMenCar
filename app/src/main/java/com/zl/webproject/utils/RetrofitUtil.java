package com.zl.webproject.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhanglei on 2017/6/1.
 */

public class RetrofitUtil {

    //李力
    public static String BASEURL = "http://172.16.18.33:8080";
    //韩斐
//    public static String BASEURL = "http://172.16.18.66:8080/";
//    public static String BASEURL = "http://60.6.202.157:8012/";

    public static Retrofit.Builder builder = new Retrofit.Builder();

    public static OkHttpClient.Builder client = new OkHttpClient.Builder();

    public static <T> T createRetrofit(Class<T> serviceClass) {
        return createRetrofit(serviceClass, false);
    }

    /**
     * @param serviceClass
     * @param isCarryHeader 是否携带请求头
     * @param <T>
     * @return
     */
    public static <T> T createRetrofit(Class<T> serviceClass, boolean isCarryHeader) {

        if (isCarryHeader) {
            client.cookieJar(new CookieJar() {

                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            });
//            client.interceptors().add(new Interceptor() {
//                @Override
//                public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
//                    Request original = chain.request();
//
//                    // Request customization: add request headers
//                    Request.Builder requestBuilder = original.newBuilder()
//                            .header("Authorization", "")
//                            .method(original.method(), original.body());
//
//                    Request request = requestBuilder.build();
//                    return chain.proceed(request);
//                }
//            });
        }

        return builder
                .baseUrl(BASEURL)
                //添加gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
                .create(serviceClass);
    }

}

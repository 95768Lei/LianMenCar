package com.zl.webproject.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zl.webproject.model.BaseBean;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp网络请求工具类
 *
 * @author zhanglei
 */
public class HttpUtils {

//    compile 'com.squareup.okhttp3:okhttp:3.2.0'

    private static HttpUtils mHttpUtils;
    public OkHttpClient mOkClient;

    private HttpUtils() {
        mOkClient = new OkHttpClient.Builder().build();
    }

    /**
     * 获取一个本类的对象
     *
     * @return
     */
    public static HttpUtils getInstance() {
        if (mHttpUtils == null) {
            synchronized (HttpUtils.class) {
                if (mHttpUtils == null) {
                    mHttpUtils = new HttpUtils();
                }
            }
        }

        return mHttpUtils;
    }

    /**
     * get形式的请求方式
     *
     * @param url
     * @param callback
     */
    public void GET(final Activity activity, String url, final OnOkHttpCallback callback) {
        Request request = new Request.Builder().url(url).build();
        mOkClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onError(call.request(), e);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onSuccess(string);
                        }
                    }
                });
            }
        });
    }

    /**
     * @param activity
     * @param mList
     * @param callback
     */
    public void upLoadFile(final Activity activity, final List<String> mList, final OnOkHttpCallback callback) {

        new Thread() {
            @Override
            public void run() {
                MultipartBody.Builder builder = new MultipartBody.Builder();
                for (String s : mList) {
                    byte[] getimage = ImageFactory.getimage(s);
                    Log.e("length", getimage.length + "");
                    final RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), getimage);
                    String fileName = s.substring(s.lastIndexOf("/"));
                    builder.addFormDataPart("file", fileName, body);
                }

                //创建请求体
                Request request = new Request.Builder().url(API.saveTempImg).post(builder.build()).build();

                //加入任务调度
                mOkClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    callback.onError(call.request(), e);
                                }
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            final String string = response.body().string();
                            JSONObject jsonObject = new JSONObject(string);
                            boolean result = jsonObject.optBoolean("result");
                            String jsonData = jsonObject.optString("data");
                            if (result) {
                                if (callback != null) {
                                    callback.onSuccess(jsonData);
                                }
                            } else {
                                Toast.makeText(activity, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                                if (callback != null) {
                                    callback.onError(call.request(), new Exception());
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(activity, "发生了未知错误", Toast.LENGTH_SHORT).show();
                            if (callback != null) {
                                callback.onError(call.request(), new Exception());
                            }
                        }
                    }
                });
            }
        }.start();

    }

    /**
     * @param activity
     * @param mList
     * @param callback
     */
    public void upLoadFile(final Activity activity, final Map<String, String> params, final String url, final List<String> mList, final OnOkHttpCallback callback) {

        new Thread() {
            @Override
            public void run() {
                MultipartBody.Builder builder = new MultipartBody.Builder();
                Set<Map.Entry<String, String>> entries = params.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    builder.addFormDataPart(entry.getKey(), entry.getValue());
                }
                for (String s : mList) {
                    byte[] getimage = ImageFactory.getimage(s);
                    Log.e("length", getimage.length + "");
                    final RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), getimage);
                    String fileName = s.substring(s.lastIndexOf("/"));
                    builder.addFormDataPart("file", fileName, body);
                }

                //创建请求体
                Request request = new Request.Builder().url(url).post(builder.build()).build();

                //加入任务调度
                mOkClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    callback.onError(call.request(), e);
                                }
                            }
                        });
                    }

                    @Override
                    public void onResponse(final Call call, final Response response) throws IOException {
                        final String string = response.body().string();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(string);
                                    boolean result = jsonObject.optBoolean("result");
                                    String jsonData = jsonObject.optString("data");
                                    if (result) {
                                        if (callback != null) {
                                            callback.onSuccess(jsonData);
                                        }
                                    } else {
                                        Toast.makeText(activity, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                                        if (callback != null) {
                                            callback.onError(call.request(), new Exception(jsonObject.optString("msg")));
                                        }
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(activity, "发生了未知错误", Toast.LENGTH_SHORT).show();
                                    if (callback != null) {
                                        callback.onError(call.request(), new Exception());
                                    }
                                }
                            }
                        });

                    }
                });
            }
        }.start();

    }

    /**
     * @param activity
     * @param mList
     * @param url
     * @param callback
     */
    public void upLoadFile(final Activity activity, final List<String> mList, final String phone, final String url, final int size, final OnOkHttpCallback callback) {

        new Thread() {
            @Override
            public void run() {
                MultipartBody.Builder builder = new MultipartBody.Builder();
                if (!TextUtils.isEmpty(phone)) {
                    builder.addFormDataPart("userPhone", phone);
                }
                for (String s : mList) {
                    byte[] getimage = ImageFactory.getimage(s);
                    Log.e("length", getimage.length + "");
                    final RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), getimage);
                    String fileName = s.substring(s.lastIndexOf("/"));
                    builder.addFormDataPart("file", fileName, body);
                }

                //创建请求体
                Request request = new Request.Builder().url(url).post(builder.build()).build();

                //加入任务调度
                mOkClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    callback.onError(call.request(), e);
                                }
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String string = response.body().string();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    callback.onSuccess(string);
                                }
                            }
                        });
                    }
                });
            }
        }.start();

    }

    /**
     * POST形式的网络请求
     *
     * @param params
     * @param url
     * @param callback
     */
    private void POST(final Activity activity, String params, String url, final OnOkHttpCallback callback) {

        final RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params);
        //创建请求体
        Request request = new Request.Builder().url(url).post(body).build();

        //加入任务调度
        mOkClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onError(call.request(), e);
                        }
                    }
                });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final String string = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            boolean result = jsonObject.optBoolean("result");
                            String jsonData = jsonObject.optString("data");
                            if (result) {
                                if (callback != null) {
                                    callback.onSuccess(jsonData);
                                }
                            } else {
                                Toast.makeText(activity, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                                if (callback != null) {
                                    callback.onError(call.request(), new Exception());
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(activity, "发生了未知错误", Toast.LENGTH_SHORT).show();
                            if (callback != null) {
                                callback.onError(call.request(), new Exception());
                            }
                        }

                    }
                });
            }
        });
    }

    /**
     * POST形式的网络请求
     *
     * @param params
     * @param url
     * @param callback
     */
    public void Post(final Activity activity, Map<String, String> params, String url, final OnOkHttpCallback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            builder.add(entry.getKey(), entry.getValue());
        }
        //创建请求体
        Request request = new Request.Builder().url(url).post(builder.build()).build();

        //加入任务调度
        mOkClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onError(call.request(), e);
                        }
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final String string = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            boolean result = jsonObject.optBoolean("result");
                            String jsonData = jsonObject.optString("data");
                            if (result) {
                                if (callback != null) {
                                    callback.onSuccess(jsonData);
                                }
                            } else {
                                Toast.makeText(activity, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                                if (callback != null) {
                                    Exception exception = new Exception(jsonObject.optString("msg"));
                                    callback.onError(call.request(), exception);
                                }
                            }

                        } catch (Exception e) {
                            Toast.makeText(activity, "发生了未知错误", Toast.LENGTH_SHORT).show();
                            if (callback != null) {
                                callback.onError(call.request(), new Exception());
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        mOkClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, getNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
                    listener.onDownloadSuccess(getNameFromUrl(url));
                } catch (Exception e) {
                    listener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * 下载文件的接口
     */
    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String filePath);

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

    /**
     * 获取数据的接口
     *
     * @author zhanglei
     */
    public interface OnOkHttpCallback {
        void onSuccess(String body);

        void onError(Request error, Exception e);
    }

    /**
     * 对图片进行质量压缩
     * 压缩到大概700K
     *
     * @param bitmap
     * @return
     */
    public byte[] zpImage(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int options = 80;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, outputStream);
        while ((outputStream.toByteArray().length / 1024) > 700) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, outputStream);
            options -= 10;
            if (options < 10) {
                break;
            }
        }

        return outputStream.toByteArray();
    }

}

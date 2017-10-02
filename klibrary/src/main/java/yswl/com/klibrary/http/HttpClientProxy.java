package yswl.com.klibrary.http;


import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import yswl.com.klibrary.MApplication;
import yswl.com.klibrary.http.CallBack.DownloadCallBack;
import yswl.com.klibrary.http.CallBack.HttpCallback;
import yswl.com.klibrary.http.okhttp.IRequestMethod;
import yswl.com.klibrary.http.okhttp.OkHttpClientManager;
import yswl.com.klibrary.util.L;
import yswl.com.klibrary.util.ToastUtil;

/**
 * Created by kangpAdministrator on 2017/5/3 0003.
 * Emial kangpeng@yunhetong.net
 */

public class HttpClientProxy implements IRequestMethod {
    private static final String TAG = HttpClientProxy.class.getSimpleName();
    private volatile static HttpClientProxy instance;

    public static final int readTimeout = 20;
    public static final int writeTimeout = 20;
    public static final int connectTimeout = 20;
    public static final int readTimeoutForSign = 40;
    public static final int readTimeoutForUpload = 40;
    public static final int writeTimeoutForUpload = 40;
    public static final int connectTimeoutForUpload = 40;

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    public static final String BASE_URL = MApplication.getApplication().getBaseUrl_Https();//请求接口根地址

    private HttpClientProxy() {
    }

    public static HttpClientProxy getInstance() {
        if (null == instance) {
            synchronized (HttpClientProxy.class) {
                if (null == instance) {
                    instance = new HttpClientProxy();
                }
            }
        }
        return instance;
    }


    @Override
    public void getAsyn(String url, final int requestId, Map<String, Object> paramsMap, final HttpCallback<JSONObject> httpCallback) {
        StringBuilder tempParams = new StringBuilder();
        try {
            if (paramsMap != null) {
                int pos = 0;
                for (Map.Entry<String, ?> entry : paramsMap.entrySet()) {
                    Object obj = entry.getValue();
                    if (pos > 0) {
                        tempParams.append("&");
                    }
                    String value = (obj instanceof String) ? (String) obj : String.valueOf(obj);
                    tempParams.append(String.format("%s=%s", entry.getKey(), URLEncoder.encode(value, "utf-8")));
                    pos++;
                }
            }
            String requestUrl;
            if (url.startsWith("http://") || url.startsWith("https://")) {
                requestUrl = url;
            } else {
                requestUrl = String.format("%s/%s?%s", BASE_URL, url, tempParams.toString());
            }
            final String finalUrl = requestUrl;
            Request request = new Request.Builder().url(requestUrl).build();
            OkHttpClientManager.getSingleInstance().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "url:" + finalUrl + "\n msg:" + e.getMessage());
                    if (httpCallback != null) {
                        httpCallback.onFail(requestId, "访问失败");
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    analysisResponse(requestId, response, httpCallback);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postJSONAsyn(String url, final int requestId, Map<String, Object> paramsMap, final HttpCallback<JSONObject> httpCallback) {
        try {
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            if(paramsMap!=null) {
                for (String key : paramsMap.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }
                    Object obj = paramsMap.get(key);
                    String value = (obj instanceof String) ? (String) obj : String.valueOf(obj);
                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(value, "utf-8")));
                    pos++;
                }
            }
            String params = tempParams.toString();
            L.e(TAG,"http request params :"+ params);
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
            String requestUrl;
            if (url.startsWith("http://") || url.startsWith("https://")) {
                requestUrl = url;
            } else {
                requestUrl = String.format("%s%s", BASE_URL, url);
            }
            final String finalUrl = requestUrl;
            Request request = new Request.Builder().url(requestUrl).post(body).build();
            OkHttpClientManager.getSingleInstance().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "url:" + finalUrl + "\n msg:" + e.getMessage());
                    if (httpCallback != null) {
                        httpCallback.onFail(requestId, "访问失败");
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    analysisResponse(requestId, response, httpCallback);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postAsyn(String url, final int requestId, Map<String, Object> paramsMap, final HttpCallback<JSONObject> httpCallback) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            if(paramsMap!=null) {
                for (String key : paramsMap.keySet()) {
                    Object obj = paramsMap.get(key);
                    String value = (obj instanceof String) ? (String) obj : String.valueOf(obj);
                    L.e(TAG,"http request params key :"+ key +", value ："+value);
                    builder.add(key, URLEncoder.encode(value, "utf-8"));
                }
            }
            RequestBody body = builder.build();
            String requestUrl;
            if (url.startsWith("http://") || url.startsWith("https://")) {
                requestUrl = url;
            } else {
                requestUrl = String.format("%s%s", BASE_URL, url);
            }
            final String finalUrl = requestUrl;
            Request request = new Request.Builder().url(requestUrl).post(body).build();
            OkHttpClientManager.getSingleInstance().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "url:" + finalUrl + "\n msg:" + e.getMessage());
                    if (httpCallback != null) {
                        httpCallback.onFail(requestId, "访问失败");
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    analysisResponse(requestId, response, httpCallback);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    //文件上传
    public void postMultipart(String url, final int requestId, Map<String, Object> paramsMap, final HttpCallback<JSONObject> httpCallback) {
        MultipartBody.Builder formBodyBuilder = new MultipartBody.Builder();
        formBodyBuilder.setType(MultipartBody.FORM);
        if (paramsMap != null) {
            for (Map.Entry<String, ?> entry : paramsMap.entrySet()) {
                Object obj = entry.getValue();
                if (obj instanceof File) {
                    RequestBody fileBody = RequestBody.create(MEDIA_TYPE_STREAM, (File) obj);
                    formBodyBuilder.addFormDataPart(entry.getKey(), ((File) obj).getName(), fileBody);
                } else {
                    formBodyBuilder.addFormDataPart(entry.getKey(), String.valueOf(obj));
                }
            }
        }
        String requestUrl;
        if (url.startsWith("http://") || url.startsWith("https://")) {
            requestUrl = url;
        } else {
            requestUrl = String.format("%s%s", BASE_URL, url);
        }
        final String finalUrl = requestUrl;
        RequestBody body = formBodyBuilder.build();
        Request request = new Request.Builder().url(requestUrl)
                .post(body)
                .build();
        OkHttpClientManager.getSingleInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "url:" + finalUrl + "\n msg:" + e.getMessage());
                if (httpCallback != null) {
                    httpCallback.onFail(requestId, "访问失败");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                analysisResponse(requestId, response, httpCallback);
            }
        });
    }

    //
    public void uploadImgs(String url, List<String> imgPaths) {
        MultipartBody.Builder buidler = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (String imgPath : imgPaths) {
            buidler.addFormDataPart("upload", null, RequestBody.create(MediaType.parse("image/png"), new File(imgPath)));
        }

        RequestBody requestBody = buidler.build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        OkHttpClientManager.getSingleInstance().newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }

            @Override
            public void onFailure(Call call, IOException ex) {

            }
        });
    }


    /**
     * @param url          下载链接
     * @param requestId    下载保存文件夹
     * @param fileRootPath 下载保存文件夹
     * @param fileName     下载文件名
     */
    public void download(final String url, final int requestId, final String fileRootPath, final String fileName, final DownloadCallBack downloadCallBack) {

        Request request = new Request.Builder().url(url).tag(url).build();
        Call call = OkHttpClientManager.getSingleInstance().newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                if (code != 200) {
                    if (downloadCallBack != null) {
                        downloadCallBack.onFilure(response.message());
                    }
                    return;
                }
                InputStream inputStream = response.body().byteStream();
                if (inputStream != null) {
                    long fileLength = response.body().contentLength();
                    if (downloadCallBack != null) {
                        downloadCallBack.onStart(fileLength);
                    }
                    try {
                        File rootFile = new File(fileRootPath);
                        if (!rootFile.exists() || !rootFile.isDirectory()) {
                            rootFile.mkdirs();
                        }
                        File tempFile = new File(rootFile.getAbsolutePath() + File.separator + fileName);
                        if (tempFile.exists()) {
                            tempFile.delete();
                        }
                        tempFile.createNewFile();
                        // 已读出流作为参数创建一个带有缓冲的输出流
                        BufferedInputStream bis = new BufferedInputStream(inputStream);
                        // 创建一个新的写入流，讲读取到的图像数据写入到文件中
                        FileOutputStream fos = new FileOutputStream(tempFile);
                        // 已写入流作为参数创建一个带有缓冲的写入流
                        BufferedOutputStream bos = new BufferedOutputStream(fos);
                        int read;
                        int count = 0;

                        byte[] buffer = new byte[1024];
                        while ((read = bis.read(buffer)) != -1) {
                            bos.write(buffer, 0, read);
                            count += read;
                            if (downloadCallBack != null) {
                                downloadCallBack.onGoing(fileLength, count);
                            }

                        }
                        bos.flush();
                        bos.close();
                        fos.flush();
                        fos.close();
                        inputStream.close();
                        bis.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (downloadCallBack != null) {
                            downloadCallBack.onFilure(e.toString());
                        }
                    }
                } else {
                    if (downloadCallBack != null) {
                        downloadCallBack.onFilure("无法找到该资源");
                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException ex) {
                if (downloadCallBack != null) {
                    downloadCallBack.onFilure(ex.toString());
                }
            }
        });
    }


    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("platform", "2")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", "3.2.0");
        return builder;
    }


    private void analysisResponse(final int requestId, Response response, final HttpCallback<JSONObject> callback) {
        int code = response.code();
        String msg = response.message();
        JSONObject result = null;
        try {
            if (response.isSuccessful()) {
                String body = response.body().string();
                if (!TextUtils.isEmpty(body) && !body.startsWith("<!DOCTYPE")) {
                    result = new JSONObject(body);
                    Log.e(TAG, "onSucceed url:" + response.request().url() + "\n +responseCode:" + code + "\n message:" + msg);
                } else {
                    code = -103;
                    msg = "数据解析异常";
                }
            }
        } catch (JSONException e) {
            code = -103;
            msg = "数据解析异常";
        } catch (IOException e) {
            msg = "IOException";
        } catch (Exception e) {
            msg = "Exception";
        } finally {
            response.close();
        }
        final int newcode = code;
        final JSONObject newResult = result;
        final String newMsg = msg;
        Log.e(TAG, "onSucceed data:" + result);
        MApplication.getApplication().getGolbalHander().post(new Runnable() {
            @Override
            public void run() {
                callback(requestId, newcode, newResult, newMsg, callback);
            }
        });

    }


    private void callback(int requestId, int code, JSONObject object, String msg, HttpCallback<JSONObject> callback) {
        if (null == callback) return;
        switch (code) {
            case 200:
                callback.onSucceed(requestId, object);
                break;
            case 600:
                //帐号其它地方登录
                break;
            case -102:
                //-102 与服务端协商定义 比如用户名密码错误，访问无权限等
                ToastUtil.showToast(msg);
                break;
            case 401:
                //TODO 登录超时，需要重新登录
                break;
            case -101:
                callback.onFail(requestId, "网络错误" + msg);
                ToastUtil.showToast("网络错误" + msg);
                break;
            case 404:
                callback.onFail(requestId, msg);
                ToastUtil.showToast(msg);
                break;
            case -103:
                callback.onFail(requestId, msg);
                ToastUtil.showToast(msg);
                break;
            default:
                callback.onFail(requestId, "Undefined error " + msg);
                ToastUtil.showToast("Undefined error " + msg + code);
                break;
        }
    }
}

package yswl.com.klibrary.http.okhttp;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by nixn@yunhetong.net on 2016/10/10.
 */

public class HttpLog implements Interceptor {
    public static final String TAG = "HttpLog";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.e(TAG,"#############intercept#################");
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.e(TAG,String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.e(TAG,String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        Log.e(TAG,"#############intercept  end #################");
        return response;
    }
}

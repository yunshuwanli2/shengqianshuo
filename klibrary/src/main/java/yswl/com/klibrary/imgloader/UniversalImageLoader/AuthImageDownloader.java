package yswl.com.klibrary.imgloader.UniversalImageLoader;

import android.content.Context;

import com.nostra13.universalimageloader.core.assist.ContentLengthInputStream;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by kangpAdministrator on 2017/5/2 0002.
 * Emial kangpeng@yunhetong.net
 */

public class AuthImageDownloader extends BaseImageDownloader {
    private SSLSocketFactory sslSocketFactory;

    public AuthImageDownloader(Context context, int connectTimeout, int readTimeout) {
        super(context, connectTimeout, readTimeout);
        sslSocketFactory = getSSLSocketFactory();
    }

    AuthImageDownloader(Context context) {
        super(context);
        sslSocketFactory = getSSLSocketFactory();
    }

    private SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            return sc.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {
        HttpURLConnection connection = createConnection(imageUri, extra);
        if (connection instanceof HttpsURLConnection) {
            ((HttpsURLConnection) connection).setSSLSocketFactory(sslSocketFactory);
            ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }
        return new ContentLengthInputStream(new BufferedInputStream(connection.getInputStream(), BUFFER_SIZE), connection.getContentLength());
    }

    @Override
    protected HttpURLConnection createConnection(String url, Object extra) throws IOException {

        //设置sessionId
//        HttpURLConnection conn = super.createConnection(url, extra);
//        String sessionId = SessionManager.getSessionId();
//        if (!TextUtils.isEmpty(sessionId)) {
//            conn.setRequestProperty("Cookie", "JSESSIONID=" + sessionId);
//        }
        return super.createConnection(url, extra);
    }

}

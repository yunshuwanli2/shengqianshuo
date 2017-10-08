package yswl.com.klibrary.http.HttpUrlConnection;

import android.os.Build;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public final class HttpTools {
    private static final int CONNECT_TIMEOUT = 50000;

    private static final int READ_TIMEOUT = 30000;
    public static final String USER_AGENT = "ting_4.1.7(" + Build.MODEL + "," + Build.VERSION.SDK_INT + ")";

    private HttpTools() {
    }


    /**
     * 使用get请求方式请求数据
     *
     * @param url 请求数据的URL
     * @return 返回 byte[] 数组
     */
    public static byte[] doGet(String url) {
        byte[] ret = null;
        if (url != null) {
            HttpURLConnection conn = null;
            try {

                URL u = new URL(url);
                conn = (HttpURLConnection) u.openConnection();

                //设置连接设置
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setRequestMethod("GET");
                //完善HTTp请求的内容
                //1.设定通用的Http头信息
                //设置Accept头信息，告诉服务器，客户端能够接受啥样的数据
                // conn.setRequestProperty("Accept", "application/*,text/*,image/*,*/*");

                //2.设置接受的内容压缩编码算法
                conn.setRequestProperty("Accept-Encoding", "gzip");

                //设置User-Agent

                conn.setRequestProperty("User-Agent", USER_AGENT);

                conn.connect();
                int code = conn.getResponseCode();
                //L.d(code+"-------------");
                InputStream fin = null;
                if (code == 200) {
                    //TODO 给data赋值
                    try {
                        fin = conn.getInputStream();
                        //TODO 进行网络输入流的GZIP解压缩
                        String encoding = conn.getHeaderField("Content-Encoding");
                        if ("gzip".equals(encoding)) {
                            fin = new GZIPInputStream(fin);
                        }
                        ret = readBytes(fin);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        fin.close();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        }
        return ret;
    }


    /**
     * 使用post 的方式进行数据的请求
     *
     * @param strUrlPath 请求数据路径
     * @param params     请求体参数
     * @param encode     编码格式
     * @return 返回String
     */
    public static String submitPostData(String strUrlPath, Map<String, String> params, String encode) {

        byte[] data = getRequestData(params, encode).toString().getBytes();//获得请求体
        try {
            URL url = new URL(strUrlPath);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);     //设置连接超时时间
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");     //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);

            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }
        } catch (IOException e) {
            //e.printStackTrace();
            return "err: " + e.getMessage().toString();
        }
        return "-1";
    }

    /**
     * 封装请求体信息
     *
     * @param params 请求体 参数
     * @param encode 编码格式
     * @return 返回封装好的StringBuffer
     */
    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    /**
     * 处理服务器返回结果
     *
     * @param inputStream 输入流
     * @return 返回处理后的String 字符串
     */
    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

    public static byte[] readBytes(InputStream ins) throws IOException {
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int size = 0;
        while ((size = ins.read(bytes)) != -1) {
            outputStream.write(bytes, 0, size);
        }
        outputStream.close();
        ins.close();
        return outputStream.toByteArray();
    }
}
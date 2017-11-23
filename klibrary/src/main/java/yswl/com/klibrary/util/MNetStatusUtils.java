package yswl.com.klibrary.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
/**
 * 网络状态，打开网络设置界面操作
 * @author nixn@yunhetong.net
 *
 */
public class MNetStatusUtils{
	private MNetStatusUtils(){  
		/* cannot be instantiated */  
		throw new UnsupportedOperationException("cannot be instantiated");  
	} 
	/**
	 * 网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager mgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context,boolean notice) {
		boolean result = isNetworkAvailable(context);
		if(!result&&notice){
			Toast.makeText(context,"网络不可用",Toast.LENGTH_SHORT).show();
		}
		return result;
	}
	/** 
     * 判断是否是wifi连接 
     */  
    public static boolean isWifi(Context context){  
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        if (cm == null)  
            return false;  
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;  
    }  
  
    /** 
     * 打开网络设置界面 
     */  
    public static void openSetting(Activity activity)  {  
        Intent intent = new Intent("/");  
        ComponentName cm = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");  
        intent.setComponent(cm);  
        intent.setAction("android.intent.action.VIEW");  
        activity.startActivityForResult(intent, 0);  
    }  

	/**
     * 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
	 * @return
	 */
	public static final boolean ping() {
		String result = null;
		try {
			String ip = "www.baidu.com";// ping 的地址可以换成任何可靠的地址
			Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " + ip);// ping网址1次
			// 读取ping的内容，可以不加
			InputStream input = p.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			StringBuffer stringBuffer = new StringBuffer();
			String content = "";
			while ((content = in.readLine()) != null) {
				stringBuffer.append(content);
			}
			// ping的状态
			int status = p.waitFor();
			if (status == 0) {
				result = "success";
				return true;
			} else {
				result = "failed";
			}
		} catch (IOException e) {
			result = "IOException";
		} catch (InterruptedException e) {
			result = "InterruptedException";
		} finally {
		}
		return false;
	}
}

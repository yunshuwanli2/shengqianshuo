package yswl.com.klibrary.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Process;
import android.text.TextUtils;

import java.util.Iterator;

import yswl.com.klibrary.MApplication;

public class MAppInfoUtil {


    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本号]
     *
     * @param context
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        if (context != null) {
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                return packageInfo.versionCode;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static String getCurProcessName(Context paramContext) {
        int pid = Process.myPid();
        Iterator localIterator = ((ActivityManager) paramContext.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses().iterator();
        while (localIterator.hasNext()) {
            ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo) localIterator.next();
            if (localRunningAppProcessInfo.pid == pid) {
                return localRunningAppProcessInfo.processName;
            }
        }
        return null;
    }

    //非推送进程
    public static boolean notPush() {
        String proces = MAppInfoUtil.getCurProcessName(MApplication.getApplication());
        if (!TextUtils.isEmpty(proces) && proces.endsWith(":push")) {
            return false;
        }
        return true;
    }
}

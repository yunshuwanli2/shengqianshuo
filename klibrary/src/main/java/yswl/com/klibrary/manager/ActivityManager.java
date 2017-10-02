package yswl.com.klibrary.manager;

import android.app.Activity;
import android.text.TextUtils;

import java.util.Stack;

/**
 * activity 管理
 *
 * @author nixn@yunhetong.net
 */
public class ActivityManager {
    private static volatile Stack<Activity> activityStack;
    /**
     * 获得activity单例管理器
     *
     * @return
     */
    public static Stack<Activity> getStack() {
        if (activityStack == null) {
            synchronized (ActivityManager.class) {
                if (activityStack == null) {
                    activityStack = new Stack<>();
                }
            }
        }
        return activityStack;
    }

    /**
     * 添加Activity到栈
     *
     * @param activity
     */
    public static synchronized void addActivity(Activity activity) {
        ActivityManager.getStack().add(activity);
    }

    /**
     * finish and remove activity
     *
     * @param activity
     */
    public static synchronized void removeAndfinishActivity(Activity activity) {
        if (activity == null)
            return;
        if (activityStack != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing())
                activity.finish();
        }
    }

    public synchronized static <T> void removeAndFinishActivityByType(TypeFilter filter) {
        int removeId = -1;
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            Activity ac = activityStack.get(i);
            if (ac == null) continue;
            if (filter.filter(ac)) {
                if (!ac.isFinishing())
                    ac.finish();
                removeId = i;
            }
        }
        if (removeId != -1)
            activityStack.remove(removeId);
    }

    public interface TypeFilter {
        public boolean filter(Activity ac);
    }

    /**
     * finish and remove activity
     *
     * @param activity
     */
    public synchronized static void removeActivity(Activity activity) {
        if (activityStack != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * finish and remove activity
     *
     * @param activity
     */
    public synchronized static void removeAndfinishAllActivityEx(Activity activity) {
        if (activityStack != null&&activityStack.size()>0) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (activity != null && activity != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }
        addActivity(activity);
    }

    /**
     * 结束所有Activity
     */
    public synchronized static void finishAllActivity() {
        if (activityStack == null) return;
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public static Activity getActivityByFilter(TypeFilter filter) {
        if (activityStack == null) return null;
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            Activity temp = activityStack.get(i);
            if (filter.filter(temp)) {
                return temp;
            }
        }
        return null;
    }

    public static boolean isTop(String clazzName) {
        if (TextUtils.isEmpty(clazzName)) return false;
        Activity ac = getTop();
        if (ac == null) return false;
        String acClazzName = ac.getClass().getName();
        return clazzName.equals(acClazzName);
    }

    public static Activity getTop() {
        if (activityStack == null || activityStack.size() == 0) return null;
        Activity ac = activityStack.lastElement();
        return ac;
    }
}

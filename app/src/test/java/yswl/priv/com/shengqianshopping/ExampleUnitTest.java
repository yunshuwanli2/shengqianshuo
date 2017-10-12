package yswl.priv.com.shengqianshopping;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void addition_isCorrect() throws Exception {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, +1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date1 = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT, Locale.CHINA);
        String ss = formatter.format(date1);
        if (ss.equals("2017-10-12 18:42")) ;
    }
}
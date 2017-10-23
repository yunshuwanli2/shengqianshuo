package yswl.priv.com.shengqianshopping;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Iterator;

import yswl.com.klibrary.util.T;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String str = T.getCertificateSHA1Fingerprint(appContext);

        assertEquals("yswl.priv.com.shengqianshopping", appContext.getPackageName());
    }
    @Test
    public void iteratorJSONObj() throws Exception {
        String str = "{\"44201\":{\"optList\":[{\"optName\":\"删除\",\"optCode\":\"delete\"},{\"optName\":\"复制合同\",\"optCode\":\"copyPdf\"}],\"colStatusStr\":\"被废弃\",\"completeStatusStr\":\"已作废-被废弃\"}}";
        JSONObject jsonObject = new JSONObject(str);
        System.out.println(jsonObject.toString());
        Iterator iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            JSONObject value = jsonObject.optJSONObject(key);
        }
    }
}

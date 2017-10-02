package yswl.com.klibrary.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间解析
 * 
 * @author nixn@yunhetong.net
 *
 */
public class DateJsonDeserializer implements JsonDeserializer<Date> {
	private static final String TAG = "DateJsonDeserializer";
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Date deserialize(JsonElement arg0, Type arg1,
                            JsonDeserializationContext arg2) throws JsonParseException {
		Date date = null;
		String dataStr = arg0.getAsString();
		try {
			if (dataStr.contains(":")) {
				date = DateJsonDeserializer.format.parse(dataStr);
			} else {
				date = new Date(arg0.getAsLong());
			}
		} catch (Exception e) {
		}
		return date;
	}
}

package yswl.com.klibrary.http;

/**
 * session管理
 * 
 * @author nixn@yunhetong.net
 *
 */
public class Session {
	public static final String JSESSIONID = "JSESSIONID";
	/**
	 * 对应服务器端的sessionId
	 */
	public String sessionId;
	/**
	 * sessionId的开始时间
	 */
	public String time;
 	public String webviewCookies;
}

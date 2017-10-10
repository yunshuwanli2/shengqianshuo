package yswl.priv.com.shengqianshopping.util;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
public class MTextViewUtil {
	public static void setHint(TextView v, String hint) {
		if (v != null && !TextUtils.isEmpty(hint)) {
			v.setHint(hint);
		}
	}

	public static void setTextOrHint(TextView v, String text, String hint) {
		if (v == null)
			return;
		if (TextUtils.isEmpty(text)) {
			setHint(v, hint);
		} else {
			v.setText(text);
		}
	}

	public static void setText(EditText v, String text) {
		if (v != null && !TextUtils.isEmpty(text)) {
			v.setText(text);
		}
	}

	public static void setText(TextView v, String text) {
		if (v == null)
			return;
		if (v != null && !TextUtils.isEmpty(text)) {
			v.setText(text);
		} else {
			v.setText("");
		}
	}
	public static void setText(TextView v, String text,View v2) {
		if (v != null && !TextUtils.isEmpty(text)) {
			v.setText(text);
		} else {
			if(v2!=null){
				v2.setVisibility(View.GONE);
			}
		}
	}
	public static void setText(TextView v, int res) {
		if (v != null && res != -1) {
			v.setText(res);
		}
	}

	public static void setText(TextView v, String text, String defalut) {
		if (v == null)
			return;
		if (!TextUtils.isEmpty(text)) {
			v.setText(text);
		} else {
			v.setText(defalut);
		}
	}

	public static void setText(TextView v, String text, int visiable) {
		if (v == null)
			return;
		if (!TextUtils.isEmpty(text)) {
			if (v.getVisibility() != View.VISIBLE)
				v.setVisibility(View.VISIBLE);
			v.setText(text);
		} else {
			v.setVisibility(visiable);
		}
	}

	public static void setText(TextView v, String text, int visibility,
			String except) {
		if (v != null && !TextUtils.isEmpty(text) && !text.equals(except)) {
			v.setVisibility(visibility);
			v.setText(text);
		}
	}

	public static void setHtml(TextView v, String text) {
		if (v != null && !TextUtils.isEmpty(text)) {
			v.setText(Html.fromHtml(text));
		}
	}

	public static void setCompoundDrawablesLeft(TextView v, Drawable draw) {
		if (v != null && draw != null) {
			// draw.setBounds(0, 0, draw.getMinimumWidth(),
			// draw.getMinimumHeight());
			// v.setCompoundDrawablesRelativeWithIntrinsicBounds(draw, null,
			// null, null);
			v.setCompoundDrawablesWithIntrinsicBounds(draw, null, null, null);
		}
	}

	public static void setCompoundDrawablesRight(TextView v, int iconResource) {
		if (v != null && iconResource != -1) {
			Drawable draw = v.getContext().getResources()
					.getDrawable(iconResource);
			// v.setCompoundDrawablesRelativeWithIntrinsicBounds(draw, null,
			// null, null);
			v.setCompoundDrawablesWithIntrinsicBounds(null, null, draw, null);
		} else if (v != null && iconResource == -1) {
			// v.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null,
			// null, null);
			v.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}
	}

	public static void setCompoundDrawablesLeft(TextView v, int iconResource) {
		if (v != null && iconResource != -1) {
			Drawable draw = v.getContext().getResources()
					.getDrawable(iconResource);
			// v.setCompoundDrawablesRelativeWithIntrinsicBounds(draw, null,
			// null, null);
			v.setCompoundDrawablesWithIntrinsicBounds(draw, null, null, null);
		} else if (v != null && iconResource == -1) {
			// v.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null,
			// null, null);
			v.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		}
	}
}
package yswl.priv.com.shengqianshopping.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * Created by yunshuwanli on 17/10/2.
 */

public class SerializableParamsMap implements Serializable {
   public Map<String,Object> map;

    public SerializableParamsMap(Map<String, Object> map) {
        this.map = map;
    }
}

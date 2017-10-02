package yswl.priv.com.shengqianshopping.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * Created by yunshuwanli on 17/10/2.
 */

public class SerializableMap implements Serializable {
   public Map<String,Object> map;

    public SerializableMap(Map<String, Object> map) {
        this.map = map;
    }
}

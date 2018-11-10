package me.cxj.j3dtiles.utils;

import java.util.List;
import java.util.Map;

/**
 * Created by vipcxj on 2018/10/30.
 */
public interface JsonParser {

    Object parse(String json);

    Map<String, Object> parseObject(String json);

    List<Object> parseArray(String json);

    String toJsonString(Object obj);
}

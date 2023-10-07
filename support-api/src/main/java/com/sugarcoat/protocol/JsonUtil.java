package com.sugarcoat.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/9/27
 */
public class JsonUtil {

    public static String toJsonStr(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

}

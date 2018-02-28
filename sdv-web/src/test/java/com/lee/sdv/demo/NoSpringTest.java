package com.lee.sdv.demo;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * 测试json
 */
public class NoSpringTest {
    @Test
    public void testJson() {
        JSONObject json = JSONObject.parseObject("{\"password\":\"123456\",\"username\":\"张三\"}");
        String password = json.getString("password");
        System.out.println(password);
    }
}

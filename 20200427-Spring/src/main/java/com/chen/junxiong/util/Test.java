package com.chen.junxiong.util;


import java.util.HashSet;
import java.util.Set;

/**
 * @author junxiong.chen
 * @date 6/16
 */
public class Test {

    private static String test4(String common){
        String result = "部分打印";
        Set<String> set = new HashSet<>();
        String[] split = common.split(",");
        int i1 = common.indexOf(",");
        for (int i = 0; i < split.length; i++) {
            set.add(split[i]);
        }
        if (set.size() == 1) {
            return split[0];
        }
        return result;
    }
}

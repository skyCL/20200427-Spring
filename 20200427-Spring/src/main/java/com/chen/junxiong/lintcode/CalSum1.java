package com.chen.junxiong.lintcode;

import com.google.common.collect.Lists;

import java.util.List;

/**
 *  2020.07.31
 *  计算两数之和 II
 *  给定一个已按照升序排列的有序数组，找到两个数使它们相加之和等于目标数 (找到下标)
 *  举例
 *  输入 numbers=[2,7,11,9] target = 9
 *  输出 [1,2]
 */
public class CalSum1 {
    public static void main(String[] args) {
        List<Integer> function = function(Lists.newArrayList(3, 7, 9, 11, 34, 56), 65);
        System.out.println(function);
    }

    private static List<Integer> function(List<Integer> numbers, Integer target) {

        int startIndex = 0;
        int lastIndex = numbers.size() - 1;

        for (int i = 0; i < numbers.size(); i++) {
            Integer startNum = numbers.get(startIndex);
            Integer endNum = numbers.get(lastIndex);
            if ((startNum + endNum == target)) {
                return Lists.newArrayList(startIndex + 1, lastIndex + 1);
            }
            if ((startNum + endNum < target)) {
                startIndex++;
                continue;
            }
            if ((startNum + endNum > target)) {
                lastIndex--;
            }
        }
        return Lists.newArrayList();
    }
}

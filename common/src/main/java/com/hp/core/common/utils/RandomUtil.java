package com.hp.core.common.utils;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;

/**
 * Created by frank.sang on 2019/6/18.
 */
public class RandomUtil {

    /**
     * 随机获得列表中的元素
     *
     * @param <T> 元素类型
     * @param list 列表
     * @return 随机元素
     */
    public static <T> T randomEle(List<T> list) {
        return randomEle(list, list.size(), false);
    }

    /**
     * 随机获得列表中的元素
     * @param <T>
     * @param list
     * @param remove		是否删除随机获得的元素
     * @return
     */
    public static <T> T randomEle(List<T> list, boolean remove) {
    	return randomEle(list, list.size(), remove);
    }
    
    /**
     * 随机获得列表中的元素
     *
     * @param <T> 元素类型
     * @param list 列表
     * @param limit 限制列表的前N项
     * @param remove 是否删除随机活动的元素
     * @return 随机元素
     */
    public static <T> T randomEle(List<T> list, int limit, boolean remove) {
    	int index = RandomUtils.nextInt(0, limit);
    	if (remove) {
    		return list.remove(index);
    	} else {
    		return list.get(index);
    	}
    }
}

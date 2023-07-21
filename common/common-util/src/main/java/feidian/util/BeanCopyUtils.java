package feidian.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {
    }
    /*
    单个返回规范化
     */
    public static <V> V copyProperty(Object source, Class<V> clazz) {
        V dto = null;
        try {
            dto = clazz.newInstance();
            BeanUtils.copyProperties(source, dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }
    /*
    数组类型的返回规范化
     */
    public static <V,T> List<V> copyProperties(List<T> sources,Class<V> clazz) {
        List<V> result = null;
        try {
             result = sources.stream()
                    .map(o -> copyProperty(o, clazz))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
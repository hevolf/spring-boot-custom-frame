package com.example.security;

import org.apache.shiro.util.ClassUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author caohaifengx@163.com 2021-05-13 14:54
 * 参考shiro DefaultFilter
 */
public enum CustomShiroFilterEnum {
    authc(AuthcFilter.class);

    private final Class<? extends Filter> filterClass;

    private CustomShiroFilterEnum(Class<? extends Filter> filterClass){
        this.filterClass = filterClass;
    }

    public Filter newInstance(){
        return (Filter) ClassUtils.newInstance(this.filterClass);
    }

    public Class<? extends Filter> getFilterClass() {
        return this.filterClass;
    }

    public static Map<String, Filter> createInstanceMap(FilterConfig config) {
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>(values().length);
        for (CustomShiroFilterEnum filterEnum : values()) {
            Filter filter = filterEnum.newInstance();
            if (config != null) {
                try {
                    filter.init(config);
                } catch (ServletException e) {
                    String msg = "Unable to correctly init default filter instance of type " +
                            filter.getClass().getName();
                    throw new IllegalStateException(msg, e);
                }
            }
            filters.put(filterEnum.name(), filter);
        }
        return filters;
    }
}

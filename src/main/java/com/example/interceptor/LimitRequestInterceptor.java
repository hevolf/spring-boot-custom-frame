package com.example.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author caohaifengx@163.com 2021-05-31 16:40
 */
public class LimitRequestInterceptor implements HandlerInterceptor {
    private final String LIMIT_METHOD_KEY = "limit:request";
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // todo 查询数据库对应的接口是否下线(查询出所有已下线接口)
        // redis set
        Set limitMembers = getLimitMembers();
        String servletPath = request.getServletPath();

        if (servletPath != null && limitMembers.contains(servletPath)){
            throw new IllegalArgumentException("接口已下线22");
        }
        return true;
    }

    /**
     * 获取禁用状态接口列表，缓存未获取到 -> 从数据库获取? (todo 若缓存为0如何获取？？？)
     * 无key即缓存过期，有key value.size为0 即无禁用
     * @return
     */
    private Set getLimitMembers(){
        Set members = redisTemplate.opsForSet().members(LIMIT_METHOD_KEY);
        if (CollectionUtils.isEmpty(members)){
            // todo 访问数据库并存缓存  设置member过期时间
            Set<String> members2 = new HashSet<>();
            members2.add("/tet");
            members2.add("/a");
            // 写法错误，set里存了set
            // redisTemplate.opsForSet().add(LIMIT_METHOD_KEY, members2);

            // set里存单个string
            SetOperations setOperations = redisTemplate.opsForSet();
            members2.forEach(
                    i -> {
                        setOperations.add(LIMIT_METHOD_KEY, i);
                    }
            );
            // 设置过期时间
            redisTemplate.expire(LIMIT_METHOD_KEY, 30 * 60, TimeUnit.SECONDS);
            members = redisTemplate.opsForSet().members(LIMIT_METHOD_KEY);
        }
        return members;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

package com.example.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author caohaifengx@163.com 2021-06-10 14:24
 * 对 restTemplate 进行封装
 * @apiNote  Object... uriVariables 此参数可有可无 - url不带参数时，不传此参数即可
 */
@Component
public class RestTemplateUtil {

    @Autowired
    RestTemplate restTemplate;

    // ---------------------------- GET -------------------------------

    /**
     * GET请求
     * @param url 请求地址
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，顺序依次对应（若为Map, 则变量对应key）
     * @return
     */
    public <T> ResponseEntity<T> get(String url, Class<T> responseType, Object... uriVariables){
        return restTemplate.getForEntity(url, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> get(String url, Class<T> responseType, Map<String, ?> uriVariables){
        return restTemplate.getForEntity(url, responseType, uriVariables);
    }

    /**
     * 带有请求头HttpHeaders的GET请求
     * @param url 请求地址
     * @param httpHeaders 请求头
     * @param responseType 返回对象类型
     * @param uriVariables URL中的变量，顺序依次对应
     * @return
     */
    public <T> ResponseEntity<T> get(String url, HttpHeaders httpHeaders, Class<T> responseType, Object... uriVariables){
        // httpHeaders => httpEntity
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        return exchange(url, HttpMethod.GET, httpEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> get(String url, HttpHeaders httpHeaders, Class<T> responseType, Map<String, ?> uriVariables){
        // httpHeaders => httpEntity
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        return exchange(url, HttpMethod.GET, httpEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> get(String url, Map<String, String> headers, Class<T> responseType, Object... uriVariables){
        // httpHeaders => httpEntity
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return get(url, httpHeaders, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> get(String url, Map<String, String> headers, Class<T> responseType, Map<String, ?> uriVariables){
        // httpHeaders => httpEntity
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return get(url, httpHeaders, responseType, uriVariables);
    }

    // ---------------------------- POST -----------------------------------

    /**
     * POST请求 - 不带请求body 不带请求头
     * @param url 请求地址
     * @param responseType 返回类型
     * @return
     */
    public <T> ResponseEntity<T> post(String url, Class<T> responseType){
        return restTemplate.postForEntity(url, HttpEntity.EMPTY, responseType);
    }

    /**
     * POST请求 - 带请求body 不带请求头 带url参数
     * @param url 请求地址
     * @param request 请求参数
     * @param responseType 返回类型
     * @param uriVariables URL中的变量，顺序依次对应（Map中为key）
     * @return
     */
    public <T> ResponseEntity<T> post(String url, Object request, Class<T> responseType, Object... uriVariables){
        return restTemplate.postForEntity(url, request, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> post(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables){
        return restTemplate.postForEntity(url, request, responseType, uriVariables);
    }

    /**
     * POST请求 - 带url参数 带请求头 带请求body
     * @param url 请求地址
     * @param headers 请求头
     * @param request 请求body
     * @param responseType 返回类型
     * @param uriVariables url参数
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> post(String url, HttpHeaders headers, Object request, Class<T> responseType, Object... uriVariables){
        // httpEntity = 请求body + 请求头
        HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);
        return exchange(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> post(String url, HttpHeaders headers, Object request, Class<T> responseType, Map<String, ?> uriVariables){
        HttpEntity<Object> httpEntity = new HttpEntity<>(request, headers);
        return exchange(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object request, Class<T> responseType, Object... uriVariables){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return post(url, httpHeaders, request, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> post(String url, Map<String, String> headers, Object request, Class<T> responseType, Map<String, ?> uriVariables){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return post(url, httpHeaders, request, responseType, uriVariables);
    }


    // ---------------------------- EXCHANGE -------------------------------

    /**
     * 通用调用方式 EXCHANGE
     * @param url 请求地址
     * @param method 请求方法类型GET/POST等
     * @param httpEntity 请求体-包含请求头
     * @param responseType 返回类型
     * @param uriVariables URL中的变量，顺序依次对应（Map中为key） 若url不带参数 - uriVariables不传即可
     * @return
     */
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> httpEntity,
                                          Class<T> responseType, Object... uriVariables){
         return restTemplate.exchange(url, method, httpEntity, responseType, uriVariables);
    }
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> httpEntity,
                                          Class<T> responseType, Map<String, ?> uriVariables){
        return restTemplate.exchange(url, method, httpEntity, responseType, uriVariables);
    }
}

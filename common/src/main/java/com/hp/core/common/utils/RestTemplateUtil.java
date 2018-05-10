/**
 * 
 */
package com.hp.core.common.utils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

/**
 * @author ping.huang 2016年10月10日
 */
public class RestTemplateUtil {

	private static Logger log = LoggerFactory.getLogger(RestTemplateUtil.class);

	/**
	 * 使用json格式调用post请求
	 * 
	 * @param restTemplate
	 * @param url
	 * @param request
	 * @param responseClass
	 * @return
	 */
	public static <T> T postJSON(RestTemplate restTemplate, String url, Object request, Class<T> responseClass) {
		log.info("call postJSON with url={}, request={}", url, request);
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		HttpEntity<String> entity = new HttpEntity<String>(JSON.toJSONString(request), headers);
		T response = restTemplate.postForObject(url, entity, responseClass);
		return response;
	}
	
	/**
	 * 使用form格式调用post的请求
	 * @param restTemplate
	 * @param url
	 * @param request
	 * @param responseClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T postForm(RestTemplate restTemplate, String url, Object request, Class<T> responseClass) {
		log.info("call postForm with url={}, request={}", url, request);
		MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
		if (request != null) {
			if (request instanceof Map) {
				String key = null;
				Object value = null;
				for (Entry<String, Object> entry : ((Map<String, Object>) request).entrySet()) {
					value = entry.getValue();
					key = entry.getKey();
					if (value == null) {
						continue;
					}
					requestEntity.add(key, value.toString());
				}
			} else {
				try {
					Field[] declaredFields = request.getClass().getDeclaredFields();
					for (Field field : declaredFields) {
						field.setAccessible(true);
						// 过滤内容为空的
						if (field.get(request) == null) {
							continue;
						}
						requestEntity.add(field.getName(), field.get(request).toString());
					}
				} catch (IllegalAccessException e) {
					log.error("", e);
				}
			}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> r = new HttpEntity<>(requestEntity, headers);
		T response = restTemplate.postForObject(url, r, responseClass);
		return response;
	}
	
	/**
	 * 发送get请求
	 * @param restTemplate
	 * @param url
	 * @param responseClass
	 * @return
	 */
	public static <T> T get(RestTemplate restTemplate, String url, Class<T> responseClass) {
		log.info("call get with url={}", url);
		T response = restTemplate.getForObject(url, responseClass);
		return response;
	}
	
}

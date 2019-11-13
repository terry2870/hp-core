/**
 * 
 */
package com.hp.core.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author ping.huang 2016年10月10日
 */
public class RestTemplateUtil {

	private static Logger log = LoggerFactory.getLogger(RestTemplateUtil.class);
	
	/**
	 * 使用json格式调用post请求
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
	 * 发送form提交
	 * @param restTemplate
	 * @param url
	 * @param request
	 * @return
	 */
	public static JSONObject postFormForJSON(RestTemplate restTemplate, String url, Object request) {
		return postForm(restTemplate, url, request, JSONObject.class);
	}
	
	/**
	 * 发送form提交
	 * @param <T>
	 * @param restTemplate
	 * @param url
	 * @param request
	 * @param responseClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T postForm(RestTemplate restTemplate, String url, Object request, Class<T> responseClass) {
		log.info("call postFormForJSON with url={}, request={}", url, request);
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
		headers.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8"));
		HttpEntity<MultiValueMap<String, String>> r = new HttpEntity<>(requestEntity, headers);
		T response = restTemplate.postForObject(url, r, responseClass);
		return response;
	}
	
	/**
	 * 发送get请求
	 * @param restTemplate
	 * @param url
	 * @param param
	 * @param responseClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(RestTemplate restTemplate, String url, Object param, Class<T> responseClass) {
		log.info("call get with url={}, param={}", url, param);
		if (param == null) {
			return get(restTemplate, url, responseClass);
		}
		
		List<String> list = new ArrayList<>();
		if (param instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) param;
			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() == null) {
					continue;
				}
				list.add(entry.getKey() + "=" + entry.getValue());
			}
		} else {
			Field[] declaredFields = param.getClass().getDeclaredFields();
			for (Field field : declaredFields) {
				try {
					field.setAccessible(true);
					// 过滤内容为空的
					if (field.get(param) == null) {
						continue;
					}
					list.add(field.getName() + "=" + field.get(param).toString());
				} catch (Exception e) {
				}
			}
		}

		if (url.contains("?")) {
			url = url + "&" + StringUtils.join(list, "&");
		} else {
			url = url + "?" + StringUtils.join(list, "&");
		}
		return get(restTemplate, url, responseClass);
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

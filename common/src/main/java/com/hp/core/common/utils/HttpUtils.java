package com.hp.core.common.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {

	static Logger log = LoggerFactory.getLogger(HttpUtils.class);
	
	//默认超时时间
	private static final int DEFAULT_TIME_OUT = 6000;
	//默认字符集
	private static final String DEFAULT_CHAR_SET = "UTF-8";
	
	/**
	 * 发送http请求，传输body
	 * @param url
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static Pair<Integer, String> postBody(String url, String body) throws Exception {
		return postBody(url, body, null);
	}
	
	/**
	 * 发送http请求，传输body
	 * @param url
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static Pair<Integer, String> postJSON(String url, String body) throws Exception {
		List<Header> headList = new ArrayList<Header>();
		headList.add(new BasicHeader("Content-Type", "application/json"));
		return postBody(url, body, headList, DEFAULT_CHAR_SET, DEFAULT_TIME_OUT);
	}
	
	/**
	 * 发送http请求，传输body
	 * @param url
	 * @param body
	 * @param charset
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static Pair<Integer, String> postJSON(String url, String body, String charset, int timeout) throws Exception {
		List<Header> headList = new ArrayList<Header>();
		headList.add(new BasicHeader("Content-Type", "application/json"));
		return postBody(url, body, headList, charset, timeout);
	}
	
	/**
	 * 发送http请求，传输body
	 * @param url
	 * @param body
	 * @param headList
	 * @return
	 * @throws Exception
	 */
	public static Pair<Integer, String> postBody(String url, String body, List<Header> headList) throws Exception {
		return postBody(url, body, headList, DEFAULT_CHAR_SET, DEFAULT_TIME_OUT);
	}
	
	/**
	 * 发送http请求，传输body
	 * @param url
	 * @param body
	 * @param headList
	 * @param charset
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static Pair<Integer, String> postBody(String url, String body, List<Header> headList, String charset, int timeout) throws Exception {
		log.info("postBody with url={}, body={}", url, body);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse re = null;
		HttpPost post = null;
		int code = 0;
		String message = "";
		try {
			post = new HttpPost(url);
			post.setEntity(new StringEntity(body, Charset.forName(charset)));
			
			setConfig(post, headList, timeout);
			long d1 = DateUtil.getCurrentTimeMilliSeconds();
			re = client.execute(post);
			code = re.getStatusLine().getStatusCode();
			message = EntityUtils.toString(re.getEntity(), charset);
			long d2 = DateUtil.getCurrentTimeMilliSeconds();
			log.info("postBody with url={}, code={}, and cost time={}", url, code, (d2 - d1));
		} catch (Exception e) {
			log.error("postBody error with url={}, body={}", url, body, e);
			throw e;
		} finally {
			if (re != null) {
				EntityUtils.consume(re.getEntity());
				re.close();
			}
			if (client != null) {
				client.close();
			}
		}
		return Pair.of(code, message);
	}
	
	/**
	 * 设置head和超时
	 * @param base
	 * @param headList
	 */
	private static void setConfig(HttpRequestBase base, List<Header> headList, int timeout) {
		if (CollectionUtils.isNotEmpty(headList)) {
			for (Header head : headList) {
				base.setHeader(head.getName(), head.getValue());
			}
		}
		RequestConfig config = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).build();//设置请求和传输超时时间
		base.setConfig(config);
	}
	
	/**
	 * 发送post请求
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Pair<Integer, String> httpPost(String url, Map<String, Object> param) throws Exception {
		return httpPost(url, param, null);
	}
	
	/**
	 * 发送post请求
	 * @param url
	 * @param param
	 * @param headList
	 * @return
	 * @throws Exception
	 */
	public static Pair<Integer, String> httpPost(String url, Map<String, Object> param, List<Header> headList) throws Exception {
		return httpPost(url, param, headList, DEFAULT_CHAR_SET, DEFAULT_TIME_OUT);
	}
	
	public static void main(String[] args) {
		
		try {
			Pair<Integer, String> pair = httpGet("http://www.hao123.com");
			System.out.println("pair= " + pair);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送http请求，传递参数
	 * @param url
	 * @param param
	 * @param headList
	 * @param charset
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static Pair<Integer, String> httpPost(String url, Map<String, Object> param, List<Header> headList, String charset, int timeout) throws Exception {
		log.info("httpPost with url={}, param={}", url, param);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse re = null;
		HttpPost post = null;
		int code = 0;
		String message = "";
		try {
			post = new HttpPost(url);
			if (param != null) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				for (Entry<String, Object> entry : param.entrySet()) {
					log.debug("请求参数 --> " + entry.getKey() + "=" + entry.getValue());
					if (entry.getKey() == null || entry.getValue() == null) {
						throw new Exception("键值不能为null" + entry.getKey() + "=" + entry.getValue());
					}
					list.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
				}
				post.setEntity(new UrlEncodedFormEntity(list, charset));
			}
			setConfig(post, headList, timeout);
			long d1 = DateUtil.getCurrentTimeMilliSeconds();
			re = client.execute(post);
			code = re.getStatusLine().getStatusCode();
			message = EntityUtils.toString(re.getEntity(), charset);
			long d2 = DateUtil.getCurrentTimeMilliSeconds();
			log.info("httpPost with url={}, code={}, and cost time={}", url, code, (d2 - d1));
		} catch (Exception e) {
			log.error("httpPost error with url={}, param={}", url, param, e);
			throw e;
		} finally {
			if (re != null) {
				EntityUtils.consume(re.getEntity());
				re.close();
			}
			if (client != null) {
				client.close();
			}
		}
		return Pair.of(code, message);
	}
	
	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static Pair<Integer, String> httpGet(String url) throws Exception {
		return httpGet(url, null);
	}
	
	/**
	 * get请求
	 * @param url
	 * @param headList
	 * @return
	 * @throws Exception
	 */
	public static Pair<Integer, String> httpGet(String url, List<Header> headList) throws Exception {
		return httpGet(url, headList, DEFAULT_CHAR_SET, DEFAULT_TIME_OUT);
	}
	
	/**
	 * get请求
	 * @param url
	 * @param headList
	 * @param charset
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static Pair<Integer, String> httpGet(String url, List<Header> headList, String charset, int timeout) throws Exception {
		log.info("httpGet with url={}", url);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse re = null;
		HttpGet get = null;
		int code = 0;
		String message = "";
		try {
			get = new HttpGet(url);
			setConfig(get, headList, timeout);
			long d1 = DateUtil.getCurrentTimeMilliSeconds();
			re = client.execute(get);
			code = re.getStatusLine().getStatusCode();
			message = EntityUtils.toString(re.getEntity(), charset);
			long d2 = DateUtil.getCurrentTimeMilliSeconds();
			log.info("httpGet with url={}, code={}, and cost time={}", url, code, (d2 - d1));
		} catch (Exception e) {
			log.error("httpGet error with url={}", url, e);
			throw e;
		} finally {
			if (re != null) {
				EntityUtils.consume(re.getEntity());
				re.close();
			}
			if (client != null) {
				client.close();
			}
		}
		return Pair.of(code, message);
	}
}

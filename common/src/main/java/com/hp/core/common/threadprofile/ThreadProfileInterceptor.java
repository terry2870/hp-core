package com.hp.core.common.threadprofile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 *  所有的工程都会加载这个
 *
 *  服务的interceptor，用来做慢的服务调用统计
 *
 * Created by chunhua.zhang@yoho.cn on 2016/2/23.
 */
public class ThreadProfileInterceptor implements HandlerInterceptor {


    protected final Logger logger = LoggerFactory.getLogger(getClass());


    //多少毫秒会打印异常日志
    private  int threshold = 200;


    public final static String HTTP_HEADER_SERVICE_NAME = "X-YH-SERVICE-NAME";

    //服务的方法名
    private final static ThreadLocal<String> serviceNameThreadLocal = new ThreadLocal<>();

    //本服务内的方法名称
    private final static ThreadLocal<String> localServiceNameThreadLocal = new ThreadLocal<>();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //设置服务名称
        this.setupServiceName(request);

        //需要记录请求处理时长以及堆栈
        ThreadProfile.start(request.getRequestURI(), this.threshold);
        //ThreadProfile.start(request.getRequestURI(), 100);

        ThreadProfile.enter(this.getClass().getName(),"preHandle");

        return true;
    }


    private void setupServiceName(final HttpServletRequest request){

        String serviceName = request.getHeader(HTTP_HEADER_SERVICE_NAME);

        serviceNameThreadLocal.set(serviceName);

        //local
        localServiceNameThreadLocal.set(request.getRequestURI());
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        /**remove all threadlocal */
        serviceNameThreadLocal.remove();
        localServiceNameThreadLocal.remove();

        ThreadProfile.exit();
        ThreadProfile.stop();
    }


    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }


    /**
     * 获取服务名称
     * @return 服务名称
     */
    public static String  getServiceName(){
        String name= serviceNameThreadLocal.get();
        return StringUtils.isEmpty(name) ? "unknown" : name;
    }

    /**
     * 获取本地服务名称
     * @return 服务名称
     */
    public static String  getLocalServiceName(){
        String name= localServiceNameThreadLocal.get();
        return StringUtils.isEmpty(name) ? "unknown" : name;
    }
}

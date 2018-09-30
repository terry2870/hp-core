package com.hp.core.common.threadprofile;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ThreadProfile {

	/**
	 * 打印调试日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ThreadProfile.class);

	/**
	 * 打印运行日志
	 */
	private static final Logger runLogger = logger; //LoggerFactory.getLogger("Run");

	/**
	 * 调用堆栈信息记录，每次在start和stop时，都进行清理，避免出现引用仍然存在的问题
	 */
	private static final ThreadLocal<StackTrace> stackTrace = new ThreadLocal<StackTrace>();

	/**
	 * 调用响应过长统计，默认情况下，超过300ms响应的请求会被输出到日志中
	 */
	private static int threadshold = 300 ;

	/**
	 * 线程统计开始标记，每次在start和stop时，都进行清理，避免出现引用仍然存在的问题
	 */
	private static final ThreadLocal<Boolean> isStart = new ThreadLocal<Boolean>() ;
	
	/**
	 * 开始统计当前线程的执行延时
	 * @param message
	 */
	public static void start(String message, int threadshold ){

		//runLogger.warn("start:"+System.currentTimeMillis());
		/**
		 * 调用的 response time （线程执行的时间）超过 threadshold时，打印错误日志，并dump堆栈
		 */
		ThreadProfile.threadshold = threadshold ;

		/**
		 * 避免异常情况未清理，造成内存泄露
		 */
		stackTrace.remove();
		//stackTrace.remove();
		/**
		 * 创建当前线程的调用堆栈实例，用于记录关联路径的调用情况，关键路径通过全局的interceptor进行拦截
		 */
		stackTrace.set(new StackTrace(message));

		/**
		 * 开始标记
		 */
		isStart.set(new Boolean(true)) ;
	}
	
	/**
	 * 结束统计，并打印结果, 整个调用的延时
	 */
	public static Pair<String,Long> stop(){

		//runLogger.warn("stop:"+System.currentTimeMillis());

		if( stackTrace.get() == null ){
			return null;
		}

		/**
		 * 记录线程执行结束时间
		 */
		stackTrace.get().end();

		/**
		 * 记录延时，返回监控profile信息的filter
		 */
		long duration = stackTrace.get().duration();

		/**
		 * dump调用堆栈
		 */
		String dumpStack = stackTrace.get().dump() ;
		//手动移除map
		stackTrace.get().clear();
		
		//结束标记
		isStart.remove();
		//清理threadlocal引用
		stackTrace.remove();

		return Pair.of(dumpStack,duration) ;
	}

	/**
	 * 方法进入时记录线程的执行堆栈信息，拦截器调用
	 * @param className
	 * @param methodName
	 */
	public static void enter(String className, String methodName){
		//如果已经标记为开始，并且 stacktrace实例已经创建，则进行统计
		if( ( isStart.get() != null ) && ( stackTrace.get() != null ) ){
			stackTrace.get().enter(new Entry(className, methodName, "enter"));
		}
	}
	
	/**
	 * 方法return时记录线程的执行堆栈信息，拦截器调用
	 */
	public static void exit(){
		//如果已经标记为开始，并且 stacktrace实例已经创建，则进行统计
		if( (isStart.get() != null ) && ( stackTrace.get() != null ) ){
			stackTrace.get().exit();
		}
	}

	public static void asynCall(Future<?> future, String className, String methodName){
		if( ( isStart.get() != null ) && ( stackTrace.get() != null ) ){
			stackTrace.get().asynCall(future, new Entry(className, methodName, "enter"));
		}
	}

	public static void asynReturn(Future<?> future){
		if( (isStart.get() != null ) && ( stackTrace.get() != null ) ){
			stackTrace.get().asynReturn(future);
		}
	}

	
	/**
	 * 当前线程的线程的堆栈信息
	 * @author jipeng
	 *
	 */
	public static final class StackTrace{
		
		private List<Entry> entryList = new ArrayList<Entry>(32);
		Stack<Entry> entryStack = new Stack<>();
		ConcurrentHashMap<Integer,Entry> hashMap = new ConcurrentHashMap<>() ;

		/**
		 * 当前线程的描述信息，timer filter调用时为请求的url
		 */
		private String message ;
		private long beginTime ;
		private long endTime ;

		/**
		 * 当前处于堆栈的层次
		 */
		private int currentStackLevel;

		/**
		 * 线程执行开始
		 * @param message
		 */
		public StackTrace(String message){
			this.beginTime = System.currentTimeMillis();
			this.endTime = beginTime ;
			this.message = message ;
			this.currentStackLevel = 0 ;
		}

		/**
		 * 记录方法的进入
		 * @param entry
		 */
		public void enter(Entry entry){

			entry.level = ++this.currentStackLevel ;
			entryList.add(entry);
			entryStack.push(entry);


		}
		
		public void clear(){
			if(null!=hashMap){
				hashMap.clear();
			}
			if(CollectionUtils.isNotEmpty(entryList)) {
				entryList.clear();
			}
			
		}

		/**
		 * 记录方法的返回
		 */
		public void exit(){
			this.currentStackLevel--;
			Entry entryPop = (Entry)entryStack.pop();
			entryPop.exitTimestamp = System.currentTimeMillis();
		}


		/**
		 * 记录方法的进入
		 * @param entry
		 */
		public void asynCall(Future<?> future, Entry entry){

			entry.level = this.currentStackLevel + 1;
			entryList.add(entry);
			hashMap.put(future.hashCode(), entry);

		}

		/**
		 * 记录方法的返回
		 */
		public void asynReturn(Future<?> future){

			Entry entry = hashMap.get(future.hashCode());
			if(entry!=null){//多线程情况下entry会出现空指针,需要做个不为空判断
				entry.exitTimestamp = System.currentTimeMillis();
			}
		}



		/**
		 * 线程执行结束
		 */
		public void end(){
			endTime = System.currentTimeMillis();
		}

		/**
		 * 线程执行延时
		 * @return
		 */
		public long duration(){
			return endTime - beginTime ;
		}
		
		public String dump(){
			long dur = this.duration();
			if( dur > ThreadProfile.threadshold ){

				StringBuffer sb = new StringBuffer();
				sb.append("Total Delay [").append(duration()).append("ms] ").append(this.message).append("\n");

				for(Entry entry:entryList){
					if(entry == null){
						continue;
					}
					for(int i=0; i<entry.level;i++) {
						sb.append("    ");
					}
					sb.append(entry.toString()).append("\n");
				}

				// 大于300毫秒的，才需要warn
				runLogger.warn("Response time [{}] exceed threadshold [{}], request [{}], stack info:\n[{}]\n\n\n",
						this.duration(), ThreadProfile.threadshold, this.message, sb.toString());
				return sb.toString() ;
			}
			
			/**
			 * [9] Busy(4.7%) thread(24236/0x5eac) stack of java process(11873) under user(test):
		"http-nio-8083-exec-796" #7443 daemon prio=5 os_prio=0 tid=0x00007f93a0040800 nid=0x5eac runnable [0x00007f932573c000]
   		java.lang.Thread.State: RUNNABLE
        at java.util.Arrays.copyOf(Arrays.java:3332)
        at java.lang.AbstractStringBuilder.expandCapacity(AbstractStringBuilder.java:137)
        at java.lang.AbstractStringBuilder.ensureCapacityInternal(AbstractStringBuilder.java:121)
        at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:622)
        at java.lang.StringBuilder.append(StringBuilder.java:202)
        at java.util.AbstractCollection.toString(AbstractCollection.java:465)
        at com.yoho.core.common.monitor.ThreadProfile$StackTrace.dump(ThreadProfile.java:224)
        at com.yoho.core.common.monitor.ThreadProfile.stop(ThreadProfile.java:97)
        at com.yoho.core.common.monitor.ThreadProfileInterceptor.afterCompletion(ThreadProfileInterceptor.java:104)
        at org.springframework.web.servlet.HandlerExecutionChain.triggerAfterCompletion(HandlerExecutionChain.java:170)
			 */
			//logger.debug("Response time [{}], request [{}], stack info\n[{}]",this.duration() ,this.message ,entryList.toString());
			return null;
		}


	}

	public static final class Entry{
		public String className;
		public String method;
		public long enterTimestamp;
		public String event;
		public int level ;
		public long exitTimestamp;

		public Entry(String className,String method,String event){
			this.className = className ;
			this.method = method;
			this.enterTimestamp = System.currentTimeMillis();
			this.exitTimestamp = this.enterTimestamp;
			this.event = event ;
			this.level = 0 ;
		}
		
		@Override
		public String toString(){
			
			StringBuffer sb = new StringBuffer();

			sb.append("+---[").append(this.exitTimestamp-this.enterTimestamp).append("ms] - ")
					.append(className).append(".").append(method)
					.append(" - [enter:").append(this.enterTimestamp).append(",exit:").append(this.exitTimestamp).append("]");

			return sb.toString();
			
		}
	}
	
	 static class DefaultHandler implements RejectedExecutionHandler{
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				logger.warn("threadPoolExecutor invoke find wrong. className is {}", r.getClass().getName());
			}
		}
	    
	    static class WrapExceptionRunnable implements Runnable {

			private static Logger logger = LoggerFactory.getLogger(WrapExceptionRunnable.class);

			private Runnable runnable;

			public WrapExceptionRunnable(Runnable runnable) {
				this.runnable = runnable;
			}
			@Override
			public void run() {
				try {
					runnable.run();
				} catch (Throwable e) {
					// catch any exception, because the scheduled thread will break if the exception thrown to outside.
					logger.error("Unexpected error occurred in task", e);
				}
			}
		}
}

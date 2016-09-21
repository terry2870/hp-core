/**
 * 
 */
package com.hp.core.task.taskjob;

import java.io.Serializable;
import java.util.concurrent.ScheduledFuture;

/**
 * @author ping.huang 2016年6月18日
 */
public class JobBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3836707636086168651L;

	private String jobName;

	// 任务时间表达式
	private String timeExpression;

	// 1-单次执行,2-循环执行
	private int activeMode;
	
	// 任务线程
	private Runnable commond;
	
	/**
	 * @param jobName
	 * @param timeExpression
	 * @param activeMode
	 * @param commond
	 */
	public JobBean(String jobName, String timeExpression, int activeMode, Runnable commond) {
		this.jobName = jobName;
		this.timeExpression = timeExpression;
		this.activeMode = activeMode;
		this.commond = commond;
	}

	/**
	 * 
	 */
	public JobBean() {
	}

	// 定时任务返回对象
	private ScheduledFuture<?> future;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getTimeExpression() {
		return timeExpression;
	}

	public void setTimeExpression(String timeExpression) {
		this.timeExpression = timeExpression;
	}

	public int getActiveMode() {
		return activeMode;
	}

	public void setActiveMode(int activeMode) {
		this.activeMode = activeMode;
	}

	public Runnable getCommond() {
		return commond;
	}

	public void setCommond(Runnable commond) {
		this.commond = commond;
	}

	public ScheduledFuture<?> getFuture() {
		return future;
	}

	public void setFuture(ScheduledFuture<?> future) {
		this.future = future;
	}

	@Override
	public String toString() {
		return "JobBean [jobName=" + jobName + ", timeExpression=" + timeExpression + ", activeMode=" + activeMode + "]";
	}

	
}

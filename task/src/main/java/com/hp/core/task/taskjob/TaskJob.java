/**
 * 
 */
package com.hp.core.task.taskjob;

import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.hp.core.common.utils.DateUtil;
import com.hp.core.task.enums.TaskModeEnum;

/**
 * @author ping.huang
 * 2016年6月18日
 */
public class TaskJob {

	private static Logger log = LoggerFactory.getLogger(TaskJob.class);
	
	private TaskScheduler scheduler;
	
	/**
	 * 添加或修改任务
	 * @param job
	 */
	public void addOrUpdateJob(JobBean job) {
		log.info("addJob with job={}", job);
		if (job == null) {
			log.warn("addJob error. job is null");
			return;
		}
		if (StringUtils.isEmpty(job.getTimeExpression())) {
			log.warn("addJob error. time is empty with job={}", job);
			return;
		}
		JobBean o = TaskJobManager.jobMap.get(job.getJobName());
		if (o != null) {
			// 任务存在，判断是否修改了时间属性
			if (job.getActiveMode() == o.getActiveMode() && job.getTimeExpression().equals(o.getTimeExpression())) {
				//任务时间没有改变，则直接退出
				return;
			}
			//任务修改了时间属性，则停止掉以前的，并重新创建
			cancel(o, false);
		}
		ScheduledFuture<?> future = null;
		if (job.getActiveMode() == TaskModeEnum.SINGLE.getValue()) {
			int runTimeInteger = DateUtil.string2Int(job.getTimeExpression(), "yyyy-MM-dd HH:mm:ss");
			int now = DateUtil.getCurrentTimeSeconds();
			if (now > runTimeInteger) {
				log.warn("addJob error. time is out. with job={}", job);
				return;
			}
			future = scheduler.schedule(job.getCommond(), DateUtil.string2Date(job.getTimeExpression(), DateUtil.DATE_TIME_FORMAT));
		} else if (job.getActiveMode() == TaskModeEnum.LOOP.getValue()) {
			future = scheduler.schedule(job.getCommond(), new CronTrigger(job.getTimeExpression()));
		} else {
			return;
		}
		//任务存入缓存
		job.setFuture(future);
		TaskJobManager.jobMap.put(job.getJobName(), job);
	}
	
	/**
	 * 关闭定时任务
	 * @param job
	 * @param force
	 * @return
	 */
	public boolean cancel(JobBean job, boolean force) {
		return job.getFuture().cancel(force);
	}
	
	public boolean cancel(String jobName, boolean force) {
		JobBean o = TaskJobManager.jobMap.get(jobName);
		if (o == null) {
			return true;
		}
		return cancel(o, force);
	}

	public void setScheduler(TaskScheduler scheduler) {
		this.scheduler = scheduler;
	}
}

package top.lshaci.dt.rmfc.server.task.model;

import java.io.Serializable;
import java.util.Date;

import top.lshaci.dt.rmfc.server.task.enums.TaskStatus;
import top.lshaci.dt.rmfc.server.task.utils.CronUtils;

/**
 * 定时任务模型
 * 
 * @author lshaci
 */
public class ScheduledTaskModel implements Serializable {

	private static final long serialVersionUID = -5080246186845808446L;
	
	private Long id;			// 主键
	private String taskName;	// 任务名称
	private String cron;		// 执行时间表达式
	private Date lastTime;		// 上一次执行时间
	private Date executeTime;	// 执行时间
	private Date nextTime;		// 下次执行时间
	private TaskStatus status;	// 任务状态
	
	public ScheduledTaskModel() { }

	/**
	 * 初始化一个定时任务配置对象
	 * 
	 * @param taskName	任务名称
	 * @param cron		时间表达式
	 */
	public ScheduledTaskModel(String taskName, String cron) {
		super();
		this.taskName = taskName;
		this.cron = cron;
		this.nextTime = CronUtils.getNext(cron);
		this.status = TaskStatus.WAITEXECUTE;
	}
	
	/**
	 * 设置下一次任务信息
	 */
	public void setNext() {
		this.lastTime = this.executeTime;
		this.nextTime =  CronUtils.getNext(this.cron);
		this.status = TaskStatus.WAITEXECUTE;
	}
	
	/**
	 * getter and setter
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	public Date getNextTime() {
		return nextTime;
	}

	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ScheduledTaskModel [id=" + id + ", taskName=" + taskName + ", cron=" + cron + ", lastTime="
				+ lastTime + ", nextTime=" + nextTime + ", status=" + status + "]";
	}
	
}

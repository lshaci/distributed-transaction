package top.lshaci.dt.rmfc.server.task.service;


/**
 * 定时任务模型Service
 * 
 * @author lshaci
 */
public interface ScheduledTaskModelService {
	
	/**
	 * 根据任务名称和时间表达式判断任务是否应该执行
	 * 
	 * @param taskName		任务名称
	 * @param cron			时间表达式
	 * @return	是否执行
	 */
	boolean executeTask(String taskName, String cron);
	
	/**
	 * 将任务修改为待执行
	 * 
	 * @param taskName		任务名称
	 */
	void update2WaitExecute(String taskName);

}

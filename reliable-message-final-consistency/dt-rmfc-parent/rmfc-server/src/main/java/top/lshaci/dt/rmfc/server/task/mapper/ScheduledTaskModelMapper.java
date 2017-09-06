package top.lshaci.dt.rmfc.server.task.mapper;

import top.lshaci.dt.rmfc.server.task.enums.TaskStatus;
import top.lshaci.dt.rmfc.server.task.model.ScheduledTaskModel;

/**
 * 定时任务模型Mapper
 * 
 * @author lshaci
 */
public interface ScheduledTaskModelMapper {

	/**
	 * 建表语句
	 */
	void createTable();
	
	/**
	 * 保存一条定时任务信息
	 * 
	 * @param model		定时任务模型
	 * @return
	 */
	int save(ScheduledTaskModel model);
	
	/**
	 * 根据任务名称修改一条定时任务信息
	 * 
	 * @param model		定时任务模型
	 * @return
	 */
	int updateByTaskName(ScheduledTaskModel model);
	
	/**
	 * 根据任务名称查询一条定时任务信息
	 * 
	 * @param taskName	任务名称
	 * @return
	 */
	ScheduledTaskModel findByTaskName(String taskName);
	
	/**
	 * 根据任务状态进行统计
	 * 
	 * @param status	任务状态
	 * @return	该任务状态的任务条数
	 */
	int countByStatus(TaskStatus status);
	
	/**
	 * 将指定任务状态的任务全部修改为待执行
	 * 
	 * @param status	任务状态
	 */
	void update2WaitExecute(TaskStatus status);
	
}

package top.lshaci.dt.rmfc.server.task.service.impl;

import java.util.Date;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import top.lshaci.dt.rmfc.server.task.enums.TaskStatus;
import top.lshaci.dt.rmfc.server.task.mapper.ScheduledTaskModelMapper;
import top.lshaci.dt.rmfc.server.task.model.ScheduledTaskModel;
import top.lshaci.dt.rmfc.server.task.service.ScheduledTaskModelService;

@Service
@Transactional(
		isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, 
		rollbackFor = Exception.class, transactionManager = "taskTransactionManager"
	)
public class ScheduledTaskModelServiceImpl implements ScheduledTaskModelService {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskModelServiceImpl.class);
	
	@Autowired
	private ScheduledTaskModelMapper mapper;
	
	@PostConstruct
	private void createTable() {
		mapper.createTable();
		logger.debug("Create table successed.");
		init();
	}
	
	/**
	 * 系统启动时，将所有
	 */
	private void init() {
		int i = mapper.countByStatus(TaskStatus.EXECUTING);
		
		if (i > 0) {
			logger.warn("There are {} executing scheduled task!", i);
			
			mapper.update2WaitExecute(TaskStatus.EXECUTING);
			logger.debug("Update the executing task to wait execute successed.");
		}
	}

	@Override
	public boolean executeTask(String taskName, String cron) {
		Objects.requireNonNull(taskName, "Task name must not be null!");
		Objects.requireNonNull(cron, "Cron expression must not be null!");
		
		logger.debug("This scheduled task name is: {}, cron is: {}", taskName, cron);
		
		Date nowTime = new Date();
		
		ScheduledTaskModel model = mapper.findByTaskName(taskName);
		
		try {
			if (model == null) {
				model = new ScheduledTaskModel(taskName, cron);
				mapper.save(model);
				logger.debug("Save this scheduled task({}) successed.", taskName);
			}
		} catch (Exception e) {
			// 此处insert会去获取锁，说明有另一个服务在运行，忽略此处异常，结束方法
			return false;
		}
		
		if (TaskStatus.WAITEXECUTE.equals(model.getStatus())) {
			logger.debug("This scheduled task({}) is wait execute.", taskName);
			
			model.setExecuteTime(nowTime);
			model.setStatus(TaskStatus.EXECUTING);
			mapper.updateByTaskName(model);
			
			logger.debug("Update this scheduled task({}) to executing successed.", taskName);
			
			return true;
		}
		
		logger.warn("This scheduled task({}) is executing!", taskName);
		
		return false;
	}

	@Override
	public void update2WaitExecute(String taskName) {
		Objects.requireNonNull(taskName, "Task name must not be null!");
		
		ScheduledTaskModel model = mapper.findByTaskName(taskName);
		
		Objects.requireNonNull(model, "Scheduled Task Model must not be null!");
		
		model.setNext();
		mapper.updateByTaskName(model);
		
		logger.debug("Update this scheduled task({}) to wait execute successed.", taskName);
	}

}

package top.lshaci.dt.rmfc.server.task.enums;

/**
 * 定时任务执行状态
 * 
 * @author lshaci
 */
public enum TaskStatus {
	
	/**
	 * 待执行
	 */
	WAITEXECUTE		(1, "待执行"),
	/**
	 * 执行中
	 */
	EXECUTING		(2, "执行中"),
	;
	
	private int status;
	private String name;
	
	private TaskStatus(int status, String name) {
		this.status = status;
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public String getName() {
		return name;
	}

	
}

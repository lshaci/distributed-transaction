package top.lshaci.dt.rmfc.common.enums;

/**
 * 消息状态
 * 
 * @author lshaci
 */
public enum MessageStatus {
	/**
	 * 待发送
	 */
	WAITSEND	(1, "待发送"),
	/**
	 * 发送中
	 */
	SENDING		(2, "发送中"),
	/**
	 * 已发送
	 */
	SENT		(3, "已发送"),
	/**
	 * 已死亡
	 */
	DEAD		(4, "已死亡"),
	;
	
	private int status;
	private String name;
	
	private MessageStatus(int status, String name) {
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


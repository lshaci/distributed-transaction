package top.lshaci.dt.rmfc.common.model;


import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.lshaci.validate.annotation.Between;
import com.lshaci.validate.annotation.Json;
import com.lshaci.validate.annotation.Length;
import com.lshaci.validate.annotation.NotNull;

import top.lshaci.dt.rmfc.common.enums.MessageStatus;

/**
 * 持久化消息实体
 * 
 * @author lshaci
 */
public class DtMessage implements Serializable {

	private static final long serialVersionUID = 138880036196152217L;
	
	@Length(max = 36)
	private String id;				// 主键
	@Length(max = 20, require = false)
	private String creator;			// 创建者
	@NotNull
	private Date createTime;		// 创建时间
	@Length(max = 50)
	private String confirmUrl;		// 业务确认地址
	@Length(max = 50)
	private String confirmParam;	// 业务确认参数
	@Json
	private String content;			// 消息内容
	@Length(max = 100)
	private String queueName;		// 队列名称
	private Date sendTime;			// 发送时间
	@Between(min = "3", max = "10")
	private int retries;			// 可重试次数
	private int currentRetry;		// 当前重试次数
	@NotNull
	private MessageStatus status;	// 消息状态(1: 待发送, 2: 发送中, 3: 已发送, 4: 已死亡)
	private Date receivedTime;		// 接收到消息的时间
	@Length(max = 200, require = false)
	private String remark;			// 备注
	
	
	public DtMessage() { }

	/**
	 * Create A Distributed Transaction Message
	 * 
	 * @param confirmUrl	业务确认地址
	 * @param confirmParam	业务确认参数
	 * @param content		消息内容
	 * @param queueName		队列名称
	 */
	public DtMessage(String confirmUrl, String confirmParam, String content, String queueName) {
		init();
		this.confirmUrl = confirmUrl;
		this.confirmParam = confirmParam;
		this.content = content;
		this.queueName = queueName;
	}
	
	/**
	 * Create A Distributed Transaction Message
	 * 
	 * @param creator		消息创建者
	 * @param confirmUrl	业务确认地址
	 * @param confirmParam	业务确认参数
	 * @param content		消息内容
	 * @param queueName		队列名称
	 * @param retries		可重试次数
	 * @param remark		备注
	 */
	public DtMessage(String creator, String confirmUrl, String confirmParam, String content, String queueName,
			int retries, String remark) {
		this(confirmUrl, confirmParam, content, queueName);
		this.creator = creator;
		this.retries = retries;
		this.remark = remark;
	}

	/**
	 * <b>初始化事务消息</b><br><br>
	 * 	id: uuid<br>
	 * 	createTime: new Date()<br>
	 *  currentRetry: 0<br>
	 * 	status: MessageStatus.WAITSEND(1, "待发送")<br>
	 */
	private void init() {
		this.id = UUID.randomUUID().toString();
		this.createTime = new Date();
		this.currentRetry = 0;
		this.status = MessageStatus.WAITSEND;
	}
	
	/**
	 * Add retry times
	 */
	public void addRetry() {
		this.currentRetry++;
	}

	/*
	 * getter and setter
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getConfirmUrl() {
		return confirmUrl;
	}

	public void setConfirmUrl(String confirmUrl) {
		this.confirmUrl = confirmUrl;
	}

	public String getConfirmParam() {
		return confirmParam;
	}

	public void setConfirmParam(String confirmParam) {
		this.confirmParam = confirmParam;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public int getCurrentRetry() {
		return currentRetry;
	}

	public void setCurrentRetry(int currentRetry) {
		this.currentRetry = currentRetry;
	}

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

	public Date getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "DtMessage [id=" + id + ", creator=" + creator + ", createTime=" + createTime + ", confirmUrl="
				+ confirmUrl + ", confirmParam=" + confirmParam + ", content=" + content + ", queueName=" + queueName
				+ ", sendTime=" + sendTime + ", retries=" + retries + ", currentRetry=" + currentRetry + ", status="
				+ status + ", receivedTime=" + receivedTime + ", remark=" + remark + "]";
	}

}


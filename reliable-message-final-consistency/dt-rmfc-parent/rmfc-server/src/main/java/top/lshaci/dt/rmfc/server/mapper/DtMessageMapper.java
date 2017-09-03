package top.lshaci.dt.rmfc.server.mapper;

import java.util.List;

import top.lshaci.dt.rmfc.common.enums.MessageStatus;
import top.lshaci.dt.rmfc.common.model.DtMessage;

/**
 * 事务消息持久化接口
 * 
 * @author lshaci
 */
public interface DtMessageMapper {
	
	/**
	 * 建表语句
	 */
	void createTable();
	
	/**
	 * 保存一条消息
	 * 
	 * @param message	事务消息
	 * @return
	 */
	int save(DtMessage message);
	
	/**
	 * 根据id删除一条消息
	 * 
	 * @param id		消息id
	 * @return
	 */
	int delete(String id);
	
	/**
	 * 根据id修改一条消息
	 * 
	 * @param message	事务消息
	 * @return
	 */
	int update(DtMessage message);
	
	/**
	 * 根据Id查询一条事务消息
	 * 
	 * @param id		消息id
	 * @return
	 */
	DtMessage find(String id);
	
	/**
	 * 根据消息状态查询消息集合
	 * 
	 * @param status	消息状态
	 * @return
	 */
	List<DtMessage> findByStatus(MessageStatus status);

}

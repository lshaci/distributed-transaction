package top.lshaci.dt.rmfc.common.constants;

/**
 * 公共的常量
 * 
 * @author lshaci
 */
public abstract class CommonConstants {
	/**
	 * 可重试次数最小值在配置文件中的key
	 */
	public static final String RETRIES_MIN_KEY = "retries.min";
	/**
	 * 可重试次数最大值在配置文件中的key
	 */
	public static final String RETRIES_MAX_KEY = "retries.max";
	
	/**
	 * 可重试次数默认最小值
	 */
	public static final int RETRIES_MIN_VALUE = 3;
	/**
	 * 可重试次数默认最大值
	 */
	public static final int RETRIES_MAX_VALUE = 10;
}

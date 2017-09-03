package top.lshaci.dt.rmfc.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

// 激活Eureka中的DiscoveryClient实现, 才能实现Controller中对服务信息的输出
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class DtMessageCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(DtMessageCoreApplication.class, args);
		System.out.println("============= Start Spring Cloud DtMessagae Core Success =============");
	}
}

package top.lshaci.dt.rmfc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// 激活Eureka中的DiscoveryClient实现, 才能实现Controller中对服务信息的输出
@EnableDiscoveryClient
@SpringBootApplication
public class DtMessageServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DtMessageServerApplication.class, args);
		System.out.println("============= Start Spring Cloud DtMessagae Server Success =============");
	}
}

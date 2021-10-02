package sunflower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SunflowerGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SunflowerGatewayApplication.class, args);
    }
}

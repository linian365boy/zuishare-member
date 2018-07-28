package top.zuishare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

/**
 * @author niange
 * @ClassName: Application
 * @desp:
 * @date: 2017/11/22 下午10:01
 * @since JDK 1.7
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

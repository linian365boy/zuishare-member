package top.zuishare;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author niange
 * @ClassName: Application
 * @desp:
 * @date: 2017/11/22 下午10:01
 * @since JDK 1.7
 */
@SpringBootApplication
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.warn("Spring Boot use {} profile" , profile);
        }
    }

}

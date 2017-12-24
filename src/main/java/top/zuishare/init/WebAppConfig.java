package top.zuishare.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.zuishare.constants.BussinessConfig;

/**
 * @author niange
 * @ClassName: WebAppConfig
 * @desp:
 * @date: 2017/12/23 下午10:36
 * @since JDK 1.7
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebAppConfig.class);

    @Autowired
    private BussinessConfig bussinessConfig;

    //访问图片方法
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String picPath = bussinessConfig.getPicPath();
        logger.info("upload local images path = {}", picPath);
        registry.addResourceHandler("/upload/**").addResourceLocations(picPath);
        super.addResourceHandlers(registry);
    }
}

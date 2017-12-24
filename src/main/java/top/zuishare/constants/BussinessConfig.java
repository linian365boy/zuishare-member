package top.zuishare.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author niange
 * @ClassName: BussinessConfig
 * @desp:
 * @date: 2017/12/23 下午10:37
 * @since JDK 1.7
 */
@Configuration
@ConfigurationProperties(prefix = "zuishare")
public class BussinessConfig {

    private String picPath;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}

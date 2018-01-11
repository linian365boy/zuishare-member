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
    /**
     * 图片地址
     */
    private String picPath;
    /**
     * 热门文章显示条数
     */
    private int hotLimit;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getHotLimit() {
        return hotLimit;
    }

    public void setHotLimit(int hotLimit) {
        this.hotLimit = hotLimit;
    }
}

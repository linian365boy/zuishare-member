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
    /**
     * 公司的配置文件路径
     */
    private String companyConfigPath;
    /**
     *网站的配置文件路径
     */
    private String webConfigPath;
    /**
     * 首页滚动图片显示数量
     */
    private int indexAds;

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

    public String getCompanyConfigPath() {
        return companyConfigPath;
    }

    public void setCompanyConfigPath(String companyConfigPath) {
        this.companyConfigPath = companyConfigPath;
    }

    public String getWebConfigPath() {
        return webConfigPath;
    }

    public void setWebConfigPath(String webConfigPath) {
        this.webConfigPath = webConfigPath;
    }

    public int getIndexAds() {
        return indexAds;
    }

    public void setIndexAds(int indexAds) {
        this.indexAds = indexAds;
    }
}

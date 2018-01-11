package top.zuishare.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author niange
 * @ClassName: IpUtil
 * @desp: 获取请求ip的工具
 * @date: 2018/1/5 下午11:04
 * @since JDK 1.7
 */
public class IpUtil {

    private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);
    /**
     * 获取请求的IP
     * @param request
     * @return
     * @throws Exception
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            logger.info("the request ip => {}", ip);
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
        // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                logger.info("the request ip => {}, index => {}", ip, index);
                return ip.substring(0, index);
            } else {
                logger.info("the request ip => {}, index => {}", ip, index);
                return ip;
            }
        } else {
            ip = request.getRemoteAddr();
            logger.info("the request ip => {}", ip);
            return ip;
        }
    }
}

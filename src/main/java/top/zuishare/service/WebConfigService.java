package top.zuishare.service;

import top.zuishare.spi.model.WebConfig;

/**
 * @author niange
 * @ClassName: WebConfigService
 * @desp:
 * @date: 2018/5/6 上午11:50
 * @since JDK 1.7
 */
public interface WebConfigService {
    WebConfig loadWebConfig(String path);
}

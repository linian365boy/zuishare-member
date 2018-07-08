package top.zuishare.service;

import top.zuishare.spi.model.Info;

/**
 * @author niange
 * @ClassName: InfoService
 * @desp:
 * @date: 2018/7/8 上午10:31
 * @since JDK 1.7
 */
public interface InfoService {

    Info loadInfo(String code);

}

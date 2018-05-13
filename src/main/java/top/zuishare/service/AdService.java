package top.zuishare.service;

import top.zuishare.spi.model.Advertisement;

import java.util.List;

/**
 * @author niange
 * @ClassName: AdService
 * @desp:
 * @date: 2018/5/6 上午11:46
 * @since JDK 1.7
 */
public interface AdService {
    List<Advertisement> queryIndexAd(int maxLimit);
}

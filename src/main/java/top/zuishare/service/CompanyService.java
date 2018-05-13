package top.zuishare.service;

import top.zuishare.spi.model.Company;

/**
 * @author niange
 * @ClassName: CompanyService
 * @desp:
 * @date: 2018/5/6 上午11:49
 * @since JDK 1.7
 */
public interface CompanyService {
    Company loadCompany(String path);
}

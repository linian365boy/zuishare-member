package top.zuishare.service;

import top.zuishare.spi.model.Column;

import java.util.List;

/**
 * @author niange
 * @ClassName: ColumnService
 * @desp:
 * @date: 2018/5/6 上午11:50
 * @since JDK 1.7
 */
public interface ColumnService {
    List<Column> queryIndexColumn();
    Column getColumnByCode(String code);
}

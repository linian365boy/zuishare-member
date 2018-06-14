package top.zuishare.dao;

import org.apache.ibatis.annotations.Mapper;
import top.zuishare.spi.model.Feedback;

/**
 * @author niange
 * @ClassName: FeedbackDao
 * @desp:
 * @date: 2018/6/7 下午11:06
 * @since JDK 1.7
 */

@Mapper
public interface FeedbackDao {

    void saveFeedback(Feedback feedback);

}

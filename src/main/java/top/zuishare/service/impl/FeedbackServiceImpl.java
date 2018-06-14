package top.zuishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zuishare.dao.FeedbackDao;
import top.zuishare.service.FeedbackService;
import top.zuishare.spi.model.Feedback;

import java.util.Date;

/**
 * @author niange
 * @ClassName: FeedbackServiceImpl
 * @desp:
 * @date: 2018/6/7 下午11:04
 * @since JDK 1.7
 */

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    public void addFeedback(Feedback feedback){
        feedback.setCreateTime(new Date());
        feedbackDao.saveFeedback(feedback);
    }

}

/**
* @Author ln
* @Date 2014年7月16日 上午10:45:22    
* @Version V4.0
* @Company 聚成华企科技有限公司
*/
package top.zuishare.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.zuishare.constants.BussinessConfig;
import top.zuishare.service.MailService;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service("mailService")
public class MailServiceImpl implements MailService {

	private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private BussinessConfig config;

	@Value("${spring.mail.username}")
	private String sender;

	/**
	 *
	 * 发送邮件的具体实现, 目前是异步发送
	 * 
	 */

	@Async
	public void sendHtmlEmails(String subject, String content) {
		this.sendMailByAsynchronousMode(subject, content);
	}

	/**
	 * 异步发送
	 * 
	 */

	public void sendMailByAsynchronousMode(final String subject, final String content) {
		logger.info("start send email, subject:{}", subject);
		if (sendMail(subject, content)) {
			logger.info("success send email, subject:{}", subject);
			return;
		}
		logger.warn("fail send email, subject:{}", subject);
	}


	public boolean sendMail(String subject, String content) {
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, "utf-8");
		try {
			// 设置收件人，寄件人
			InternetAddress[] toAddress = InternetAddress.parse(config.getEmailToUsers());
			mailMessage.setRecipients(Message.RecipientType.TO, toAddress); // 发送给多个账号
			messageHelper.setFrom(sender); // 发件人
			messageHelper.setSubject(subject); // 主题
			// true 表示启动HTML格式的邮件
			messageHelper.setText(content, true); // 邮件内容，注意加参数true，表示启用html格式
			// 发送邮件
			mailSender.send(mailMessage);
			return true;
		} catch (Exception e) {
			logger.error("send email fail...",e);
			return false;
		}
	}

}

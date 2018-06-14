package top.zuishare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import top.zuishare.service.MailService;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


@SpringBootTest
public class MailTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MailService mailService;

    @Test
    public void test() throws Exception{
        mailService.sendHtmlEmails("hehe", "test content<p>2345</p>");
        TimeUnit.SECONDS.sleep(100);
        System.out.println("sleep over...");
    }

}

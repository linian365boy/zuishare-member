package top.zuishare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author niange
 * @ClassName: HomeController
 * @desp:
 * @date: 2017/11/22 下午10:03
 * @since JDK 1.7
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value={"","/","/index","/home"}, method = RequestMethod.GET)
    public String home(ModelMap map){
        logger.info("enter index page.");
        map.put("hello", "hello world");
        return "index";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }


}
